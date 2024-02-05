package pt.ipleiria.estg.dei.ei.dae.appbackend.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.glassfish.jaxb.runtime.v2.runtime.reflect.Lister;
import pt.ipleiria.estg.dei.ei.dae.appbackend.entitites.Package;
import pt.ipleiria.estg.dei.ei.dae.appbackend.entitites.PackageType;
import pt.ipleiria.estg.dei.ei.dae.appbackend.entitites.Product;
import pt.ipleiria.estg.dei.ei.dae.appbackend.entitites.SensorType;

import java.util.List;

@Stateless
public class PackageBean {
    @PersistenceContext
    private EntityManager em;

    public void create(String name, long package_type_id, String material, List<SensorType> sensorsTypes){
        PackageType packageType = em.find(PackageType.class, package_type_id);
        if (packageType == null) {
            System.err.println("Package type does not exist");
            return;
        }
        Package pack = new Package(name, packageType, material, sensorsTypes);
        if (pack == null) {
            System.err.println("Something Wrong when creating a package");
            return;
        }
        em.persist(pack);
    }

    public boolean exists(String name) {
        Query query = em.createQuery(
                "SELECT COUNT(p.name) FROM Package p WHERE p.name = :name",
                Long.class
        );
        query.setParameter("name", name);
        return (Long)query.getSingleResult() > 0L;
    }


}
