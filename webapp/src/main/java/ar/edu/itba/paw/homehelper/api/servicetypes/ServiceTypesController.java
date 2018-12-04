package ar.edu.itba.paw.homehelper.api.servicetypes;

import ar.edu.itba.paw.homehelper.dto.ServiceTypeDto;
import ar.edu.itba.paw.homehelper.dto.ServiceTypesListDto;
import ar.edu.itba.paw.interfaces.services.STypeService;
import ar.edu.itba.paw.model.ServiceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.Locale;

@Path("serviceTypes")
@Controller
public class ServiceTypesController {
    @Autowired
    private STypeService sTypeService;


    @Autowired
    private MessageSource messageSource;

    @Context
    private HttpServletRequest request;

    @Context
    private UriInfo uriInfo;



    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getServiceTypes() {

        Locale locale = request.getLocale();

        List<ServiceType> serviceTypes = sTypeService.getServiceTypes();

        return Response.ok(new ServiceTypesListDto(serviceTypes,locale,messageSource)).build();
}

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getServiceTypeById(@PathParam("id") final int id) {

        Locale locale = request.getLocale();

       final ServiceType serviceType = sTypeService.getServiceTypeWithId(id);

        if(serviceType == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(new ServiceTypeDto(serviceType,locale,messageSource)).build();
    }

//    @POST
//    @Path("/")
//    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Response createServiceType(final ServiceTypeDto serviceTypeDTO) {
//        final ServiceType newServiceType = sTypeService.create(serviceTypeDTO.getName());
//
//        if(newServiceType == null) {
//            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build(); // TODO:Internal Server Error?
//        }
//
//        final URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(newServiceType.getId())).build();
//
//        return Response.created(uri).build();
//    }
//
//    @PUT
//    @Path("/{id}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response updateServiceType(@PathParam("id") final int id, final ServiceTypeDto serviceTypeDTO) {
//        final ServiceType serviceType = sTypeService.getServiceTypeWithId(id);
//
//        if(serviceType == null) {
//            return Response.status(Response.Status.NOT_FOUND).build();
//        }
//
//        final ServiceType newServiceType = sTypeService.updateServiceTypeWithId(id, serviceTypeDTO.getName());
//
//        if(newServiceType == null) {
//            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build(); // TODO:Internal Server Error?
//        }
//
//        return Response.ok().build();
//    }


}