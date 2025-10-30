package com.intelligentDiary.factory.entry;

import lombok.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ImageEntry  extends BasedEntry{

    private Integer id;
    private String userId;
    private String event;
    private String placeCapture;
    private String description;
    private MultipartFile image;
    private String imageUrl;


    @Override
    public BasedEntry createObject() {
        return null;
    }
}
