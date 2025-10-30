package com.intelligentDiary.strategy.processing.processingImpl;

import com.intelligentDiary.factory.entry.ImageEntry;
import com.intelligentDiary.model.InputTypeFormat;
import com.intelligentDiary.strategy.processing.ImageProcessing;
import com.intelligentDiary.strategy.processing.ProcessingStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class ImageProcessingStrategy implements ProcessingStrategy, ImageProcessing {

    private final RestClient restClient;
    private ObjectMapper objectMapper=new ObjectMapper();

    @Value("${mlServices.api.key}")
    private String apiKey;

    @Value("${mlServices.api.url}")
    private String url;


    private String content= """
           
            You are a Named Entity recognition analyser for given images.
    
            Return exactly one JSON object, with this structure:
            {
              "place": "place", 
              "event": "event", 
              "description": "single description text"
            }
            example json output:
            {
              "place": "beach", 
              "event": "play", 
              "description": "some people playing"
            }
            another json output:
             {
              "place": "wedding", 
              "event": "wedding", 
              "description": "marriage"
            }
            
            
            Always wrap all text values in double quotes. Do not include anything else.""";


    @Override
    public boolean checkStrategy(InputTypeFormat inputTypeFormat) {
        return InputTypeFormat.Image==inputTypeFormat;
    }
    public String convertToBase64(ImageEntry imageEntry){
        try {
            String data=Base64.getEncoder().encodeToString(imageEntry.getImage().getBytes());
            return "data:image/jpeg;base64,"+data;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void extractFeatureOfImage(ImageEntry imageEntry) {

        Map<String, Object> requestBody = Map.of(
                "messages", List.of(Map.of(
                        "role", "system",
                        "content", content), Map.of(

                        "role", "user", "content", List.of(
                                Map.of("type", "text",
                                        "text", "always reply with one json object"),
                                Map.of("type", "image_url", "image_url",
                                        Map.of("url", convertToBase64(imageEntry)))
                        )
                )),

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

        String data= response.path("choices").get(0).path("message")
                .path("content").asText();

        System.out.println(data);


        JsonNode node= objectMapper.readTree(data);


        if (node.has("place")){
            imageEntry.setPlaceCapture(node.get("place").asText());
        }
        if (node.has(("event"))){
            imageEntry.setEvent(node.get("event").asText());
        }
        if (node.has("description")){
            imageEntry.setDescription(node.get("description").asText());
        }
    }

    public String extractJsonPart(String data){

        int start=data.indexOf("{");
        int end=data.indexOf("}");

        if (start != -1 && end !=-1 && end>start ){
            return data.substring(start,end+1);
        }
        throw new RuntimeException();


    }


}
