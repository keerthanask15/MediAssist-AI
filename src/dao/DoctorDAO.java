package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Doctor;
import util.DBConnection;

public class DoctorDAO {

    public boolean addDoctor(Doctor doctor) {

        String sql = "INSERT INTO doctors(name,specialization,phone,username,password) VALUES(?,?,?,?,?)";

        try(Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql))
        {

            ps.setString(1, doctor.getName());
            ps.setString(2, doctor.getSpecialization());
            ps.setString(3, doctor.getPhone());
            ps.setString(4, doctor.getUsername());
            ps.setString(5, doctor.getPassword());

            return ps.executeUpdate() > 0;

        } catch(Exception e) {

            e.printStackTrace();
        }

        return false;
    }

    public List<Doctor> getAllDoctors() {

        List<Doctor> doctors = new ArrayList<>();

        String sql = "SELECT * FROM doctors";

        try(Connection con = DBConnection.getConnection();

            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery(sql))
        {

            while(rs.next())
            {

                doctors.add(
                        new Doctor(
                                rs.getInt("doctor_id"),
                                rs.getString("name"),
                                rs.getString("specialization"),
                                rs.getString("phone"),
                                rs.getString("username"),
                                rs.getString("password")
                        )
                );
            }

        } catch(Exception e) {

            e.printStackTrace();
        }

        return doctors;
    }
}