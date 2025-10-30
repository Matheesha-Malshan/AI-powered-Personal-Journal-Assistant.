package com.intelligentDiary.service.ml.mlServiceImpl;

import com.intelligentDiary.service.ml.MLFeatureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmbeddingGenerationService {

    private final RestTemplate restTemplate;

    private static final String API_URL =
            "https://router.huggingface.co/hf-inference/models/sentence-transformers/all-MiniLM-L6-v2/pipeline/feature-extraction";
    private static final String HF_API_KEY = "";


    public String featureExtraction(String userMessage) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(HF_API_KEY);

        Map<String, Object> body = new HashMap<>();
        body.put("inputs", userMessage);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                API_URL,
                HttpMethod.POST,
                request,
                String.class
        );

        System.out.println("Embedding response:");
        System.out.println(response.getBody());
        return response.getBody();
    }

}
