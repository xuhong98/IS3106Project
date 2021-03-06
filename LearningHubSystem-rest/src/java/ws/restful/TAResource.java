/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful;

import ejb.session.stateless.ModuleControllerLocal;
import ejb.session.stateless.TeachingAssistantControllerLocal;
import entity.Module;
import entity.TeachingAssistant;

import java.util.List;


import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.Consumes;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;

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

import javax.xml.bind.JAXBElement;
import util.exception.ModuleExistException;
import util.exception.ModuleNotFoundException;
import util.exception.TANotFoundException;
import ws.restful.datamodel.AssignModuleTAReq;
import ws.restful.datamodel.CreateModuleRsp;
import ws.restful.datamodel.DropModuleTAReq;
import ws.restful.datamodel.ErrorRsp;
import ws.restful.datamodel.UpdateLecturerReq;
import ws.restful.datamodel.UpdateTaReq;
import ws.restful.datamodel.UpdateTaRsp;


/**
 * REST Web Service
 *
 * @author wyh
 */
@Path("ta")
public class TAResource {

    ModuleControllerLocal moduleController;

    TeachingAssistantControllerLocal taController;
    

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of FacilitatorResource
     */
    public TAResource() {
        taController = lookupTeachingAssistantControllerLocal();
        moduleController = lookupModuleControllerLocal();
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
        

    @Path("getTa/{username}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTa(@PathParam("username") String username) {
        try {
            TeachingAssistant ta = taController.retrieveTAByUsername(username);
            ta.getModules().clear();
            UpdateTaRsp updateTaRsp = new UpdateTaRsp(ta);
            //RetrieveStudentRsp retrieveStudentRsp = new RetrieveStudentRsp(s);
            return Response.status(Response.Status.OK).entity(updateTaRsp).build();
        } catch (TANotFoundException ex) {

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
    

    

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateTa(JAXBElement<UpdateTaReq> jaxbUpdateTaReq)
    {
        if((jaxbUpdateTaReq != null) && (jaxbUpdateTaReq.getValue() != null))
        {
            try
            {
                UpdateTaReq updateTaReq= jaxbUpdateTaReq.getValue();
                
                taController.updateTA(updateTaReq.getTa());
                
                return Response.status(Response.Status.OK).build();
            }
            catch(Exception ex)
            {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
            }
        }
        else
        {
            ErrorRsp errorRsp = new ErrorRsp("Invalid update TA request");
            
            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        }
    }
    
    @Path("assignModule")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response assignModule(JAXBElement<AssignModuleTAReq> jaxbAssignModuleTAReq) throws ModuleNotFoundException
    {
        if((jaxbAssignModuleTAReq != null) && (jaxbAssignModuleTAReq.getValue() != null))
        {
            try
            {
                AssignModuleTAReq assignModuleReq = jaxbAssignModuleTAReq.getValue();
                Module module = moduleController.retrieveModuleById(assignModuleReq.getModuleId());
                TeachingAssistant ta= taController.retrieveTAById(assignModuleReq.getTaId());
     
    
                taController.registerModule(ta, module);
                
                
                
                return Response.status(Response.Status.OK).build();
            }
            
            catch(TANotFoundException | ModuleExistException ex){
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
            }
        }
        else
        {
            ErrorRsp errorRsp = new ErrorRsp("Invalid assign module request");
            
            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        }
    }
    
    @Path("dropModule")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response dropModuleTA(JAXBElement<DropModuleTAReq> jaxbDropModuleTAReq) throws ModuleNotFoundException
    {
        if((jaxbDropModuleTAReq != null) && (jaxbDropModuleTAReq.getValue() != null))
        {
            try
            {
                DropModuleTAReq dropModuleTAReq = jaxbDropModuleTAReq.getValue();
                Module module = moduleController.retrieveModuleById(dropModuleTAReq.getModuleId());
                TeachingAssistant ta= taController.retrieveTAById(dropModuleTAReq.getTaId());
                taController.dropModule(ta, module);
                
         
                
                return Response.status(Response.Status.OK).build();
            }
            
            catch(TANotFoundException ex){
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
            }
        }
        else
        {
            ErrorRsp errorRsp = new ErrorRsp("Invalid remove TA request");
            
            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
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

    private ModuleControllerLocal lookupModuleControllerLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (ModuleControllerLocal) c.lookup("java:global/LearningHubSystem/LearningHubSystem-ejb/ModuleController!ejb.session.stateless.ModuleControllerLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
