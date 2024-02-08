package pt.ipleiria.estg.dei.ei.dae.appbackend.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import pt.ipleiria.estg.dei.ei.dae.appbackend.dtos.UserDTO;
import pt.ipleiria.estg.dei.ei.dae.appbackend.ejbs.UserBean;
import pt.ipleiria.estg.dei.ei.dae.appbackend.entitites.User;

import java.util.List;
import java.util.stream.Collectors;

@Path("/users")
@Produces({MediaType.APPLICATION_JSON}) // Injects header "Content-Type: application/json"
@Consumes({MediaType.APPLICATION_JSON}) // Injects header "Accept: application/json"
public class UserService {
    @EJB
    private UserBean userBean;

    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/") // means: the relative url path is "/api/users"
    public List<UserDTO> getAllUsers() {
        return usersToDTOs(userBean.getAll());
    }

    private List<UserDTO> usersToDTOs(List<User> users) {
        return users.stream().map(this::userToDTO).collect(Collectors.toList());
    }

    private UserDTO userToDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.getEmail(),
                user.getRole().getName()
        );
    }
}
