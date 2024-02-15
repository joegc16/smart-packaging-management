package pt.ipleiria.estg.dei.ei.dae.appbackend.dtos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ManufacturerDTO extends UserDTO implements Serializable {
    private List<ProductDTO> products;
    private List<OrderDTO> orders;

    public ManufacturerDTO() {
        this.products = new ArrayList<>();
        this.orders = new ArrayList<>();
    }

    public ManufacturerDTO(long id, String name, String password, String username, String email, long role) {
        super(id, name, password, username, email, role);
        this.products = new ArrayList<>();
        this.orders = new ArrayList<>();
    }

    public List<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }

    public List<OrderDTO> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderDTO> orders) {
        this.orders = orders;
    }
}
