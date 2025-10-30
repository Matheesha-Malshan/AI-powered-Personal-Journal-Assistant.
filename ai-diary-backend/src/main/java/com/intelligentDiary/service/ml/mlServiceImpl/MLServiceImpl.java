package com.intelligentDiary.service.ml.mlServiceImpl;

import ch.qos.logback.core.joran.conditional.ThenAction;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intelligentDiary.factory.entry.BasedEntry;
import com.intelligentDiary.factory.entry.TextEntry;
import com.intelligentDiary.service.ml.MLService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;



@RequiredArgsConstructor
@Service
public class MLServiceImpl implements MLService {

    private final EmbeddingGenerationService embeddingGenerationService;
    private final NamedEntityRecognitionService namedEntityRecognitionService;
    private final SentimentAnalysisService sentimentAnalysisService;
    private final TopicExtractionService topicExtractionService;
    private  ObjectMapper mapper=new ObjectMapper();

    List<Thread> list=new ArrayList<>();

    @Override
    public BasedEntry featureExtractionOfData(BasedEntry userMessage) {

        Thread thread1 = Thread.ofVirtual().start(()->executes1(userMessage));
        Thread thread2 = Thread.ofVirtual().start(()->executes2(userMessage));
        Thread thread3 = Thread.ofVirtual().start(()->executes3(userMessage));
        //Thread thread4 = Thread.ofVirtual().start(()->executes4(userMessage));
        try {

              thread1.join();
              thread2.join();
              thread3.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread interrupted", e);
        }

      //

        return userMessage;

    }
    public void executes4(TextEntry userMessage){
        try {
            Thread.sleep(10000);
            System.out.println("request arrived");

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }



    public void executes1(BasedEntry userMessage)  {


        String data=namedEntityRecognitionService.featureExtraction(userMessage.getProceedContent());
        try {
            JsonNode node = mapper.readTree(data);
            node.get("personNames").forEach(keywordNode ->
                    userMessage.getPersonNames().add(keywordNode.asText()));
            node.get("places").forEach(keywordNode ->
                    userMessage.getPlaces().add(keywordNode.asText()));

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


    }
    public void executes2(BasedEntry userMessage){

        String data =sentimentAnalysisService.featureExtraction(userMessage.getProceedContent());
        System.out.println(data);
        try {
            JsonNode node = mapper.readTree(data);
            userMessage.setSentiment(node.get("sentiment").asText());
            userMessage.setSentiment_score(node.get("sentiment_score").asDouble());

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
    public void executes3(BasedEntry userMessage){


        String data=topicExtractionService.featureExtraction(userMessage.getProceedContent());
        System.out.println(data);
        try {
            JsonNode node = mapper.readTree(data);
            node.get("extracted_keyword").forEach(keywordNode ->
                    userMessage.getExtracted_keyword().add(keywordNode.asText()));

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


    }

}


