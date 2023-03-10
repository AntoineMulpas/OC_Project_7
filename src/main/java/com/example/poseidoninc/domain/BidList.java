package com.example.poseidoninc.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "bidlist")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BidList {
    // TODO: Map columns in data table BIDLIST with corresponding java fields

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer BidListId;
    private String account;
    private String type;
    private Double bidQuantity;
    private Double askQuantity;
    private Double bid;
    private Double ask;
    private String benchmark;
    private Timestamp bidListDate;
    private String commentary;
    private String security;
    private String status;
    private String trader;
    private String book;
    private String creationName;
    private Timestamp creationDate;
    private String revisionName;
    private Timestamp revisionDate;
    private String dealName;
    private String dealType;
    private String sourceListId;
    private String side;
}
