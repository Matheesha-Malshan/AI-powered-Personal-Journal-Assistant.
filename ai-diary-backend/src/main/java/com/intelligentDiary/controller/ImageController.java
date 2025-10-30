package com.intelligentDiary.controller;


import com.intelligentDiary.model.InputEntry;
import com.intelligentDiary.model.response.ImageResponse;
import com.intelligentDiary.service.entry.ImageEntryTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
@RequestMapping("/image")
public class ImageController {

    private final ImageEntryTypeService entryTypeService;

    @PostMapping("/save-image")
    public void captureImages(@ModelAttribute InputEntry imageEntry){
       entryTypeService.extractFeatures(imageEntry);
    }

    @GetMapping("/search-image-by-date/{userId}/{date}/")
    public ImageResponse searchImagesByDate(@PathVariable String userId, @PathVariable LocalDate date){
       return entryTypeService.searchImagesByDate(userId,date);
    }

    @GetMapping("/search-image-by-event/{userId}/{place}/")
    public ImageResponse searchImagesByEvent(@PathVariable String userId,String place){
        return entryTypeService.searchImagesByEvent(userId,place);
    }


}
