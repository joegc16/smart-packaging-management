package pt.ipleiria.estg.dei.ei.dae.appbackend.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.appbackend.dtos.PackageDTO;
import pt.ipleiria.estg.dei.ei.dae.appbackend.dtos.ProductDTO;
import pt.ipleiria.estg.dei.ei.dae.appbackend.dtos.ProductPackageDTO;
import pt.ipleiria.estg.dei.ei.dae.appbackend.ejbs.ProductPackageBean;
import pt.ipleiria.estg.dei.ei.dae.appbackend.entitites.Package;
import pt.ipleiria.estg.dei.ei.dae.appbackend.entitites.ProductPackage;
import pt.ipleiria.estg.dei.ei.dae.appbackend.entitites.ProductPackageId;

import java.util.List;
import java.util.stream.Collectors;

@Path("/productPackages")
@Produces({MediaType.APPLICATION_JSON}) // Injects header "Content-Type: application/json"
@Consumes({MediaType.APPLICATION_JSON}) // Injects header "Accept: application/json"
public class ProductPackageService {
    @EJB
    private ProductPackageBean productPackageBean;

    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/") // means: the relative url path is "/api/users"
    public List<ProductPackageDTO> getAllProductPackages() {
        return productPackagesToDTOs(productPackageBean.getAll());
    }

    @GET
    @Path("/{id}")
    public Response getProductPackageDetails(@PathParam("id") long id) {
        ProductPackage productPackage = productPackageBean.findProductPackageById(id);
        if (productPackage != null) {
            return Response.ok(productPackageToDTO(productPackage)).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("ERROR_FINDING_PRODUCT_P")
                .build();
    }

    @GET
    @Path("/{id}/image")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getProductPackageImage(@PathParam("id") long id) {
        ProductPackage productPackage = productPackageBean.findProductPackageById(id);
        if (productPackage != null) {
            return Response.ok(productPackage.getImage()).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("ERROR_FINDING_PRODUCT_P")
                .build();
    }



    @POST
    @Path("/")
    public Response createProductPackage(ProductPackageDTO productPackageDTO) {
        long productCode = productPackageDTO.getProductCode();
        long packageCode = productPackageDTO.getPackageCode();

        productPackageBean.create(
                productCode,
                packageCode,
                productPackageDTO.getPrice(),
                productPackageDTO.getQuantityProductsInPackage(),
                productPackageDTO.getImage()
        );
        ProductPackage newProductPackage = productPackageBean.findProductPackage(productCode, packageCode);
        if (newProductPackage == null)
            return Response.status(Response.Status.BAD_REQUEST).build();
        return
                Response.status(Response.Status.CREATED).entity(productPackageToDTO(newProductPackage)).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateProductPackage(@PathParam("id") long id, ProductPackageDTO productPackageDTO) {
        ProductPackage productPackage = productPackageBean.findProductPackageById(id);
        if (productPackage == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("ERROR_FINDING_PRODUCT_P")
                    .build();
        }
        boolean success =productPackageBean.update(
                id,
                productPackageDTO.getPrice(),
                productPackageDTO.getQuantityProductsInPackage(),
                productPackageDTO.getImage()
        );
        ProductPackage updatedProductPackage = productPackageBean.findProductPackageById(id);
        if (!success  || updatedProductPackage == null)
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        return Response.ok(productPackageToDTO(updatedProductPackage)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteProductPackage(@PathParam("id") long id) {
        ProductPackage productPackage = productPackageBean.findProductPackageById(id);
        if (productPackage == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("ERROR_FINDING_PRODUCT_P")
                    .build();
        }
        productPackageBean.delete(id);
        if (productPackageBean.findProductPackageById(id) == null)
            return Response.ok("PRODUCT_PACKAGE_DELETE_SUCCESSFULLY").build();
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }

    private List<ProductPackageDTO> productPackagesToDTOs(List<ProductPackage> productPackages) {
        return productPackages.stream().map(this::productPackageToDTO).collect(Collectors.toList());
    }

    private ProductPackageDTO productPackageToDTO(ProductPackage productPackage) {
        return new ProductPackageDTO(
                productPackage.getId(),
                productPackage.getPack().getCode(),
                productPackage.getProduct().getCode(),
                productPackage.getPrice(),
                productPackage.getQuantityProductsInPackage(),
                productPackage.getImage()
        );
    }


}
