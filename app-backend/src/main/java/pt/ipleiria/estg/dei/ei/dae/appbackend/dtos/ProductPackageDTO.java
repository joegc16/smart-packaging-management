package pt.ipleiria.estg.dei.ei.dae.appbackend.dtos;

import java.io.Serializable;

public class ProductPackageDTO implements Serializable{
    private long id;
    private long productCode;
    private long packageCode;
    private Double price;
    private int quantityProductsInPackage;
    private String image;

    public ProductPackageDTO() {
    }

    public ProductPackageDTO(long id, long productCode, long packageCode, Double price, int quantityProductsInPackage, String image) {
        this.id = id;
        this.productCode = productCode;
        this.packageCode = packageCode;
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

    public long getProductCode() {
        return productCode;
    }

    public void setProductCode(long productCode) {
        this.productCode = productCode;
    }

    public long getPackageCode() {
        return packageCode;
    }

    public void setPackageCode(long packageCode) {
        this.packageCode = packageCode;
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
