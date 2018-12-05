package ar.edu.itba.paw.homehelper.api.providers;
import ar.edu.itba.paw.homehelper.dto.*;
import ar.edu.itba.paw.homehelper.utils.LoggedUser;
import ar.edu.itba.paw.interfaces.services.SProviderService;
import ar.edu.itba.paw.model.CoordenatesPoint;
import ar.edu.itba.paw.model.SProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.*;

@Path("providers")
@Controller
public class ProvidersController {


    @Autowired
    LoggedUser loggedUser;
    @Autowired
    SProviderService sProviderService;

    @Context
    private UriInfo uriInfo;

    @Context
    HttpServletRequest request;

    @Autowired
    private MessageSource messageSource;

    private final static String CURRENT_PAGE = "1";
    private final static String PAGE_SIZE = "100";

    @GET
    @Path("/")
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response getProviders(
            @QueryParam("st") final Integer serviceTypeId,
            @QueryParam("lat") final Double latitude,
            @QueryParam("lng") final Double longitude,
            @QueryParam("page") @DefaultValue(CURRENT_PAGE) final int page,
            @QueryParam("pageSize") @DefaultValue(PAGE_SIZE) final int pageSize) {


        if(page < 1 || pageSize < 1) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        List<SProvider> providers;

        if(latitude == null && longitude == null && serviceTypeId!= null){
            providers = sProviderService.getServiceProvidersByServiceType(serviceTypeId,loggedUser.id(),page,pageSize);
        } else if(latitude != null && longitude != null && serviceTypeId == null){
            providers = sProviderService.getServiceProvidersByNeighborhood(latitude,longitude,loggedUser.id(),page,pageSize);
        } else if(latitude !=null && longitude != null && serviceTypeId != null){
            providers = sProviderService.getServiceProvidersByNeighborhoodAndServiceType(latitude,longitude,serviceTypeId,loggedUser.id(),page,pageSize);
        }else if(latitude == null && longitude == null && serviceTypeId == null) {
            providers = sProviderService.getServiceProviders(loggedUser.id(),page,pageSize);
        }else{
                return Response.status(Response.Status.BAD_REQUEST).build();
        }

         // TODO: get sorted and paginated providers, procesing all params (st, lat, lng, page, pageSize)

        Locale locale = request.getLocale();


        final int maxPage = (int) Math.ceil((double) providers.size() / pageSize); // TODO: get max page from sProviderService

        if(page > maxPage && maxPage != 0) { // TODO: this should be before searching for providers

            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        final Link[] links = getPaginationLinks(page, maxPage);

        return Response.ok(new ProvidersListDto(new ArrayList<>(providers), page, pageSize, maxPage,locale,messageSource)).links(links).build();
    }

    @POST
    @Path("/")
    @Produces(value = MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createProvider(CreateProviderDto providerDto){

        if(loggedUser.isProvider()){
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        if(providerDto == null || providerDto.getWorkingZone()== null || providerDto.getAptitudes() == null || providerDto.getDescription() == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        Map<Integer,String> aptitudes = new HashMap<>();

        for(BasicAptitudeDto aptitudeDto: providerDto.getAptitudes()){
            aptitudes.put(aptitudeDto.getServiceTypeId(),aptitudeDto.getDescription());
        }

        Set<CoordenatesPoint> coordenates = new HashSet<>();

        int i = 0;

        for(CoordenateDto coordenateDto: providerDto.getWorkingZone()){
            coordenates.add(new CoordenatesPoint(loggedUser.id(),i++,coordenateDto.getLat(),coordenateDto.getLng()));
        }

        Optional<SProvider> provider = sProviderService.create(loggedUser.id(),providerDto.getDescription(),aptitudes,coordenates);

        if(!provider.isPresent()){ return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build(); }

        final URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(provider.get().getId())).build();

        return Response.created(uri).build();

    }



    private Link[] getPaginationLinks(final int page, final int maxPage) {
        List<Link> links = new ArrayList<>();

        if(page > 1) {
            links.add(getLink("prev", page - 1));
        }

        if(page < maxPage) {
            links.add(getLink("next", page + 1));
        }

        links.add(getLink("first", 1));
        links.add(getLink("last", maxPage));

        return links.toArray(new Link[0]);
    }

    private Link getLink(final String rel, final int idx) {
        UriBuilder uriBuilder = uriInfo.getRequestUriBuilder();
        uriBuilder.replaceQueryParam("page", idx);
        return Link.fromUriBuilder(uriBuilder).rel(rel).build();
    }
}
