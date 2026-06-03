package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Patient;
import util.DBConnection;

public class PatientDAO {

    public boolean addPatient(Patient patient) {

        String sql =
                "INSERT INTO patients(name,age,gender,phone,username,password) VALUES(?,?,?,?,?,?)";

        try(Connection con =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql))
        {

            ps.setString(1, patient.getName());
            ps.setInt(2, patient.getAge());
            ps.setString(3, patient.getGender());
            ps.setString(4, patient.getPhone());
            ps.setString(5, patient.getUsername());
            ps.setString(6, patient.getPassword());

            return ps.executeUpdate() > 0;

        } catch(Exception e) {

            e.printStackTrace();
        }

        return false;
    }

    public List<Patient> getAllPatients() {

        List<Patient> patients =
                new ArrayList<>();

        String sql =
                "SELECT * FROM patients";

        try(Connection con =
                    DBConnection.getConnection();

            Statement st =
                    con.createStatement();

            ResultSet rs =
                    st.executeQuery(sql))
        {

            while(rs.next())
            {
                patients.add(
                        new Patient(
                                rs.getInt("patient_id"),
                                rs.getString("name"),
                                rs.getInt("age"),
                                rs.getString("gender"),
                                rs.getString("phone"),
                                rs.getString("username"),
                                rs.getString("password")
                        )
                );
            }

        } catch(Exception e) {

            e.printStackTrace();
        }

        return patients;
    }


    public boolean updatePatient(Patient patient)
{
    String sql =
            "UPDATE patients SET name=?, age=?, gender=?, phone=? WHERE patient_id=?";

    try(Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql))
    {

        ps.setString(1, patient.getName());
        ps.setInt(2, patient.getAge());
        ps.setString(3, patient.getGender());
        ps.setString(4, patient.getPhone());
        ps.setInt(5, patient.getPatientId());

        return ps.executeUpdate() > 0;
    }
    catch(Exception e)
    {
        e.printStackTrace();
    }

    return false;
}

public boolean deletePatient(int patientId)
{
    String sql =
            "DELETE FROM patients WHERE patient_id=?";

    try(Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql))
    {

        ps.setInt(1, patientId);

        return ps.executeUpdate() > 0;
    }
    catch(Exception e)
    {
        e.printStackTrace();
    }

    return false;
}
}