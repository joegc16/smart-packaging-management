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

    public CustomerDTO(long id, String name, String password, String username, String email, long role) {
        super(id, name, password, username, email, role);
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
