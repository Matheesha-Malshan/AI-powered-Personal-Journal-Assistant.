package com.intelligentDiary.strategy.processing.processingImpl;

import com.intelligentDiary.factory.TextCreation;
import com.intelligentDiary.factory.entry.BasedEntry;
import com.intelligentDiary.factory.entry.TextEntry;
import com.intelligentDiary.model.InputTypeFormat;
import com.intelligentDiary.strategy.processing.ProcessingStrategy;
import com.intelligentDiary.strategy.processing.TextProcessing;
import org.springframework.stereotype.Component;

@Component
public class TextProcessingStrategy implements ProcessingStrategy, TextProcessing {

    InputTypeFormat typeFormat=InputTypeFormat.Text;

    @Override
    public boolean checkStrategy(InputTypeFormat inputTypeFormat){
        return inputTypeFormat==typeFormat;
    }

    @Override
    public Object textProcess(Object object) {
        BasedEntry textEntry=(BasedEntry)object;

        if (textEntry.getContent()!=null){
            System.out.println(textEntry.getContent());
            String content=textEntry.getContent().trim();
            content=content.replaceAll("[^a-zA-Z0-9\\s]", "");
            content=content.replaceAll("<[^>]*>", "");
            textEntry.setProceedContent(content);
            return textEntry;
        }
        return null;

    }


}
