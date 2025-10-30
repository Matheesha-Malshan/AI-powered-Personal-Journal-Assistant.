package com.intelligentDiary.service.entry;

import com.intelligentDiary.model.InputEntry;

public interface EntryService {

    void createEntry(InputEntry inputEntry) throws InterruptedException;

}
