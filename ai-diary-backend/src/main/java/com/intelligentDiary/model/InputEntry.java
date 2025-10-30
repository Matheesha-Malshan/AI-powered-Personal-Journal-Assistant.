package com.intelligentDiary.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class InputEntry {

    private String userId;
    private String content;
    private InputTypeFormat type;
    private LocalDate createdAt;
    private String status;
    private MultipartFile image;
    private MultipartFile audio;
    private float duration;
}
