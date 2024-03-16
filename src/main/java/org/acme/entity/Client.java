package org.acme.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Client implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String name;
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    List<CartProduct> cart = new ArrayList<>();

    public Client() {
    }

    public Client(String name) {
        setName(name);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CartProduct> getCart() {
        return cart;
    }

    public void setCart(List<CartProduct> cart) {
        this.cart.removeIf((item) -> true);
        this.cart.addAll(cart);
    }
}
