package com.intelligentDiary.strategy.processing;

import com.intelligentDiary.model.InputTypeFormat;

public interface ProcessStrategy {
    ProcessingStrategy selectStrategy(InputTypeFormat format);
}
