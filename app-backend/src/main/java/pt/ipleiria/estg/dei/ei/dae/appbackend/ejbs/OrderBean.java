package pt.ipleiria.estg.dei.ei.dae.appbackend.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.appbackend.entitites.*;

import java.util.Date;
import java.util.List;

@Stateless
public class OrderBean {
    @PersistenceContext
    private EntityManager em;

    public void create(long customerId, long manufacturerId, long logisticOperatorId, Date orderDate, Date deliveryDate, String city,
                       String postalCode, String country, String address, String paymentMethod,
                       String status, Double count) {
        Customer customer = em.find(Customer.class, customerId);
        if (customer == null) {
            System.err.println("Customer does not exist");
            return;
        }
        Manufacturer manufacturer = em.find(Manufacturer.class, manufacturerId);
        if (manufacturer == null) {
            System.err.println("Manufacturer does not exist");
            return;
        }
        LogisticOperator logisticOperator = em.find(LogisticOperator.class, logisticOperatorId);
        if (logisticOperator == null) {
            System.err.println("Logistic Operator does not exist");
            return;
        }
        Order order = new Order(customer, manufacturer, logisticOperator, orderDate, deliveryDate, city, postalCode, country, address, paymentMethod, status, count);
        customer.addOrder(order);
        manufacturer.addOrder(order);
        logisticOperator.addOrder(order);
        em.persist(order);
    }

    public Order findOrder(long id) {
        return em.find(Order.class, id);
    }

    public List<Order> getAll() {
        return em.createNamedQuery("getAllOrders", Order.class).getResultList();
    }

    public Order getOrderItems(long id) {
        Order order = this.findOrder(id);
        // Check if it exists, etc...
        if (order == null) {
            return null;
        }
        Hibernate.initialize(order.getOrderItems());
        return order;
    }

    public Order getOrderSensors(long id) {
        Order order = this.findOrder(id);
        // Check if it exists, etc...
        if (order == null) {
            return null;
        }
        Hibernate.initialize(order.getSensors());
        return order;
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
}
