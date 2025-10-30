package com.intelligentDiary.service.file;

import com.intelligentDiary.factory.entry.VoiceEntry;
import com.intelligentDiary.model.response.ImageResponse;
import com.intelligentDiary.model.response.VoiceResponse;

import java.util.List;

public interface VoiceFileService {

    String saveVoice(VoiceEntry voiceEntry);

    VoiceResponse searchAudio(List<String> pathList, VoiceResponse response);
}
