package com.intelligentDiary.service.ml.mlServiceImpl;


import com.intelligentDiary.service.ml.MLFeatureService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.JsonNode;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class NamedEntityRecognitionService implements MLFeatureService {

    private final RestClient restClient;

    @Value("${mlServices.api.key}")
    private String apiKey;

    @Value("${mlServices.api.url}")
    private String url;

    private String role;
    private String content= """
           
           You are a Named Entity recognition analyser.
           
           You will receive human daily talks.Your task is to analyse them and return the 
           proper exactly one json object as below structure:
           {
            "personNames":the human names,family members such as mother,father inside the user input texts",
            "places":the places names inside the user input texts
           }
           this is the user input: 
           """;

    @Override
    public String featureExtraction(String userMessage) {
        Map<String, Object> requestBody = Map.of(
                "messages", List.of(Map.of(
                                "role", "system",
                        "content", "always respond with exactly one json object.No extra text")
                ,Map.of("role","user","content",content +" "+userMessage)),

                "model", "meta-llama/llama-4-scout-17b-16e-instruct",
                "temperature", 1,
                "max_completion_tokens", 1024,
                "top_p", 1,
                "stream", false
        );

        JsonNode response = restClient.post()
                .uri(url)
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .body(requestBody)
                .retrieve()
                .body(JsonNode.class);

        return response.path("choices").get(0).path("message").path("content").asText();


    }
}
