package pt.ipleiria.estg.dei.ei.dae.appbackend.dtos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CustomerDTO extends UserDTO implements Serializable {
    private List<OrderDTO> orders;
    private long cartId;

    public CustomerDTO() {
        this.orders = new ArrayList<>();
    }

    public CustomerDTO(String username, String email, String name, String role) {
        super(name, username, name, role);
        this.orders = new ArrayList<>();
    }

    public List<OrderDTO> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderDTO> orders) {
        this.orders = orders;
    }

    public long getCartId() {
        return cartId;
    }

    public void setCartId(long cartId) {
        this.cartId = cartId;
    }
}
