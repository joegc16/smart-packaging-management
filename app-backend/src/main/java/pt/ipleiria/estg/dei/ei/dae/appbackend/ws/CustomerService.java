package pt.ipleiria.estg.dei.ei.dae.appbackend.ws;


import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.appbackend.dtos.*;
import pt.ipleiria.estg.dei.ei.dae.appbackend.ejbs.CartBean;
import pt.ipleiria.estg.dei.ei.dae.appbackend.ejbs.CustomerBean;
import pt.ipleiria.estg.dei.ei.dae.appbackend.entitites.*;

import java.util.List;
import java.util.stream.Collectors;

@Path("/customers")
@Produces({MediaType.APPLICATION_JSON}) // Injects header "Content-Type: application/json"
@Consumes({MediaType.APPLICATION_JSON}) // Injects header "Accept: application/json"
public class CustomerService {
    @EJB
    CustomerBean customerBean;
    @EJB
    CartBean cartBean;

    @GET
    @Path("{id}")
    public Response getCustomerDetails(@PathParam("id") long id) {
        Customer customer = customerBean.find(id);
        if (customer == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("ERROR_FINDING_CUSTOMER")
                    .build();
        }
        return Response.ok(customerToDTO(customer)).build();
    }

    @GET
    @Path("{id}/orders")
    public Response getCustomerOrders(@PathParam("id") long id){
        Customer customer = customerBean.getCustomerOrders(id);
        if (customer == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("ERROR_FINDING_CUSTOMER")
                    .build();
        }
        List<OrderDTO> dtos = ordersToDTOs(customer.getOrders());
        return Response.ok(dtos).build();
    }

    @GET
    @Path("{id}/cart")
    public Response getCustomerCart(@PathParam("id") long id){
        Customer customer = customerBean.getCustomerCart(id);
        if (customer == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("ERROR_FINDING_CUSTOMER")
                    .build();
        }
        CartDTO dto = cartToDTO(customer.getCart());
        return Response.ok(dto).build();
    }

    @POST
    @Path("/")
    public Response createCustomer(CustomerDTO customerDTO) {
        if (customerBean.exists(customerDTO.getUsername()))
            return Response.status(Response.Status.CONFLICT).entity("Username Customer already exists").build();

        customerBean.create(
                customerDTO.getName(),
                customerDTO.getPassword(),
                customerDTO.getUsername(),
                customerDTO.getEmail(),
                customerDTO.getRole()
        );
        Customer newCustomer = customerBean.findByUsername(customerDTO.getUsername());
        if (newCustomer == null)
            return Response.status(Response.Status.BAD_REQUEST).entity("ERROR_CREATING_CUSTOMER").build();

        Cart cartCustomer = cartBean.create(newCustomer.getId(), 0);
        if (cartCustomer == null)
            return Response.status(Response.Status.BAD_REQUEST).entity("ERROR_CREATING_CART").build();

        return
                Response.status(Response.Status.CREATED).entity(customerToDTO(newCustomer)).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteCustomer(@PathParam("id") long id) {
        Customer customer = customerBean.find(id);
        if (customer == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("ERROR_FINDING_CUSTOMER")
                    .build();
        }
        customerBean.delete(id);
        Customer deletedCustomer= customerBean.find(id);
        if (deletedCustomer != null)
            return Response.status(Response.Status.BAD_REQUEST).entity("SOMETHING WRONg DELETING CUSTOMER").build();
        cartBean.delete(customer.getCart().getId());
        return Response.ok().build();
    }

    @PUT
    @Path("{id}")
    public Response updateCustomer(@PathParam("id") long id, CustomerDTO customerDTO) {
        Customer existingCustomer = customerBean.find(id);
        if (existingCustomer == null)
            return Response.status(Response.Status.NOT_FOUND).entity("CUSTOMER NOT FOUND").build();

        if (!existingCustomer.getUsername().equals(customerDTO.getUsername()) && customerBean.exists(customerDTO.getUsername()))
            return Response.status(Response.Status.CONFLICT)
                    .entity("MANUFACTURER NAME ALREADY EXISTS")
                    .build();

        boolean success = customerBean.update(
                id,
                customerDTO.getName(),
                customerDTO.getPassword(),
                customerDTO.getUsername(),
                customerDTO.getEmail(),
                customerDTO.getRole()
        );

        Customer updatedCustomer = customerBean.find(id);
        if (!success || updatedCustomer == null)
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("ERROR_UPDATING_CUSTOMER").build();
        return Response.ok(customerToDTO(updatedCustomer)).build();
    }



    private CustomerDTO customerToDTO(Customer customer) {
        return new CustomerDTO(
                customer.getId(),
                customer.getName(),
                customer.getPassword(),
                customer.getUsername(),
                customer.getEmail(),
                customer.getRole().getId()
        );
    }


    private CartDTO cartToDTO(Cart cart) {
        return new CartDTO(
                cart.getId(),
                cart.getCustomer().getId(),
                cartItemsToDTOs(cart.getCartItems()),
                cart.getCount()
        );
    }

    private List<CartItemDTO> cartItemsToDTOs(List<CartItem> cartItems) {
        return cartItems.stream().map(this::cartItemToDTO).collect(Collectors.toList());
    }

    private CartItemDTO cartItemToDTO(CartItem cartItem) {
        return new CartItemDTO(
                cartItem.getId(),
                cartItem.getProductPackage().getId(),
                cartItem.getQuantity(),
                cartItem.getSubPrice()
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
