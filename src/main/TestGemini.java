package main;

import util.GeminiService;

public class TestGemini {

    public static void main(String[] args) {

        String response =
                GeminiService.askGemini(
                        "What is Java? Answer in one sentence.");

        System.out.println(
                "\nRESPONSE:\n");

        System.out.println(response);
    }
}