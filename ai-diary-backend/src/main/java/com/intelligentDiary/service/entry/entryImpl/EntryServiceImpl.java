package com.intelligentDiary.service.entry.entryImpl;

import com.intelligentDiary.model.InputEntry;
import com.intelligentDiary.service.entry.EntryService;
import com.intelligentDiary.service.entry.TextEntryTypeService;
import com.intelligentDiary.service.entry.VoiceEntryTypeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EntryServiceImpl implements EntryService {

    private final TextEntryTypeService entryTypeService;
    private final VoiceEntryTypeService voiceEntryTypeService;

    @Override
    public void createEntry(InputEntry inputEntry) throws InterruptedException {

        Object entry=entryTypeService.createEntry(inputEntry);

    }



}
