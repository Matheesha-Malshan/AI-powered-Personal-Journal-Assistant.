package com.intelligentDiary.service.entry.entryImpl;

import com.intelligentDiary.entity.ImageEntryEntity;
import com.intelligentDiary.factory.EntryFactory;

import com.intelligentDiary.factory.entry.ImageEntry;

import com.intelligentDiary.model.InputEntry;
import com.intelligentDiary.model.InputTypeFormat;
import com.intelligentDiary.model.response.ImageResponse;
import com.intelligentDiary.repository.ImageEntryRepository;
import com.intelligentDiary.service.entry.ImageEntryTypeService;
import com.intelligentDiary.service.file.FileService;
import com.intelligentDiary.strategy.processing.ImageProcessing;
import com.intelligentDiary.strategy.processing.ProcessStrategy;
import com.intelligentDiary.strategy.processing.ProcessingStrategy;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import org.springframework.stereotype.Service;

import org.springframework.transaction.support.TransactionTemplate;


import java.time.LocalDate;


@RequiredArgsConstructor
@Service
public class ImageEntryTypeServiceImpl implements ImageEntryTypeService {

    private final ProcessStrategy processStrategy;
    private final ImageProcessing imageProcessing;
    private final EntryFactory entryFactory;
    private final ModelMapper modelMapper;
    private final ImageEntryRepository imageEntryRepository;
    private final TransactionTemplate transactionTemplate;
    private final FileService fileService;


    @Override
    public void extractFeatures(InputEntry inputEntry) {

        ImageEntry imageEntry=(ImageEntry) entryFactory.createEntry(InputTypeFormat.Image);

        ProcessingStrategy imageStrategy=processStrategy.selectStrategy(InputTypeFormat.Image);

        imageEntry.setUserId(inputEntry.getUserId());
        imageEntry.setCreatedAt(inputEntry.getCreatedAt());
        imageEntry.setImage(inputEntry.getImage());



        //imageProcessing.extractFeatureOfImage(imageEntry);

        ImageEntryEntity entryEntity=modelMapper.map(imageEntry,ImageEntryEntity.class);

        System.out.println("image url is"+entryEntity.getImageUrl());

        transactionTemplate.executeWithoutResult(transactionStatus ->{
            try {

                ImageEntryEntity imageEntryEntity1=imageEntryRepository.save(entryEntity);
                imageEntry.setId(entryEntity.getId());
                String url=fileService.saveImage(imageEntry);
                imageEntryEntity1.setImageUrl(url);
                imageEntryRepository.save(imageEntryEntity1);

            }
            catch (Exception e) {
                transactionStatus.setRollbackOnly();
                throw  new RuntimeException("transaction failed");
            }

        });

    }


    public ImageResponse searchImagesByDate(String userId, LocalDate date){

        //System.out.println(imageEntryRepository.findImageUrlByUserIdAndCreatedDate(userId,date));
        ImageResponse imageResponse=new ImageResponse();

        return fileService.searchImage(imageEntryRepository.
                findImageUrlByUserIdAndCreatedDate(userId,date),imageResponse);


    }

    public ImageResponse searchImagesByEvent(String userId,String place){

        ImageResponse imageResponse=new ImageResponse();

        return fileService.searchImage(imageEntryRepository.
                findImageUrlByUserIdAndEvent(userId,place),imageResponse);
    }

}
