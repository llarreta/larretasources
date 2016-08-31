package co.com.directv.sdii.reports.dto;

import javax.activation.DataHandler;

/**
 * 
 * Clase que encapsula los archivos generados en la capa de negocio y la informacion
 * del archivo 
 * 
 * Fecha de Creación: 14/07/2011
 * @author jnova <a href="mailto:jnova@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public class FileResponseDTO implements java.io.Serializable{

	private static final long serialVersionUID = 5880252113123326515L;
	
	private DataHandler dataHandler;
	private String fileName;
	
	public DataHandler getDataHandler() {
		return dataHandler;
	}
	public void setDataHandler(DataHandler dataHandler) {
		this.dataHandler = dataHandler;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
