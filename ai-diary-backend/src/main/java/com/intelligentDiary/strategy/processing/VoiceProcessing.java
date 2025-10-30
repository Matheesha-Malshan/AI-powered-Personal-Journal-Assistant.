package com.intelligentDiary.strategy.processing;

import com.intelligentDiary.factory.entry.VoiceEntry;
import com.intelligentDiary.model.InputEntry;
import org.springframework.web.multipart.MultipartFile;

public interface VoiceProcessing {

    VoiceEntry  createTranscript(VoiceEntry voiceEntry, MultipartFile audioFile);
}
