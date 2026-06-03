package ai;

import dao.DoctorDAO;
import java.util.List;
import model.Doctor;

public class DoctorRecommendation {

    public static Doctor recommendDoctor(
            String department)
    {

        DoctorDAO doctorDAO =
                new DoctorDAO();

        List<Doctor> doctors =
                doctorDAO.getAllDoctors();

        String dept =
                department.toLowerCase();

        for(Doctor doctor : doctors)
        {

            String specialization =
                    doctor.getSpecialization()
                            .toLowerCase();

            boolean match = false;

            if(dept.contains("cardio")
                    && specialization.contains("cardio"))
            {
                match = true;
            }

            else if(dept.contains("derma")
                    && specialization.contains("derma"))
            {
                match = true;
            }

            else if(dept.contains("neuro")
                    && specialization.contains("neuro"))
            {
                match = true;
            }

            else if(dept.contains("gastro")
                    && specialization.contains("gastro"))
            {
                match = true;
            }

            else if(dept.contains("general")
                    && specialization.contains("general"))
            {
                match = true;
            }

            if(match)
            {
                System.out.println(
                        "\n===== RECOMMENDED DOCTOR =====");

                System.out.println(
                        "Doctor : Dr. "
                        + doctor.getName());

                System.out.println(
                        "Specialization : "
                        + doctor.getSpecialization());

                System.out.println(
                        "Phone : "
                        + doctor.getPhone());

                return doctor;
            }
        }

        System.out.println(
                "\nNo suitable doctor found for "
                        + department);

        return null;
    }
}