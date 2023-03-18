package com.example.springsecurity.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "photos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_hospitals")
    @SequenceGenerator(
            name = "seq_hospitals", allocationSize = 1
    )
    private Long id;
    private String url;

}
