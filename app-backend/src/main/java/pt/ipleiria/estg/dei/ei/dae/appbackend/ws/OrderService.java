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
import pt.ipleiria.estg.dei.ei.dae.appbackend.ejbs.OrderItemBean;
import pt.ipleiria.estg.dei.ei.dae.appbackend.ejbs.SensorBean;
import pt.ipleiria.estg.dei.ei.dae.appbackend.entitites.Order;
import pt.ipleiria.estg.dei.ei.dae.appbackend.entitites.OrderItem;
import pt.ipleiria.estg.dei.ei.dae.appbackend.entitites.Sensor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Path("/orders")
@Produces({MediaType.APPLICATION_JSON}) // Injects header "Content-Type: application/json"
@Consumes({MediaType.APPLICATION_JSON}) // Injects header "Accept: application/json"
public class OrderService {
    @EJB
    private OrderBean orderBean;
    @EJB
    private OrderItemBean orderItemBean;

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


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

    @POST
    @Path("/")
    public Response createOrder(OrderDTO orderDTO) throws ParseException {
         Order newOrder = orderBean.create(
                orderDTO.getCustomerId(),
                orderDTO.getManufacturerId(),
                orderDTO.getLogisticOperatorId(),
                dateFormat.parse(orderDTO.getOrderDate()),
                null,
                orderDTO.getEstimatedDeliveryTime(),
                orderDTO.getPackageLocation(),
                orderDTO.getCity(),
                orderDTO.getPostalCode(),
                orderDTO.getCountry(),
                orderDTO.getAddress(),
                orderDTO.getPaymentMethod(),
                orderDTO.getStatus(),
                orderDTO.getCount()
        );
        if (newOrder == null)
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("ERROR_CREATING_ORDER").build();

        List<OrderItemDTO> orderItems = orderDTO.getOrderItems();
        for (OrderItemDTO orderItemDTO : orderItems) {
            orderItemBean.create(
                    orderItemDTO.getProductPackageId(),
                    newOrder.getId(),
                    orderItemDTO.getQuantity(),
                    orderItemDTO.getSubPrice()
            );
        }
        return Response.ok(orderToDTOWithOrderItem(newOrder)).build();

    }

    @PUT
    @Path("/{id}")
    public Response updateOrder(@PathParam("id") long id, OrderDTO orderDTO)
            throws ParseException {
        Order existingOrder = orderBean.findOrder(id);
        if (existingOrder == null)
            return Response.status(Response.Status.NOT_FOUND).entity("ORDER NOT FOUND").build();

        boolean success = orderBean.update(
                id,
                dateFormat.parse(orderDTO.getDeliveryDate()),
                orderDTO.getEstimatedDeliveryTime(),
                orderDTO.getPackageLocation(),
                orderDTO.getStatus()
        );

        Order updatedOrder = orderBean.findOrder(id);
        if (!success || updatedOrder == null)
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("ERROR_UPDATING_ORDER").build();
        return Response.ok(orderToDTO(updatedOrder)).build();
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

    private OrderDTO orderToDTOWithOrderItem(Order order) {
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
                order.getEstimatedDeliveryTime(),
                order.getPackageLocation(),
                order.getCity(),
                order.getPostalCode(),
                order.getCountry(),
                order.getAddress(),
                order.getPaymentMethod(),
                order.getStatus(),
                order.getCount(),
                itemsToDTOs( order.getOrderItems())
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
