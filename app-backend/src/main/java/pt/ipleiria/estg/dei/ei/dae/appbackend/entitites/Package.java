package pt.ipleiria.estg.dei.ei.dae.appbackend.entitites;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "packages")
public class Package {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long code;
    @NotNull
    private String name;
    @ManyToOne
    @NotNull
    private PackageType packageType;
    @NotNull
    private String material;
    @ManyToOne
    private Product product;
    @OneToMany(mappedBy = "pack")
    private List<ProductPackage> productPackages;

    public Package() {
        this.productPackages = new ArrayList<>();
    }

    public Package(String name, PackageType packageType, String material) {
        this.name = name;
        this.packageType = packageType;
        this.material = material;
        this.productPackages = new ArrayList<>();
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PackageType getPackageType() {
        return packageType;
    }

    public void setPackageType(PackageType packageType) {
        this.packageType = packageType;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public List<ProductPackage> getProductPackages() {
        return productPackages;
    }

    public void setProductPackages(List<ProductPackage> productPackages) {
        this.productPackages = productPackages;
    }

    public void addProductPackage(ProductPackage productPackage){
        if (this.productPackages.contains(productPackage)) {
            return;
        }
        if (productPackage == null) {
            return;
        }
        this.productPackages.add(productPackage);
    }

    public void removeProductPackage(ProductPackage productPackage){
        this.productPackages.remove(productPackage);
    }
}
