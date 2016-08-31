package co.com.directv.sdii.model.vo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.SQLException;

import org.apache.commons.io.FileUtils;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.pojo.LoadFile;

/**
 * Objeto para extener las funcionalidades de LoadFile 
 * 
 * Fecha de Creación: 25/01/2012
 * @author cduarte <a href="mailto:cduarte@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
public class LoadFileVO extends LoadFile implements Serializable {
	
	private static final long serialVersionUID = 5343552433513220467L;
	
	private File file;
	public LoadFileVO() {
	}
	
	/**
	 * Metodo: Retorna un File del blob
	 * @return File File to Blob
	 * @author
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws PropertiesException 
	 */
	public File getFile() throws IOException, SQLException, PropertiesException {
		
		String path = getPathFile();
    	FileOutputStream fos=null;
    	int size=0;
    	InputStream inStream = null;
		
    	FileUtils.forceMkdir(new File(getLocation()));
    		file = new File(path);
    		inStream = getObjectFile().getBinaryStream();
		
			size = (int)getObjectFile().length();
			byte[] buffer = new byte[size];                
	    	int length = -1;                
	    	fos = new FileOutputStream(file);
			while ((length = inStream.read(buffer)) != -1){                  
				fos.write(buffer, 0, length);                                
			}
			inStream.close();
			fos.close();
		return file;
    	
	}
	
	/**
	 * Metodo: Retorna un el Path File
	 * @return String PathFile
	 * @author
	 * @throws PropertiesException 
	 */
	
	public String getPathFile() throws PropertiesException {
		
		return getLocation() + getLoadFileType().getCode() + getId() +getObjectName();
    	
	}
	
	/**
	 * Metodo: Retorna la localizacion del archivo
	 * @return String
	 * @author
	 * @throws PropertiesException 
	 */
	public String getLocation() throws PropertiesException {
		
		String location = "/"; 
		
		location =  CodesBusinessEntityEnum.CODE_UPLOAD_FILE_LOCATION.getCodeEntity();
		return location;
	}
	
}
