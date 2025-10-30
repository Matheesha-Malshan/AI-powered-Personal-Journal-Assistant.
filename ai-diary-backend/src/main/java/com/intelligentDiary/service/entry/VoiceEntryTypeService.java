package com.intelligentDiary.service.entry;

import com.intelligentDiary.factory.entry.VoiceEntry;
import com.intelligentDiary.model.InputEntry;
import com.intelligentDiary.model.response.VoiceResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDate;

public interface VoiceEntryTypeService {

    void createVoiceEntry(InputEntry inputEntry, MultipartFile audio);
    VoiceResponse searchVoiceByDate(String userId, LocalDate date);
    VoiceResponse searchVoiceBySentimentAnalysis(String userId,String sentiment);

}
