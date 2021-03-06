/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.FileEntity;
import entity.Module;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import util.exception.FileEntityExistException;
import util.exception.FileEntityNotFoundException;
import util.exception.GeneralException;

/**
 *
 * @author mango
 */
@Stateless
@Local(FileEntityControllerLocal.class)
public class FileEntityController implements FileEntityControllerLocal {

    @PersistenceContext(unitName = "LearningHubSystem-ejbPU")
    private EntityManager em;

    private FileEntity fileEntity;

    @Override
    public FileEntity createNewFileEntity(FileEntity file, Long moduleId) throws FileEntityExistException {
        List<FileEntity> files = retrieveAllFiles();
        for (FileEntity each : files) {
            if (each.getId().equals(file.getId())) {
                throw new FileEntityExistException("This File Already Exist.\n");
            }
        }
        em.persist(file);
        Module module = em.find(Module.class, moduleId);
        file.setModule(module);
        module.getFiles().add(file);
        
        return file;

    }

    @Override
    public FileEntity retrieveFileById(Long fileId) {
        fileEntity = em.find(FileEntity.class, fileId);
        //lazy fectching
        fileEntity.getModule().getId();
        return fileEntity;
    }

    @Override
    public List<FileEntity> retrieveAllFiles() {
        Query query = em.createQuery("SELECT f FROM FileEntity f");
        return (List<FileEntity>) query.getResultList();
    }

    public void persist(Object object) {
        em.persist(object);
    }
}
