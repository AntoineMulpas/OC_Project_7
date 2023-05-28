package com.example.poseidoninc.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

import java.sql.Timestamp;


@Entity
@Table(name = "trade")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Trade {
    // TODO: Map columns in data table TRADE with corresponding java fields
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer tradeId;

    @NotBlank(message = "Account is mandatory")
    private String account;

    @NotBlank(message = "Type is mandatory")
    private String type;

    @NotNull(message = "BuyQuantity is mandatory")
    private Double buyQuantity;
    private Double sellQuantity;
    private Double buyPrice;
    private Double sellPrice;
    private String benchmark;
    private Timestamp tradeDate;
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

    public Trade(String account, String type, Double buyQuantity) {
        this.account = account;
        this.type = type;
        this.buyQuantity = buyQuantity;
    }

    public Trade(Integer tradeId, String account, String type, Double buyQuantity) {
        this.tradeId = tradeId;
        this.account = account;
        this.type = type;
        this.buyQuantity = buyQuantity;
    }
}
