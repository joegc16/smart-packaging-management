package pt.ipleiria.estg.dei.ei.dae.appbackend.dtos;

import java.io.Serializable;

public class CartItemDTO implements Serializable {
    private long id;
    private long cartId;
    private long productPackageId;
    private String imageProductPackage;
    private double priceProductPackage;
    private String nameProduct;
    private String brandProduct;
    private String namePackage;

    private int quantity;
    private double subPrice;


    public CartItemDTO() {
    }

    public CartItemDTO(long cartId, long productPackageId, int quantity, double subPrice) {
        this.cartId = cartId;
        this.productPackageId = productPackageId;
        this.quantity = quantity;
        this.subPrice = subPrice;
    }

    public CartItemDTO(long id, long cartId, long productPackageId, String imageProductPackage, String nameProduct,
                       String namePackage, String brandProduct, int quantity, double subPrice, double priceProductPackage) {
        this.id = id;
        this.cartId = cartId;
        this.productPackageId = productPackageId;
        this.imageProductPackage = imageProductPackage;
        this.nameProduct = nameProduct;
        this.namePackage = namePackage;
        this.brandProduct = brandProduct;
        this.quantity = quantity;
        this.subPrice = subPrice;
        this.priceProductPackage = priceProductPackage;
    }

    public String getImageProductPackage() {
        return imageProductPackage;
    }

    public void setImageProductPackage(String imageProductPackage) {
        this.imageProductPackage = imageProductPackage;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public String getNamePackage() {
        return namePackage;
    }

    public void setNamePackage(String namePackage) {
        this.namePackage = namePackage;
    }

    public double getPriceProductPackage() {
        return priceProductPackage;
    }

    public void setPriceProductPackage(double priceProductPackage) {
        this.priceProductPackage = priceProductPackage;
    }

    public String getBrandProduct() {
        return brandProduct;
    }

    public void setBrandProduct(String brandProduct) {
        this.brandProduct = brandProduct;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCartId() {
        return cartId;
    }

    public void setCartId(long cartId) {
        this.cartId = cartId;
    }

    public long getProductPackageId() {
        return productPackageId;
    }

    public void setProductPackageId(long productPackageId) {
        this.productPackageId = productPackageId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getSubPrice() {
        return subPrice;
    }

    public void setSubPrice(double subPrice) {
        this.subPrice = subPrice;
    }
}
