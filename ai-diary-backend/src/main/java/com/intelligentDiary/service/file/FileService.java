package com.intelligentDiary.service.file;

import com.intelligentDiary.factory.entry.ImageEntry;
import com.intelligentDiary.model.response.ImageResponse;

import java.util.List;

public interface FileService {


    String saveImage(ImageEntry imageEntry);
    ImageResponse searchImage(List<String> pathList, ImageResponse response);

}
