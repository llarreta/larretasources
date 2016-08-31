package co.com.directv.sdii.model.vo;

import java.io.Serializable;
import java.util.Date;

import co.com.directv.sdii.model.pojo.Alliance;


/**
 * 
 * AllianceFile Value Object
 * 
 * Fecha de Creación: Mar 23, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.AllianceFile
 */
public class AllianceFileVO /*extends AllianceFile*/ implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5714517678128650978L;
	private byte[] datos;
	private Long id;
	private Alliance alliance;
    private String filename;
    private Date filedate;
    
    
    public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Alliance getAlliance() {
		return alliance;
	}


	public void setAlliance(Alliance alliance) {
		this.alliance = alliance;
	}


	public String getFilename() {
		return filename;
	}


	public void setFilename(String filename) {
		this.filename = filename;
	}


	public Date getFiledate() {
		return filedate;
	}


	public void setFiledate(Date filedate) {
		this.filedate = filedate;
	}


	
	
	public byte[] getDatos() {
        return this.datos;
    }
    
    
    public void setDatos(byte[] datos) {
        this.datos = datos;
    }
	
}
