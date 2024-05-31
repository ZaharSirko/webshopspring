package com.example.webshopspring.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;


@Entity
@Table(name = "goods")
public class Good {
    @Id
    @SequenceGenerator(
            name = "good_sequence",
            sequenceName = "good_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "good_sequence"
    )
    @Column(name = "good_id")
    private Long id;

    @NotNull
    @Column(name = "good_name")
    private String goodName;

    @NotNull
    @Column(name = "good_description")
    private String goodDescription;

    @NotNull
    @Column(name = "good_brand")
    private String goodBrand;

    @NotNull
    @Column(name = "good_photo")
    private String[] goodPhoto;

    @Column(name = "good_likes")
    private int goodLikes;


    public Good(long id, String goodName, String goodDescription, String goodBrand, String[] goodPhoto, int goodLikes) {

        this.goodName = goodName;
        this.goodDescription = goodDescription;
        this.goodBrand = goodBrand;
        this.goodPhoto = goodPhoto;
        this.goodLikes = goodLikes;
    }

    public Good() {
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public String getGoodDescription() {
        return goodDescription;
    }

    public void setGoodDescription(String goodDescription) {
        this.goodDescription = goodDescription;
    }

    public String getGoodBrand() {
        return goodBrand;
    }

    public void setGoodBrand(String goodBrand) {
        this.goodBrand = goodBrand;
    }

    public String[] getGoodPhoto() {
        return goodPhoto;
    }

    public void setGoodPhoto(String[] goodPhoto) {
        this.goodPhoto = goodPhoto;
    }

    public int getGoodLikes() {
        return goodLikes;
    }

    public void setGoodLikes(int goodLikes) {
        this.goodLikes = goodLikes;
    }
}
