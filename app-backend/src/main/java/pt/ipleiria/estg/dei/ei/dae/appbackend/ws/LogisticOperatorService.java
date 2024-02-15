package pt.ipleiria.estg.dei.ei.dae.appbackend.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.appbackend.dtos.LogisticOperatorDTO;
import pt.ipleiria.estg.dei.ei.dae.appbackend.dtos.ManufacturerDTO;
import pt.ipleiria.estg.dei.ei.dae.appbackend.dtos.OrderDTO;
import pt.ipleiria.estg.dei.ei.dae.appbackend.ejbs.LogisticOperatorBean;
import pt.ipleiria.estg.dei.ei.dae.appbackend.entitites.LogisticOperator;
import pt.ipleiria.estg.dei.ei.dae.appbackend.entitites.Manufacturer;
import pt.ipleiria.estg.dei.ei.dae.appbackend.entitites.Order;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Path("/logistic-operators")
@Produces({MediaType.APPLICATION_JSON}) // Injects header "Content-Type: application/json"
@Consumes({MediaType.APPLICATION_JSON}) // Injects header "Accept: application/json"
public class LogisticOperatorService {
    @EJB
    LogisticOperatorBean logisticOperatorBean;

    @GET
    @Path("{id}")
    public Response getLogisticDetails(@PathParam("id") long id) {
        LogisticOperator logisticOperator = logisticOperatorBean.find(id);
        if (logisticOperator == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("ERROR_FINDING_LOGISTIC_OPERATOR")
                    .build();
        }
        return Response.ok(logisticToDTO(logisticOperator)).build();
    }

    @GET
    @Path("{id}/orders")
    public Response getLogisticOrders(@PathParam("id") long id){
        LogisticOperator logisticOperator = logisticOperatorBean.getLogisticOrders(id);
        if (logisticOperator == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("ERROR_FINDING_LOGISTIC_OPERATOR")
                    .build();
        }
        List<OrderDTO> dtos = ordersToDTOs(logisticOperator.getOrders());
        return Response.ok(dtos).build();

    }

    @POST
    @Path("/")
    public Response createLogistic(LogisticOperatorDTO logisticOperatorDTO) {
        if (logisticOperatorBean.exists(logisticOperatorDTO.getUsername()))
            return Response.status(Response.Status.CONFLICT).entity("Username already exists").build();

        logisticOperatorBean.create(
                logisticOperatorDTO.getName(),
                logisticOperatorDTO.getPassword(),
                logisticOperatorDTO.getUsername(),
                logisticOperatorDTO.getEmail(),
                logisticOperatorDTO.getRole()
        );
        LogisticOperator newLogistic = logisticOperatorBean.findByUsername(logisticOperatorDTO.getUsername());
        if (newLogistic == null)
            return Response.status(Response.Status.BAD_REQUEST).build();
        return
                Response.status(Response.Status.CREATED).entity(logisticToDTO(newLogistic)).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteLogistic(@PathParam("id") long id) {
        LogisticOperator logisticOperator = logisticOperatorBean.find(id);
        if (logisticOperator == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("ERROR_FINDING_LOGISTIC_OPERATOR")
                    .build();
        }
        logisticOperatorBean.delete(id);
        LogisticOperator deletedLogisticOperator = logisticOperatorBean.find(id);
        if (deletedLogisticOperator != null)
            return Response.status(Response.Status.BAD_REQUEST).entity("ERROR_DELETING_LOGISTIC_OPERATOR").build();
        return Response.ok().entity("DELETED SUCCESSFULLY").build();
    }

    @PUT
    @Path("{id}")
    public Response updateManufacturer(@PathParam("id") long id, LogisticOperatorDTO logisticOperatorDTO) {
        LogisticOperator existingLogistic = logisticOperatorBean.find(id);
        if (existingLogistic == null)
            return Response.status(Response.Status.NOT_FOUND).entity("LOGISTIC NOT FOUND").build();

        if (!existingLogistic.getUsername().equals(logisticOperatorDTO.getUsername()) && logisticOperatorBean.exists(logisticOperatorDTO.getUsername()))
            return Response.status(Response.Status.CONFLICT)
                    .entity("LOGISTIC OPERATOR NAME ALREADY EXISTS")
                    .build();

        if (logisticOperatorDTO.getRole() != 3)
            return Response.status(Response.Status.BAD_REQUEST).entity("ROLE IS NOT A LOGISTIC OPERATOR").build();

        boolean success = logisticOperatorBean.update(
                id,
                logisticOperatorDTO.getName(),
                logisticOperatorDTO.getPassword(),
                logisticOperatorDTO.getUsername(),
                logisticOperatorDTO.getEmail(),
                logisticOperatorDTO.getRole()
        );

        LogisticOperator updatedLogistic = logisticOperatorBean.find(id);
        if (!success || updatedLogistic == null)
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("ERROR_UPDATING_LOGISTIC").build();
        return Response.ok(logisticToDTO(updatedLogistic)).build();
    }


    private LogisticOperatorDTO logisticToDTO(LogisticOperator logisticOperator) {
        return new LogisticOperatorDTO(
                logisticOperator.getId(),
                logisticOperator.getName(),
                logisticOperator.getPassword(),
                logisticOperator.getUsername(),
                logisticOperator.getEmail(),
                logisticOperator.getRole().getId()
        );
    }

    private List<OrderDTO> ordersToDTOs(List<Order> orders) {
        return orders.stream().map(this::orderToDTO).collect(Collectors.toList());
    }

    private OrderDTO orderToDTO(Order order) {
        String deliveryDate = order.getDeliveryDate() == null ? null : order.getDeliveryDate().toString() ;
        if (deliveryDate == null)
            deliveryDate = "Not Defined";
        return new OrderDTO(
                order.getId(),
                order.getCustomer().getId(),
                order.getManufacturer().getId(),
                order.getLogisticOperator().getId(),
                order.getOrderDate().toString(),
                deliveryDate.toString(),
                order.getEstimatedDeliveryTime(),
                order.getPackageLocation(),
                order.getCity(),
                order.getPostalCode(),
                order.getCountry(),
                order.getAddress(),
                order.getPaymentMethod(),
                order.getStatus(),
                order.getCount(),
                null
        );
    }
}
