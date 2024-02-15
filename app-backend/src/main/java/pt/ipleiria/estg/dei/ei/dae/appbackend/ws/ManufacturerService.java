package pt.ipleiria.estg.dei.ei.dae.appbackend.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.appbackend.dtos.ManufacturerDTO;
import pt.ipleiria.estg.dei.ei.dae.appbackend.dtos.OrderDTO;
import pt.ipleiria.estg.dei.ei.dae.appbackend.dtos.OrderItemDTO;
import pt.ipleiria.estg.dei.ei.dae.appbackend.dtos.ProductDTO;
import pt.ipleiria.estg.dei.ei.dae.appbackend.ejbs.ManufacturerBean;
import pt.ipleiria.estg.dei.ei.dae.appbackend.entitites.Manufacturer;
import pt.ipleiria.estg.dei.ei.dae.appbackend.entitites.Order;
import pt.ipleiria.estg.dei.ei.dae.appbackend.entitites.Product;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Path("/manufacturers")
@Produces({MediaType.APPLICATION_JSON}) // Injects header "Content-Type: application/json"
@Consumes({MediaType.APPLICATION_JSON}) // Injects header "Accept: application/json"
public class ManufacturerService {
    @EJB
    ManufacturerBean manufacturerBean;

    @GET
    @Path("{id}")
    public Response getManufacturerDetails(@PathParam("id") long id) {
        Manufacturer manufacturer = manufacturerBean.find(id);
        if (manufacturer == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("ERROR_FINDING_MANUFACTURER")
                    .build();
        }
        return Response.ok(manufacturerToDTO(manufacturer)).build();

    }

    @GET
    @Path("{id}/products")
    public Response getManufacturerProducts(@PathParam("id") long id){
        Manufacturer manufacturer = manufacturerBean.getManufacturerProducts(id);
        if (manufacturer == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("ERROR_FINDING_MANUFACTURER")
                    .build();
        }
        List<ProductDTO> dtos = productsToDTOs(manufacturer.getProducts());
        return Response.ok(dtos).build();

    }

    @GET
    @Path("{id}/orders")
    public Response getManufacturerOrders(@PathParam("id") long id){
        Manufacturer manufacturer = manufacturerBean.getManufacturerOrders(id);
        if (manufacturer == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("ERROR_FINDING_MANUFACTURER")
                    .build();
        }
        List<OrderDTO> dtos = ordersToDTOs(manufacturer.getOrders());
        return Response.ok(dtos).build();

    }

    @POST
    @Path("/")
    public Response createManufacturer(ManufacturerDTO manufacturerDTO) {
        if (manufacturerBean.exists(manufacturerDTO.getUsername()))
            return Response.status(Response.Status.CONFLICT).entity("Username already exists").build();

        manufacturerBean.create(
                manufacturerDTO.getName(),
                manufacturerDTO.getPassword(),
                manufacturerDTO.getUsername(),
                manufacturerDTO.getEmail(),
                manufacturerDTO.getRole()
        );
        Manufacturer newManufacturer = manufacturerBean.findByUsername(manufacturerDTO.getUsername());
        if (newManufacturer == null)
            return Response.status(Response.Status.BAD_REQUEST).build();
        return
                Response.status(Response.Status.CREATED).entity(manufacturerToDTO(newManufacturer)).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteManufacturer(@PathParam("id") long id) {
        Manufacturer manufacturer = manufacturerBean.find(id);
        if (manufacturer == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("ERROR_FINDING_MANUFACTURER")
                    .build();
        }
        manufacturerBean.delete(id);
        Manufacturer deletedManufacturer = manufacturerBean.find(id);
        if (deletedManufacturer != null)
            return Response.status(Response.Status.BAD_REQUEST).build();
        return Response.ok().build();
    }

    @PUT
    @Path("{id}")
    public Response updateManufacturer(@PathParam("id") long id, ManufacturerDTO manufacturerDTO) {
        Manufacturer existingManufacturer = manufacturerBean.find(id);
        if (existingManufacturer == null)
            return Response.status(Response.Status.NOT_FOUND).entity("MANUFACTURER NOT FOUND").build();

        if (!existingManufacturer.getUsername().equals(manufacturerDTO.getUsername()) && manufacturerBean.exists(manufacturerDTO.getUsername()))
            return Response.status(Response.Status.CONFLICT)
                    .entity("MANUFACTURER NAME ALREADY EXISTS")
                    .build();

        boolean success = manufacturerBean.update(
                id,
                manufacturerDTO.getName(),
                manufacturerDTO.getPassword(),
                manufacturerDTO.getUsername(),
                manufacturerDTO.getEmail(),
                manufacturerDTO.getRole()
        );

        Manufacturer updatedManufacturer = manufacturerBean.find(id);
        if (!success || updatedManufacturer == null)
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("ERROR_UPDATING_MANUFACTURER").build();
        return Response.ok(manufacturerToDTO(updatedManufacturer)).build();
    }

    private ManufacturerDTO manufacturerToDTO(Manufacturer Manufacturer) {
        return new ManufacturerDTO(
                Manufacturer.getId(),
                Manufacturer.getName(),
                Manufacturer.getPassword(),
                Manufacturer.getUsername(),
                Manufacturer.getEmail(),
                Manufacturer.getRole().getId()
        );
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

    private List<ProductDTO> productsToDTOs(List<Product> products) {
        return products.stream().map(this::productToDTO).collect(Collectors.toList());
    }

    private ProductDTO productToDTO(Product product) {
        return new ProductDTO(
                product.getCode(),
                product.getName(),
                product.getDescription(),
                product.getBrand(),
                product.getTotalQuantity(),
                null,
                null
        );
    }
}
