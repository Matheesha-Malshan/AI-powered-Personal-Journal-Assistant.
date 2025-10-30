package com.intelligentDiary.factory;

import com.intelligentDiary.builder.EntryBuilder;
import com.intelligentDiary.builder.EntryBuilderImpl;
import com.intelligentDiary.factory.entry.BasedEntry;
import com.intelligentDiary.factory.entry.ImageEntry;
import com.intelligentDiary.factory.entry.TextEntry;
import com.intelligentDiary.factory.entry.VoiceEntry;
import com.intelligentDiary.model.InputEntry;
import com.intelligentDiary.model.InputTypeFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EntryFactoryImpl implements EntryFactory{

    private BasedEntry entry;
    private final EntryBuilder entryBuilder;

    @Override
    public BasedEntry createEntry(InputTypeFormat inputEntry) {

        if (inputEntry == InputTypeFormat.Text) {
            return new TextEntry();

        }
        if (inputEntry == InputTypeFormat.Voice) {
            return new VoiceEntry();

        }
        if (inputEntry == InputTypeFormat.Image) {
            return new ImageEntry();

        }
        else{
            return null;
        }


    }

}
