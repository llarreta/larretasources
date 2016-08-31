package co.com.directv.sdii.model.pojo;


import java.sql.Blob;
import java.util.Date;

import co.com.directv.sdii.audit.Auditable;









/**
 * AllianceFile entity. @author MyEclipse Persistence Tools
 */

public class AllianceFile  implements java.io.Serializable,Auditable {


    @Override
	public String toString() {
		return "AllianceFile [id=" + id + "]";
	}


	/**
	 * 
	 */
	private static final long serialVersionUID = 6188553829924921337L;
	// Fields    

     private Long id;
     private Alliance alliance;
     private String filename;
     private Date filedate;     
     private Blob data;
     

    // Constructors

    /** default constructor */
    public AllianceFile() {
    }

    
    /**
     * 
     * @param alliance
     * @param filename
     * @param filedate
     */
    public AllianceFile(Alliance alliance, String filename, Date filedate) {
        this.alliance = alliance;
        this.filename = filename;
        this.filedate = filedate;
    }

   
    // Property accessors

    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public Alliance getAlliance() {
        return this.alliance;
    }
    
    public void setAlliance(Alliance alliance) {
        this.alliance = alliance;
    }

    public String getFilename() {
        return this.filename;
    }
    
    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Date getFiledate() {
        return this.filedate;
    }
    
    public Blob getData() {
		return data;
	}


	public void setData(Blob data) {
		this.data = data;
	}


	public void setFiledate(Date filedate) {
        this.filedate = filedate;
    }

    
    
    
    
   








}