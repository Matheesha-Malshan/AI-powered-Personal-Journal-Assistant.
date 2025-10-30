package com.intelligentDiary.service.entry;

import com.intelligentDiary.model.InputEntry;

public interface TextEntryTypeService {
    Object createEntry(InputEntry inputEntry) throws InterruptedException;
    void searchByText(String userId,String query);
}
