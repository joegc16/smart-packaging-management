package pt.ipleiria.estg.dei.ei.dae.appbackend.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.*;
import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.appbackend.entitites.Manufacturer;
import pt.ipleiria.estg.dei.ei.dae.appbackend.entitites.Product;
import pt.ipleiria.estg.dei.ei.dae.appbackend.entitites.UserRole;

import java.util.List;

@Stateless
public class ManufacturerBean {
    @PersistenceContext
    private EntityManager em;

    public void create(String name, String password, String username, String email, long roleId) {
        UserRole role = em.find(UserRole.class, roleId);
        if (role == null || role.getId() != 1) {
            System.err.println("Role does not exist or Is not a Manufacturer");
        }
        if (exists(username)) {
            System.err.println("Manufacturer username already exists");
        }
        Manufacturer manufacturer = new Manufacturer(name, password, username, email, role);
        em.persist(manufacturer);
    }

    public Manufacturer find(long id){
        Manufacturer manufacturer = em.find(Manufacturer.class, id);
        if (manufacturer == null){
            System.err.println("Manufacturer Does not Exists");
        }
        return manufacturer;
    }

    public Manufacturer findByUsername(String username) {
        TypedQuery<Manufacturer> query = em.createQuery(
                "SELECT m FROM Manufacturer m WHERE m.username = :username", Manufacturer.class);
        query.setParameter("username", username);
        List<Manufacturer> manufacturers = query.getResultList();
        return manufacturers.isEmpty() ? null : manufacturers.get(0);
    }

    public boolean exists(String username) {
        Query query = em.createQuery(
                "SELECT COUNT(s.username) FROM User s WHERE s.username = :username",
                Long.class
        );
        query.setParameter("username", username);
        return (Long)query.getSingleResult() > 0L;
    }

    public Manufacturer getManufacturerProducts(long id) {
        Manufacturer manufacturer = this.find(id);
        if (manufacturer == null) {
            return null;
        }
        Hibernate.initialize(manufacturer.getProducts());
        return manufacturer;
    }

    public Manufacturer getManufacturerOrders(long id) {
        Manufacturer manufacturer = this.find(id);
        if (manufacturer == null) {
            return null;
        }
        Hibernate.initialize(manufacturer.getOrders());
        return manufacturer;
    }

    public void delete(long id) {
        Manufacturer manufacturer = this.find(id);
        if (manufacturer == null) {
            System.err.println("Manufacturer does not exist");
            return;
        }
        List<Product> products = manufacturer.getProducts();
        for (Product product : products) {
            em.remove(product);
        }
        em.remove(manufacturer);
    }

    public boolean update(long id, String name, String password, String username, String email, long role) {
        Manufacturer manufacturer = em.find(Manufacturer.class, id);
        if (manufacturer == null) {
            System.err.println("Manufacturer does not exist");
            return false;
        }
        UserRole userRole = em.find(UserRole.class, role);
        if (userRole == null || userRole.getId() != 1) {
            System.err.println("Role does not exist or Is not a Manufacturer");
            return false;
        }
        //em.lock(manufacturer, LockModeType.OPTIMISTIC);
        manufacturer.setName(name);
        manufacturer.setPassword(password);
        manufacturer.setUsername(username);
        manufacturer.setEmail(email);
        manufacturer.setRole(userRole);
        return true;
    }
}
