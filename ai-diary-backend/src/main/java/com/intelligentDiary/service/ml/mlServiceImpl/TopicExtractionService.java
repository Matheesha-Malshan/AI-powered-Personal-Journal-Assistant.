package com.intelligentDiary.service.ml.mlServiceImpl;

import com.intelligentDiary.service.ml.MLFeatureService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.JsonNode;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TopicExtractionService implements MLFeatureService {


    private final RestClient restClient;

    @Value("${mlServices.api.key}")
    private String apiKey;

    @Value("${mlServices.api.url}")
    private String url;

    private String role;
    private String content= """
           
           You are a topic extraction analyser.
           
           You will receive human daily talks.Your task is to analyse them and return the 
           proper exactly one json object as below structure:
           {
            "extracted_keyword":"analyse the user thoughts and what are topics user taking about
            as example ["work", "promotion", "career","life"] as well as there can be more than one topic
            at least there should be exactly one topic"
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

                "model", "llama-3.3-70b-versatile",
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
        //System.out.println(reply);

    }
}
