package pt.ipleiria.estg.dei.ei.dae.appbackend.entitites;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "products")
@NamedQueries({
        @NamedQuery(
                name = "getAllProducts",
                query = "SELECT p FROM Product p ORDER BY p.name"
        )
})
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long code;
    @NotNull
    @Column(unique = true)
    private String name;
    @NotNull
    private String brand;
    private String description;
    @NotNull
    private int totalQuantity;
    private String image;

    @ManyToOne
    @JoinColumn(name = "manufacturer_username")
    @NotNull
    private Manufacturer manufacturer;

    @OneToMany(mappedBy = "product")
    private List<ProductPackage> productPackages;

    public Product() {
        this.productPackages = new ArrayList<>();
    }

    public Product(String name, String brand, String description, int totalQuantity, Manufacturer manufacturer) {
        this.name = name;
        this.brand = brand;
        this.description = description;
        this.totalQuantity = totalQuantity;
        this.manufacturer = manufacturer;
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

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public List<ProductPackage> getProductPackages() {
        return productPackages;
    }

    public void setProductPackages(List<ProductPackage> productPackages) {
        this.productPackages = productPackages;
    }

    public void addProductPackage(ProductPackage productPackage) {
        if (this.productPackages.contains(productPackage)) {
            return;
        }
        if (productPackage == null) {
            return;
        }
        this.productPackages.add(productPackage);
    }

    public void removeProductPackage(ProductPackage productPackage) {
        this.productPackages.remove(productPackage);
    }
}
