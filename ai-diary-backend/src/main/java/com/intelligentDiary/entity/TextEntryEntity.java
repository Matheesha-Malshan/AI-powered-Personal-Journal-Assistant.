package com.intelligentDiary.entity;



import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TextEntryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int entryId;

    @Column(nullable = false)
    private String userId;

    private String status;
    private LocalDate createdAt;
    private String content;
    private String proceedContent;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private String personNames;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private String places;

    private String sentiment;

    private Double sentiment_score;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private String extracted_keyword;

    private String rawContent;

    @OneToOne(mappedBy = "entry",cascade = CascadeType.ALL,orphanRemoval = true)
    private EntryVectorEntity entryVector;


}
