package pt.ipleiria.estg.dei.ei.dae.appbackend.dtos;

import java.io.Serializable;

public class ProductPackageDTO implements Serializable{
    private long id;
    private long productId;
    private long packageId;
    private Double price;
    private int quantityProductsInPackage;
    private String image;

    public ProductPackageDTO() {
    }

    public ProductPackageDTO(long productId, long packageId, Double price, int quantityProductsInPackage, String image) {
        this.productId = productId;
        this.packageId = packageId;
        this.price = price;
        this.quantityProductsInPackage = quantityProductsInPackage;
        this.image = image;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public long getPackageId() {
        return packageId;
    }

    public void setPackageId(long packageId) {
        this.packageId = packageId;
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
