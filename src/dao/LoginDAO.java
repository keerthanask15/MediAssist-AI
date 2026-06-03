package dao;

import java.sql.*;
import model.Doctor;
import model.Patient;
import util.DBConnection;

public class LoginDAO {

    public Patient patientLogin(String username, String password)
    {

        String sql = "SELECT * FROM patients WHERE username=? AND password=?";

        try(Connection con = DBConnection.getConnection();

            PreparedStatement ps =con.prepareStatement(sql))
        {

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs =
                    ps.executeQuery();

            if(rs.next())
            {

                return new Patient(
                        rs.getInt("patient_id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("gender"),
                        rs.getString("phone"),
                        rs.getString("username"),
                        rs.getString("password")
                );
            }

        } catch(Exception e) {

            e.printStackTrace();
        }

        return null;
    }

    public Doctor doctorLogin(String username, String password)
    {

        String sql = "SELECT * FROM doctors WHERE username=? AND password=?";

        try(Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql))
        {

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if(rs.next())
            {

                return new Doctor(
                        rs.getInt("doctor_id"),
                        rs.getString("name"),
                        rs.getString("specialization"),
                        rs.getString("phone"),
                        rs.getString("username"),
                        rs.getString("password")
                );
            }

        } catch(Exception e) {

            e.printStackTrace();
        }

        return null;
    }
}