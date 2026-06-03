package main;

import ai.DoctorRecommendation;
import ai.HealthAssessment;
import dao.AppointmentDAO;
import dao.DashboardDAO;
import dao.LoginDAO;
import dao.PatientDAO;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
import model.Appointment;
import model.Doctor;
import model.Patient;
import util.DBConnection;
import util.ReportGenerator;
public class Main {

    static Scanner sc = new Scanner(System.in);
    static LoginDAO loginDAO = new LoginDAO();
    static AppointmentDAO appointmentDAO = new AppointmentDAO();
    static PatientDAO patientDAO = new PatientDAO();
    static DashboardDAO dashboardDAO = new DashboardDAO();

    public static void main(String[] args) {

        while(true) {

            System.out.println("\n==========================");
            System.out.println("      MEDIASSIST AI");
            System.out.println("==========================");
            System.out.println("1. Patient Login");
            System.out.println("2. Patient Registration");
            System.out.println("3. Doctor Login");
            System.out.println("4. Exit");

            System.out.print("Enter Choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch(choice) {

                case 1:
                    patientLogin();
                    break;

                case 2:
                    patientRegistration();
                    break;

                case 3:
                    doctorLogin();
                    break;

                case 4:
                    System.out.println("Thank You!");
                    System.exit(0);

                default:
                    System.out.println("Invalid Choice");
            }
        }
    }

    public static void patientLogin() {

        System.out.print("Username: ");
        String username = sc.nextLine();

        System.out.print("Password: ");
        String password = sc.nextLine();

        Patient patient =
                loginDAO.patientLogin(username,password);

        if(patient != null) {

            System.out.println(
                    "\nWelcome " + patient.getName());

            patientDashboard(patient);

        } else {

            System.out.println("Invalid Credentials");
        }
    }
     
    public static void patientRegistration()
        {
            System.out.println(
                    "\n===== PATIENT REGISTRATION =====");

            System.out.print("Name: ");
            String name = sc.nextLine();

            System.out.print("Age: ");
            int age = sc.nextInt();
            sc.nextLine();

            System.out.print("Gender: ");
            String gender = sc.nextLine();

            System.out.print("Phone: ");
            String phone = sc.nextLine();

            System.out.print("Username: ");
            String username = sc.nextLine();

            System.out.print("Password: ");
            String password = sc.nextLine();

            Patient patient =
                    new Patient(
                            0,
                            name,
                            age,
                            gender,
                            phone,
                            username,
                            password);

            boolean status =
                    patientDAO.addPatient(patient);

            if(status)
            {
                System.out.println(
                        "\nRegistration Successful!");
            }
            else
            {
                System.out.println(
                        "\nRegistration Failed!");
            }
        }




    public static void doctorLogin() {

        System.out.print("Username: ");
        String username = sc.nextLine();

        System.out.print("Password: ");
        String password = sc.nextLine();

        Doctor doctor =
                loginDAO.doctorLogin(username,password);

        if(doctor != null) {

            System.out.println(
                    "\nWelcome Dr. " + doctor.getName());

            doctorDashboard(doctor);

        } else {

            System.out.println("Invalid Credentials");
        }
    }

    public static void patientDashboard(Patient patient)
{
while(true)
{
System.out.println("\n===== PATIENT DASHBOARD =====");
System.out.println("1. Book Appointment");
System.out.println("2. View My Appointments");
System.out.println("3. AI Symptom Analyzer");
System.out.println("4. Logout");

    int choice = sc.nextInt();
    sc.nextLine();

    switch(choice)
    {
        case 1:

            System.out.print("Doctor ID: ");
            int doctorId = sc.nextInt();
            sc.nextLine();

            System.out.print("Date (YYYY-MM-DD): ");
            String date = sc.nextLine();

            System.out.print("Time Slot: ");
            String slot = sc.nextLine();

            Appointment appointment =
                    new Appointment(
                            0,
                            patient.getPatientId(),
                            doctorId,
                            date,
                            slot,
                            "Scheduled");

            boolean booked =
                    appointmentDAO.bookAppointment(
                            appointment);

            if(booked)
            {
                System.out.println(
                        "Appointment Booked!");
            }

            break;

        case 2:

            for(Appointment a :
                    appointmentDAO.getAllAppointments())
            {
                if(a.getPatientId()
                        == patient.getPatientId())
                {
                    System.out.println(
                            a.getAppointmentId()
                            + " | "
                            + a.getDoctorId()
                            + " | "
                            + a.getAppointmentDate()
                            + " | "
                            + a.getTimeSlot()
                            + " | "
                            + a.getStatus());
                }
            }

            break;

        case 3:

            System.out.print(
                    "Enter Symptoms: ");

            String symptoms =
                    sc.nextLine();

            String result =
                    HealthAssessment.analyze(
                            patient.getAge(),
                            symptoms);

            System.out.println(
                    "\n===== AI HEALTH ASSESSMENT =====");

            System.out.println(result);

            System.out.println(
                    "\nGenerate Report?");
            System.out.println("1. Yes");
            System.out.println("2. No");

            int reportChoice =
                    sc.nextInt();

            sc.nextLine();

            if(reportChoice == 1)
            {
                ReportGenerator.generateReport(
                        patient,
                        symptoms,
                        result);
            }

            Doctor recommendedDoctor =
                    null;

            String aiResult =
                    result.toLowerCase();

            if(aiResult.contains("cardio"))
            {
                recommendedDoctor =
                        DoctorRecommendation
                                .recommendDoctor(
                                        "Cardiology");
            }
            else if(aiResult.contains("derma"))
            {
                recommendedDoctor =
                        DoctorRecommendation
                                .recommendDoctor(
                                        "Dermatology");
            }
            else if(aiResult.contains("neuro"))
            {
                recommendedDoctor =
                        DoctorRecommendation
                                .recommendDoctor(
                                        "Neurologist");
            }
            else if(aiResult.contains("gastro"))
            {
                recommendedDoctor =
                        DoctorRecommendation
                                .recommendDoctor(
                                        "Gastroenterology");
            }
            else
            {
                recommendedDoctor =
                        DoctorRecommendation
                                .recommendDoctor(
                                        "General Medicine");
            }

            if(recommendedDoctor != null)
            {
                System.out.println(
                        "\nBook Appointment with Dr. "
                        + recommendedDoctor.getName()
                        + "?");

                System.out.println(
                        "1. Yes");
                System.out.println(
                        "2. No");

                int bookChoice =
                        sc.nextInt();

                sc.nextLine();

                if(bookChoice == 1)
                {
                    System.out.print(
                            "Date (YYYY-MM-DD): ");

                    String appointmentDate =
                            sc.nextLine();

                    System.out.print(
                            "Time Slot: ");

                    String appointmentSlot =
                            sc.nextLine();

                    Appointment autoAppointment =
                            new Appointment(
                                    0,
                                    patient.getPatientId(),
                                    recommendedDoctor.getDoctorId(),
                                    appointmentDate,
                                    appointmentSlot,
                                    "Scheduled");

                    boolean autoBooked =
                            appointmentDAO.bookAppointment(
                                    autoAppointment);

                    if(autoBooked)
                    {
                        System.out.println(
                                "\nAppointment Booked Successfully!");
                    }
                }
            }

            break;

        case 4:
            return;
            
    }
}
    
}


    public static void doctorDashboard(Doctor doctor) {

        while(true) {

            System.out.println("\n===== DOCTOR DASHBOARD =====");
            System.out.println("1. View Patients");
            System.out.println("2. Update Patient");
            System.out.println("3. Delete Patient");
            System.out.println("4. View Appointments");
            System.out.println("5. Update Appointment Status");
            System.out.println("6. Dashboard Statistics");
            System.out.println("7. Logout");

            int choice = sc.nextInt();
            sc.nextLine();

            switch(choice) {

                case 1:

                    for(Patient p : patientDAO.getAllPatients())
                    {

                        System.out.println("\n==============================");

                        System.out.println("Patient ID   : " + p.getPatientId());

                        System.out.println("Name         : " + p.getName());

                        System.out.println("Age          : " + p.getAge());

                        System.out.println("Gender       : " + p.getGender());

                        System.out.println("Phone        : " + p.getPhone());

                        System.out.println("Username     : "+ p.getUsername());

                        System.out.println("==============================");
                    }

                    break;
                case 2:

                System.out.print("Patient ID: ");
                int updateId = sc.nextInt();
                sc.nextLine();

                System.out.print("New Name: ");
                String newName = sc.nextLine();

                System.out.print("New Age: ");
                int newAge = sc.nextInt();
                sc.nextLine();

                System.out.print("New Gender: ");
                String newGender = sc.nextLine();

                System.out.print("New Phone: ");
                String newPhone = sc.nextLine();

                Patient updatedPatient = new Patient(
                                updateId,
                                newName,
                                newAge,
                                newGender,
                                newPhone,
                                "",
                                ""
                        );

                boolean updatedPatientStatus = patientDAO.updatePatient(updatedPatient);

                if(updatedPatientStatus)
                {
                        System.out.println("Patient Updated Successfully");
                }
                else
                {
                        System.out.println(  "Update Failed");
                }

                break;

                case 3:

                        System.out.print( "Patient ID to Delete: ");

                        int deleteId = sc.nextInt();

                        sc.nextLine();

                        boolean deleted = patientDAO.deletePatient(deleteId);

                        if(deleted)
                        {
                                System.out.println("Patient Deleted Successfully");
                        }
                        else
                        {
                                System.out.println("Delete Failed");
                        }

                        break;

                case 4:

                    String sql =
                            "SELECT a.appointment_id, " +
                            "p.name AS patient_name, " +
                            "d.name AS doctor_name, " +
                            "a.appointment_date, " +
                            "a.time_slot, " +
                            "a.status " +
                            "FROM appointments a " +
                            "JOIN patients p ON a.patient_id = p.patient_id " +
                            "JOIN doctors d ON a.doctor_id = d.doctor_id";

                    try (
                            Connection con = DBConnection.getConnection();
                            Statement st = con.createStatement();
                            ResultSet rs = st.executeQuery(sql)
                    ) {

                        while (rs.next()) {

                            System.out.println("\n==============================");

                            System.out.println("Appointment ID : "
                                    + rs.getInt("appointment_id"));

                            System.out.println("Patient Name   : "
                                    + rs.getString("patient_name"));

                            System.out.println("Doctor Name    : "
                                    + rs.getString("doctor_name"));

                            System.out.println("Date           : "
                                    + rs.getString("appointment_date"));

                            System.out.println("Time Slot      : "
                                    + rs.getString("time_slot"));

                            System.out.println("Status         : "
                                    + rs.getString("status"));

                            System.out.println("==============================");
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;
                case 5:

                System.out.print("Appointment ID: ");

                int appointmentId = sc.nextInt();

                sc.nextLine();

                System.out.print("New Status: ");

                String status = sc.nextLine();

                boolean updated = appointmentDAO.updateStatus(appointmentId, status);

                if(updated)
                {
                    System.out.println(
                            "Status Updated");
                }
                else
                {
                    System.out.println(
                            "Update Failed");
                }

                break;

                case 6:

                    System.out.println("\n===== HOSPITAL STATISTICS =====");

                    System.out.println("Total Patients      : " + dashboardDAO.getPatientCount());

                    System.out.println("Total Doctors       : " + dashboardDAO.getDoctorCount());

                    System.out.println("Total Appointments  : " + dashboardDAO.getAppointmentCount());

                    System.out.println("Completed           : " + dashboardDAO.getCompletedCount());

                    System.out.println("Scheduled           : " + dashboardDAO.getScheduledCount());

                    break;

                case 7:
                    return;
            }
        }
    }
}
