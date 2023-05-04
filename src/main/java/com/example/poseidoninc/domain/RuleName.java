package com.example.poseidoninc.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;


@Entity
@Table(name = "rulename")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RuleName {
    // TODO: Map columns in data table RULENAME with corresponding java fields
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Description is mandatory")
    private String description;

    @NotBlank(message = "Json is mandatory")
    private String json;

    @NotBlank(message = "Template is mandatory")
    private String template;

    @NotBlank(message = "SqlStr is mandatory")
    private String sqlStr;

    @NotBlank(message = "SqlPart is mandatory")
    private String sqlPart;
}
