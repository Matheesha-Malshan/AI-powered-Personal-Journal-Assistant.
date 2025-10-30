package com.intelligentDiary.strategy.processing.processingImpl;

import com.intelligentDiary.factory.entry.VoiceEntry;
import com.intelligentDiary.model.InputEntry;
import com.intelligentDiary.model.InputTypeFormat;
import com.intelligentDiary.strategy.processing.ProcessingStrategy;
import com.intelligentDiary.strategy.processing.VoiceProcessing;
import lombok.RequiredArgsConstructor;
import org.hibernate.sql.ast.tree.expression.JsonObjectAggUniqueKeysBehavior;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.Map;


@RequiredArgsConstructor
@Component
public class VoiceProcessingStrategy implements ProcessingStrategy, VoiceProcessing {

    private final RestClient restClient;

    @Value("${mlServices.api.key}")
    private String apiKey;

    @Value("${mlServices.api.urlc}")
    private String url;

    @Override
    public boolean checkStrategy(InputTypeFormat inputTypeFormat) {
        return inputTypeFormat==InputTypeFormat.Voice;
    }


    @Override
    public VoiceEntry createTranscript(VoiceEntry voiceEntry, MultipartFile audioFile) {

        ByteArrayResource arrayResource;

        try {
            byte[] audioByte=audioFile.getBytes();
            arrayResource=new ByteArrayResource(audioByte){

                @Override
                public String getFilename() {
                    return audioFile.getOriginalFilename();
                }
            };

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        MultiValueMap<String,Object> multiValueMap=new LinkedMultiValueMap<>();

        multiValueMap.add("file",arrayResource);
        multiValueMap.add("model","whisper-large-v3");
        multiValueMap.add("response_format","verbose_json");



        JsonNode response = restClient.post()
                .uri(url)
                .header("Authorization", "Bearer " + apiKey)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(multiValueMap)
                .retrieve()
                .body(JsonNode.class);

        String transcription = response.path("text").asText(); // adjust if using verbose_json

        voiceEntry.setTranscription(transcription);

        return  voiceEntry;

    }
}
/*
{
  "model": "whisper-large-v3",
  "temperature": 0,
  "response_format": "verbose_json",
  "file": "audio.m4a"
}
 */