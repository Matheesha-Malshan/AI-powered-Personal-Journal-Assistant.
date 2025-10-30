package com.intelligentDiary.factory;

import com.intelligentDiary.factory.entry.BasedEntry;
import com.intelligentDiary.model.InputEntry;
import com.intelligentDiary.model.InputTypeFormat;

public interface EntryFactory {

    BasedEntry createEntry(InputTypeFormat inputEntry);
}
