package pt.ipleiria.estg.dei.ei.dae.appbackend.ejbs;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.appbackend.entitites.*;

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
    private CartBean cartBean;
    @EJB
    private CartItemBean cartItemBean;
    @EJB
    private SensorBean sensorBean;
    @EJB
    private AlertBean alertBean;
    @EJB
    private UserRoleBean userRoleBean;
    @EJB
    private PackageTypeBean packageTypeBean;
    @EJB
    private UnidadeTypeBean unidadeTypeBean;
    @EJB
    private SensorTypeBean sensorTypeBean;
    @EJB
    private AlertTypeBean alertTypeBean;

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
        sensorTypeBean.create("Temperature"); //1
        sensorTypeBean.create("Humidity"); //2
        sensorTypeBean.create("Light"); //3
        sensorTypeBean.create("Shock"); //4
        sensorTypeBean.create("Open"); //5
        unidadeTypeBean.create("Celsius"); //1
        unidadeTypeBean.create("Percentage"); //2
        unidadeTypeBean.create("Lux"); //3
        unidadeTypeBean.create("G"); //4
        alertTypeBean.create("Dangerous Temperature"); //1
        alertTypeBean.create("Dangerous Humidity"); //2
        alertTypeBean.create("Dangerous Light"); //3
        alertTypeBean.create("Package Shock"); //4
        alertTypeBean.create("Package Open"); //5

        manufacturerBean.create("Joao", "password123", "joao", "joao@gmail.com", 1); //1
        customerBean.create("Sam", "password123", "sam", "sam@gmail.com", 2); //2
        customerBean.create("John", "password123", "john", "jhon@gmail.com", 2); //3
        cartBean.create(2, 0.0);//1
        cartBean.create(3, 0.0);//2

        productBean.create("Beer Cergal", "Cergal", "Beer 33cl", 100, 1);//1
        productBean.create("Beer Super Bock", "Super Bock", "Beer 33cl", 100, 1);//2

        SensorType sensorTemperature = sensorTypeBean.findSensorType(1);
        SensorType sensorHumidity = sensorTypeBean.findSensorType(2);
        SensorType sensorLight = sensorTypeBean.findSensorType(3);
        SensorType sensorShock = sensorTypeBean.findSensorType(4);
        SensorType sensorOpen = sensorTypeBean.findSensorType(5);
        List<SensorType> sensorTypes = new ArrayList<>();
        sensorTypes.add(sensorTemperature);
        sensorTypes.add(sensorHumidity);
        sensorTypes.add(sensorLight);
        sensorTypes.add(sensorShock);
        sensorTypes.add(sensorOpen);

        packageBean.create("Cergal 6x33cl", 1, "Plastic", sensorTypes);//1
        packageBean.create("Cergal 12x33cl", 2, "Cartoon", sensorTypes);//2
        packageBean.create("Super Bock 6x33cl", 1, "Plastic", sensorTypes);//3
        packageBean.create("Super Bock 12x33cl", 2, "Cartoon", sensorTypes);//4

        productPackageBean.create(1, 1, 3.48, 6, "Not available");//1
        productPackageBean.create(1, 2, 4.99, 12, "Not available");//2
        productPackageBean.create(2, 3, 2.99, 6, "Not available");//3
        productPackageBean.create(2, 4, 5.99, 12, "Not available");//4

        cartItemBean.create(1,1,2,6.96);//1
        Cart cart = cartBean.find(1);
        cart.setCount(14.94);

        cartItemBean.create(2,2,1,4.99);//2
        cartItemBean.create(2,3,1,5.98);//3
        Cart cart2 = cartBean.find(2);
        cart2.setCount(7.98);


        orderBean.create(2,currentDate, null,"Leiria","2410-000",
                "Portugal","Rua 1","Credit Card","Pending",0.0);//1
        orderBean.create(3,currentDate, null,"Lisboa","2410-150",
                "Portugal","Rua 2","Credit Card","Pending",0.0);//2

        orderItemBean.create(1,1,2,6.96);//1
        orderItemBean.create(2,1,1,4.99);//2
        orderItemBean.create(3,1,1,2.99);//3
        orderItemBean.create(4,1,1,5.99);//4
        Order order = orderBean.findOrder(1);
        order.setCount(20.93);

        orderItemBean.create(4,2,1,4.99);//5
        orderItemBean.create(3,2,1,2.99);//6
        Order order2 = orderBean.findOrder(2);
        order2.setCount(7.98);

        sensorBean.create(1, currentDate, 20.0, 1, 1, 1); //1
        sensorBean.create(2, currentDate, 150.0, 2, 1, 1); //2
        sensorBean.create(3, currentDate, 100.0, 3, 1, 1); //3
        sensorBean.create(4, currentDate, 0.0, 4, 1, 1); //4
        sensorBean.create(5, currentDate, 0.0, 4, 1, 1); //5
        alertBean.create(2, 1, currentDate, "Humidity too High!"); //1

    }
}
