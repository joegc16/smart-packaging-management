package pt.ipleiria.estg.dei.ei.dae.appbackend.dtos;

import java.io.Serializable;

public class OrderItemDTO implements Serializable {
    private long productPackageId;
    private long orderId;
    private int quantity;
    private Double subPrice;

    public OrderItemDTO() {
    }

    public OrderItemDTO(long productPackageId, long orderId, int quantity, Double subPrice) {
        this.productPackageId = productPackageId;
        this.orderId = orderId;
        this.quantity = quantity;
        this.subPrice = subPrice;
    }

    public long getProductPackageId() {
        return productPackageId;
    }

    public void setProductPackageId(long productPackageId) {
        this.productPackageId = productPackageId;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Double getSubPrice() {
        return subPrice;
    }

    public void setSubPrice(Double subPrice) {
        this.subPrice = subPrice;
    }
}
