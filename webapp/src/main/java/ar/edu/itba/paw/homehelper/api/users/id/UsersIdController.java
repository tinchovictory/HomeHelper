package ar.edu.itba.paw.homehelper.api.users.id;


import ar.edu.itba.paw.homehelper.api.users.UsersController;
import ar.edu.itba.paw.homehelper.dto.UserDto;
import ar.edu.itba.paw.homehelper.utils.LoggedUser;
import ar.edu.itba.paw.interfaces.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/users/{id}")
@Controller
public class UsersIdController {

    @Autowired
    UserService userService;

    @Autowired
    LoggedUser loggedUser;

    final static Logger LOGGER = LoggerFactory.getLogger(UsersController.class);

    @Path("/image")
    @Produces({"image/png", "image/jpeg"})
    public Response getImage(@PathParam("id") Integer id){
        if(id == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        return userService.getProfileImage(id)
                .map(im->{
                    System.out.println("GOT IMAGEE"+im.getImageId());
                    return Response.ok(im.getImage()).build();
                })
                .orElse(Response.status(Response.Status.REQUESTED_RANGE_NOT_SATISFIABLE).build());


    }

    @PUT
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateClient(@PathParam("id") final Integer id, UserDto userDto){

        LOGGER.info("ENTRE");

        if(!loggedUser.id().isPresent() || id != loggedUser.id().get()){
            LOGGER.info("!loggedUser.id().isPresent() || id != loggedUser.id().get()");
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        if(userDto == null){
            LOGGER.info("userDto == null");
        }

        if(userDto.getAddress() != null){
            userService.updateAddressOfUser(id,userDto.getAddress());
        }
        if(userDto.getEmail() != null){
            userService.updateEmailOfUser(id,userDto.getEmail());
        }
        if(userDto.getFirstname() != null){
            userService.updateFirstNameOfUser(id,userDto.getFirstname());
        }
        if(userDto.getLastname() != null){
            userService.updateLastNameOfUser(id,userDto.getLastname());
        }
        if(userDto.getPassword() != null){
            userService.updatePasswordOfUser(id,userDto.getPassword());
        }
        if(userDto.getPhone() != null){
            userService.updatePhoneOfUser(id,userDto.getPhone());
        }

        return Response.ok().build();
    }


}