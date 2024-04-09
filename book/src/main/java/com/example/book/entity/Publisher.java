package com.example.book.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString(exclude = "books")
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Publisher extends BaseEntity {
    @SequenceGenerator(name = "publisher_seq_gen", sequenceName = "publisher_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "publisher_seq_gen")
    @Id
    @Column(name = "publisher_id")
    private Long id;

    private String name;

    @Builder.Default
    @OneToMany(mappedBy = "publisher")
    private List<Book> books = new ArrayList<>();
}
