/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.AdministratorControllerLocal;
import ejb.session.stateless.LecturerControllerLocal;
import ejb.session.stateless.StudentControllerLocal;
import ejb.session.stateless.TeachingAssistantControllerLocal;
import entity.Lecturer;
import entity.Student;
import entity.Administrator;
import entity.TeachingAssistant;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpSession;
import util.exception.AdminNotFoundException;
import util.exception.LecturerNotFoundException;
import util.exception.StudentNotFoundException;
import util.exception.TANotFoundException;

/**
 *
 * @author mango
 */
@Named
@ViewScoped
public class settingManagedBean implements Serializable {

    @EJB(name = "TeachingAssistantControllerLocal")
    private TeachingAssistantControllerLocal teachingAssistantControllerLocal;

    @EJB(name = "AdministratorControllerLocal")
    private AdministratorControllerLocal administratorControllerLocal;

    @EJB(name = "LecturerControllerLocal")
    private LecturerControllerLocal lecturerControllerLocal;

    @EJB(name = "StudentControllerLocal")
    private StudentControllerLocal studentControllerLocal;

    private String userType;
    FacesContext context;
    HttpSession session;
    private Student student;
    private Administrator admin;
    private Lecturer lecturer;
    private TeachingAssistant ta;

    public settingManagedBean() {

    }

    @PostConstruct
    public void postConstruct() {
        context = FacesContext.getCurrentInstance();
        session = (HttpSession) context.getExternalContext().getSession(true);

        setUserType((String) session.getAttribute("role"));
        if (getUserType().equals("student")) {
            setStudent((Student) session.getAttribute("currentStudent"));
        } else if (getUserType().equals("lecturer")) {
            setLecturer((Lecturer) session.getAttribute("currentLecturer"));
        } else if (getUserType().equals("TA")) {
            setTa((TeachingAssistant) session.getAttribute("currentTA"));
        } else if (getUserType().equals("admin")) {
            setAdmin((Administrator) session.getAttribute("currentAdmin"));
        }
    }

    public void update(ActionEvent event) {
        if (getUserType().equals("student")) {
            try {
                studentControllerLocal.updateStudent(student);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Profile updated successfully", null));
            } catch (StudentNotFoundException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating product: " + ex.getMessage(), null));
            } catch (Exception ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
            }
        } else if (getUserType().equals("lecturer")) {
            try {
                lecturerControllerLocal.updateLecturer(lecturer);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Profile updated successfully", null));
            } catch (LecturerNotFoundException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating product: " + ex.getMessage(), null));
            } catch (Exception ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
            }
        } else if (getUserType().equals("TA")) {
            try {
                teachingAssistantControllerLocal.updateTA(ta);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Profile updated successfully", null));
            } catch (TANotFoundException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating product: " + ex.getMessage(), null));
            } catch (Exception ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
            }
        } else if (getUserType().equals("admin")) {
            try {
                administratorControllerLocal.updateAdmin(admin);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Profile updated successfully", null));
            } catch (AdminNotFoundException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating product: " + ex.getMessage(), null));
            } catch (Exception ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
            }
        }

    }

    public String getUserType() {
        return userType;
    }

    /**
     * @param userType the userType to set
     */
    public void setUserType(String userType) {
        this.userType = userType;
    }

    /**
     * @return the student
     */
    public Student getStudent() {
        return student;
    }

    /**
     * @param student the student to set
     */
    public void setStudent(Student student) {
        this.student = student;
    }

    /**
     * @return the admin
     */
    public Administrator getAdmin() {
        return admin;
    }

    /**
     * @param admin the admin to set
     */
    public void setAdmin(Administrator admin) {
        this.admin = admin;
    }

    /**
     * @return the lecturer
     */
    public Lecturer getLecturer() {
        return lecturer;
    }

    /**
     * @param lecturer the lecturer to set
     */
    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
    }

    /**
     * @return the ta
     */
    public TeachingAssistant getTa() {
        return ta;
    }

    /**
     * @param ta the ta to set
     */
    public void setTa(TeachingAssistant ta) {
        this.ta = ta;
    }

}
