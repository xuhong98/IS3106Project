/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.datamodel;

import entity.Module;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Samango
 */
@XmlRootElement
@XmlType(name = "updateModuleReq", propOrder = {
    "moduleId"
})
public class UpdateModuleReq {
    private Long moduleId;

    public UpdateModuleReq() {
    }

    public UpdateModuleReq(Long moduleId) {
        this.moduleId = moduleId;
    }
    
    

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }
    
    
}
