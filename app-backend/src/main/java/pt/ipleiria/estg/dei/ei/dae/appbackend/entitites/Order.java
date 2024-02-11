package pt.ipleiria.estg.dei.ei.dae.appbackend.entitites;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
@NamedQueries({
        @NamedQuery(
                name = "getAllOrders",
                query = "SELECT o FROM Order o ORDER BY o.id"
        )
})
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    private Customer customer;
    @ManyToOne
    private Manufacturer manufacturer;
    @ManyToOne
    private LogisticOperator logisticOperator;
    @NotNull
    private Date orderDate;
    private Date deliveryDate;
    private String estimatedDeliveryTime;
    private String packageLocation;
    @NotNull
    private String city;
    @NotNull
    private String postalCode;
    @NotNull
    private String country;
    @NotNull
    private String address;
    @NotNull
    private String paymentMethod;
    @NotNull
    private String status;
    @NotNull
    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;
    @NotNull
    private Double count;

    @OneToMany(mappedBy = "order")
    private List<Sensor> sensors;

    //@OneToMany(mappedBy = "order")
    //private List<OrderUpdates> updates;

    public Order() {
        this.orderItems = new ArrayList<>();
        this.sensors = new ArrayList<>();
    }

    public Order(Customer customer, Manufacturer manufacturer, LogisticOperator logisticOperator, Date orderDate,
                 Date deliveryDate, String estimatedDeliveryTime, String packageLocation, String city, String postalCode, String country,
                 String address, String paymentMethod, String status, Double count) {
        this.customer = customer;
        this.manufacturer = manufacturer;
        this.logisticOperator = logisticOperator;
        this.orderDate = orderDate;
        this.deliveryDate = deliveryDate;
        this.estimatedDeliveryTime = estimatedDeliveryTime;
        this.packageLocation = packageLocation;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
        this.address = address;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.orderItems = new ArrayList<>();
        this.sensors = new ArrayList<>();
        this.count = count;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public LogisticOperator getLogisticOperator() {
        return logisticOperator;
    }

    public void setLogisticOperator(LogisticOperator logisticOperator) {
        this.logisticOperator = logisticOperator;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getEstimatedDeliveryTime() {
        return estimatedDeliveryTime;
    }

    public void setEstimatedDeliveryTime(String estimatedDeliveryTime) {
        this.estimatedDeliveryTime = estimatedDeliveryTime;
    }

    public String getPackageLocation() {
        return packageLocation;
    }

    public void setPackageLocation(String packageLocation) {
        this.packageLocation = packageLocation;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
    }

    public void removeOrderItem(OrderItem orderItem) {
        orderItems.remove(orderItem);
    }

    public double getCount() {
        return count;
    }

    public void setCount(Double count) {
        this.count = count;
    }

    public List<Sensor> getSensors() {
        return sensors;
    }

    public void setSensors(List<Sensor> sensors) {
        this.sensors = sensors;
    }

    public void addSensor(Sensor sensor) {
        sensors.add(sensor);
    }

    public void removeSensor(Sensor sensor) {
        sensors.remove(sensor);
    }
}
