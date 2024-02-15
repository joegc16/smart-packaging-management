package pt.ipleiria.estg.dei.ei.dae.appbackend.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.appbackend.dtos.CartItemDTO;
import pt.ipleiria.estg.dei.ei.dae.appbackend.ejbs.CartBean;
import pt.ipleiria.estg.dei.ei.dae.appbackend.ejbs.CartItemBean;
import pt.ipleiria.estg.dei.ei.dae.appbackend.entitites.Cart;
import pt.ipleiria.estg.dei.ei.dae.appbackend.entitites.CartItem;

@Path("/carts")
@Produces({MediaType.APPLICATION_JSON}) // Injects header "Content-Type: application/json"
@Consumes({MediaType.APPLICATION_JSON}) // Injects header "Accept: application/json"
public class CartService {
    @EJB
    CartBean cartBean;
    @EJB
    CartItemBean cartItemBean;

    //Creating a cartitem
    @POST
    @Path("/{id}/items")
    public Response createCartItem(@PathParam("id") long id, CartItemDTO cartItemDTO) {
        Cart cart = cartBean.find(id);
        if (cart == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("ERROR_FINDING_CART")
                    .build();
        }
        CartItem newCartItem = cartItemBean.create(
                cart.getId(),
                cartItemDTO.getProductPackageId(),
                cartItemDTO.getQuantity(),
                cartItemDTO.getSubPrice());
        if (newCartItem == null) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("ERROR_CREATING_CART_ITEM")
                    .build();
        }
        cartBean.updateCartCount(cart.getId());
        return Response.ok(cartItemToDTO(newCartItem)).build();
    }

    @DELETE
    @Path("/{id}/items/{itemId}")
    public Response removeCartItem(@PathParam("id") long id, @PathParam("itemId") long itemId) {
        Cart cart = cartBean.find(id);
        if (cart == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("ERROR_FINDING_CART")
                    .build();
        }
        CartItem cartItem = cartItemBean.find(itemId);
        if (cartItem == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("ERROR_FINDING_CART_ITEM")
                    .build();
        }
        if (cartItem.getCart().getId() != cart.getId()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("ERROR_CART_ITEM_NOT_IN_CART")
                    .build();
        }
        cartItemBean.delete(itemId);
        CartItem deletedCartItem = cartItemBean.find(itemId);
        if (deletedCartItem != null) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("ERROR_DELETING_CART_ITEM")
                    .build();
        }
        cartBean.updateCartCount(cart.getId());
        return Response.ok().build();
    }

    @PATCH
    @Path("/{id}/items/{itemId}")
    public Response updateCartItem(@PathParam("id") long id,
                                   @PathParam("itemId") long itemId,
                                   CartItemDTO cartItemDTO) {
        Cart cart = cartBean.find(id);
        if (cart == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("ERROR_FINDING_CART")
                    .build();
        }
        CartItem cartItem = cartItemBean.find(itemId);
        if (cartItem == null || cartItem.getCart().getId() != cart.getId()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("ERROR_FINDING_CART_ITEM")
                    .build();
        }
        boolean success = cartItemBean.update(
                cartItem.getId(),
                cartItemDTO.getQuantity(),
                cartItemDTO.getSubPrice());
        if (!success) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("ERROR_UPDATING_CART_ITEM")
                    .build();
        }
        cartBean.updateCartCount(cart.getId());
        CartItem updatedCartItem = cartItemBean.find(itemId);
        return Response.ok(cartItemToDTO(updatedCartItem)).build();
    }

    @PUT
    @Path("{id}/clean")
    public Response cleanCart(@PathParam("id") long id) {
        Cart cart = cartBean.find(id);
        if (cart == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("ERROR_FINDING_CART")
                    .build();
        }
        cartBean.clearCart(cart.getId());
        Cart clearedCart = cartBean.findWithCartItems(cart.getId());
        if (clearedCart.getCartItems().size() != 0) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("ERROR_CLEARING_CART")
                    .build();
        }
        return Response.ok().build();
    }

    private CartItemDTO cartItemToDTO(CartItem cartItem) {
        return new CartItemDTO(
                cartItem.getId(),
                cartItem.getProductPackage().getId(),
                cartItem.getQuantity(),
                cartItem.getSubPrice()
        );
    }


}
