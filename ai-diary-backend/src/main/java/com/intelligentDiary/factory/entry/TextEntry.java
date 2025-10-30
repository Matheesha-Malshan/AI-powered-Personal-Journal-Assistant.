package com.intelligentDiary.factory.entry;


import com.intelligentDiary.factory.TextCreation;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Service;

@ToString
@Getter
@Setter
public class TextEntry extends BasedEntry implements TextCreation {

    String rawContent;

    @Override
    public BasedEntry createObject() {
        return null;
    }


}
