package com.intelligentDiary.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="entry_vectors")
public class EntryVectorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String Id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId",referencedColumnName ="userId")
    private TextEntryEntity entry;

    @Column(columnDefinition = "vector(384)")
    private float[] embeddings;

}
