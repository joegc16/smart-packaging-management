package pt.ipleiria.estg.dei.ei.dae.appbackend.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.appbackend.dtos.LogisticOperatorDTO;
import pt.ipleiria.estg.dei.ei.dae.appbackend.entitites.LogisticOperator;
import pt.ipleiria.estg.dei.ei.dae.appbackend.entitites.Manufacturer;
import pt.ipleiria.estg.dei.ei.dae.appbackend.entitites.UserRole;

@Stateless
public class LogisticOperatorBean {
    @PersistenceContext
    private EntityManager em;

    public void create(String name, String password, String username, String email, long roleId) {
        UserRole role = em.find(UserRole.class, roleId);
        if (role == null || role.getId() != 3) {
            System.err.println("Role does not exist or Is not a Logistic Operator");
            return;
        }
        if (exists(username)) {
            System.err.println("User username already exists");
            return;
        }
        LogisticOperator logisticOperator = new LogisticOperator(name, password, username, email, role);
        em.persist(logisticOperator);
    }

    public boolean exists(String username) {
        Query query = em.createQuery(
                "SELECT COUNT(s.username) FROM User s WHERE s.username = :username",
                Long.class
        );
        query.setParameter("username", username);
        return (Long)query.getSingleResult() > 0L;
    }

    public LogisticOperator find(long id) {
        LogisticOperator logisticOperator = em.find(LogisticOperator.class, id);
        if (logisticOperator == null) {
            System.err.println("Logistic Operator does not exist");
            return null;
        }
        return logisticOperator;
    }

    public LogisticOperator findByUsername(String username) {
        Query query = em.createQuery(
                "SELECT l FROM LogisticOperator l WHERE l.username = :username",
                LogisticOperator.class
        );
        query.setParameter("username", username);
        return (LogisticOperator) query.getSingleResult();
    }

    public LogisticOperator getLogisticOrders(long id) {
        LogisticOperator logisticOperator = this.find(id);
        if (logisticOperator == null) {
            System.err.println("Logistic Operator does not exist");
            return null;
        }
        Hibernate.initialize(logisticOperator.getOrders());
        return logisticOperator;
    }


    public void delete(long id) {
        LogisticOperator logisticOperator = this.find(id);
        if (logisticOperator == null) {
            System.err.println("Logistic Operator does not exist");
            return;
        }
        em.remove(logisticOperator);
    }

    public boolean update(long id, String name, String password, String username, String email, long role) {
        LogisticOperator logisticOperator = this.find(id);
        if (logisticOperator == null) {
            System.err.println("Logistic Operator does not exist");
            return false;
        }
        UserRole userRole = em.find(UserRole.class, role);
        if (userRole == null || userRole.getId() != 3) {
            System.err.println("Role does not exist or Is not a Logistic Operator");
            return false;
        }
        logisticOperator.setName(name);
        logisticOperator.setPassword(password);
        logisticOperator.setUsername(username);
        logisticOperator.setEmail(email);
        logisticOperator.setRole(userRole);
        em.merge(logisticOperator);
        return true;
    }
}
