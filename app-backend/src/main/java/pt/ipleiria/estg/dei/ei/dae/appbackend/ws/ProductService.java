package pt.ipleiria.estg.dei.ei.dae.appbackend.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.appbackend.dtos.OrderDTO;
import pt.ipleiria.estg.dei.ei.dae.appbackend.dtos.ProductDTO;
import pt.ipleiria.estg.dei.ei.dae.appbackend.ejbs.OrderBean;
import pt.ipleiria.estg.dei.ei.dae.appbackend.ejbs.ProductBean;
import pt.ipleiria.estg.dei.ei.dae.appbackend.entitites.Order;
import pt.ipleiria.estg.dei.ei.dae.appbackend.entitites.Product;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Path("/products")
@Produces({MediaType.APPLICATION_JSON}) // Injects header "Content-Type: application/json"
@Consumes({MediaType.APPLICATION_JSON}) // Injects header "Accept: application/json"
public class ProductService {
    @EJB
    private ProductBean productBean;

    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/") // means: the relative url path is "/api/users"
    public List<ProductDTO> getAllProducts() {
        return productsToDTOs(productBean.getAll());
    }

    @GET
    @Path("/{code}")
    public Response getProductDetails(@PathParam("code") long code) {
        Product product = productBean.findProduct(code);
        if (product != null) {
            return Response.ok(productToDTO(product)).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("ERROR_FINDING_PRODUCT")
                .build();
    }

    @POST
    @Path("/")
    public Response createProduct(ProductDTO productDTO) {
        if (productBean.exists(productDTO.getName()))
            return Response.status(Response.Status.CONFLICT).build();

        productBean.create(
                productDTO.getName(),
                productDTO.getBrand(),
                productDTO.getDescription(),
                productDTO.getTotalQuantity(),
                productDTO.getManufacturerId()
        );
        Product newProduct = productBean.findByName(productDTO.getName());
        if (newProduct == null)
            return Response.status(Response.Status.BAD_REQUEST).build();
        return
                Response.status(Response.Status.CREATED).entity(productToDTO(newProduct)).build();
    }

    @PUT
    @Path("/{code}")
    public Response updateProduct(@PathParam("code") long code, ProductDTO productDTO) {
        Product existingProduct = productBean.findProduct(code);
        if (existingProduct == null)
            return Response.status(Response.Status.NOT_FOUND).entity("PRODUCT NOT FOUND").build();

        if (!existingProduct.getName().equals(productDTO.getName()) && productBean.exists(productDTO.getName()))
            return Response.status(Response.Status.CONFLICT)
                    .entity("PRODUCT NAME ALREADY EXISTS")
                    .build();

        boolean success = productBean.update(
                code,
                productDTO.getName(),
                productDTO.getBrand(),
                productDTO.getDescription(),
                productDTO.getTotalQuantity(),
                productDTO.getManufacturerUsername()
        );

        Product updatedProduct = productBean.findProduct(code);
        if (!success || updatedProduct == null)
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();

        return Response.status(Response.Status.OK).entity("PRODUCT UPDATED SUCCESSFULLY").build();
    }

    @DELETE
    @Path("{code}")
    public Response deleteProduct(@PathParam("code") long code) {
        Product existingProduct = productBean.findProduct(code);

        if (existingProduct == null)
            return Response.status(Response.Status.NOT_FOUND).entity("PRODUCT NOT FOUND").build();

        productBean.delete(code);

        if (productBean.findProduct(code) == null)
            return Response.ok("PRODUCT DELETE SUCCESSFULLY").build();
        else
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }

    //This method call a product DTO with less information
    private List<ProductDTO> productsToDTOs(List<Product> products) {
        return products.stream().map(this::productLessInformationToDTO).collect(Collectors.toList());
    }

    private ProductDTO productToDTO(Product product) {
        return new ProductDTO(
                product.getCode(),
                product.getName(),
                product.getDescription(),
                product.getBrand(),
                product.getTotalQuantity(),
                product.getManufacturer().getUsername(),
                product.getManufacturer().getId()
        );
    }

    private ProductDTO productLessInformationToDTO(Product product) {
        return new ProductDTO(
                product.getCode(),
                product.getName(),
                null,
                product.getBrand(),
                product.getTotalQuantity(),
                null,
                null
        );
    }



}
