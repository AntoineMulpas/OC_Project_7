package com.example.poseidoninc.domain;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;


@Entity
@Table(name = "rating")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Rating {
    // TODO: Map columns in data table RATING with corresponding java fields
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    @NotNull
    private String moodysRating;
    @NotNull
    private String sandPRating;
    @NotNull
    private String fitchRating;
    @NotNull
    private Integer orderNumber;
}
