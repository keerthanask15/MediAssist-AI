package ai;

public class PromptBuilder {

    public static String buildPrompt(
            int age,
            String symptoms)
    {

        return """
                Patient Age:
                %d

                Symptoms:
                %s

                Analyze and provide:
                1. Possible Conditions
                2. Risk Level
                3. Recommended Department
                4. Advice
                """
                .formatted(age, symptoms);
    }
}