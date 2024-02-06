package pt.ipleiria.estg.dei.ei.dae.appbackend.dtos;

import java.io.Serializable;

public class SensorDTO implements Serializable {
    private long id;
    private String sensorType;
    private String timestamp;
    private String value;
    private String unitType;
    private long orderId;
    private long packageId;

    public SensorDTO() {
    }

    public SensorDTO(String sensorType, String timestamp, String value, String unitType, long orderId, long packageId) {
        this.sensorType = sensorType;
        this.timestamp = timestamp;
        this.value = value;
        this.unitType = unitType;
        this.orderId = orderId;
        this.packageId = packageId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSensorType() {
        return sensorType;
    }

    public void setSensorType(String sensorType) {
        this.sensorType = sensorType;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getPackageId() {
        return packageId;
    }

    public void setPackageId(long packageId) {
        this.packageId = packageId;
    }
}
