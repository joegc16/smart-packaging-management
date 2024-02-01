package pt.ipleiria.estg.dei.ei.dae.appbackend.ejbs;


import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.appbackend.entitites.*;

@Stateless
public class OrderItemBean {
    @PersistenceContext
    private EntityManager em;

    public OrderItem create(long productPackageId, long orderId, int quantity, double subPrice) {
        ProductPackage productPackage = em.find(ProductPackage.class, productPackageId);
        if (productPackage == null) {
            System.err.println("ProductPackage does not exist");
            return null;
        }
        Order order = em.find(Order.class, orderId);
        if (order == null) {
            System.err.println("Order does not exist");
            return null;
        }
        OrderItemId id = new OrderItemId(productPackageId, orderId);
        OrderItem orderItem = new OrderItem(id, productPackage, order, quantity, subPrice);
        order.addOrderItem(orderItem);
        em.persist(orderItem);
        return orderItem;
    }
}
