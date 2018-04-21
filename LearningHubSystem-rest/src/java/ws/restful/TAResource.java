/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful;

import ejb.session.stateless.TeachingAssistantControllerLocal;
import entity.TeachingAssistant;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import util.exception.TANotFoundException;
import ws.restful.datamodel.ErrorRsp;
import ws.restful.datamodel.RetrieveTAsRsp;

/**
 * REST Web Service
 *
 * @author wyh
 */
@Path("ta")
public class TAResource {

    TeachingAssistantControllerLocal taController;

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of FacilitatorResource
     */
    public TAResource() {
        taController = lookupTeachingAssistantControllerLocal();
    }
    
    @Path("retrieveAllTAs")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllTAs()
    {
        try
        {   
             List<TeachingAssistant> tas = taController.retrieveAllTAs();
            for(TeachingAssistant t:tas){
                t.getModules().clear();
               
               
            }
           
            RetrieveTAsRsp retrieveTAsRsp = new RetrieveTAsRsp(tas);
            return Response.status(Response.Status.OK).entity(retrieveTAsRsp).build();
        }
        catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }
    
    @Path("{taId}")
    @DELETE
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteTA(@PathParam("taId") Long taId)
    {
        try
        {
            taController.deleteTA(taController.retrieveTAById(taId));
            
            return Response.status(Response.Status.OK).build();
        }
       catch (TANotFoundException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }
    

    
    

    private TeachingAssistantControllerLocal lookupTeachingAssistantControllerLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (TeachingAssistantControllerLocal) c.lookup("java:global/LearningHubSystem/LearningHubSystem-ejb/TeachingAssistantController!ejb.session.stateless.TeachingAssistantControllerLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
