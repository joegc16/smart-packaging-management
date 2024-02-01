package pt.ipleiria.estg.dei.ei.dae.appbackend.ejbs;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.appbackend.entitites.Product;

import java.util.logging.Logger;

@Startup
@Singleton
public class ConfigBean {
    @PersistenceContext
    private EntityManager em;
    @EJB
    private ManufacturerBean manufacturerBean;
    @EJB
    private ProductBean productBean;
    @EJB
    private PackageBean packageBean;
    @EJB
    private ProductPackageBean productPackageBean;
    @EJB
    private UserRoleBean userRoleBean;
    @EJB
    private PackageTypeBean packageTypeBean;

    private static final Logger logger = Logger.getLogger("ejbs.ConfigBean");

    @PostConstruct
    public void populateDB() {
        logger.info("ConfigBean: Populating DB");

        userRoleBean.create("Manufacturer"); //1
        userRoleBean.create("Customer"); //2
        userRoleBean.create("LogisticsOperator"); //3
        packageTypeBean.create("Primary"); //1
        packageTypeBean.create("Secondary"); //2
        packageTypeBean.create("Tertiary"); //3

        manufacturerBean.create("Joao", "password123", "joao", "joao@gmail.com", 1);

        productBean.create("Beer Cergal", "Cergal", "Beer 33cl", 100, 1);
        productBean.create("Beer Super Bock", "Super Bock", "Beer 33cl", 100, 1);

        packageBean.create("Cergal 6x33cl", 1, "Plastic");
        packageBean.create("Cergal 12x33cl", 2, "Cartoon");
        packageBean.create("Super Bock 6x33cl", 1, "Plastic");
        packageBean.create("Super Bock 12x33cl", 2, "Cartoon");

        productPackageBean.create(1, 1, 3.48, 6, "Not available");
        productPackageBean.create(1, 2, 4.99, 12, "Not available");
        productPackageBean.create(2, 3, 2.99, 6, "Not available");
        productPackageBean.create(2, 4, 5.99, 12, "Not available");
    }
}
