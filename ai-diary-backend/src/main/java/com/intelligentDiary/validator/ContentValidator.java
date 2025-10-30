package com.intelligentDiary.validator;


import com.intelligentDiary.factory.entry.TextEntry;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ValidationException;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class ContentValidator extends ValidationChain{

    @Override
    public boolean isValid(Object object) {
        if (object instanceof TextEntry textEntry){
            if (textEntry.getProceedContent()==null||textEntry.
                    getProceedContent().trim().isEmpty()){
                log.error("input cant be empty");
                return false;
            }
            if (textEntry.getProceedContent().length()>200){
                log.error("content is greater than 200");
                return false;
            }
            System.out.println("validation is ok");
            return true;
        }
        log.error("invalid object type");
        return false;
    }

}
