package pt.ipleiria.estg.dei.ei.dae.appbackend.ejbs;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.appbackend.entitites.OrderItem;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Startup
@Singleton
public class ConfigBean {
    @PersistenceContext
    private EntityManager em;
    @EJB
    private ManufacturerBean manufacturerBean;
    @EJB
    private CustomerBean customerBean;
    @EJB
    private ProductBean productBean;
    @EJB
    private PackageBean packageBean;
    @EJB
    private ProductPackageBean productPackageBean;
    @EJB
    private OrderBean orderBean;
    @EJB
    private OrderItemBean orderItemBean;
    @EJB
    private UserRoleBean userRoleBean;
    @EJB
    private PackageTypeBean packageTypeBean;

    private static final Logger logger = Logger.getLogger("ejbs.ConfigBean");

    @PostConstruct
    public void populateDB() {
        logger.info("ConfigBean: Populating DB");
        LocalDate d1 = LocalDate.now();
        LocalDate d2 = d1.plusDays(1);
        Date currentDate = java.sql.Date.valueOf(d1);
        Date nextDay = java.sql.Date.valueOf(d2);

        userRoleBean.create("Manufacturer"); //1
        userRoleBean.create("Customer"); //2
        userRoleBean.create("LogisticsOperator"); //3
        packageTypeBean.create("Primary"); //1
        packageTypeBean.create("Secondary"); //2
        packageTypeBean.create("Tertiary"); //3

        manufacturerBean.create("Joao", "password123", "joao", "joao@gmail.com", 1);
        customerBean.create("Sam", "password123", "sam", "sam@gmail.com", 2);

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

        orderBean.create(2,currentDate, null,"Leiria","2410-000","Portugal","Rua 1","Credit Card","Pending",0.0);
        List<OrderItem> orderItemList = new ArrayList<>();

        OrderItem item1 = orderItemBean.create(1,1,2,6.96);
        OrderItem item2 = orderItemBean.create(2,1,1,4.99);
        OrderItem item3 = orderItemBean.create(3,1,1,2.99);
        OrderItem item4 = orderItemBean.create(4,1,1,5.99);
        //orderItemList.add(item1);
        //orderItemList.add(item2);
        //orderItemList.add(item3);
        //orderItemList.add(item4);

        //orderBean.updateOrderItems(1, orderItemList);

    }
}
