package org.acme.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.io.Serializable;

@Entity
public class CartProduct implements Serializable {
    @Id
    @ManyToOne
    @JsonIgnore
    Client client;

    @Id
    @ManyToOne
    Product product;

    Integer quantity = 0;

    public CartProduct() {}

    public CartProduct(Client client, Product product) {
        this.client = client;
        this.product = product;
    }

    public void incrementQuantity() {
        ++quantity;
    }

    public void decrementQuantity() {
        --quantity;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
