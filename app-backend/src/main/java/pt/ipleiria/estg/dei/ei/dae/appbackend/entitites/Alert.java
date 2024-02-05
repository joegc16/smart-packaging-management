package pt.ipleiria.estg.dei.ei.dae.appbackend.entitites;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "alerts")
public class Alert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    private AlertType alertType;
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
    private Date timestamp;
    private String message;

    public Alert() {
    }

    public Alert(AlertType alertType, Order order, Date timestamp, String message) {
        this.alertType = alertType;
        this.order = order;
        this.timestamp = timestamp;
        this.message = message;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public AlertType getAlertType() {
        return alertType;
    }

    public void setAlertType(AlertType alertType) {
        this.alertType = alertType;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
