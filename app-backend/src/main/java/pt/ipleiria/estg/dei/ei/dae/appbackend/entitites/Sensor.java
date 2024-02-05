package pt.ipleiria.estg.dei.ei.dae.appbackend.entitites;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

@Entity
@Table(name = "sensors")
public class Sensor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    @ManyToOne
    private SensorType sensorType;
    @NotNull
    private Date timestamp;
    @NotNull
    private double value;
    @NotNull
    @ManyToOne
    private UnidadeType unidadeType;
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
    @ManyToOne
    @JoinColumn(name = "package_id", nullable = false)
    private Package pack;

    public Sensor() {
    }

    public Sensor(SensorType sensorType, Date timestamp, double value,
                  UnidadeType unidadeType, Order order, Package pack) {
        this.sensorType = sensorType;
        this.timestamp = timestamp;
        this.value = value;
        this.unidadeType = unidadeType;
        this.order = order;
        this.pack = pack;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public SensorType getSensorType() {
        return sensorType;
    }

    public void setSensorType(SensorType sensorType) {
        this.sensorType = sensorType;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public UnidadeType getUnidadeType() {
        return unidadeType;
    }

    public void setUnidadeType(UnidadeType unidadeType) {
        this.unidadeType = unidadeType;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Package getPack() {
        return pack;
    }

    public void setPack(Package pack) {
        this.pack = pack;
    }
}
