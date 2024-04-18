package com.example.webshopspring.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;


@Entity
@Table(name = "prices")
public class Price {
    @Id
    @SequenceGenerator(
            name = "price_sequence",
            sequenceName = "price_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "price_sequence"
    )
    @Column(name = "price_id")
    private Long id;

    @NotNull
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "good_id")
    private Good good_id;

    @NotNull
    @Column(name = "supplier_price")
    private double supplier_price;

    @NotNull
    @Column(name = "client_price")
    private double client_price;

    @NotNull
    @Column(name = "bought_amount")
    private int bought_amount;

    @Column(name = "sold_amount")
    private int sold_amount;

    @Column(name = "created_at")
    private String created_at;

    @Column(name = "deleted_at")
    private String deleted_at;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Good getGood_id() {
        return good_id;
    }

    public void setGood_id(Good good_id) {
        this.good_id = good_id;
    }


    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(String deleted_at) {
        this.deleted_at = deleted_at;
    }

    public double getSupplier_price() {
        return supplier_price;
    }

    public void setSupplier_price(double supplier_price) {
        this.supplier_price = supplier_price;
    }

    public double getClient_price() {
        return client_price;
    }

    public void setClient_price(double client_price) {
        this.client_price = client_price;
    }

    public int getBought_amount() {
        return bought_amount;
    }

    public void setBought_amount(int bought_amount) {
        this.bought_amount = bought_amount;
    }

    public int getSold_amount() {
        return sold_amount;
    }

    public void setSold_amount(int sold_amount) {
        this.sold_amount = sold_amount;
    }
}
