package pt.ipleiria.estg.dei.ei.dae.appbackend.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.appbackend.dtos.OrderDTO;
import pt.ipleiria.estg.dei.ei.dae.appbackend.dtos.OrderItemDTO;
import pt.ipleiria.estg.dei.ei.dae.appbackend.dtos.SensorDTO;
import pt.ipleiria.estg.dei.ei.dae.appbackend.dtos.UserDTO;
import pt.ipleiria.estg.dei.ei.dae.appbackend.ejbs.OrderBean;
import pt.ipleiria.estg.dei.ei.dae.appbackend.ejbs.SensorBean;
import pt.ipleiria.estg.dei.ei.dae.appbackend.entitites.Order;
import pt.ipleiria.estg.dei.ei.dae.appbackend.entitites.OrderItem;
import pt.ipleiria.estg.dei.ei.dae.appbackend.entitites.Sensor;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Path("/orders")
@Produces({MediaType.APPLICATION_JSON}) // Injects header "Content-Type: application/json"
@Consumes({MediaType.APPLICATION_JSON}) // Injects header "Accept: application/json"
public class OrderService {
    @EJB
    private OrderBean orderBean;

    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/") // means: the relative url path is "/api/users"
    public List<OrderDTO> getAllOrders() {
        return ordersToDTOs(orderBean.getAll());
    }

    @GET
    @Path("{id}")
    public Response getOrderDetails(@PathParam("id") long id) {
        Order order = orderBean.findOrder(id);
        if (order != null) {
            return Response.ok(orderToDTO(order)).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("ERROR_FINDING_ORDER")
                .build();
    }

    @GET
    @Path("/{id}/items/")
    public Response getOrderItems(@PathParam("id") long id) {
        Order order = orderBean.getOrderItems(id);
        if (order != null) {
            List<OrderItemDTO> dtos = itemsToDTOs(order.getOrderItems());
            return Response.ok(dtos).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("ERROR_FINDING_Order_Sensors")
                .build();
    }

    @GET
    @Path("/{id}/sensors/")
    public Response getOrderSensors(@PathParam("id") long id) {
        Order order = orderBean.getOrderSensors(id);
        if (order != null) {
            List<SensorDTO> dtos = sensorsToDTOs(order.getSensors());
            return Response.ok(dtos).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("ERROR_FINDING_Order_Sensors")
                .build();
    }

    private List<OrderDTO> ordersToDTOs(List<Order> orders) {
        return orders.stream().map(this::orderToDTO).collect(Collectors.toList());
    }

    private OrderDTO orderToDTO(Order order) {
        Date deliveryDate = order.getDeliveryDate();
        if (deliveryDate == null)
            deliveryDate = new Date();
        return new OrderDTO(
                order.getId(),
                order.getCustomer().getId(),
                order.getManufacturer().getId(),
                order.getLogisticOperator().getId(),
                order.getOrderDate().toString(),
                deliveryDate.toString(),
                order.getCity(),
                order.getPostalCode(),
                order.getCountry(),
                order.getAddress(),
                order.getPaymentMethod(),
                order.getStatus(),
                order.getCount()
        );
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

    private List<OrderItemDTO> itemsToDTOs(List<OrderItem> items) {
        return items.stream().map(this::itemToDTO).collect(Collectors.toList());
    }

    private OrderItemDTO itemToDTO(OrderItem item) {
        return new OrderItemDTO(
                item.getProductPackage().getId(),
                item.getOrder().getId(),
                item.getQuantity(),
                item.getSubPrice()
        );
    }
}
