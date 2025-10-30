package com.intelligentDiary.service.ml;

import com.intelligentDiary.factory.entry.BasedEntry;
import com.intelligentDiary.factory.entry.TextEntry;

import java.util.stream.Stream;

public interface MLService {
    BasedEntry featureExtractionOfData(BasedEntry userMessage) throws InterruptedException;
}
