package pt.ipleiria.estg.dei.ei.dae.appbackend.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.appbackend.dtos.PackageDTO;
import pt.ipleiria.estg.dei.ei.dae.appbackend.dtos.ProductDTO;
import pt.ipleiria.estg.dei.ei.dae.appbackend.dtos.SensorDTO;
import pt.ipleiria.estg.dei.ei.dae.appbackend.dtos.SensorTypeDTO;
import pt.ipleiria.estg.dei.ei.dae.appbackend.ejbs.PackageBean;
import pt.ipleiria.estg.dei.ei.dae.appbackend.entitites.Package;
import pt.ipleiria.estg.dei.ei.dae.appbackend.entitites.Product;
import pt.ipleiria.estg.dei.ei.dae.appbackend.entitites.SensorType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Path("/packages")
@Produces({MediaType.APPLICATION_JSON}) // Injects header "Content-Type: application/json"
@Consumes({MediaType.APPLICATION_JSON}) // Injects header "Accept: application/json"
public class PackageService {
    @EJB
    private PackageBean packageBean;

    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/") // means: the relative url path is "/api/users"
    public List<PackageDTO> getAllPackages() {
        return packagesToDTOs(packageBean.getAll());
    }

    @GET
    @Path("/{code}")
    public Response getPackageDetails(@PathParam("code") long code) {
        Package pack = packageBean.findPackage(code);
        if (pack != null)
            return Response.ok(packageToDTO(pack)).build();

        return Response.status(Response.Status.NOT_FOUND)
                .entity("ERROR_FINDING_PACKAGE")
                .build();
    }

    @POST
    @Path("/")
    public Response createPackage(PackageDTO packageDTO) {
        if (packageBean.exists(packageDTO.getName()))
            return Response.status(Response.Status.CONFLICT).build();

        List<SensorType> sensorTypes = dtoToSensorType(packageDTO);
        Package pack = packageBean.create(
                packageDTO.getName(),
                packageDTO.getPackageType(),
                packageDTO.getMaterial(),
                sensorTypes
        );
        if (pack == null)
            return Response.status(Response.Status.BAD_REQUEST).build();

        return Response.status(Response.Status.CREATED).entity(packageToDTO(pack)).build();
    }

    @PUT
    @Path("/{code}")
    public Response updatePackage(@PathParam("code") long code, PackageDTO packageDTO) {
        Package existingPackage = packageBean.findPackage(code);
        if (existingPackage == null)
            return Response.status(Response.Status.NOT_FOUND).entity("PACKAGE NOT FOUND").build();

        List<SensorType> sensorTypes = dtoToSensorType(packageDTO);
        boolean success = packageBean.update(
                code,
                packageDTO.getName(),
                packageDTO.getPackageType(),
                packageDTO.getMaterial(),
                sensorTypes
        );

        Package updatedPackage = packageBean.findPackage(code);
        if (!success || updatedPackage == null) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.status(Response.Status.OK).entity("PACKAGE UPDATED SUCCESSFULLY").build();
    }

    @DELETE
    @Path("/{code}")
    public Response deletePackage(@PathParam("code") long code) {
        Package existingPackage = packageBean.findPackage(code);
        if (existingPackage == null)
            return Response.status(Response.Status.NOT_FOUND).entity("PACKAGE NOT FOUND").build();

        if (packageBean.delete(code))
            if (packageBean.findPackage(code) == null)
                return Response.ok("PACKAGE DELETE SUCCESSFULLY").build();

        return Response.status(Response.Status.NOT_FOUND)
                .entity("ERROR_DELETING_PACKAGE")
                .build();
    }

    private List<PackageDTO> packagesToDTOs(List<Package> packages) {
        return packages.stream().map(this::packageToDTO).collect(Collectors.toList());
    }

    private PackageDTO packageToDTO(Package pack) {
        Package pack2 = packageBean.findPackage(pack.getCode());
        return new PackageDTO(
                pack2.getCode(),
                pack2.getName(),
                pack2.getPackageType().getId(),
                pack2.getMaterial(),
                sensorsTypesToDTOs(pack2.getSensorsTypes())
        );
    }

    private List<SensorTypeDTO> sensorsTypesToDTOs(List<SensorType> sensorTypes) {
        return sensorTypes.stream().map(this::sensorTypeToDTO).collect(Collectors.toList());
    }

    private SensorTypeDTO sensorTypeToDTO(SensorType sensorType) {
        return new SensorTypeDTO(
                sensorType.getId(),
                sensorType.getName()
        );
    }


    private List<SensorType> dtoToSensorType(PackageDTO packageDTO) {
        List <SensorType> sensorTypes = new ArrayList<>();
        if (packageDTO.getSensorsTypes() != null)
        {
            for (SensorTypeDTO sensorTypeDTO : packageDTO.getSensorsTypes()) {
                SensorType sensorType = packageBean.getSensorTypeById(sensorTypeDTO.getId());
                if (sensorType != null)
                    sensorTypes.add(sensorType);
            }
        }
        return sensorTypes;
    }
}
