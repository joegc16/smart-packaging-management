package pt.ipleiria.estg.dei.ei.dae.appbackend.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.appbackend.dtos.SensorDTO;
import pt.ipleiria.estg.dei.ei.dae.appbackend.dtos.UserDTO;
import pt.ipleiria.estg.dei.ei.dae.appbackend.ejbs.SensorBean;
import pt.ipleiria.estg.dei.ei.dae.appbackend.entitites.Sensor;

import java.util.List;
import java.util.stream.Collectors;

@Path("/sensors")
@Produces({MediaType.APPLICATION_JSON}) // Injects header "Content-Type: application/json"
@Consumes({MediaType.APPLICATION_JSON}) // Injects header "Accept: application/json"
public class SensorService {
    @EJB
    private SensorBean sensorBean;

    @GET
    @Path("/{id}")
    public Response getSensorDetails(@PathParam("id") long id){
        Sensor sensor = sensorBean.findSensor(id);
        if (sensor != null) {
            return Response.ok(sensorToDTO(sensor)).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("ERROR_FINDING_SENSOR")
                .build();
    }

    private List<SensorDTO> sensorsToDTOs(List<Sensor> sensors) {
        return sensors.stream().map(this::sensorToDTO).collect(Collectors.toList());
    }

    private SensorDTO sensorToDTO(Sensor sensor) {
        return new SensorDTO(
                sensor.getId(),
                sensor.getSensorType().getName(),
                sensor.getTimestamp().toString(),
                sensor.getValue(),
                sensor.getUnitType().getName(),
                sensor.getOrder().getId(),
                sensor.getPack().getCode()
        );
    }
}
