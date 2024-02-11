package pt.ipleiria.estg.dei.ei.dae.appbackend.entitites;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "packages")
@NamedQueries({
        @NamedQuery(
                name = "getAllPackages",
                query = "SELECT p FROM Package p ORDER BY p.code"
        )
})
public class Package {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long code;
    @NotNull
    private String name;
    @ManyToOne
    @NotNull
    private PackageType packageType;
    @NotNull
    private String material;
    @OneToMany(mappedBy = "pack")
    private List<ProductPackage> productPackages;
    @ManyToMany
    @JoinTable(
            name = "packages_sensor_types",
            joinColumns = @JoinColumn(name = "package_code"),
            inverseJoinColumns = @JoinColumn(name = "sensor_id")
    )
    private List<SensorType> sensorsTypes;

    public Package() {
        this.productPackages = new ArrayList<>();
        this.sensorsTypes = new ArrayList<>();
    }

    public Package(String name, PackageType packageType, String material) {
        this.name = name;
        this.packageType = packageType;
        this.material = material;
        this.productPackages = new ArrayList<>();
        this.sensorsTypes = new ArrayList<>();
    }

    public Package(String name, PackageType packageType, String material, List<SensorType> sensorsTypes) {
        this.name = name;
        this.packageType = packageType;
        this.material = material;
        this.productPackages = new ArrayList<>();
        this.sensorsTypes = sensorsTypes;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PackageType getPackageType() {
        return packageType;
    }

    public void setPackageType(PackageType packageType) {
        this.packageType = packageType;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public List<ProductPackage> getProductPackages() {
        return productPackages;
    }

    public void setProductPackages(List<ProductPackage> productPackages) {
        this.productPackages = productPackages;
    }

    public void addProductPackage(ProductPackage productPackage){
        this.productPackages.add(productPackage);
    }

    public void removeProductPackage(ProductPackage productPackage){
        this.productPackages.remove(productPackage);
    }

    public List<SensorType> getSensorsTypes() {
        return sensorsTypes;
    }

    public void setSensorsTypes(List<SensorType> sensors) {
        this.sensorsTypes = sensors;
    }

    public void addSensorType(SensorType sensorType) {
        sensorsTypes.add(sensorType);
    }

    public void addSensorTypeById(long id) {
        SensorType sensorType = new SensorType();
        sensorType.setId(id);
        sensorsTypes.add(sensorType);
    }
    public void removeSensorType(SensorType sensorType) {
        sensorsTypes.remove(sensorType);
    }
}
