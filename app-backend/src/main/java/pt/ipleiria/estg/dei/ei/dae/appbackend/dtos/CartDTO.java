package pt.ipleiria.estg.dei.ei.dae.appbackend.dtos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CartDTO implements Serializable {
    private long id;
    private long customerId;
    private List<CartItemDTO> cartItems;
    private double count;

    public CartDTO() {
        cartItems = new ArrayList<>();
    }

    public CartDTO(long id, long customerId, List<CartItemDTO> cartItems, double count) {
        this.id = id;
        this.customerId = customerId;
        this.cartItems = cartItems;
        this.count = count;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCustomer() {
        return customerId;
    }

    public void setCustomer(long customerId) {
        this.customerId = customerId;
    }

    public List<CartItemDTO> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItemDTO> cartItems) {
        this.cartItems = cartItems;
    }

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = count;
    }
}
