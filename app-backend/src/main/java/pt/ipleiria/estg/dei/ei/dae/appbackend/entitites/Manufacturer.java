package pt.ipleiria.estg.dei.ei.dae.appbackend.entitites;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Manufacturer extends User{
    @OneToMany(mappedBy = "manufacturer", cascade = CascadeType.ALL)
    private List <Product> products;
    //private List<Order> orders;

    public Manufacturer() {
        this.products = new ArrayList<>();
    }

    public Manufacturer(String name, String password, String username, String email, UserRole role) {
        super(name, password, username, email, role);
        this.products = new ArrayList<>();
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(Product product){
        products.remove(product);
    }
}
