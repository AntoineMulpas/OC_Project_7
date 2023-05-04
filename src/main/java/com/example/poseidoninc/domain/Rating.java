package com.example.poseidoninc.domain;

import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "MoodysRating is mandatory")
    private String moodysRating;

    @NotBlank(message = "SandPRating is mandatory")
    private String sandPRating;

    @NotBlank(message = "Fitchrating is mandatory")
    private String fitchRating;

    @NotNull(message = "OrderNumber is mandatory")
    private Integer orderNumber;
}
