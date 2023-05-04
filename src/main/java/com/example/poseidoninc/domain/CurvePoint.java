package com.example.poseidoninc.domain;

import jakarta.persistence.*;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;


@Entity
@Table(name = "curvepoint")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CurvePoint {
    // TODO: Map columns in data table CURVEPOINT with corresponding java fields
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @NotNull(message = "CurveId is mandatory")
    private Integer curveId;
    private Timestamp asOfDate;
    @NotNull(message = "Term is mandatory")
    private Double term;
    @NotNull(message = "Value is mandatory")
    private Double value;
    private Timestamp creationDate;
}
