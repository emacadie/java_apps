/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.shelfunit.contact.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author ericm
 */
@ManagedBean( name="statusBean" )
@SessionScoped
public class StatusBean {
    
    private String status;
    private String statusMessage;
    
    /** Creates a new instance of ErrorBean */
    public StatusBean() {
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage( String statusMessage ) {
        this.statusMessage = statusMessage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
}
