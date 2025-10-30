package com.intelligentDiary.service.entry.entryImpl;

import com.intelligentDiary.entity.TextEntryEntity;
import com.intelligentDiary.factory.EntryFactory;
import com.intelligentDiary.factory.TextCreation;
import com.intelligentDiary.factory.entry.BasedEntry;
import com.intelligentDiary.factory.entry.TextEntry;
import com.intelligentDiary.model.InputEntry;
import com.intelligentDiary.model.InputTypeFormat;
import com.intelligentDiary.repository.TextEntryRepository;
import com.intelligentDiary.service.entry.TextEntryTypeService;
import com.intelligentDiary.service.ml.MLService;
import com.intelligentDiary.service.queary.QueryService;
import com.intelligentDiary.strategy.processing.ProcessStrategy;
import com.intelligentDiary.strategy.processing.ProcessingStrategy;
import com.intelligentDiary.strategy.processing.TextProcessing;
import com.intelligentDiary.validator.ValidationChain;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;


import java.time.LocalDateTime;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TextEntryServiceImpl implements TextEntryTypeService {

    private final EntryFactory entryFactory;
    private final ValidationChain validationChain;
    private final ProcessStrategy processStrategy;
    private final TextProcessing textProcessing;
    private final ValidationChain chain;
    private final MLService mlService;
    private final TextEntryEntity textEntryEntity;
    private final TextEntryRepository textEntryRepository;
    private final ModelMapper modelMapper;
    private final QueryService queryService;

    private ObjectMapper mapper=new ObjectMapper();

    @Override
    public Object createEntry(InputEntry inputEntry) throws InterruptedException {

        Object object= entryFactory.createEntry(InputTypeFormat.Text);
        TextEntry textEntry=(TextEntry)object;

        TextEntry textEntryObj=modelMapper.map(inputEntry,TextEntry.class);

        ProcessingStrategy textProcessingObj=processStrategy.selectStrategy(InputTypeFormat.Text);
        TextProcessing textProcessing=(TextProcessing)textProcessingObj;

        Object object1=textProcessing.textProcess(textEntryObj);


        BasedEntry validatedObject=(BasedEntry)chain.process(object1);

        BasedEntry obj=mlService.featureExtractionOfData(validatedObject);
        TextEntry entry=(TextEntry)validatedObject;


        saveData(entry);


        return object;
    }
    public TextCreation selectStrategy(TextCreation textEntry){
        ProcessingStrategy selectStrategy=processStrategy.selectStrategy(InputTypeFormat.Text);

        textProcessing.textProcess(textEntry);
        return null;

    }
    public void entryValidation(TextCreation textEntry){
        TextEntry entry=(TextEntry)textEntry;
        chain.process(entry);
    }
    public boolean isValidate(Object object){
        validationChain.isValid(object);
        return false;
    }
    public TextEntry saveData(TextEntry textEntry){

        textEntryEntity.setUserId(textEntry.getUserId());
        textEntryEntity.setCreatedAt(textEntry.getCreatedAt());
        textEntryEntity.setSentiment(textEntry.getSentiment());
        textEntryEntity.setSentiment_score(textEntry.getSentiment_score());
        textEntryEntity.setContent(textEntry.getContent());
        textEntryEntity.setProceedContent(textEntry.getProceedContent());

        textEntryEntity.setPersonNames(mapper.writeValueAsString(textEntry.getPersonNames()));
        textEntryEntity.setPlaces(mapper.writeValueAsString(textEntry.getPlaces()));
        textEntryEntity.setExtracted_keyword(mapper.writeValueAsString(textEntry.getExtracted_keyword()));

        TextEntryEntity textEntry1=textEntryRepository.save(textEntryEntity);

        return null;
    }


    public void searchByText(String userId,String query){

        //String json=queryService.searchByText(query);
        String json="{\n" +
                "  \"extractedKeywords\": [\"happiest\"],\n" +
                "  \"personNames\": [],\n" +
                "  \"places\": [],\n" +
                "  \"sentiment\": \"very positive\",\n" +
                "  \"date\": \"\",\n" +
                "  \"month\": \"\",\n" +
                "  \"year\": 2025\n" +
                "}";


        JsonNode jsonNode=mapper.readTree(json);


        List<String> extractedKeywords=mapper.convertValue(jsonNode.get("extractedKeywords"),
                new TypeReference<List<String>>(){});

        List<String> personNames=mapper.convertValue(jsonNode.get("personNames"),
                new TypeReference<List<String>>(){});

        List<String> places=mapper.convertValue(jsonNode.get("places"),
                new TypeReference<List<String>>(){});

        String sentiment=jsonNode.get("sentiment").asText();

        String year_=jsonNode.get("year").asText();
        String month_=jsonNode.get("month").asText();
        String date_=jsonNode.get("date").asText();

        int year=0;
        int month=0;
        int date=0;

        LocalDateTime DBDate;

        if (year_!=null&& !year_.isEmpty()){
            year=Integer.parseInt(year_);

        }
        if (month_!=null!=month_.isEmpty()){
            month=Integer.parseInt(month_);
        }
        if (date_!=null!= date_.isEmpty()){
            date=Integer.parseInt(date_);
        }

        if (year>0 && month>0 && date>0){
            //user id,date
        }
        if (year>0 && month>0){
            //user id,date
        }

        if (year>0 && month>0 && extractedKeywords.size()>0
                && sentiment!=null && !sentiment.isEmpty()){

            List<TextEntryEntity> entry=textEntryRepository.
                    searchByUserIdAndSentiment(userId,sentiment);

            System.out.println(entry);
        }
        if (year>0 && extractedKeywords.size()>0
                && sentiment!=null && !sentiment.isEmpty()){

            throw new UnsupportedOperationException("Not implemented yet");


        }

        if (year>0 && month>0 && sentiment!=null && !sentiment.isEmpty()){
            throw new UnsupportedOperationException("Not implemented yet");

        }
        if (year>0 && month>0 && personNames.size()>0){
            throw new UnsupportedOperationException("Not implemented yet");

        }
        if (year>0 && personNames.size()>0 && sentiment!=null && !sentiment.isEmpty() ){
            throw new UnsupportedOperationException("Not implemented yet");

        }
        if (year>0 && places.size()>0 && sentiment!=null && !sentiment.isEmpty()){
            throw new UnsupportedOperationException("Not implemented yet");

        }
        if (year>0 && personNames.size()>0){
            throw new UnsupportedOperationException("Not implemented yet");

        }
        if (year>0 && places.size()>0){
            throw new UnsupportedOperationException("Not implemented yet");

        }

    }


}
