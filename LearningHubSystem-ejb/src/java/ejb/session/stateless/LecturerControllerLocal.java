/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Lecturer;
import entity.Module;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;
import util.exception.GeneralException;
import util.exception.InvalidLoginCredentialException;
import util.exception.LecturerExistException;
import util.exception.LecturerNotFoundException;
import util.exception.ModuleExistException;
import util.exception.ModuleNotFoundException;
import util.exception.PasswordChangeException;

/**
 *
 * @author mango
 */
@Local
public interface LecturerControllerLocal {

    public List<Lecturer> retrieveAllLecturers();

    public Lecturer createNewLecturer(Lecturer lecturer) throws LecturerExistException,GeneralException;

    public Lecturer retrieveLecturerById(Long lecturerId) throws LecturerNotFoundException;

    public Lecturer retrieveLecturerByUsername(String username) throws LecturerNotFoundException;

    public Lecturer retrieveLecturerByEmail(String email) throws LecturerNotFoundException;

    public Lecturer retrieveLecturerByPhoneNum(String phoneNum) throws LecturerNotFoundException;

    public Lecturer login(String username, String password) throws InvalidLoginCredentialException, LecturerNotFoundException;

    public Lecturer updateLecturer(Lecturer lec) throws LecturerNotFoundException;

    public void changePassword(String currentPassword, String newPassword, Long lecturerId) throws LecturerNotFoundException, PasswordChangeException;
    
    public void deleteLecturer (Lecturer lec) throws LecturerNotFoundException;
    
    public Module registerModule (Lecturer lec, Module mod) throws ModuleExistException, ModuleNotFoundException, LecturerNotFoundException;
    
    public void dropModule(Lecturer l, Module m) throws ModuleNotFoundException, LecturerNotFoundException;

    List<Module> retrieveEnrolledModules(Long lecturerId);

    public Lecturer updateLecturerPassword(Long id, String newPassword);
}
