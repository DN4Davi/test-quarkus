package org.acme.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.io.Serializable;
import java.util.Objects;

@Entity
public class CartProduct implements Serializable {
    @Id
    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    Client client;

    @Id
    @ManyToOne
    Product product;

    Integer quantity = 1;

    public CartProduct() {
    }

    public CartProduct(Client client, Product product) {
        setClient(client);
        setProduct(product);
    }

    public CartProduct(String clientId, String productId) {
        var client = new Client();
        client.setId(clientId);
        this.client = client;

        var product = new Product();
        product.setId(productId);
        this.product = product;
    }

    public CartProduct(Client client, Product product, int quantity) {
        setClient(client);
        setProduct(product);
        setQuantity(quantity);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartProduct that = (CartProduct) o;
        return Objects.equals(getClient(), that.getClient()) && Objects.equals(getProduct(), that.getProduct());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClient(), getProduct());
    }
}
