package pt.ipleiria.estg.dei.ei.dae.appbackend.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import pt.ipleiria.estg.dei.ei.dae.appbackend.entitites.Customer;
import pt.ipleiria.estg.dei.ei.dae.appbackend.entitites.UserRole;

@Stateless
public class CustomerBean {
    @PersistenceContext
    private EntityManager em;

    public void create(String name, String password, String username, String email, long role_id) {
        UserRole role = em.find(UserRole.class, role_id);
        if (role == null) {
            System.err.println("Role does not exist");
        }
        if (exists(username)) {
            System.err.println("Customer username already exists");
        }
        Customer customer = new Customer(name, password, username, email, role);
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
}
