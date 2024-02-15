package pt.ipleiria.estg.dei.ei.dae.appbackend.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.appbackend.entitites.Cart;
import pt.ipleiria.estg.dei.ei.dae.appbackend.entitites.CartItem;
import pt.ipleiria.estg.dei.ei.dae.appbackend.entitites.ProductPackage;

@Stateless
public class CartItemBean {
    @PersistenceContext
    private EntityManager em;

    public CartItem create(long cartId, long productPackageId, int quantity, double subPrice) {
        Cart cart = em.find(Cart.class, cartId);
        if (cart == null) {
            System.out.println("Cart not found");
            return null;
        }
        ProductPackage productPackage = em.find(ProductPackage.class, productPackageId);
        if (productPackage == null) {
            System.out.println("Product not found");
            return null;
        }
        CartItem cartItem = new CartItem(cart, productPackage, quantity, subPrice);
        if (cartItem == null) {
            System.out.println("Something wrong creating the cart item");
            return null;
        }
        cart.addCartItem(cartItem);
        em.persist(cartItem);
        return cartItem;
    }

    public CartItem find(long itemId) {
        CartItem cartItem = em.find(CartItem.class, itemId);
        if (cartItem == null) {
            System.out.println("Cart item not found");
            return null;
        }
        return cartItem;
    }

    public void delete(long itemId) {
        CartItem cartItem = em.find(CartItem.class, itemId);
        if (cartItem == null) {
            System.out.println("Cart item not found");
            return;
        }
        Cart cart = cartItem.getCart();
        cart.removeCartItem(cartItem);
        em.remove(cartItem);
    }

    public boolean update(long itemId, int quantity, double subPrice) {
        CartItem cartItem = em.find(CartItem.class, itemId);
        if (cartItem == null) {
            System.out.println("Cart item not found");
            return false;
        }
        cartItem.setQuantity(quantity);
        cartItem.setSubPrice(subPrice);
        em.merge(cartItem);
        return true;
    }
}
