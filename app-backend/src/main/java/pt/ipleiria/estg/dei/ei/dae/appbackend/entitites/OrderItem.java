package pt.ipleiria.estg.dei.ei.dae.appbackend.entitites;

import jakarta.persistence.*;

@Entity
@Table(name = "order_item")
public class OrderItem {
    @EmbeddedId
    private OrderItemId id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @MapsId("productPackageId")
    @JoinColumn(name = "product_package_id")
    private ProductPackage productPackage;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @MapsId("orderId")
    @JoinColumn(name = "order_id")
    private Order order;

    private int quantity;
    private Double subPrice;

    public OrderItem() {
    }

    public OrderItem(OrderItemId id, ProductPackage productPackage, Order order,
                     int quantity, Double subPrice) {
        this.id = id;
        this.productPackage = productPackage;
        this.order = order;
        this.quantity = quantity;
        this.subPrice = subPrice;
    }

    public OrderItemId getId() {
        return id;
    }

    public void setId(OrderItemId id) {
        this.id = id;
    }

    public ProductPackage getProductPackage() {
        return productPackage;
    }

    public void setProductPackage(ProductPackage productPackage) {
        this.productPackage = productPackage;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
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
