package pt.ipleiria.estg.dei.ei.dae.appbackend.dtos;

import java.io.Serializable;
import java.util.List;

public class PackageDTO implements Serializable {
    private long code;
    private String name;
    private long packageType;
    private String material;
    private List<SensorTypeDTO> sensorsTypes;

    public PackageDTO() {
    }

    public PackageDTO(long code,String name, long packageType, String material, List<SensorTypeDTO> sensorsTypes) {
        this.code = code;
        this.name = name;
        this.packageType = packageType;
        this.material = material;
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

    public long getPackageType() {
        return packageType;
    }

    public void setPackageType(long packageType) {
        this.packageType = packageType;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public List<SensorTypeDTO> getSensorsTypes() {
        return sensorsTypes;
    }

    public void setSensorsTypes(List<SensorTypeDTO> sensorsTypes) {
        this.sensorsTypes = sensorsTypes;
    }
}
