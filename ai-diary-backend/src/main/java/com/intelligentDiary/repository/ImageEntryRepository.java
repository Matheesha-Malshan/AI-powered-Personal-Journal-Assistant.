package com.intelligentDiary.repository;

import com.intelligentDiary.entity.ImageEntryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ImageEntryRepository extends JpaRepository<ImageEntryEntity,Integer> {

    @Query("SELECT i.imageUrl FROM ImageEntryEntity i WHERE i.userId = :" +
            "userId AND DATE(i.createdAt) = :createdDate")
    List<String> findImageUrlByUserIdAndCreatedDate(@Param("userId") String userId,
                                            @Param("createdDate") LocalDate createdDate);

    @Query("SELECT i.imageUrl FROM ImageEntryEntity i WHERE i.userId = :" +
            "userId AND i.placeCapture = :placeCapture")
    List<String> findImageUrlByUserIdAndEvent(@Param("userId") String userId,
                                                    @Param("placeCapture") String placeCapture);


}
