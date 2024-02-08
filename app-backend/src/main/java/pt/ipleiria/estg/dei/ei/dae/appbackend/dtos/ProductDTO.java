package pt.ipleiria.estg.dei.ei.dae.appbackend.dtos;

import java.io.Serializable;

public class ProductDTO implements Serializable {
    private long code;
    private String name;
    private String brand;
    private String description;
    private int totalQuantity;
    private String manufacturerUsername;

    public ProductDTO() {
    }

    public ProductDTO(long code, String name, String description, String brand, int totalQuantity, String manufacturerUsername) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.brand = brand;
        this.totalQuantity = totalQuantity;
        this.manufacturerUsername = manufacturerUsername;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public String getManufacturerUsername() {
        return manufacturerUsername;
    }

    public void setManufacturerUsername(String manufacturerUsername) {
        this.manufacturerUsername = manufacturerUsername;
    }
}
