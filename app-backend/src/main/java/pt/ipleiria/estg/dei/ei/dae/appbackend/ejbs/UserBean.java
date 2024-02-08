package pt.ipleiria.estg.dei.ei.dae.appbackend.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.appbackend.entitites.User;

import java.util.List;

@Stateless
public class UserBean {
    @PersistenceContext
    private EntityManager em;

    public List<User> getAll() {
        return em.createNamedQuery("getAllUsers", User.class).getResultList();
    }
}
