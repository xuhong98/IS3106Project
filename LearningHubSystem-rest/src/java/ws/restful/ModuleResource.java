/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful;

import ejb.session.stateless.ModuleControllerLocal;
import entity.Module;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBElement;
import util.exception.ModuleNotFoundException;
import ws.restful.datamodel.ClassAndGroupsRsp;
import ws.restful.datamodel.CreateModuleReq;
import ws.restful.datamodel.CreateModuleRsp;
import ws.restful.datamodel.DeleteModuleReq;
import ws.restful.datamodel.ErrorRsp;
import ws.restful.datamodel.RetrieveAnnouncementsRsp;
import ws.restful.datamodel.RetrieveLecturersRsp;
import ws.restful.datamodel.RetrieveModulesRsp;
import ws.restful.datamodel.RetrieveSpecificModuleRsp;
import ws.restful.datamodel.RetrieveStudentsRsp;
import ws.restful.datamodel.RetrieveTAsRsp;

/**
 * REST Web Service
 *
 * @author wyh
 */
@Path("module")
public class ModuleResource {

    ModuleControllerLocal moduleController;

    @Context
    private UriInfo context;

    public ModuleResource() {
        moduleController = lookupModuleControllerLocal();
    }

    @Path("retrieveEnrolledModules/{username}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveEnrolledModules(@PathParam("username") String username) {
        try {
            RetrieveModulesRsp retrieveModulesRsp = new RetrieveModulesRsp(moduleController.retrieveModulesByStudentUsername(username));

            return Response.status(Response.Status.OK).entity(retrieveModulesRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    @Path("retrieveSpecificModule/{moduleId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveSpecificModule(@PathParam("moduleId") Long moduleId) {
        try {
            RetrieveSpecificModuleRsp retrieveSpecificModuleRsp = new RetrieveSpecificModuleRsp(moduleController.retrieveModuleById(moduleId));

            return Response.status(Response.Status.OK).entity(retrieveSpecificModuleRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    @Path("retrieveClassAndGroups/{moduleId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveClassAndGroups(@PathParam("moduleId") Long moduleId) {
        try {
            ClassAndGroupsRsp classAndGroupsRsp = new ClassAndGroupsRsp(moduleController.retrieveClassAndGroups(moduleId));

            return Response.status(Response.Status.OK).entity(classAndGroupsRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    @Path("retrieveAnnoucements/{moduleId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAnnoucements(@PathParam("moduleId") Long moduleId) {
        try {
            RetrieveAnnouncementsRsp retrieveAnnouncementsRsp = new RetrieveAnnouncementsRsp(moduleController.retrieveAnnoucements(moduleId));

            return Response.status(Response.Status.OK).entity(retrieveAnnouncementsRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    @Path("retrieveLecturers/{moduleId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveLecturers(@PathParam("moduleId") Long moduleId) {
        try {
            RetrieveLecturersRsp retrieveLecturersRsp = new RetrieveLecturersRsp(moduleController.retrieveLecturers(moduleId));

            return Response.status(Response.Status.OK).entity(retrieveLecturersRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    @Path("retrieveStudents/{moduleId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveStudents(@PathParam("moduleId") Long moduleId) {
        try {
            RetrieveStudentsRsp retrieveStudentsRsp = new RetrieveStudentsRsp(moduleController.retrieveStudents(moduleId));

            return Response.status(Response.Status.OK).entity(retrieveStudentsRsp).build();
        } catch (ModuleNotFoundException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    @Path("retrieveTAs/{moduleId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveTAs(@PathParam("moduleId") Long moduleId) {
        try {
            RetrieveTAsRsp retrieveTAsRsp = new RetrieveTAsRsp(moduleController.retrieveTAs(moduleId));

            return Response.status(Response.Status.OK).entity(retrieveTAsRsp).build();
        } catch (ModuleNotFoundException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    @Path("createModule/{module}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createModule(JAXBElement<CreateModuleReq> jaxbCreateModuleReq) {
        if ((jaxbCreateModuleReq != null) && (jaxbCreateModuleReq.getValue() != null)) {
            try {
                CreateModuleReq createModuleReq = jaxbCreateModuleReq.getValue();

                Module module = moduleController.createNewModule(createModuleReq.getModule());
                CreateModuleRsp createModuleRsp = new CreateModuleRsp(module);

                return Response.status(Response.Status.OK).entity(createModuleRsp).build();
            } catch (Exception ex) {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
            }
        } else {
            ErrorRsp errorRsp = new ErrorRsp("Invalid create module request");

            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        }
    }

    @Path("deleteModule/{module}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void deleteModule(JAXBElement<DeleteModuleReq> jaxbDeleteModuleReq) {
        if ((jaxbDeleteModuleReq != null) && (jaxbDeleteModuleReq.getValue() != null)) {
            try {
                DeleteModuleReq deleteModuleReq = jaxbDeleteModuleReq.getValue();

                moduleController.deleteModule(deleteModuleReq.getModule());

                //return Response.status(Response.Status.OK).build();
            } catch (ModuleNotFoundException ex) {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

                //return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
            }
        } else {
            ErrorRsp errorRsp = new ErrorRsp("Invalid create module request");

            //return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
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
