/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.datamodel;

import entity.TimeEntry;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author lxy
 */

@XmlRootElement
@XmlType(name = "createTimeEntryReq", propOrder = {
    "timeEntry",
    "username"
})
public class CreateLecturerTimeEntryReq {
    
    private TimeEntry timeEntry;
    private String username;

    public CreateLecturerTimeEntryReq() {
        
    }
    public CreateLecturerTimeEntryReq(TimeEntry timeEntry) {
        this.timeEntry = timeEntry;
    }

    public TimeEntry getTimeEntry() {
        return timeEntry;
    }

    public void setTimeEntry(TimeEntry timeEntry) {
        this.timeEntry = timeEntry;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    
    
}
