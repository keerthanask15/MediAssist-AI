package util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class GeminiService {

    private static final String API_KEY = System.getenv("GEMINI_API_KEY");

    public static String askGemini(String prompt) {

        try {

            String endpoint =
                    "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key="
                            + API_KEY;

            URL url = new URL(endpoint);

            HttpURLConnection conn =
                    (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");

            conn.setRequestProperty(
                    "Content-Type",
                    "application/json");

            conn.setDoOutput(true);

            String jsonInput =
                    """
                    {
                      "contents": [{
                        "parts": [{
                          "text": "%s"
                        }]
                      }]
                    }
                    """.formatted(
                            prompt.replace("\"", "\\\""));

            try(OutputStream os =
                        conn.getOutputStream())
            {
                os.write(
                        jsonInput.getBytes());
            }

            int responseCode =
                    conn.getResponseCode();

            System.out.println(
                    "HTTP Code : "
                    + responseCode);

            BufferedReader reader;

            if(responseCode == 200)
            {
                reader =
                        new BufferedReader(
                                new InputStreamReader(
                                        conn.getInputStream()));
            }
            else
            {
                reader =
                        new BufferedReader(
                                new InputStreamReader(
                                        conn.getErrorStream()));
            }

            String line;

            StringBuilder response =
                    new StringBuilder();

            while((line = reader.readLine())
                    != null)
            {
                response.append(line);
            }

            reader.close();

            String json =
        response.toString();

int start =
        json.indexOf("\"text\": \"");

if(start != -1)
{
    start += 9;

    int end =
            json.indexOf("\"", start);

    if(end != -1)
    {
        return json.substring(
                start,
                end)
                .replace("\\n", "\n");
    }
}

return json;

        }
        catch(Exception e)
        {
            return "Gemini Error : "
                    + e.getMessage();
        }
    }
}

















































