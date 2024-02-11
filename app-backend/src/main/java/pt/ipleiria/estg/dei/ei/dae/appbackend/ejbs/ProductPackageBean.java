package pt.ipleiria.estg.dei.ei.dae.appbackend.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import pt.ipleiria.estg.dei.ei.dae.appbackend.entitites.Product;
import pt.ipleiria.estg.dei.ei.dae.appbackend.entitites.Package;
import pt.ipleiria.estg.dei.ei.dae.appbackend.entitites.ProductPackage;
import pt.ipleiria.estg.dei.ei.dae.appbackend.entitites.ProductPackageId;

import java.util.List;

@Stateless
public class ProductPackageBean{
    @PersistenceContext
    private EntityManager em;

    public void create(long productCode, long packageCode, Double price, int quantityInPackage, String image) {
        Product product = em.find(Product.class, productCode);
        if (product == null) {
            System.err.println("Product does not exist");
            return;
        }
        Package pack = em.find(Package.class, packageCode);
        if (pack == null) {
            System.err.println("Package does not exist");
            return;
        }
        if (this.exists(productCode, packageCode)) {
            System.err.println("Product Package already exists");
            return;
        }
        ProductPackageId id = new ProductPackageId(productCode, packageCode);
        ProductPackage productPackage = new ProductPackage(id, product, pack, price, quantityInPackage, image);
        product.addProductPackage(productPackage);
        pack.addProductPackage(productPackage);
        em.persist(productPackage);
    }

    public List<ProductPackage> getAll() {
        return em.createNamedQuery("getAllProductPackages", ProductPackage.class).getResultList();
    }

    public ProductPackage findProductPackage(long productCode, long packageCode) {
        TypedQuery<ProductPackage> query = em.createQuery(
                "SELECT pp FROM ProductPackage pp WHERE pp.product.code = :productCode AND pp.pack.code = :packageCode",
                ProductPackage.class
        );
        query.setParameter("productCode", productCode);
        query.setParameter("packageCode", packageCode);

        List<ProductPackage> productPackages = query.getResultList();
        return productPackages.isEmpty() ? null : productPackages.get(0);
    }

    public ProductPackage findProductPackageById(long id) {
        ProductPackage productPackage = em.find(ProductPackage.class, id);
        if (productPackage == null) {
            System.err.println("Product Package does not exist");
            return null;
        }
        return productPackage;
    }


    public boolean exists(long productCode, long packageCode) {
        Query query = em.createQuery(
                "SELECT COUNT(*) FROM ProductPackage pp WHERE pp.product.code = :productCode AND pp.pack.code = :packageCode",
                Long.class
        );
        query.setParameter("productCode", productCode);
        query.setParameter("packageCode", packageCode);
        return (Long)query.getSingleResult() > 0L;
    }

    public boolean update(long id, Double price,
                       int quantityProductsInPackage, String image) {
        ProductPackage productPackage = em.find(ProductPackage.class, id);
        if (productPackage == null) {
            System.err.println("Product Package does not exist");
            return false;
        }
        productPackage.setPrice(price);
        productPackage.setQuantityProductsInPackage(quantityProductsInPackage);
        productPackage.setImage(image);
        em.merge(productPackage);
        return true;
    }

    public void delete(long id) {
        ProductPackage productPackage = this.findProductPackageById(id);
        if (productPackage == null) {
            System.err.println("Product Package does not exist");
            return;
        }
        em.remove(productPackage);
    }
}
