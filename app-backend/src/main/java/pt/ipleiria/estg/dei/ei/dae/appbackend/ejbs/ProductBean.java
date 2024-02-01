package pt.ipleiria.estg.dei.ei.dae.appbackend.ejbs;

import jakarta.ejb.Startup;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import pt.ipleiria.estg.dei.ei.dae.appbackend.entitites.Manufacturer;
import pt.ipleiria.estg.dei.ei.dae.appbackend.entitites.Product;

@Stateless
public class ProductBean {
    @PersistenceContext
    private EntityManager em;

    public void create(String name, String brand, String description, int quantity, long manufacturer_id){
        if (exists(name)) {
            System.err.println("Product already exists");
            return;
        }
        Manufacturer manufacturer = em.find(Manufacturer.class, manufacturer_id);
        if (manufacturer == null) {
            System.err.println("Manufacturer does not exist");
            return;
        }
        Product product = new Product(name, brand, description, quantity, manufacturer);
        manufacturer.addProduct(product);
        em.persist(product);
    }

    public boolean exists(String name) {
        Query query = em.createQuery(
                "SELECT COUNT(p.name) FROM Product p WHERE p.name = :name",
                Long.class
        );
        query.setParameter("name", name);
        return (Long)query.getSingleResult() > 0L;
    }

}
