package pt.ipleiria.estg.dei.ei.dae.appbackend.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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

    public void cleanCart(long id) {
        Cart cart = em.find(Cart.class, id);
        if (cart == null) {
            System.out.println("Cart not found");
            return;
        }
        List<CartItem> cartItemsCopy = new ArrayList<>(cart.getCartItems()); //just  reference to the original list
        for (CartItem cartItem : cartItemsCopy) {
            cart.removeCartItem(cartItem);
            em.remove(cartItem);
        }
        cart.setCount(0);
        em.merge(cart);
    }
}
