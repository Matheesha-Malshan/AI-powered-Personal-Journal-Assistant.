package com.intelligentDiary.service.queary.querySerivece;

import com.intelligentDiary.service.queary.QueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.JsonNode;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class QueryServiceImpl implements QueryService {

    private final RestClient restClient;

    @Value("${mlServices.api.key}")
    private String apiKey;

    @Value("${mlServices.api.url}")
    private String url;

    @Override
    public String searchByText(String query){

        String content= """
                kkkk
                you are a entity analyser, You will receive a user query related to diary or journal search.
                Your task is to extract structured information from the query and return it as valid JSON.
                You will receive a user query related to diary or journal search.
                You must:
                - Identify and extract keywords, person names, and places mentioned in the query.
                - Detect sentiment category (one of: "very negative", "negative", "neutral", "positive", "very positive").
                - Detect and normalize date/time information:
                    - If the query includes expressions like "today", "yesterday", "last week", "this year", etc.,
                      convert them to actual calendar values (day, month, year).
                    - If not specified, return empty strings for `date`, `month`, and `year`.                                       
                
                Return a JSON object with this exact schema:
                
                 {
                   "extractedKeywords": [string],
                   "personNames": [string],
                   "places": [string],
                   "sentiment": "string",
                   "date": "number or empty string (day of month)",
                   "month": "number or empty string (1–12)",
                   "year": "number or empty string (YYYY)"
                 }
                 Example:
                 User query: "When was I happiest this year?"
                
                 Expected JSON:
                 {
                   "extractedKeywords": ["happiest"],
                   "personNames": [],
                   "places": [],
                   "sentiment": "very positive",
                   "date": "",
                   "month": "",
                   "year": 2025
                 }
                 this is your user query : 
                """;
        Map<String, Object> requestBody = Map.of(
                "messages", List.of(Map.of(
                                "role", "system",
                                "content", "always respond with exactly one json object.No extra text")
                        ,Map.of("role","user","content",content +" "+query)),

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
