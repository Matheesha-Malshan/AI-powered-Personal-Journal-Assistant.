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
public class SentimentAnalysisService implements MLFeatureService {

    private final RestClient restClient;

    @Value("${mlServices.api.key}")
    private String apiKey;

    @Value("${mlServices.api.url}")
    private String url;


    private String content= """
           
           You are a sentiment analyser.
           You will receive human daily talks.Your task is to analyse them and return the 
           proper exactly one json object as below structure:
          
           {
            "sentiment":analysing user thoughts and generate one sentiment 
            from[very negative,negative,neutral,positive,very positive]",
            analysing user thoughts generate one sentiment_score according to sentiment
            "sentiment_score":select one of score based on above [0.1,0.3,0.5,0.65,0.75,0.9]
           }
           ex output:
           {
               "sentiment":"positive",
               "sentiment_score":0.75
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


    }

}
