/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful;

import ejb.session.stateless.LecturerControllerLocal;
import ejb.session.stateless.StudentControllerLocal;
import ejb.session.stateless.TimeEntryControllerLocal;
import entity.Module;
import entity.TimeEntry;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.JAXBElement;
import util.exception.TimeEntryNotFoundException;
import ws.restful.datamodel.CreateLecturerTimeEntryReq;
import ws.restful.datamodel.CreateLecturerTimeEntryRsp;
import ws.restful.datamodel.CreateTimeEntryReq;
import ws.restful.datamodel.CreateTimeEntryRsp;
import ws.restful.datamodel.ErrorRsp;
import ws.restful.datamodel.RetrieveTimeEntryByNameRsp;
import ws.restful.datamodel.RetrieveTimeEntryRsp;
import ws.restful.datamodel.UpdateTimeEntryReq;

/**
 * REST Web Service
 *
 * @author lxy
 */
@Path("schedule")
public class ScheduleResource {

    @Context
    private UriInfo context;
    
    TimeEntryControllerLocal timeEntryController;
    StudentControllerLocal studentController;
    LecturerControllerLocal lecturerController;

    /**
     * Creates a new instance of ScheduleResource
     */
    public ScheduleResource() {
        timeEntryController = lookupTimeEntryControllerLocal();
        studentController = lookupStudentControllerLocal();
        lecturerController = lookupLecturerControllerLocal();
    }

    /**
     * Retrieves representation of an instance of ws.restful.ScheduleResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getXml() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of ScheduleResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
    }
    
    @Path("retrieveTimeEntryByName/{username}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveTimeEntryByName(@PathParam("username") String username) {
        try {
            List<TimeEntry> timeEntries = timeEntryController.retrieveTimeEntrysByName(username);
            for(TimeEntry t:timeEntries){
            }
            RetrieveTimeEntryByNameRsp retrieveTimeEntryByNameRsp = 
                    new RetrieveTimeEntryByNameRsp(timeEntries);
            
            System.out.println(Response.status(Response.Status.OK).entity(retrieveTimeEntryByNameRsp).build());
            return Response.status(Response.Status.OK).entity(retrieveTimeEntryByNameRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }
    
    @Path("retrieveTimeEntryByLecturerName/{username}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveTimeEntryByLecturerName(@PathParam("username") String username) {
        try {
            List<TimeEntry> timeEntries = timeEntryController.retrieveTimeEntrysByLecturerName(username);
            for(TimeEntry t:timeEntries){
            }
            RetrieveTimeEntryByNameRsp retrieveTimeEntryByNameRsp = 
                    new RetrieveTimeEntryByNameRsp(timeEntries);
            
            System.out.println(Response.status(Response.Status.OK).entity(retrieveTimeEntryByNameRsp).build());
            return Response.status(Response.Status.OK).entity(retrieveTimeEntryByNameRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }
    
    @Path("retrieveTimeEntry/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveTimeEntry(@PathParam("id") Long id) {
        try {
            RetrieveTimeEntryRsp retrieveTimeEntryRsp = new RetrieveTimeEntryRsp(timeEntryController.retrieveTimeEntryById(id));

            return Response.status(Response.Status.OK).entity(retrieveTimeEntryRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateLecturer(JAXBElement<UpdateTimeEntryReq> jaxbUpdateTimeEntryReq)
    {
        if((jaxbUpdateTimeEntryReq != null) && (jaxbUpdateTimeEntryReq.getValue() != null))
        {
            try
            {
                UpdateTimeEntryReq updateTimeEntryReq= jaxbUpdateTimeEntryReq.getValue();
                
                timeEntryController.updateTimeEntry(updateTimeEntryReq.getTimeEntry());
                
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
            ErrorRsp errorRsp = new ErrorRsp("Invalid update timeEntry request");
            
            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        }
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createTimeEntry(JAXBElement<CreateTimeEntryReq> jaxbCreateTimeEntryReq)
    {   
        if((jaxbCreateTimeEntryReq != null) && (jaxbCreateTimeEntryReq.getValue() != null))
        {
            try
            {   
                
                CreateTimeEntryReq createTimeEntryReq = jaxbCreateTimeEntryReq.getValue();
                System.out.println(createTimeEntryReq.getRole());
                if(createTimeEntryReq.getRole().equals("student")){
                    System.out.println("student");
                    TimeEntry timeEntry =  timeEntryController.createTimeEntry(createTimeEntryReq.getTimeEntry(), 
                        studentController.retrieveStudentByUsername(createTimeEntryReq.getUsername()));
                    System.out.println("T id" + timeEntry.getId());
                    CreateTimeEntryRsp createTimeEntryRsp = new CreateTimeEntryRsp(timeEntry.getId());
                    
                    System.out.println(Response.status(Response.Status.OK).entity(createTimeEntryRsp).build());
                    return Response.status(Response.Status.OK).entity(createTimeEntryRsp).build();
                }else if(createTimeEntryReq.getRole().equals("lecturer")){
                    System.out.println("lecturer");
                    TimeEntry timeEntry =  timeEntryController.createTimeEntry(createTimeEntryReq.getTimeEntry(), 
                        lecturerController.retrieveLecturerByUsername(createTimeEntryReq.getUsername()));
                    System.out.println("T id" + timeEntry.getId());
                    CreateTimeEntryRsp createTimeEntryRsp = new CreateTimeEntryRsp(timeEntry.getId());
                    System.out.println(Response.status(Response.Status.OK).entity(createTimeEntryRsp).build());
                    return Response.status(Response.Status.OK).entity(createTimeEntryRsp).build();
                }
                else{
                    ErrorRsp errorRsp = new ErrorRsp("no role");
            
                    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
                }
            }
            
            catch(Exception ex)
            {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
            }
            
        }
        else
        {
            ErrorRsp errorRsp = new ErrorRsp("Invalid create time entry request");
            
            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        }
    }
    
    @Path("{timeEntryId}")
    @DELETE
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteTimeEntry(@PathParam("timeEntryId") Long timeEntryId)
    {
        try
        {
            timeEntryController.deleteTimeEntry(timeEntryId);
            
            return Response.status(Response.Status.OK).build();
        }
        catch(Exception ex)
        {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }
    
    
    
    private TimeEntryControllerLocal lookupTimeEntryControllerLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (TimeEntryControllerLocal) c.lookup("java:global/LearningHubSystem/LearningHubSystem-ejb/TimeEntryController!ejb.session.stateless.TimeEntryControllerLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    
    private StudentControllerLocal lookupStudentControllerLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (StudentControllerLocal) c.lookup("java:global/LearningHubSystem/LearningHubSystem-ejb/StudentController!ejb.session.stateless.StudentControllerLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    
    private LecturerControllerLocal lookupLecturerControllerLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (LecturerControllerLocal) c.lookup("java:global/LearningHubSystem/LearningHubSystem-ejb/LecturerController!ejb.session.stateless.LecturerControllerLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
