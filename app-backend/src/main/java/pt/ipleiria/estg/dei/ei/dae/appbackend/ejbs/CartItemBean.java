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

    public void create(long cartId, long productPackageId, int quantity, double subPrice) {
        Cart cart = em.find(Cart.class, cartId);
        if (cart == null) {
            System.out.println("Cart not found");
            return;
        }
        ProductPackage productPackage = em.find(ProductPackage.class, productPackageId);
        if (productPackage == null) {
            System.out.println("Product not found");
            return;
        }
        CartItem cartItem = new CartItem(cart, productPackage, quantity, subPrice);
        if (cartItem == null) {
            System.out.println("Something wrong creating the cart item");
            return;
        }

        cart.addCartItem(cartItem);
        em.persist(cartItem);
    }
}
