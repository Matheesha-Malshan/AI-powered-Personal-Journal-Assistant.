package com.intelligentDiary.service.entry;

import com.intelligentDiary.factory.entry.ImageEntry;
import com.intelligentDiary.model.InputEntry;
import com.intelligentDiary.model.response.ImageResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface ImageEntryTypeService {
    void extractFeatures(InputEntry inputEntry);
    ImageResponse searchImagesByDate(String userId, LocalDate date);
    ImageResponse searchImagesByEvent(String userId,String place);
}
