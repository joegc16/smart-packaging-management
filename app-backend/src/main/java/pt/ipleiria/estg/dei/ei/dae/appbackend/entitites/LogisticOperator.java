package pt.ipleiria.estg.dei.ei.dae.appbackend.entitites;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
public class LogisticOperator extends User{
    @OneToMany(mappedBy = "logisticOperator", cascade = CascadeType.ALL)
    private List<Order> orders;

    public LogisticOperator() {
        this.orders = new ArrayList<>();
    }

    public LogisticOperator(String name, String password, String username, String email, UserRole role) {
        super(name, password, username, email, role);
        this.orders = new ArrayList<>();
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public void addOrder(Order order) {
        this.orders.add(order);
    }

    public void removeOrder(Order order){
        this.orders.remove(order);
    }
}
