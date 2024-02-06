package pt.ipleiria.estg.dei.ei.dae.appbackend.dtos;

import java.io.Serializable;

public class CartItemDTO implements Serializable {
    private long id;
    private long cartId;
    private long productPackageId;
    private int quantity;
    private double subPrice;

    public CartItemDTO() {
    }

    public CartItemDTO(long cartId, long productPackageId, int quantity, double subPrice) {
        this.cartId = cartId;
        this.productPackageId = productPackageId;
        this.quantity = quantity;
        this.subPrice = subPrice;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCartId() {
        return cartId;
    }

    public void setCartId(long cartId) {
        this.cartId = cartId;
    }

    public long getProductPackageId() {
        return productPackageId;
    }

    public void setProductPackageId(long productPackageId) {
        this.productPackageId = productPackageId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getSubPrice() {
        return subPrice;
    }

    public void setSubPrice(double subPrice) {
        this.subPrice = subPrice;
    }
}
