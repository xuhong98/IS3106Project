/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *
 * @author mango
 */
@Entity
public class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(length = 32, nullable = false)
    private String name;
    
    @Column(length = 128, nullable = false)
    private String password;
    
    @Column(length = 32, nullable = false, unique = true)
    @Pattern(regexp = "\\b[\\w.%-]+@[-.\\w]+\\.[A-Za-z]{2,4}\\b")
    private String email;
    
    @Column(length = 32, nullable = false)
    private String faculty;
    
    @Column(length = 32, nullable = false)
    private String department;
    
    @Column(length = 13, nullable = false, unique = true)
    private String telephone;
    
    @Column(length = 32, nullable = false, unique = true)
    private String username;
    
    @Column(nullable = false)
    private boolean isPremium;
    
    private String photoName;

    @ManyToMany(cascade={CascadeType.PERSIST})
    @JoinTable(name="STUDENT_MODULE")
    private List<Module> modules;
    
    @OneToMany(cascade={CascadeType.ALL})
    private List<TimeEntry> timeEntries ;

    public Student() {
        modules = new ArrayList<>();
        timeEntries = new ArrayList<>();
        this.isPremium = false;
        modules = new ArrayList<>();
        photoName = "nophoto";
    }

    public Student(String name, String password, String email, String faculty, String department, String telephone, String username) {
        this();
        this.name = name;
        this.password = password;
        this.email = email;
        this.faculty = faculty;
        this.department = department;
        this.telephone = telephone;
        this.username = username;
    }
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean getIsPremium() {
        return isPremium;
    }

    public void setIsPremium(boolean isPremium) {
        this.isPremium = isPremium;
    }

    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }

    public List<TimeEntry> getTimeEntries() {
        return timeEntries;
    }

    public void setTimeEntries(List<TimeEntry> timeEntries) {
        this.timeEntries = timeEntries;
    }

    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Student)) {
            return false;
        }
        Student other = (Student) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Student[ id=" + id + " ]";
    }

    /**
     * @return the photoName
     */
    public String getPhotoName() {
        return photoName;
    }

    /**
     * @param photoName the photoName to set
     */
    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }
    
}