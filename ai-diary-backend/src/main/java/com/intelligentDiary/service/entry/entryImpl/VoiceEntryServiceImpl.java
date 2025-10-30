package com.intelligentDiary.service.entry.entryImpl;


import com.intelligentDiary.entity.ImageEntryEntity;
import com.intelligentDiary.entity.VoiceEntryEntity;
import com.intelligentDiary.factory.EntryFactory;
import com.intelligentDiary.factory.entry.BasedEntry;
import com.intelligentDiary.factory.entry.VoiceEntry;
import com.intelligentDiary.model.InputEntry;
import com.intelligentDiary.model.InputTypeFormat;
import com.intelligentDiary.model.response.VoiceResponse;
import com.intelligentDiary.repository.VoiceEntryRepository;
import com.intelligentDiary.service.entry.VoiceEntryTypeService;
import com.intelligentDiary.service.file.VoiceFileService;
import com.intelligentDiary.service.ml.MLService;
import com.intelligentDiary.strategy.processing.ProcessStrategy;
import com.intelligentDiary.strategy.processing.ProcessingStrategy;
import com.intelligentDiary.strategy.processing.TextProcessing;
import com.intelligentDiary.strategy.processing.VoiceProcessing;
import com.intelligentDiary.validator.ValidationChain;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;


@RequiredArgsConstructor
@Service
public class VoiceEntryServiceImpl implements VoiceEntryTypeService {

    private final EntryFactory entryFactory;
    private final ProcessStrategy processStrategy;
    private final TextProcessing textProcessing;
    private final ValidationChain chain;
    private final MLService mlService;
    private final ModelMapper mapper;
    private final VoiceEntryRepository voiceEntryRepository;
    private final TransactionTemplate transactionTemplate;
    private final VoiceFileService fileService;


    @Override
    public void createVoiceEntry(InputEntry inputEntry, MultipartFile audio) {

        BasedEntry basedEntry=entryFactory.createEntry(InputTypeFormat.Voice);
        VoiceEntry voiceEntry=(VoiceEntry) basedEntry;

        voiceEntry.setUserId(inputEntry.getUserId());
        voiceEntry.setCreatedAt(inputEntry.getCreatedAt());
        voiceEntry.setDuration(inputEntry.getDuration());
        voiceEntry.setAudioRecord(inputEntry.getAudio());

        ProcessingStrategy voiceStrategy=processStrategy.selectStrategy(InputTypeFormat.Voice);
        VoiceProcessing voiceProcessing=(VoiceProcessing)voiceStrategy;

        VoiceEntry processingTranscript=voiceProcessing.createTranscript(voiceEntry,audio);

        processingTranscript.setContent(processingTranscript.getTranscription());

        BasedEntry validatedObject=(BasedEntry)chain.process(processingTranscript);
        BasedEntry obj;
        try {
            obj=mlService.featureExtractionOfData(validatedObject);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        VoiceEntry voiceEntryObj=(VoiceEntry)obj;

        VoiceEntryEntity voiceEntryEntity=mapper.map(voiceEntryObj,VoiceEntryEntity.class);


        transactionTemplate.executeWithoutResult(transactionStatus ->{
            try {

                VoiceEntryEntity voiceEntryEntity2 =voiceEntryRepository.save(voiceEntryEntity);

                voiceEntryObj.setId(voiceEntryEntity2.getId());

                String url=fileService.saveVoice(voiceEntryObj);
                voiceEntryEntity2.setAudioUrl(url);
                voiceEntryRepository.save(voiceEntryEntity2);

            }
            catch (Exception e) {
                transactionStatus.setRollbackOnly();
                throw  new RuntimeException("transaction failed");
            }

        });

    }
    public VoiceResponse searchVoiceByDate(String userId, LocalDate date){

        return fileService.searchAudio
                (voiceEntryRepository.searchVoiceByDate(userId,date),new VoiceResponse());
    }

    public VoiceResponse searchVoiceBySentimentAnalysis(String userId,String sentiment){

        return fileService.searchAudio(voiceEntryRepository.
                searchVoiceBySentiment(userId,sentiment),new VoiceResponse());
    }

}
