package ar.com.larreta.commons.initializer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.GenericServlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.lang.StringUtils;

import ar.com.larreta.commons.AppManager;
import ar.com.larreta.commons.AppObject;
import ar.com.larreta.commons.AppObjectImpl;

public class LockAppInitializer extends GenericServlet {

	private static final String LOCK_APP = "lock.app";
	private AppObject appObject = new AppObjectImpl(getClass());
	

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
		try {
			appObject.getLog().debug("Inicializando lockeo de aplicacion");
	
			String path = getServletContext().getRealPath(LOCK_APP);
			
			String lockAppString = getText(path);
			LockApp lockApp = new LockApp(lockAppString);
			lockApp.setPath(path);
			
			AppManager.getInstance().getAppConfig().setLockApp(lockApp);
			
			Date actual = new Date(System.currentTimeMillis());
			if (!StringUtils.EMPTY.equals(lockAppString)){
				Date previous = lockApp.getLastDate();
				if (previous.after(actual)){
					appObject.getLog().debug("No puede utilizarse la aplicacion debido a temas de lockeo. Contacte al administrador.");
					throw new RuntimeException();
				}
				
				validateExpirationDate(appObject, lockApp, actual);
				
			}
			
	        writeText(appObject, path, lockAppString);
		} catch (RuntimeException e){
			throw e;
		} catch (Exception e){
			appObject.getLog().error("Ocurrio un error inesperado", e);
		}  
	
	}
	
	
	public static void writeLockApp(AppObject appObject){
		LockApp lockApp = getLockApp();
		if(lockApp!=null){
			writeText(appObject, lockApp.getPath(), lockApp.toString());
		}
	}


	private static LockApp getLockApp() {
		LockApp lockApp = null;
		if ((AppManager.getInstance()!=null) && (AppManager.getInstance().getAppConfig()!=null)){
			lockApp = AppManager.getInstance().getAppConfig().getLockApp();
		}
		return lockApp;
	}

	public static void validateExpirationDate(AppObject appObject) {
		LockApp lockApp = getLockApp();
		if(lockApp!=null){
			validateExpirationDate(appObject, lockApp, new Date());
		}
	}

	public static void validateExpirationDate(AppObject appObject, LockApp lockApp, Date actual) {
		Date expiration = lockApp.getExpirationDate();
		if (actual.after(expiration)){
			appObject.getLog().debug("No puede utilizarse la aplicacion debido a que expiro su licencia. Contacte al administrador.");
			throw new RuntimeException();
		}
	}


	public static void writeText(AppObject appObject, String path, String text) {
		FileWriter fileWriter = null;
        PrintWriter printWriter = null;
        try
        {
            fileWriter = new FileWriter(path);
            printWriter = new PrintWriter(new FileWriter(path));
            printWriter.write(text);
        } catch (Exception e) {
        	appObject.getLog().error("Ocurrio un error escribiendo archivo lock.app", e);
        } finally {
           try {
        	   if (null != printWriter){
        		   printWriter.close();
        	   }
        	   if (null != fileWriter){
        		   fileWriter.close();
        	   }
           } catch (Exception e2) {
        	   appObject.getLog().error("Ocurrio un error escribiendo archivo lock.app", e2);
           }
        }
	}


	private String getText(String path) {
		String line = StringUtils.EMPTY;
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
			appObject.getLog().error("Ocurrio un error leyendo archivo lock.app", e);
		} finally{
	         try{
	        	if( null != buffer ){   
	        		buffer.close();     
		        }   
	            if( null != fileReader ){   
	            	fileReader.close();     
	            }                  
	         }catch (Exception e2){
	        	 appObject.getLog().error("Ocurrio un error leyendo archivo lock.app", e2);
	         }
	     }
		return line;
	}
	
	
	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
	}

}
