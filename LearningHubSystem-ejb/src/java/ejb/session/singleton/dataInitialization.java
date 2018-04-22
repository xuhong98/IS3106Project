/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

import ejb.session.stateless.AnnouncementControllerLocal;
import ejb.session.stateless.LecturerControllerLocal;
import ejb.session.stateless.ModuleControllerLocal;
import ejb.session.stateless.StudentControllerLocal;
import ejb.session.stateless.TeachingAssistantControllerLocal;
import ejb.session.stateless.TimeEntryControllerLocal;
import entity.Administrator;
import entity.Announcement;
import entity.Lecturer;
import entity.Module;
import entity.Student;
import entity.TeachingAssistant;
import entity.TimeEntry;
import java.io.File;
import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.exception.AnnouncementNotFoundException;
import util.exception.GeneralException;
import util.exception.LecturerNotFoundException;
import util.exception.ModuleNotFoundException;
import util.exception.StudentNotFoundException;
import util.exception.TANotFoundException;
import util.exception.TimeEntryExistException;

/**
 *
 * @author mango
 */
@Singleton
@LocalBean
@Startup
public class dataInitialization {

    @EJB
    private AnnouncementControllerLocal announcementControllerLocal;

    @EJB
    private TeachingAssistantControllerLocal teachingAssistantControllerLocal;

    @EJB
    private StudentControllerLocal studentControllerLocal;

    @EJB
    private LecturerControllerLocal lecturerControllerLocal;

    @EJB(name = "ModuleControllerLocal")
    private ModuleControllerLocal moduleControllerLocal;

    @PersistenceContext(unitName = "LearningHubSystem-ejbPU")
    private EntityManager em;

    @EJB
    TimeEntryControllerLocal tecl;

    public dataInitialization() {

    }

    @PostConstruct
    public void postConstruct() {

        if (em.find(Administrator.class, 1l) == null) {
            loadAdminData();
        }
        if (em.find(Module.class, 1l) == null) {
            loadModuleData();
        }
        if (em.find(Lecturer.class, 1l) == null) {
            loadLecturerData();
        }
        if (em.find(Student.class, 1l) == null) {
            loadStudentData();
        }
        if (em.find(TeachingAssistant.class, 1l) == null) {
            loadTAData();
        }
        if (em.find(TimeEntry.class, 1l) == null) {
            loadTEData();
        }
        if (em.find(Announcement.class, 1l) == null) {
            loadAnnoucementData();
            setRelationships();
        }
    }

    private void loadAnnoucementData() {
        try {
            //twk 3106
            Lecturer twk = lecturerControllerLocal.retrieveLecturerByUsername("lecturer1");
            Module is3106 = moduleControllerLocal.retrieveModuleByModuleCode("IS3106");
            Announcement newAnnouncement1 = new Announcement("Final Set of Screencast Video Recordings", "Dear Students,The following screencast video recordings are ready for viewing", new Timestamp(118, 3, 28, 15, 0, 0, 0), twk, is3106);
            Announcement newAnnouncement2 = new Announcement("Group Project Written Report Requirements", "Dear Students,Good luck with your project submission :)\n don't miss the ddl!", new Timestamp(118, 4, 3, 13, 0, 0, 0), twk, is3106);
            em.persist(newAnnouncement1);
            em.persist(newAnnouncement2);

        } catch (LecturerNotFoundException | ModuleNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    private void loadAdminData() {
        Administrator newAdmin = new Administrator("administrator", "admin@soc.nus", "12345678", "admin", "password");
        em.persist(newAdmin);
    }

    private void loadLecturerData() {
        Lecturer newLecturer1 = new Lecturer("lecturer1", "password1", "Tan Wee Kek", "twk@soc.nus", "Computing", "IS", "12345678");
        newLecturer1.setPhotoName("twk");
        newLecturer1.setIsPremium(true);
        em.persist(newLecturer1);
        Lecturer newLecturer2 = new Lecturer("lecturer2", "password2", "Lek Hsiang Hui", "lhh@soc.nus", "Computing", "IS", "23456789");
        newLecturer2.setPhotoName("lhh");
        newLecturer1.setIsPremium(true);
        em.persist(newLecturer2);
        Lecturer newLecturer3 = new Lecturer("lecturer3", "password3", "Oh Lih Bin", "oh@soc.nus", "Computing", "IS", "34567890");
        newLecturer3.setPhotoName("oh");
        newLecturer1.setIsPremium(true);
        em.persist(newLecturer3);
        Lecturer newLecturer4 = new Lecturer("lecturer4", "password4", "Tay Seng Chuan", "tsc@sci.nus", "Science", "Physics", "45678901");
        newLecturer4.setPhotoName("tsc");
        em.persist(newLecturer4);
        Lecturer newLecturer5 = new Lecturer("lecturer5", "password5", "Lin Jo Yan", "ljy@sci.nus", "Business", "Finance", "45810671");
        newLecturer4.setPhotoName("ljy");
        em.persist(newLecturer5);
    }

    private void loadModuleData() {
        Timestamp timestamp = new Timestamp(118, 5, 3, 9, 0, 0, 0);
        Module newModule1 = new Module("Database Systems", "CS2102", 4, 177,
                "The aim of this module is to introduce the fundamental concepts and techniques necessary for the understanding and practice of design and implementation of database applications and of the management of data with relational database management systems. The module covers practical and theoretical aspects of design with entity-relationship model, theory of functional dependencies and normalisation by decomposition in second, third and Boyce-Codd normal forms. The module covers practical and theoretical aspects of programming with SQL data definition and manipulation sublanguages, relational tuple calculus, relational domain calculus and relational algebra.", timestamp);
        em.persist(newModule1);
        Timestamp timestamp2 = new Timestamp(118, 4, 30, 13, 0, 0, 0);
        Module newModule2 = new Module("Enterprise Systems Interface Design and Development", "IS3106", 4, 60,
                "This module aims to train students to be conversant in front-end development for Enterprise Systems. It complements IS2103 which focuses on backend development aspects for Enterprise Systems. Topics covered include: web development scripting languages, web templating design and component design, integrating with backend application, and basic mobile application development.", timestamp2);
        em.persist(newModule2);
        Timestamp timestamp3 = new Timestamp(118, 4, 30, 13, 0, 0, 0);
        Module newModule3 = new Module("Regression Analysis", "ST3131", 4, 232,
                "This module focuses on data analysis using multiple regression models. Topics include simple linear regression, multiple regression, model building and regression diagnostics. One and two factor analysis of variance, analysis of covariance, linear model as special case of generalized linear model. This module is targeted at students who are interested in Statistics and are able to meet the pre-requisites.", timestamp3);
        em.persist(newModule3);
        Timestamp timestamp4 = new Timestamp(118, 4, 9, 13, 0, 0, 0);
        Module newModule4 = new Module("Chemical Engineering Principles and Practice II", "CN1102", 4, 30,
                "This module is the second part of a two-part module designed to provide first year Chemical and Biomolecular Engineering students with an experiential exposure to the foundational concepts of Biomolecular/Biochemical/Bioprocess Engineering, including mass and energy balances, biosafety and sterile handling, bioreaction kinetics, bioreactor design, downstream processing and purification, biosystems modelling and optimization, etc., through a series of hands-on experimental laboratories. In the laboratory, they will learn to carry out measurement, data collection, analysis, modelling, interpretation and presentation. The laboratory sessions will be blended with real engineering applications of industrial and societal relevance to Singapore .", timestamp4);
        em.persist(newModule4);
        Timestamp timestamp5 = new Timestamp(118, 4, 5, 13, 0, 0, 0);
        Module newModule5 = new Module("Finance", "FIN2004", 4, 102,
                "This course helps students to understand the key concepts and tools in Finance. It provides a broad overview of the financial environment under which a firm operates. It equips the students with the conceptual and analytical skills necessary to make sound financial decisions for a firm. Topics to be covered include introduction to finance, financial statement analysis, long-term financial planning, time value of money, risk and return analysis, capital budgeting methods and applications, common stock valuation, bond valuation, short term management and financing.", timestamp5);
        em.persist(newModule5);
        Timestamp timestamp6 = new Timestamp(118, 4, 5, 17, 0, 0, 0);
        Module newModule6 = new Module("Essential Data Analytics Tools: Numerical Computation", "DSA2102", 4, 33,
                "This module aims at introducing basic concepts and wellestablished numerical methods that are very related to the computing foundation of data science and analytics. The emphasis is on the tight integration of numerical algorithms, implementation in industrial programming language, and examination on practical examples drawn from various disciplines related to data science. Major topics include: computer arithmetic, matrix multiplication, numerical methods for solving linear systems, least squares method, interpolation, concrete implementations in industrial program language, and sample applications related to data science.", timestamp6);
        em.persist(newModule6);

        //create folder for uploading files
        List<Module> modules = moduleControllerLocal.retrieveAllModules();

        for (Module each : modules) {
            Boolean success = (new File("/Applications/NetBeans/glassfish-4.1.1-uploadedfiles/uploadedFiles/" + each.getModuleCode())).mkdirs();
            if (!success) {
                System.err.println("The new folder is not created successfully!");
            }
        }
    }

    private void loadStudentData() {
        List<Module> modules = moduleControllerLocal.retrieveAllModules();
        Student student1 = new Student("Wang Yinhan", "wyhpassword", "wyh@soc.nus", "Computing", "IS", "13579135", "wangyinhan");
        student1.setIsPremium(true);
        student1.setPhotoName("wyh");
        em.persist(student1);
        Student student2 = new Student("Gong Zipeng", "gzppassword", "gzp@soc.nus", "Computing", "IS", "34464224", "gongzipeng");
        student2.setIsPremium(true);
        student2.setPhotoName("gzp");
        em.persist(student2);
        Student student3 = new Student("Lin Xianying", "lxypassword", "lxy@soc.nus", "Computing", "IS", "12345577", "linxianying");
        student3.setIsPremium(true);
        student3.setPhotoName("lxy");
        em.persist(student3);
        Student student4 = new Student("Xu Hong", "xhpassword", "xh@soc.nus", "Computing", "IS", "24688424", "xuhong");
        student4.setIsPremium(true);
        student4.setPhotoName("xh");
        em.persist(student4);
        Student student5 = new Student("Zou Yutong", "zytpassword", "zyt@sci.nus", "Science", "DSA", "14642694", "zouyutong");
        student5.setPhotoName("zyt");
        student5.setIsPremium(true);
        em.persist(student5);
        Student student6 = new Student("Wang Jingheng", "wjhpassword", "wjh@engin.nus", "Engineering", "Chem Engin", "12347765", "wangjingheng");
        student6.setPhotoName("wjh");
        student6.setIsPremium(false);
        em.persist(student6);
        Student student7 = new Student("Peppa pig", "pigpassword", "piggy@com.nus", "Computing", "CS", "13574214", "piggy");
        student7.setPhotoName("pig");
        student7.setIsPremium(false);
        em.persist(student7);
        Student student8 = new Student("Goat Sussie", "goatpassword", "goat@engin.nus", "Engineering", "Civil Engin", "12344515", "sussie");
        student8.setPhotoName("goat");
        student8.setIsPremium(false);
        em.persist(student8);
        Student student9 = new Student("Sponge Bob", "bobpassword", "bob@fass.nus", "FASS", "Economics", "71276345", "spongebob");
        student9.setPhotoName("bob");
        student9.setIsPremium(false);
        em.persist(student9);
        Student student10 = new Student("Garfield", "password", "garfield@biz.nus", "Business", "Accounting", "77612345", "garfield");
        student10.setPhotoName("cat");
        student10.setIsPremium(false);
        em.persist(student10);
        Student student11 = new Student("Snow White", "password", "white@biz.nus", "Business", "Finance", "73457612", "snowwhite");
        student11.setPhotoName("snow");
        student11.setIsPremium(false);
        em.persist(student11);

    }

    private void loadTAData() {
        TeachingAssistant teachingAssistant1 = new TeachingAssistant("TA111", "password1", "TA1", "TA1@soc.nus", "Computing", "12345672", "IS");
        teachingAssistant1.setPhotoName("TA1");
        teachingAssistant1.setIsPremium(true);
        em.persist(teachingAssistant1);
        TeachingAssistant teachingAssistant2 = new TeachingAssistant("TA222", "password2", "TA2", "TA2@sci.nus", "Science", "12342354", "Data Analytics");
        teachingAssistant2.setPhotoName("TA2");
        teachingAssistant2.setIsPremium(true);
        em.persist(teachingAssistant2);
        TeachingAssistant teachingAssistant3 = new TeachingAssistant("TA333", "password3", "TA3", "TA3@fass.nus", "FASS", "86356252", "Economics");
        teachingAssistant3.setPhotoName("TA3");
        teachingAssistant3.setIsPremium(true);
        em.persist(teachingAssistant3);
        TeachingAssistant teachingAssistant4 = new TeachingAssistant("TA444", "password4", "TA4", "TA4@engin.nus", "Engineering", "62863552", "Chemical Engineering");
        teachingAssistant4.setPhotoName("TA4");
        teachingAssistant4.setIsPremium(false);
        em.persist(teachingAssistant4);
        TeachingAssistant teachingAssistant5 = new TeachingAssistant("TA555", "password5", "TA5", "TA5@biz.nus", "Business", "62552863", "Accounting");
        teachingAssistant5.setPhotoName("TA5");
        teachingAssistant5.setIsPremium(false);
        em.persist(teachingAssistant5);
        TeachingAssistant teachingAssistant6 = new TeachingAssistant("TA666", "password6", "TA6", "TA6@biz.nus", "Business", "62862553", "Finance");
        teachingAssistant6.setPhotoName("TA6");
        teachingAssistant6.setIsPremium(false);
        em.persist(teachingAssistant6);

    }

    private void loadTEData() {

        TimeEntry t1 = new TimeEntry("Go out", "2018-05-29T12:00:22Z", "2018-05-29T13:00:22Z", "details");
        TimeEntry t2 = new TimeEntry("Study", "2018-04-11T12:00:22Z", "2018-04-11T13:00:22Z", "details");
        TimeEntry t3 = new TimeEntry("Work", "2018-04-29T22:00:22Z", "2018-04-29T22:00:32Z", "details");
        TimeEntry t4 = new TimeEntry("Sport", "2018-04-29T18:00:22Z", "2018-04-29T20:00:32Z", "sports with friends");
        TimeEntry t5 = new TimeEntry("IS3106 final", "2018-04-30T13:00:00Z", "2018-04-30T15:00:00Z", "IS3106 final examination at COM2, don't forget to bring 2B pencil!");
        TimeEntry t6 = new TimeEntry("IS3106 project due", "2018-04-22T00:00:22Z", "2018-04-22T23:59:59Z", "IS3106 project deadline, don't forget to zip everything in one file!");
        TimeEntry t7 = new TimeEntry("Birthday", "2018-05-22T00:00:22Z", "2018-05-22T23:59:59Z", "it's my firends birthday today!!!don't forget!");
        TimeEntry t8 = new TimeEntry("Family stuff", "2018-05-05T00:00:22Z", "2018-05-05T23:59:59Z", "remember go home by 20:00 today!it's family time!");
        TimeEntry t9 = new TimeEntry("Avenger movie day", "2018-05-01T00:00:22Z", "2018-05-01T23:59:59Z", "it's time to see avengers movie! woo hoo!");
        TimeEntry t10 = new TimeEntry("Final 4103 class", "2018-04-20T14:00:00Z", "2018-04-22T16:00:00Z", "last class of IS4103, you can't imagine how happy i am!");
        TimeEntry t11 = new TimeEntry("Hotpot night", "2018-05-01T00:00:22Z", "2018-05-01T23:59:59Z", "Hotpot night is tonight!!!");
        TimeEntry t12 = new TimeEntry("IIP interview", "2018-05-22T14:00:22Z", "2018-05-22T14:30:59Z", "phone interview with KPMG");
        TimeEntry t13 = new TimeEntry("Shopping with Cao Yu", "2018-05-11T12:00:22Z", "2018-05-11T13:00:22Z", "Orchard");
        TimeEntry t14 = new TimeEntry("Drinking night", "2018-04-21T12:00:22Z", "2018-04-21T13:00:22Z", "Go out and drink with friends");
        TimeEntry t15 = new TimeEntry("IS4103 project", "2018-04-12T13:00:00Z", "2018-04-12T15:00:00Z", "IS4103 final examination at COM2, don't forget to bring 2B pencil!");
        TimeEntry t16 = new TimeEntry("IS4103 project due", "2018-03-22T00:00:22Z", "2018-03-22T23:59:59Z", "IS4103 project deadline, don't forget to zip everything in one file!");
        TimeEntry t17 = new TimeEntry("WZY birthday", "2018-05-11T00:00:22Z", "2018-05-11T23:59:59Z", "it's WZY's firends birthday today!!!don't forget!");
        TimeEntry t18 = new TimeEntry("Family stuff", "2018-05-05T00:00:22Z", "2018-05-05T23:59:59Z", "remember go home by 22:00 today!it's family time!");
        TimeEntry t19 = new TimeEntry("A quite place movie day", "2018-05-01T00:00:22Z", "2018-05-01T23:59:59Z", "it's time to see A quite place movie! woo hoo!");
        TimeEntry t20 = new TimeEntry("Final 3106 class", "2018-04-20T14:00:00Z", "2018-04-22T16:00:00Z", "last class of IS3106, you can't imagine how happy i am!");
        TimeEntry t21 = new TimeEntry("Autodesk interview", "2018-04-01T10:00:00Z", "2018-04-01T12:59:59Z", "Autodesk interview is this morning!!!");
        TimeEntry t22 = new TimeEntry("GIC interview", "2018-04-22T14:00:22Z", "2018-04-22T14:30:59Z", "phone interview with KPMG");

        try {
            Student student1 = studentControllerLocal.retrieveStudentByUsername("xuhong");
            Student student2 = studentControllerLocal.retrieveStudentByUsername("linxianying");
            Student student3 = studentControllerLocal.retrieveStudentByUsername("wangyinhan");
            Student student4 = studentControllerLocal.retrieveStudentByUsername("gongzipeng");
            Student student5 = studentControllerLocal.retrieveStudentByUsername("wangjingheng");
            Lecturer twk = lecturerControllerLocal.retrieveLecturerByUsername("lecturer1");
            Lecturer lhh = lecturerControllerLocal.retrieveLecturerByUsername("lecturer2");
            tecl.createTimeEntry(t1, student1);
            tecl.createTimeEntry(t2, student1);
            tecl.createTimeEntry(t14, student1);
            tecl.createTimeEntry(t15, student1);
            tecl.createTimeEntry(t16, student1);
            tecl.createTimeEntry(t3, student2);
            tecl.createTimeEntry(t4, student2);
            tecl.createTimeEntry(t13, student2);
            tecl.createTimeEntry(t17, student2);
            tecl.createTimeEntry(t18, student2);
            tecl.createTimeEntry(t19, student2);
            tecl.createTimeEntry(t5, student3);
            tecl.createTimeEntry(t21, student3);
            tecl.createTimeEntry(t22, student3);
            tecl.createTimeEntry(t6, student4);
            tecl.createTimeEntry(t20, student4);
            tecl.createTimeEntry(t12, student4);
            tecl.createTimeEntry(t7, student5);
            tecl.createTimeEntry(t8, twk);
            tecl.createTimeEntry(t10, twk);
            tecl.createTimeEntry(t9, lhh);
            tecl.createTimeEntry(t11, lhh);
        } catch (TimeEntryExistException | StudentNotFoundException | LecturerNotFoundException ex) {
            Logger.getLogger(dataInitialization.class.getName()).log(Level.SEVERE, null, ex);
        } catch (GeneralException ex) {
            Logger.getLogger(dataInitialization.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void setRelationships() {

        try {
            Student wyh = studentControllerLocal.retrieveStudentByUsername("wangyinhan");
            Student xh = studentControllerLocal.retrieveStudentByUsername("xuhong");
            Student gzp = studentControllerLocal.retrieveStudentByUsername("gongzipeng");
            Student lxy = studentControllerLocal.retrieveStudentByUsername("linxianying");
            Student zyt = studentControllerLocal.retrieveStudentByUsername("zouyutong");
            Student wjh = studentControllerLocal.retrieveStudentByUsername("wangjingheng");
            Student pig = studentControllerLocal.retrieveStudentByUsername("piggy");
            Student goat = studentControllerLocal.retrieveStudentByUsername("sussie");
            Student bob = studentControllerLocal.retrieveStudentByUsername("spongebob");
            Student cat = studentControllerLocal.retrieveStudentByUsername("garfield");
            Student snow = studentControllerLocal.retrieveStudentByUsername("snowwhite");

            Module is3106 = moduleControllerLocal.retrieveModuleByModuleCode("IS3106");
            Module CS2102 = moduleControllerLocal.retrieveModuleByModuleCode("CS2102");
            Module st3131 = moduleControllerLocal.retrieveModuleByModuleCode("ST3131");
            Module cn1102 = moduleControllerLocal.retrieveModuleByModuleCode("CN1102");
            Module fin2004 = moduleControllerLocal.retrieveModuleByModuleCode("FIN2004");
            Module dsa2102 = moduleControllerLocal.retrieveModuleByModuleCode("DSA2102");

            //wyh
            wyh.getModules().add(is3106);
            wyh.getModules().add(CS2102);
            wyh.getModules().add(st3131);
            wyh.getModules().add(dsa2102);
            is3106.getStduents().add(wyh);
            CS2102.getStduents().add(wyh);
            st3131.getStduents().add(wyh);
            dsa2102.getStduents().add(wyh);

            //xh
            xh.getModules().add(is3106);
            xh.getModules().add(CS2102);
            xh.getModules().add(st3131);
            is3106.getStduents().add(xh);
            CS2102.getStduents().add(xh);
            st3131.getStduents().add(xh);

            //lxy
            lxy.getModules().add(is3106);
            lxy.getModules().add(CS2102);
            lxy.getModules().add(st3131);
            is3106.getStduents().add(lxy);
            CS2102.getStduents().add(lxy);
            st3131.getStduents().add(lxy);

            //gzp
            gzp.getModules().add(is3106);
            gzp.getModules().add(CS2102);
            gzp.getModules().add(st3131);
            gzp.getModules().add(fin2004);
            is3106.getStduents().add(gzp);
            CS2102.getStduents().add(gzp);
            st3131.getStduents().add(gzp);
            fin2004.getStduents().add(gzp);

            //zyt
            zyt.getModules().add(dsa2102);
            zyt.getModules().add(st3131);
            zyt.getModules().add(CS2102);
            dsa2102.getStduents().add(zyt);
            st3131.getStduents().add(zyt);
            CS2102.getStduents().add(zyt);

            //wjh
            wyh.getModules().add(cn1102);
            wjh.getModules().add(fin2004);
            wjh.getModules().add(dsa2102);

            List<Module> modules = moduleControllerLocal.retrieveAllModules();
            //pig
            pig.setModules(modules);
            for (Module module : modules) {
                module.getStduents().add(pig);
            }

            //goat
            goat.setModules(modules);
            for (Module module : modules) {
                module.getStduents().add(goat);
            }
            //bob
            bob.setModules(modules);
            for (Module module : modules) {
                module.getStduents().add(bob);
            }
            //cat
            cat.setModules(modules);
            for (Module module : modules) {
                module.getStduents().add(cat);
            }
            //snow

            snow.setModules(modules);
            for (Module module : modules) {
                module.getStduents().add(snow);
            }
//        for (Student student : students) {
//            student.setModules(modules);
//        }
//        for (Module module : modules) {
//            module.setStduents(students);
//        }

            //relation between module and lecturer, TA, annoucement
            try {
                Lecturer twk = lecturerControllerLocal.retrieveLecturerByUsername("lecturer1");
                Lecturer lhh = lecturerControllerLocal.retrieveLecturerByUsername("lecturer2");
                Lecturer tsc = lecturerControllerLocal.retrieveLecturerByUsername("lecturer4");
                Lecturer oh = lecturerControllerLocal.retrieveLecturerByUsername("lecturer3");
                Lecturer ljy = lecturerControllerLocal.retrieveLecturerByUsername("lecturer5");

                TeachingAssistant ta1 = teachingAssistantControllerLocal.retrieveTAByUsername("TA111");
                TeachingAssistant ta2 = teachingAssistantControllerLocal.retrieveTAByUsername("TA222");
                TeachingAssistant ta3 = teachingAssistantControllerLocal.retrieveTAByUsername("TA333");
                TeachingAssistant ta4 = teachingAssistantControllerLocal.retrieveTAByUsername("TA444");
                TeachingAssistant ta5 = teachingAssistantControllerLocal.retrieveTAByUsername("TA555");
                TeachingAssistant ta6 = teachingAssistantControllerLocal.retrieveTAByUsername("TA666");

//            Module is3106 = moduleControllerLocal.retrieveModuleByModuleCode("IS3106");
//            Module cs2102 = moduleControllerLocal.retrieveModuleByModuleCode("CS2102");
//            Module st3131 = moduleControllerLocal.retrieveModuleByModuleCode("ST3131");
                Announcement announcement1 = announcementControllerLocal.retrieveAnnouncementByName("Final Set of Screencast Video Recordings");
                Announcement announcement2 = announcementControllerLocal.retrieveAnnouncementByName("Group Project Written Report Requirements");

                twk.getModules().add(is3106);
                ta1.getModules().add(is3106);
                ta6.getModules().add(is3106);
                is3106.getLecturers().add(twk);
                is3106.getTAs().add(ta1);
                is3106.getTAs().add(ta6);
                is3106.getAnnouncements().add(announcement1);
                is3106.getAnnouncements().add(announcement2);

                lhh.getModules().add(CS2102);
                ta2.getModules().add(CS2102);
                CS2102.getLecturers().add(lhh);
                CS2102.getTAs().add(ta2);

                tsc.getModules().add(st3131);
                ta3.getModules().add(st3131);
                st3131.getLecturers().add(tsc);
                st3131.getTAs().add(ta3);
                
                oh.getModules().add(cn1102);
                ta4.getModules().add(cn1102);
                cn1102.getLecturers().add(oh);
                cn1102.getTAs().add(ta4);
                
                ljy.getModules().add(fin2004);
                ljy.getModules().add(dsa2102);
                ta5.getModules().add(fin2004);
                ta5.getModules().add(dsa2102);
                fin2004.getLecturers().add(ljy);
                dsa2102.getLecturers().add(ljy);
                fin2004.getTAs().add(ta5);
                dsa2102.getTAs().add(ta5);

            } catch (LecturerNotFoundException | TANotFoundException | AnnouncementNotFoundException ex) {
                ex.getMessage();

            }
        } catch (StudentNotFoundException | ModuleNotFoundException ex) {

        }

    }
}
