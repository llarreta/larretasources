/**
 * 
 */
package co.com.directv.sdii.jobs;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.util.UtilsBusiness;

/**
 * Esta clase ejecuta el Job para el procesamieto de archivos
 * Implementa StatefulJob de Quartz Scheduler.
 * @author Gustavo Fandiño
 * 
 *
 */
public class FileProcessorJobEC extends FileProcessorJob {

	
	private final static Logger logger = UtilsBusiness.getLog4J(FileProcessorJobEC.class);
	
	public FileProcessorJobEC(){
//		try {
//			setCodeCountry(CodesBusinessEntityEnum.CODE_COUNTRY_EC.getCodeEntity());
//		} catch (PropertiesException e) {
//			logger.error("Se generó un error consultando el códido del país");
//		}
	}
	
	

}
