package pt.ipleiria.estg.dei.ei.dae.appbackend.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.appbackend.entitites.AlertType;

@Stateless
public class AlertTypeBean {
    @PersistenceContext
    private EntityManager em;

    public void create(String name) {
        em.persist(new AlertType(name));
    }
}
