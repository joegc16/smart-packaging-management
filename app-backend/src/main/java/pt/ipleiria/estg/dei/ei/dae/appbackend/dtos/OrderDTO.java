package pt.ipleiria.estg.dei.ei.dae.appbackend.dtos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OrderDTO implements Serializable {
    private long id;
    private long customerId;
    private long manufacturerId;
    private long logisticOperatorId;
    private String orderDate;
    private String deliveryDate;
    private String estimatedDeliveryTime;
    private String packageLocation;
    private String city;
    private String postalCode;
    private String country;
    private String address;
    private String paymentMethod;
    private String status;
    private List<OrderItemDTO> orderItems;
    private Double count;
    private List<SensorDTO> sensors;

    public OrderDTO() {
        orderItems = new ArrayList<>();
        sensors = new ArrayList<>();
    }

    public OrderDTO(long id,long customerId, long manufacturerId, long logisticOperatorId,
                    String orderDate, String deliveryDate, String estimatedDeliveryTime,
                    String packageLocation, String city,  String postalCode, String country, String address,
                    String paymentMethod, String status, Double count, List<OrderItemDTO> orderItems) {
        this.id = id;
        this.customerId = customerId;
        this.manufacturerId = manufacturerId;
        this.logisticOperatorId = logisticOperatorId;
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
        this.count = count;
        this.orderItems = orderItems;
        this.sensors = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public long getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(long manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public long getLogisticOperatorId() {
        return logisticOperatorId;
    }

    public void setLogisticOperatorId(long logisticOperatorId) {
        this.logisticOperatorId = logisticOperatorId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
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

    public List<OrderItemDTO> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemDTO> orderItems) {
        this.orderItems = orderItems;
    }

    public Double getCount() {
        return count;
    }

    public void setCount(Double count) {
        this.count = count;
    }

    public List<SensorDTO> getSensors() {
        return sensors;
    }

    public void setSensors(List<SensorDTO> sensors) {
        this.sensors = sensors;
    }
}
