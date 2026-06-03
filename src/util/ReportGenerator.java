package util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import model.Patient;

public class ReportGenerator {

    public static void generateReport(
            Patient patient,
            String symptoms,
            String aiResult)
    {

        try {

            File reportsFolder =
        new File("reports");

if(!reportsFolder.exists())
{
    reportsFolder.mkdirs();
}

String fileName =
        "reports/report_"
        + patient.getUsername()
        + ".txt";

            FileWriter writer =
                    new FileWriter(fileName);

            writer.write(
                    "=================================\n");

            writer.write(
                    "MEDIASSIST AI HEALTH REPORT\n");

            writer.write(
                    "=================================\n\n");

            writer.write(
                    "Patient Name : "
                    + patient.getName()
                    + "\n");

            writer.write(
                    "Age : "
                    + patient.getAge()
                    + "\n\n");

            writer.write(
                    "Symptoms :\n"
                    + symptoms
                    + "\n\n");

            writer.write(
                    "AI Assessment :\n"
                    + aiResult
                    + "\n\n");

            writer.write(
                    "Generated On : "
                    + LocalDateTime.now());

            writer.close();

            System.out.println(
                    "\nReport Generated Successfully!");

            System.out.println(
                    "Location : "
                    + fileName);

        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}