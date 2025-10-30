package com.intelligentDiary.validator;

public abstract class ValidationChain implements Validator {

    public ValidationChain nextValidator;

    public Object process(Object object){
        boolean validity=isValid(object);

        if(validity && nextValidator!=null)nextValidator.process(object);

        return object;
    }
    public abstract boolean isValid(Object object);
}
