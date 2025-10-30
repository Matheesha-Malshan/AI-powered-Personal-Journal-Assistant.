package com.intelligentDiary.strategy.processing;

import com.intelligentDiary.factory.entry.ImageEntry;
import org.springframework.beans.factory.annotation.Value;

public interface ImageProcessing {

    void extractFeatureOfImage(ImageEntry imageEntry);
}
