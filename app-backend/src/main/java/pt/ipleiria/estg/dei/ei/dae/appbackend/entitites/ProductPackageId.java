package pt.ipleiria.estg.dei.ei.dae.appbackend.entitites;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ProductPackageId implements Serializable {
    @Column(name = "product_code")
    private Long productCode;
    @Column(name = "package_code")
    private Long packageCode;

    public ProductPackageId() {
    }

    public ProductPackageId(long productCode, long packageCode) {
        this.productCode = productCode;
        this.packageCode = packageCode;
    }

    public Long getProductCode() {
        return productCode;
    }

    public void setProductCode(Long productId) {
        this.productCode = productId;
    }

    public Long getPackageCode() {
        return packageCode;
    }

    public void setPackageCode(Long packageId) {
        this.packageCode = packageId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductPackageId that = (ProductPackageId) o;
        return productCode.equals(that.productCode) && packageCode.equals(that.packageCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productCode, packageCode);
    }
}
