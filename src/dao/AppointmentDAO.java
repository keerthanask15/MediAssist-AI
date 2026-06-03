package dao;

import model.Appointment;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDAO {

    public boolean isSlotAvailable(int doctorId,
                                   String date,
                                   String timeSlot)
    {

        String sql =
        "SELECT * FROM appointments WHERE doctor_id=? AND appointment_date=? AND time_slot=?";

        try(Connection con =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql))
        {

            ps.setInt(1, doctorId);
            ps.setString(2, date);
            ps.setString(3, timeSlot);

            ResultSet rs =
                    ps.executeQuery();

            return !rs.next();

        } catch(Exception e) {

            e.printStackTrace();
        }

        return false;
    }

    public boolean bookAppointment(
            Appointment appointment)
    {

        if(!isSlotAvailable(
                appointment.getDoctorId(),
                appointment.getAppointmentDate(),
                appointment.getTimeSlot()))
        {
            System.out.println(
                    "Slot Already Booked!");

            return false;
        }

        String sql =
        "INSERT INTO appointments(patient_id,doctor_id,appointment_date,time_slot,status) VALUES(?,?,?,?,?)";

        try(Connection con =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql))
        {

            ps.setInt(1,
                    appointment.getPatientId());

            ps.setInt(2,
                    appointment.getDoctorId());

            ps.setString(3,
                    appointment.getAppointmentDate());

            ps.setString(4,
                    appointment.getTimeSlot());

            ps.setString(5,
                    appointment.getStatus());

            return ps.executeUpdate() > 0;

        } catch(Exception e) {

            e.printStackTrace();
        }

        return false;
    }

    public List<Appointment> getAllAppointments()
    {

        List<Appointment> appointments =
                new ArrayList<>();

        String sql =
                "SELECT * FROM appointments";

        try(Connection con =
                    DBConnection.getConnection();

            Statement st =
                    con.createStatement();

            ResultSet rs =
                    st.executeQuery(sql))
        {

            while(rs.next())
            {

                appointments.add(
                        new Appointment(
                                rs.getInt("appointment_id"),
                                rs.getInt("patient_id"),
                                rs.getInt("doctor_id"),
                                rs.getString("appointment_date"),
                                rs.getString("time_slot"),
                                rs.getString("status")
                        )
                );
            }

        } catch(Exception e) {

            e.printStackTrace();
        }

        return appointments;
    }

    public boolean updateStatus(
            int appointmentId,
            String status)
    {

        String sql =
        "UPDATE appointments SET status=? WHERE appointment_id=?";

        try(Connection con =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql))
        {

            ps.setString(1,status);
            ps.setInt(2,appointmentId);

            return ps.executeUpdate() > 0;

        } catch(Exception e) {

            e.printStackTrace();
        }

        return false;
    }
}
