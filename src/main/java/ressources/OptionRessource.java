package ressources;

import entities.Option;
import metiers.OptionBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import javax.ws.rs.PathParam;

@Path("/options")
public class OptionRessource {

    OptionBusiness optionBusiness = new OptionBusiness();



    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        return Response.status(Response.Status.OK).entity(this.optionBusiness.getListeOptions()).build();
    }
    @GET
    @Path("/{code}")
    @Produces(MediaType.APPLICATION_JSON)
    public Option getOptionByCode( @PathParam("code") int code) {
        return this.optionBusiness.getOptionByCode(code);
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)

    public Response create(Option option) {
        if (this.optionBusiness.addOption(option)) {
            ;
            return Response.status(Response.Status.CREATED).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @PUT
    @Path("/{code}")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("code") int code, Option option) {

        if (this.optionBusiness.updateOption(code, option)) {
            return Response.status(Response.Status.OK).build();
        }

        return Response.status(Response.Status.BAD_REQUEST).build();
    }



}