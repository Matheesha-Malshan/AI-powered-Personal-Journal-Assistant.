package com.intelligentDiary.factory.entry;

import lombok.Data;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Data
public abstract class BasedEntry {

    String userId;
    String status;
    LocalDate createdAt;
    String content;
    String proceedContent;
    List<String> personNames=new ArrayList<>();
    List<String> places=new ArrayList<>();
    String sentiment;
    String meaning;
    Double sentiment_score;
    List<String> extracted_keyword=new ArrayList<>();
    List<Double> embeddings=new ArrayList<>();

    public abstract BasedEntry createObject();
}
