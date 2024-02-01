package pt.ipleiria.estg.dei.ei.dae.appbackend.entitites;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

@Entity
@Table(name = "product_package")
public class ProductPackage {
    @EmbeddedId
    private ProductPackageId id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @MapsId("packageId")
    @JoinColumn(name = "package_id")
    private Package pack;

    @NotNull
    private Double price;
    @NotNull
    private int quantityProductsInPackage;
    private String image;

    public ProductPackage() {
    }

    public ProductPackage(ProductPackageId id, Product product, Package pack, Double price, int quantityProductsInPackage, String image) {
        this.id = id;
        this.product = product;
        this.pack = pack;
        this.price = price;
        this.quantityProductsInPackage = quantityProductsInPackage;
        this.image = image;
    }

    public ProductPackageId getId() {
        return id;
    }

    public void setId(ProductPackageId id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Package getPack() {
        return pack;
    }

    public void setPack(Package pack) {
        this.pack = pack;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getQuantityProductsInPackage() {
        return quantityProductsInPackage;
    }

    public void setQuantityProductsInPackage(int quantityProductsInPackage) {
        this.quantityProductsInPackage = quantityProductsInPackage;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

