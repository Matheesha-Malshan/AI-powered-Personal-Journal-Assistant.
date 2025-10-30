package com.intelligentDiary.strategy.processing;

import com.intelligentDiary.model.InputTypeFormat;

public interface ProcessingStrategy{
    boolean checkStrategy(InputTypeFormat inputTypeFormat);

}
