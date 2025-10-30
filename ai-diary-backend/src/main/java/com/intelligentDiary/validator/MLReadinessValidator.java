package com.intelligentDiary.validator;

import org.springframework.stereotype.Component;

@Component
public class MLReadinessValidator extends ValidationChain{
    @Override
    public boolean isValid(Object object) {
        return false;
    }
}
