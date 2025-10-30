package com.intelligentDiary.repository;

import com.intelligentDiary.entity.VoiceEntryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.util.List;

public interface VoiceEntryRepository extends JpaRepository<VoiceEntryEntity,Integer> {

    @Query("SELECT i.audioUrl FROM VoiceEntryEntity i WHERE i.userId = :" +
            "userId AND DATE(i.createdAt) = :createdAt")
    List<String>  searchVoiceByDate(@Param("userId") String userId,
                                          @Param("createdAt") LocalDate createdAt);


    @Query("SELECT i.audioUrl FROM VoiceEntryEntity i WHERE i.userId = :" +
            "userId AND i.sentiment = :sentiment")
    List<String> searchVoiceBySentiment(@Param("userId") String userId,@Param("sentiment")String sentiment);
}
