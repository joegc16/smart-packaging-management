package pt.ipleiria.estg.dei.ei.dae.appbackend.dtos;

import pt.ipleiria.estg.dei.ei.dae.appbackend.entitites.AlertType;
import pt.ipleiria.estg.dei.ei.dae.appbackend.entitites.Order;

import java.io.Serializable;
import java.util.Date;

public class AlertDTO implements Serializable{
    private long id;
    private String alertType;
    private long orderId;
    private String timestamp;
    private String message;

    public AlertDTO() {
    }

    public AlertDTO(String alertType, long orderId, String timestamp, String message) {
        this.alertType = alertType;
        this.orderId = orderId;
        this.timestamp = timestamp;
        this.message = message;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAlertType() {
        return alertType;
    }

    public void setAlertType(String alertType) {
        this.alertType = alertType;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
