package pt.ipleiria.estg.dei.ei.dae.appbackend.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.appbackend.entitites.UserRole;

@Stateless
public class UserRoleBean {
    @PersistenceContext
    private EntityManager em;

    public void create(String role) {
        em.persist(new UserRole(role));
    }

}
