package ar.com.larreta.commons.initializer;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import ar.com.larreta.commons.AppManager;
import ar.com.larreta.commons.AppObject;
import ar.com.larreta.commons.AppObjectImpl;
import ar.com.larreta.commons.utils.Base64;

public class LockApp {

	private static final String SEPARATOR = ";";
	
	private static final Logger LOGGER = Logger.getLogger(LockApp.class);

	private AppObject appObject = new AppObjectImpl(getClass());
	
	/**
	 * ruta del archivo de lockeo
	 */
	private String path;
	
	private String lockString;
	
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


	private SimpleDateFormat format;
	private Base64 base64;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	public SimpleDateFormat getFormat() {
		if (format==null){
			format = AppManager.getInstance().getAppConfig().formatPatterns().getGeneralSimpleDateFormat();
		}
		return format;
	}

	public Base64 getBase64() {
		if (base64==null){
			base64 = AppManager.getInstance().getBase64();
		}
		return base64;
	}

	public LockApp(SimpleDateFormat format, Base64 base64){
		this.format = format;
		this.base64 = base64;
	}
	
	public LockApp(String lockString){
		try {
			setLockString(lockString);
			String decryptedLockString = getDecryptedLockString();
			String[] splitted = decryptedLockString.split(SEPARATOR);
			lastDate = getFormat().parse(splitted[0]);
			propietary = splitted[1];
			expirationDate = getFormat().parse(splitted[2]);
			identifier = new Long(splitted[3]);
		} catch (Exception e){
			LOGGER.error("Ocurrio un error inicializando LockApp", e);
		}
	}
	
	
	public synchronized void setIdentifier(Long identifier) {
		if (this.identifier<=0){
			this.identifier = identifier;
		}
	}
	
	public Long nextIdentifier(){
		synchronized (LockApp.class) {
			// Se valida la fecha de expiracion del sistema
			LockAppInitializer.validateExpirationDate(appObject);
			identifier=identifier+1;
		}
		return identifier;
	}
	
	public String getDecryptedLockString(){
		return AppManager.getInstance().getAppConfig().getBase64().decrypt(lockString);
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
		String text = format.format(lastDate) + SEPARATOR + propietary + SEPARATOR + format.format(expirationDate) + SEPARATOR + identifier ;
		return getBase64().encrypt(text);
	}	
	
	
}
