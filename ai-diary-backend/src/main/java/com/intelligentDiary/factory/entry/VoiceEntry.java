package com.intelligentDiary.factory.entry;

import lombok.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@AllArgsConstructor
@Service
@Getter
@Setter
@ToString
public class VoiceEntry extends BasedEntry {

    private Integer id;
    private float duration;
    private String format;
    private String transcription;
    private String audioUrl;
    private MultipartFile audioRecord;



    @Override
    public BasedEntry createObject() {
        return null;
    }
}
