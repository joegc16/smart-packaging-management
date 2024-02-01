package pt.ipleiria.estg.dei.ei.dae.appbackend.entitites;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ProductPackageId implements Serializable {
    @Column(name = "product_id")
    private Long productId;
    @Column(name = "package_id")
    private Long packageId;

    public ProductPackageId() {
    }

    public ProductPackageId(long productId, long packageId) {
        this.productId = productId;
        this.packageId = packageId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getPackageId() {
        return packageId;
    }

    public void setPackageId(Long packageId) {
        this.packageId = packageId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductPackageId that = (ProductPackageId) o;
        return productId.equals(that.productId) && packageId.equals(that.packageId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, packageId);
    }
}
