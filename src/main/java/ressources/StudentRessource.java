package ressources;

import entities.Etudiant;
import entities.Option;
import metiers.EtudiantBusiness;
import metiers.OptionBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.PathParam;

@Path("/etudiants")
public class StudentRessource {

    EtudiantBusiness etudiantBusiness = new EtudiantBusiness();
    OptionBusiness optionBusiness = new OptionBusiness();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        return Response.status(Response.Status.OK).entity(this.etudiantBusiness.getAllEtudiants()).build();
    }

    @GET
    @Path("/{identifiant}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEtudiantByIdentifiant(@PathParam("identifiant") String identifiant) {
        Etudiant etudiant = this.etudiantBusiness.getEtudiantByIdentifiant(identifiant);
        if (etudiant != null) {
            return Response.status(Response.Status.OK).entity(etudiant).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(Etudiant etudiant) {
        if (etudiant != null && etudiant.getOption() != null && this.etudiantBusiness.addEtudiant(etudiant)) {
            return Response.status(Response.Status.OK).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @PUT
    @Path("/{identifiant}")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("identifiant") String identifiant, Etudiant etudiant) {
        if (this.etudiantBusiness.updateEtudiant(identifiant, etudiant)) {
            return Response.status(Response.Status.OK).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("/{identifiant}")
    public Response delete(@PathParam("identifiant") String identifiant) {
        if (this.etudiantBusiness.deleteEtudiant(identifiant)) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("/option")
    @Produces(MediaType.APPLICATION_XML)
    public Response getByOption(@QueryParam("codeOption") int codeOption) {
        Option option = this.optionBusiness.getOptionByCode(codeOption);
        if (option == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        StringBuilder xml = new StringBuilder();
        xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n");
        xml.append("<etudiants>\n");
        for (Etudiant e : this.etudiantBusiness.getEtudiantsByOption(option)) {
            xml.append("  <etudiant>\n");
            xml.append("    <anneeEtude>").append(e.getAnneeEtude()).append("</anneeEtude>\n");
            xml.append("    <email>").append(e.getEmail()).append("</email>\n");
            xml.append("    <identifiant>").append(e.getIdentifiant()).append("</identifiant>\n");
            xml.append("    <nom>").append(e.getNom()).append("</nom>\n");
            xml.append("    <prenom>").append(e.getPrenom()).append("</prenom>\n");
            xml.append("  </etudiant>\n");
        }
        xml.append("</etudiants>");

        return Response.status(Response.Status.OK).entity(xml.toString()).build();
    }

}