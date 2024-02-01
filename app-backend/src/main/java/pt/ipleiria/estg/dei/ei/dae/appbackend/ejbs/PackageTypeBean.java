package pt.ipleiria.estg.dei.ei.dae.appbackend.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.appbackend.entitites.PackageType;

@Stateless
public class PackageTypeBean {
    @PersistenceContext
    private EntityManager em;

    public void create(String name) {
        em.persist(new PackageType(name));
    }
}
