package com.intelligentDiary.controller;


import com.intelligentDiary.factory.entry.VoiceEntry;
import com.intelligentDiary.model.InputEntry;
import com.intelligentDiary.model.response.VoiceResponse;
import com.intelligentDiary.service.entry.VoiceEntryTypeService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;


@RestController
@RequiredArgsConstructor
@RequestMapping("/voice")
public class VoiceController {

    private final VoiceEntryTypeService voiceEntryTypeService;


    @PostMapping("/save-voice")
    public void captureVoice(@ModelAttribute InputEntry inputEntry){
        voiceEntryTypeService.createVoiceEntry(inputEntry,inputEntry.getAudio());

    }

    @PostMapping("/search-voice-by-Date/{userId}/{date}/")
    public VoiceResponse searchVoiceByDate(@PathVariable String userId, @PathVariable LocalDate date){
        return voiceEntryTypeService.searchVoiceByDate(userId,date);
    }

    @PostMapping("/search-voice-by-sentiment/{userId}/{sentiment}/")
    public VoiceResponse searchVoiceBySentiment(@PathVariable String userId,
                                                @PathVariable String sentiment){

        return voiceEntryTypeService.searchVoiceBySentimentAnalysis(userId,sentiment);

    }


}
