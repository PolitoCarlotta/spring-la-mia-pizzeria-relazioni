package com.pizzeria.spring_la_mia_pizzeria_crud.model;

import java.util.List;

import org.hibernate.validator.constraints.URL;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="pizze")

public class Pizza {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @NotBlank(message = "Name is obbligatory")
    @Column(nullable = false, unique = true)
    private String name;

    @NotNull
    @NotBlank(message = "Description is obbligatory")
    private String note;

    @URL(message = "Url not valid")
    private String photo;

    @NotNull(message = "Price is obbligatory")
    @DecimalMin(value = "0.01", message = "Price must be higher than 0")
    private Double price;

    @OneToMany(mappedBy = "pizza")
    private List<Offer> offers;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
