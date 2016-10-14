package co.com.directv.sdii.model.pojo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


/**
 * MassiveMovement entity. @author MyEclipse Persistence Tools
 */

public class MassiveMovement  implements java.io.Serializable {


    // Fields    

     private Long id;
     private User user;
     private ProcessStatus processStatus;
     private Date creationDate;
     private Date startProcessDate;
     private Date finishProcessDate;

    // Constructors

    /** default constructor */
    public MassiveMovement() {
    }

	/** minimal constructor */
    public MassiveMovement(User user, ProcessStatus processStatus, Date creationDate, Date startProcessDate, Date finishProcessDate) {
        this.user = user;
        this.processStatus = processStatus;
        this.creationDate = creationDate;
        this.startProcessDate = startProcessDate;
        this.finishProcessDate = finishProcessDate;
    }
    // Property accessors

    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return this.user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }

    public ProcessStatus getProcessStatus() {
        return this.processStatus;
    }
    
    public void setProcessStatus(ProcessStatus processStatus) {
        this.processStatus = processStatus;
    }

    public Date getCreationDate() {
        return this.creationDate;
    }
    
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getStartProcessDate() {
        return this.startProcessDate;
    }
    
    public void setStartProcessDate(Date startProcessDate) {
        this.startProcessDate = startProcessDate;
    }

    public Date getFinishProcessDate() {
        return this.finishProcessDate;
    }
    
    public void setFinishProcessDate(Date finishProcessDate) {
        this.finishProcessDate = finishProcessDate;
    }
}