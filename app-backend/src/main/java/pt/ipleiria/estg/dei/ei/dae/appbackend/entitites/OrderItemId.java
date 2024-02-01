package pt.ipleiria.estg.dei.ei.dae.appbackend.entitites;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class OrderItemId implements Serializable {
    @Column(name = "product_package_id")
    private Long productPackageId;
    @Column(name = "order_id")
    private Long orderId;

    public OrderItemId() {
    }

    public OrderItemId(Long productPackageId, Long orderId) {
        this.productPackageId = productPackageId;
        this.orderId = orderId;
    }

    public Long getProductPackageId() {
        return productPackageId;
    }

    public void setProductPackageId(Long productPackageId) {
        this.productPackageId = productPackageId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderItemId)) return false;
        OrderItemId that = (OrderItemId) o;
        return productPackageId.equals(that.productPackageId) && orderId.equals(that.orderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productPackageId, orderId);
    }
}
