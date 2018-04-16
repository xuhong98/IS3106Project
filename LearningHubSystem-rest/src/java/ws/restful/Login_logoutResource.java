/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful;

import ejb.session.stateless.AdministratorControllerLocal;
import ejb.session.stateless.LecturerControllerLocal;
import ejb.session.stateless.ModuleControllerLocal;
import ejb.session.stateless.StudentControllerLocal;
import ejb.session.stateless.TeachingAssistantControllerLocal;
import entity.Student;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBElement;
import ws.restful.datamodel.CreateStudentReq;
import ws.restful.datamodel.CreateStudentRsp;
import ws.restful.datamodel.ErrorRsp;
import ws.restful.datamodel.LecturerLoginRsp;
import ws.restful.datamodel.RetrieveModulesRsp;
import ws.restful.datamodel.StudentLoginRsp;
import ws.restful.datamodel.UpdateStudentReq;

/**
 * REST Web Service
 *
 * @author wyh
 */
@Path("login_logout")
public class Login_logoutResource {
    
    StudentControllerLocal studentController;
    AdministratorControllerLocal adminController;
    LecturerControllerLocal lecturerController;
    TeachingAssistantControllerLocal taController;

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of Login_logoutResource
     */
    public Login_logoutResource() {
        studentController = lookupStudentControllerLocal();
        lecturerController = lookupLecturerControllerLocal();
        adminController = lookupAdminControllerLocal();
        taController = lookupTAControllerLocal();
    }

    /**
     * Retrieves representation of an instance of ws.restful.Login_logoutResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getXml() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of Login_logoutResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
    }
    
    @Path("studentLogin/{usernam}/{password}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response studentLogin(@PathParam("username") String username, @PathParam("password") String password ) {
        try {
            StudentLoginRsp studentLoginRsp = new StudentLoginRsp(studentController.login(username, password));

            return Response.status(Response.Status.OK).entity(studentLoginRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }
    

    
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createStudent(JAXBElement<CreateStudentReq> jaxbCreateStudentReq)
    {
        if((jaxbCreateStudentReq != null) && (jaxbCreateStudentReq.getValue() != null))
        {
            try
            {
                CreateStudentReq createStudentReq = jaxbCreateStudentReq.getValue();
                
                Student student = studentController.createStudent(createStudentReq.getStudent());
                CreateStudentRsp createStudentRsp = new CreateStudentRsp(student.getId());
                
                return Response.status(Response.Status.OK).entity(createStudentRsp).build();
            }
            
            catch(Exception ex)
            {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
            }
        }
        else
        {
            ErrorRsp errorRsp = new ErrorRsp("Invalid create student request");
            
            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        }
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateStudent(JAXBElement<UpdateStudentReq> jaxbUpdateStudentReq)
    {
        if((jaxbUpdateStudentReq != null) && (jaxbUpdateStudentReq.getValue() != null))
        {
            try
            {
                UpdateStudentReq updateStudentReq = jaxbUpdateStudentReq.getValue();
                
                studentController.updateStudent(updateStudentReq.getStudent());
                
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
            ErrorRsp errorRsp = new ErrorRsp("Invalid update admin request");
            
            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        }
    }
    
    @Path("lecturerLogin/{username}/{password}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response lecturerLogin(@PathParam("username") String username, @PathParam("password") String password ) {
        try {
            LecturerLoginRsp lecturerLoginRsp = new LecturerLoginRsp(lecturerController.login(username, password));

            return Response.status(Response.Status.OK).entity(lecturerLoginRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
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
    
    private AdministratorControllerLocal lookupAdminControllerLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (AdministratorControllerLocal) c.lookup("java:global/LearningHubSystem/LearningHubSystem-ejb/AdministratorController!ejb.session.stateless.AdministratorControllerLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    
    private TeachingAssistantControllerLocal lookupTAControllerLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (TeachingAssistantControllerLocal) c.lookup("java:global/LearningHubSystem/LearningHubSystem-ejb/TeachingAssistantController!ejb.session.stateless.TeachingAssistantControllerLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
