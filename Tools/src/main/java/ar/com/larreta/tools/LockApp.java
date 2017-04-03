package ar.com.larreta.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import ar.com.larreta.annotations.Log;

@Component
public class LockApp {
	
	private static final String SRC_MAIN_RESOURCES = "/src/main/resources/";
	private static final String WEB_INF_CLASSES = "/WEB-INF/classes/";
	private static final String LOCK_APP = "lock.app";
	private static final String SEPARATOR = ";";
	
	private static @Log Logger LOG;

	@Value("${app.enviroment}")
	private String enviroment;
	
	@Value("${realPath}")
	private String realPath;

	@Value("${lockApp.dev.path}")
	private String devPath;
	
	private String lockString;
	
	/**
	 * ruta del archivo de lockeo
	 */
	private String path;
	
	/**
	 * Ultima vez que se almaceno la info en el archivo
	 */
	private Date lastDate;
	
	/**
	 * Fecha de expiracion de la licencia
	 */
	private Date expirationDate;
	
	/**
	 * Ultimo identificador utilizado
	 */
	private Long identifier = new Long(0);
	
	/**
	 * Propietario de la licencia
	 */
	private String propietary;

	@Autowired
	private SimpleDateFormat dateFormatter;

	@Autowired
	private Base64 base64;
	
	@Autowired
	private ApplicationContext context;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	public LockApp(){}
	
	
	@PostConstruct
	public void initialize() throws Exception {
		try {
			path = realPath + WEB_INF_CLASSES +  LOCK_APP;
			if ("dev".equals(enviroment)){
				path = devPath + SRC_MAIN_RESOURCES +  LOCK_APP;
			}
			setLockString(getText(path));
			String decryptedLockString = getDecryptedLockString();
			String[] splitted = decryptedLockString.split(SEPARATOR);
			lastDate = dateFormatter.parse(splitted[0]);
			propietary = splitted[1];
			expirationDate = dateFormatter.parse(splitted[2]);
			identifier = new Long(splitted[3]);
			identifier++;
			
			showInfo();
		} catch (Exception e){
			LOG.error("Ocurrio un error inicializando LockApp", e);
		}
	}

	private void showInfo() {
		LOG.debug("Informacion de bloqueo de la aplicacion");
		LOG.debug("_______________________________________");
		LOG.debug("Propietary     :" + getPropietary());
		LOG.debug("Expiration Date:" + getExpirationDate());
		LOG.debug("Last Date      :" + getLastDate());
	}
	
	private String getText(String path) {
		String line = org.apache.commons.lang3.StringUtils.EMPTY;
		FileReader fileReader = null;
		BufferedReader buffer = null;
		try {
			fileReader = new FileReader(new File(path));
			buffer = new BufferedReader(fileReader);
			while((line = buffer.readLine())!=null) {
				return line;
			}
			buffer.close();
		} catch (Exception e) {
			LOG.error("Ocurrio un error leyendo archivo lock.app", e);
		} finally{
	         try{
	        	if( null != buffer ){   
	        		buffer.close();     
		        }   
	            if( null != fileReader ){   
	            	fileReader.close();     
	            }                  
	         }catch (Exception e2){
	        	 LOG.error("Ocurrio un error leyendo archivo lock.app", e2);
	         }
	     }
		return line;
	}
	
	public void write() {
		LockAppSaver saver = context.getBean(LockAppSaver.class);
		saver.start();
	}
	
	public Long getIdentifier() {
		return identifier;
	}
	
	public synchronized void setIdentifier(Long identifier) {
		if (this.identifier<=0){
			this.identifier = identifier;
		}
	}
	
	public String getDecryptedLockString(){
		return base64.decrypt(lockString);
	}
	
	public void setLockString(String lockString) {
		this.lockString = lockString;
	}

	public Date getLastDate() {
		return lastDate;
	}

	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getPropietary() {
		return propietary;
	}

	public void setPropietary(String propietary) {
		this.propietary = propietary;
	}
	
	@Override
	public String toString() {
		String text = dateFormatter.format(lastDate) + SEPARATOR + propietary + SEPARATOR + dateFormatter.format(expirationDate) + SEPARATOR + identifier ;
		return base64.encrypt(text);
	}	
	
	public void setDateFormatter(SimpleDateFormat dateFormatter) {
		this.dateFormatter = dateFormatter;
	}

	public void setBase64(Base64 base64) {
		this.base64 = base64;
	}	
}
