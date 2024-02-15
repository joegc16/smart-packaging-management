package pt.ipleiria.estg.dei.ei.dae.appbackend.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.appbackend.entitites.Cart;
import pt.ipleiria.estg.dei.ei.dae.appbackend.entitites.CartItem;
import pt.ipleiria.estg.dei.ei.dae.appbackend.entitites.Customer;

import java.util.ArrayList;
import java.util.List;

@Stateless
public class CartBean {
    @PersistenceContext
    private EntityManager em;

    public Cart create(long customerId, double count)
    {
        Customer customer = em.find(Customer.class, customerId);
        if (customer == null) {
            System.out.println("Customer not found");
            return null;
        }
        Cart cart = new Cart(customer, count);
        if (cart == null) {
            System.out.println("Something wrong creating the customer cart");
            return null;
        }
        customer.setCart(cart);
        em.persist(cart);
        return cart;
    }

    public Cart find(long id) {
        return em.find(Cart.class, id);
    }

    public void updateCartCount(long id) {
        Cart cart = this.find(id);
        if (cart == null) {
            System.out.println("Cart not found");
            return;
        }
        double count = 0;
        for (CartItem cartItem : cart.getCartItems()) {
            count += cartItem.getSubPrice();
        }
        cart.setCount(count);
    }

    public void delete(long id) {
        Cart cart = this.find(id);
        if (cart == null) {
            System.out.println("Cart not found");
            return;
        }
        em.remove(cart);
    }

    public void clearCart(long id) {
        Cart cart = this.find(id);
        if (cart == null) {
            System.out.println("Cart not found");
            return;
        }
        List<CartItem> cartItemsCopy = new ArrayList<>(cart.getCartItems());
        for (CartItem cartItem : cartItemsCopy) {
            cart.removeCartItem(cartItem);
            em.remove(cartItem);
        }
        cart.setCount(0);
        em.merge(cart);
    }

    public Cart findWithCartItems(long id) {
        Cart cart = this.find(id);
        if (cart == null) {
            System.out.println("Cart not found");
            return null;
        }
        Hibernate.initialize(cart.getCartItems());
        return cart;
    }
}
