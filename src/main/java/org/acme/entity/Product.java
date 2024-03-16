package org.acme.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.Serializable;

@Entity
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String title;

    Double price;

    public Product() {

    }

    public  Product(String title, Double price) {
        setTitle(title);
        setPrice(price);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
