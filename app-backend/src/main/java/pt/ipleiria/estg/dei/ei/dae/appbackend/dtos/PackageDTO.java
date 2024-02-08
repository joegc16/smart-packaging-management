package pt.ipleiria.estg.dei.ei.dae.appbackend.dtos;

import java.io.Serializable;
import java.util.List;

public class PackageDTO implements Serializable {
    private long code;
    private String name;
    private String packageType;
    private String material;
    private int totalQuantity;
    private List<SensorTypeDTO> sensorsTypes;

    public PackageDTO() {
    }

    public PackageDTO(long code,String name, String packageType, String material, int totalQuantity, List<SensorTypeDTO> sensorsTypes) {
        this.code = code;
        this.name = name;
        this.packageType = packageType;
        this.material = material;
        this.totalQuantity = totalQuantity;
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

    public String getPackageType() {
        return packageType;
    }

    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public List<SensorTypeDTO> getSensorsTypes() {
        return sensorsTypes;
    }

    public void setSensorsTypes(List<SensorTypeDTO> sensorsTypes) {
        this.sensorsTypes = sensorsTypes;
    }
}
