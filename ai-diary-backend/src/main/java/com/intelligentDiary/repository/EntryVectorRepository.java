package com.intelligentDiary.repository;

import com.intelligentDiary.entity.EntryVectorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntryVectorRepository extends JpaRepository<EntryVectorEntity,String> {
}
