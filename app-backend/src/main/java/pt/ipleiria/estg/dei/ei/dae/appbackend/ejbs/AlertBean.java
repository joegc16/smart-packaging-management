package pt.ipleiria.estg.dei.ei.dae.appbackend.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.appbackend.entitites.Alert;
import pt.ipleiria.estg.dei.ei.dae.appbackend.entitites.AlertType;
import pt.ipleiria.estg.dei.ei.dae.appbackend.entitites.Order;

import java.util.Date;

@Stateless
public class AlertBean {
    @PersistenceContext
    private EntityManager em;

    public void create(long alertTypeId, long orderId, Date timestamp, String message) {
        AlertType alertType = em.find(AlertType.class, alertTypeId);
        if (alertType == null) {
            throw new IllegalArgumentException("AlertType with id " + alertTypeId + " not found");
        }
        Order order = em.find(Order.class, orderId);
        if (order == null) {
            throw new IllegalArgumentException("Order with id " + orderId + " not found");
        }
        em.persist(new Alert(alertType, order, timestamp, message));
    }

}
