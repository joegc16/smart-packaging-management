package pt.ipleiria.estg.dei.ei.dae.appbackend.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.glassfish.jaxb.runtime.v2.runtime.reflect.Lister;
import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.appbackend.entitites.Package;
import pt.ipleiria.estg.dei.ei.dae.appbackend.entitites.PackageType;
import pt.ipleiria.estg.dei.ei.dae.appbackend.entitites.Product;
import pt.ipleiria.estg.dei.ei.dae.appbackend.entitites.SensorType;

import java.util.List;

@Stateless
public class PackageBean {
    @PersistenceContext
    private EntityManager em;

    public Package create(String name, long package_type_id, String material, List<SensorType> sensorsTypes){
        PackageType packageType = em.find(PackageType.class, package_type_id);
        if (packageType == null) {
            System.err.println("Package type does not exist");
            return null;
        }
        Package pack = new Package(name, packageType, material, sensorsTypes);
        if (pack == null) {
            System.err.println("Something Wrong when creating a package");
            return null;
        }
        em.persist(pack);
        return pack;
    }

    public boolean exists(String name) {
        Query query = em.createQuery(
                "SELECT COUNT(p.name) FROM Package p WHERE p.name = :name",
                Long.class
        );
        query.setParameter("name", name);
        return (Long)query.getSingleResult() > 0L;
    }


    public List<Package> getAll() {
        return em.createNamedQuery("getAllPackages", Package.class).getResultList();
    }

    public Package findPackage(long code) {
        Package pack = em.find(Package.class, code);
        if (pack == null) {
            System.err.println("Package" + code + " NOT FOUND");
            return null;
        }
        Hibernate.initialize(pack.getSensorsTypes());
        return pack;
    }

    public boolean update(long code, String name, long packageTypeId, String material,
                          List<SensorType> sensorsTypes)
    {
        Package pack = em.find(Package.class, code);
        if (pack == null) {
            System.err.println("Package" + code + " NOT FOUND");
            return false;
        }
        PackageType packageType = em.find(PackageType.class, packageTypeId);
        if (packageType == null) {
            System.err.println("Package type does not exist");
            return false;
        }
        pack.setName(name);
        pack.setPackageType(packageType);
        pack.setMaterial(material);
        pack.setSensorsTypes(sensorsTypes);
        em.merge(pack);
        return true;
    }

    public boolean delete(long code) {
        Package pack = em.find(Package.class, code);
        if (pack == null) {
            System.err.println("Package" + code + " NOT FOUND");
            return false;
        }
        em.remove(pack);
        return true;
    }

    public SensorType getSensorTypeById(long id) {
        return em.find(SensorType.class, id);
    }

}
