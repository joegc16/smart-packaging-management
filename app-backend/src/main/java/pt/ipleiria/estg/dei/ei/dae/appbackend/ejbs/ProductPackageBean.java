package pt.ipleiria.estg.dei.ei.dae.appbackend.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.appbackend.entitites.Product;
import pt.ipleiria.estg.dei.ei.dae.appbackend.entitites.Package;
import pt.ipleiria.estg.dei.ei.dae.appbackend.entitites.ProductPackage;
import pt.ipleiria.estg.dei.ei.dae.appbackend.entitites.ProductPackageId;

@Stateless
public class ProductPackageBean{
    @PersistenceContext
    private EntityManager em;

    public void create(long productId, long packageId, Double price, int quantityInPackage, int quantity, String image) {
        Product product = em.find(Product.class, productId);
        if (product == null) {
            System.err.println("Product does not exist");
            return;
        }
        Package pack = em.find(Package.class, packageId);
        if (pack == null) {
            System.err.println("Package does not exist");
            return;
        }
        ProductPackageId id = new ProductPackageId(productId, packageId);
        ProductPackage productPackage = new ProductPackage(id, product, pack, price, quantityInPackage, quantity, image);
        product.addProductPackage(productPackage);
        pack.addProductPackage(productPackage);
        em.persist(productPackage);
    }
}
