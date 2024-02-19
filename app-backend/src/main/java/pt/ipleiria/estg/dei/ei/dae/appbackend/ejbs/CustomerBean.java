package pt.ipleiria.estg.dei.ei.dae.appbackend.ejbs;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.appbackend.entitites.Customer;
import pt.ipleiria.estg.dei.ei.dae.appbackend.entitites.Order;
import pt.ipleiria.estg.dei.ei.dae.appbackend.entitites.UserRole;
import pt.ipleiria.estg.dei.ei.dae.appbackend.security.Hasher;

import java.util.List;

@Stateless
public class CustomerBean {
    @PersistenceContext
    private EntityManager em;
    @Inject
    private Hasher hasher;

    public void create(String name, String password, String username, String email, long role_id) {
        UserRole role = em.find(UserRole.class, role_id);
        if (role == null || role.getId() != 2) {
            System.err.println("Role does not exist or Is not a Customer");
        }
        if (exists(username)) {
            System.err.println("Customer username already exists");
        }
        Customer customer = new Customer(name, hasher.hash(password), username, email, role);
        em.persist(customer);
    }

    public boolean exists(String username) {
        Query query = em.createQuery(
                "SELECT COUNT(s.username) FROM User s WHERE s.username = :username",
                Long.class
        );
        query.setParameter("username", username);
        return (Long)query.getSingleResult() > 0L;
    }

    public Customer find(long id) {
        return em.find(Customer.class, id);
    }

    public Customer getCustomerOrders(long id) {
        Customer customer = this.find(id);
        if (customer == null) {
            return null;
        }
        Hibernate.initialize(customer.getOrders());
        return customer;
    }

    public Customer findByUsername(String username) {
        Query query = em.createQuery(
                "SELECT c FROM Customer c WHERE c.username = :username", Customer.class);
        query.setParameter("username", username);
        return (Customer) query.getSingleResult();
    }

    public void delete(long id) {
        Customer customer = find(id);
        if (customer == null) {
            System.err.println("Customer does not exist");
            return;
        }
        List<Order> orders = customer.getOrders();
        if (orders.size() > 0) {
            System.err.println("Customer has order associated");
        }
        customer.setCart(null);
        em.remove(customer);
    }

    public boolean update(long id, String name, String password, String username, String email, long role) {
        Customer customer = this.find(id);
        if (customer == null) {
            System.err.println("Customer does not exist");
            return false;
        }
        UserRole userRole = em.find(UserRole.class, role);
        if (userRole == null || userRole.getId() != 2) {
            System.err.println("Role does not exist or Is not a Customer");
            return false;
        }
        //em.lock(customer, LockModeType.OPTIMISTIC);
        customer.setName(name);
        customer.setPassword(hasher.hash(password));
        customer.setUsername(username);
        customer.setEmail(email);
        customer.setRole(userRole);
        return true;
    }

    public Customer getCustomerCart(long id) {
        Customer customer = this.find(id);
        if (customer == null) {
            return null;
        }
        Hibernate.initialize(customer.getCart());
        Hibernate.initialize(customer.getCart().getCartItems());
        return customer;
    }
}
