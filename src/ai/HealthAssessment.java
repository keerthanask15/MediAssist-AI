package ai;

import util.GeminiService;

public class HealthAssessment {

    public static String analyze(
            int age,
            String symptoms)
    {

        String prompt =
                """
                You are a healthcare assistant.

                Patient Age: %d

                Symptoms:
                %s

                Analyze and provide:

                1. Possible Conditions
                2. Risk Level (Low/Medium/High)
                3. Recommended Department
                4. Advice

                Keep the response under 100 words.
                """
                .formatted(age, symptoms);

        return GeminiService.askGemini(prompt);
    }
}