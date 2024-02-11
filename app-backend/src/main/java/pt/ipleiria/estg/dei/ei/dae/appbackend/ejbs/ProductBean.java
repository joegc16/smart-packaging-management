package pt.ipleiria.estg.dei.ei.dae.appbackend.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import pt.ipleiria.estg.dei.ei.dae.appbackend.entitites.Manufacturer;
import pt.ipleiria.estg.dei.ei.dae.appbackend.entitites.Product;

import java.util.List;

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

    public List<Product> getAll() {
        return em.createNamedQuery("getAllProducts", Product.class).getResultList();
    }

    public Product findProduct(long code) {
        return em.find(Product.class, code);
    }

    public Product findByName(String name) {
        TypedQuery<Product> query = em.createQuery(
                "SELECT p FROM Product p WHERE p.name = :name", Product.class
        );
        query.setParameter("name", name);
        List<Product> products = query.getResultList();
        return products.isEmpty() ? null : products.get(0);
    }

    public boolean exists(String name) {
        Query query = em.createQuery(
                "SELECT COUNT(p.name) FROM Product p WHERE p.name = :name",
                Long.class
        );
        query.setParameter("name", name);
        return (Long)query.getSingleResult() > 0L;
    }

    public boolean update(long code, String name, String brand, String description, int totalQuantity,
                          String manufacturerUsername) {
        Product existingProduct = this.findProduct(code);

        if (!existingProduct.getManufacturer().getUsername().equals(manufacturerUsername)) {
            System.err.println("UNAUTHORIZED UPDATE: Manufacturer does not match");
            return false;
        }

        existingProduct.setName(name);
        existingProduct.setBrand(brand);
        existingProduct.setDescription(description);
        existingProduct.setTotalQuantity(totalQuantity);
        em.merge(existingProduct);
        return true;
    }

    public boolean delete(long code) {
        Product product = em.find(Product.class, code);
        if (product == null)
            return false;
        em.remove(product);
        return true;
    }
}
