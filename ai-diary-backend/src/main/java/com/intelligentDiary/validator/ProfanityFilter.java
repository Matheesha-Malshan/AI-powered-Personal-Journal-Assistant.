package com.intelligentDiary.validator;

import com.intelligentDiary.factory.entry.BasedEntry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
public class ProfanityFilter extends ValidationChain{
    @Override
    public boolean isValid(Object object) {
        BasedEntry basedEntry=(BasedEntry) object;
        return textContainsNumber(basedEntry);

    }
    public boolean textContainsNumber(BasedEntry object){

        Pattern pattern=Pattern.compile("//d");
        Matcher matcher=pattern.matcher(object.getProceedContent());
        if (matcher.find()){
            log.error("contain number on your text data");
            return false;
        }
        return lengthOfWords(object);
    }
    public boolean lengthOfWords(BasedEntry object){
        String[] strings=object.getProceedContent().split("");
        for (String letter:strings){
            if (letter.length()>50){

                log.error("your text one word count is greater than 50");
                return false;
            }
        }

        return true;
    }
}
