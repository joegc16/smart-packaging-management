package pt.ipleiria.estg.dei.ei.dae.appbackend.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.appbackend.entitites.Customer;
import pt.ipleiria.estg.dei.ei.dae.appbackend.entitites.Order;
import pt.ipleiria.estg.dei.ei.dae.appbackend.entitites.OrderItem;

import java.util.Date;
import java.util.List;

@Stateless
public class OrderBean {
    @PersistenceContext
    private EntityManager em;

    public void create(long customerId, Date orderDate, Date deliveryDate, String city,
                       String postalCode, String country, String address, String paymentMethod,
                       String status, Double count) {
        Customer customer = em.find(Customer.class, customerId);
        if (customer == null) {
            System.err.println("Customer does not exist");
            return;
        }
        Order order = new Order(customer, orderDate, deliveryDate, city, postalCode, country, address, paymentMethod, status, count);
        customer.addOrder(order);
        em.persist(order);
    }

    public void updateOrderItems(long id, List<OrderItem> orderItems) {
        Order order = em.find(Order.class, id);
        if (order == null) {
            System.err.println("Order does not exist");
            return;
        }
        order.setOrderItems(orderItems);
        em.merge(order);
    }

    public Order findOrder(long id) {
        return em.find(Order.class, id);
    }

}
