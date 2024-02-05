package pt.ipleiria.estg.dei.ei.dae.appbackend.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.appbackend.entitites.SensorType;

@Stateless
public class SensorTypeBean {
    @PersistenceContext
    private EntityManager em;

    public void create(String name) {
        em.persist(new SensorType(name));
    }

    public SensorType findSensorType(long id) {
        return em.find(SensorType.class, id);
    }
}
