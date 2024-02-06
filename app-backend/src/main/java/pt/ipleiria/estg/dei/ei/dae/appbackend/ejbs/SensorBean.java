package pt.ipleiria.estg.dei.ei.dae.appbackend.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.appbackend.entitites.*;
import pt.ipleiria.estg.dei.ei.dae.appbackend.entitites.Package;


import java.util.Date;

@Stateless
public class SensorBean {
    @PersistenceContext
    private EntityManager em;

    public void create(long sensorTypeId, Date timestamp, double value,
                       long unidadeTypeId, long orderId, long packageId) {
        SensorType sensorType = em.find(SensorType.class, sensorTypeId);
        if (sensorType == null) {
            throw new IllegalArgumentException("Sensor type with id " + sensorTypeId + " not found.");
        }
        UnitType unitType = em.find(UnitType.class, unidadeTypeId);
        if (unitType == null) {
            throw new IllegalArgumentException("Unidade type with id " + unidadeTypeId + " not found.");
        }
        Order order = em.find(Order.class, orderId);
        if (order == null) {
            throw new IllegalArgumentException("Order with id " + orderId + " not found.");
        }
        Package pack = em.find(Package.class, packageId);
        if (pack == null) {
            throw new IllegalArgumentException("Package with id " + packageId + " not found.");
        }
        Sensor sensor = new Sensor(sensorType, timestamp, value, unitType, order, pack);
        order.addSensor(sensor);
        em.persist(sensor);
    }

    public Sensor findSensor(long id) {
        return em.find(Sensor.class, id);
    }
}
