package com.intelligentDiary.repository;

import com.intelligentDiary.entity.TextEntryEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface TextEntryRepository extends JpaRepository<TextEntryEntity,String> {


    @Query(value = """
    SELECT *
    FROM text_entry_entity
    WHERE user_id = :userId
      AND sentiment = :sentiment
      AND EXTRACT(YEAR FROM created_at) = :year
      AND (
          extracted_keyword @> CAST(:keyword AS jsonb)
          OR extracted_keyword IS NULL
          OR jsonb_array_length(extracted_keyword) = 0
      )
    ORDER BY created_at DESC
    """, nativeQuery = true)
    List<TextEntryEntity> searchBySentimentYear(
            @Param("userId") String userId,
            @Param("sentiment") String sentiment,
            @Param("keyword") String keyword,
            @Param("year") int year);


    @Query(value = """
        SELECT *
        FROM text_entry_entity
        WHERE user_id = :userId
          AND created_at = :date
        ORDER BY created_at DESC
        """, nativeQuery = true)
    List<TextEntryEntity> findByUserAndDate(
            @Param("userId") String userId,
            @Param("date") LocalDate date);

    @Query(value = """
        SELECT *
        FROM text_entry_entity
        WHERE user_id = :userId
          AND EXTRACT(YEAR FROM created_at) = :year
          AND EXTRACT(MONTH FROM created_at) = :month
        ORDER BY created_at DESC
        """, nativeQuery = true)
    List<TextEntryEntity> findByUserAndMonth(
            @Param("userId") String userId,
            @Param("year") int year,
            @Param("month") int month);













    @Query(value = "SELECT * FROM text_entry_entity WHERE user_id = :userId AND sentiment = :sentiment",
            nativeQuery = true)
    List<TextEntryEntity> searchByUserIdAndSentiment(@Param("userId") String userId,
                                                     @Param("sentiment") String sentiment);


    @Query(value = """
                        SELECT *
                        FROM text_entry_entity
                WHERE user_id = 'user_187389'
                AND sentiment = 'very positive'
                AND EXTRACT(YEAR FROM created_at) = 2025
                AND EXTRACT(MONTH FROM created_at) = 10
                AND (
                        extracted_keywords ? 'happiest'
                                OR extracted_keywords IS NULL
                                OR jsonb_array_length(extracted_keywords) = 0
                        )
                ORDER BY created_at DESC;
            """
            , nativeQuery = true)
    List<TextEntryEntity> searchBySentimentYearMonth(@Param("userId") String userId,
                                                     @Param("sentiment") String sentiment,
                                                     @Param("keyword")String keyword,
                                                     @Param("year")int year,
                                                     @Param("month")int month);




}
