package com.example.webshopspring.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "types")
public class Types {
    @Id
    @SequenceGenerator(
            name = "type_sequence",
            sequenceName = "type_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "type_sequence"
    )
    @Column(name = "type_id")
    private long id;

    @NotNull
    @Column(name = "type_name")
    private String name;

    @Column(name = "deleted_at")
    private String deleted_at;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(String deleted_at) {
        this.deleted_at = deleted_at;
    }
}
