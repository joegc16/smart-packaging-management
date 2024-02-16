package pt.ipleiria.estg.dei.ei.dae.appbackend.ejbs;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.appbackend.entitites.Customer;
import pt.ipleiria.estg.dei.ei.dae.appbackend.entitites.User;
import pt.ipleiria.estg.dei.ei.dae.appbackend.security.Hasher;

import java.util.List;

@Stateless
public class UserBean {
    @PersistenceContext
    private EntityManager em;
    @Inject
    private Hasher hasher;

    public User find(long id) {
        return em.find(User.class, id);
    }

    public User findByUsername(String username) {
        Query query = em.createQuery(
                "SELECT u FROM User u WHERE u.username = :username", User.class);
        query.setParameter("username", username);
        return (User) query.getSingleResult();
    }

    public User findOrFail(String username) {
        long id = findByUsername(username).getId();
        var user = em.getReference(User.class, id);
        Hibernate.initialize(user);
        return user;
    }

    public boolean canLogin(String username, String password) {
        var user = findByUsername(username);
        return user != null && user.getPassword().equals(hasher.hash(password));
    }

    public List<User> getAll() {
        return em.createNamedQuery("getAllUsers", User.class).getResultList();
    }
}
