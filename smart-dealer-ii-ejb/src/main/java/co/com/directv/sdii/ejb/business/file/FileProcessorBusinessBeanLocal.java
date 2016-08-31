package co.com.directv.sdii.ejb.business.file;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;

/**
 * Interface que expone los servicios para procesar los archivos cargados
 * @author gfandino
 *
 */
@Local
public interface FileProcessorBusinessBeanLocal {
	
	
	
	/**
	 * Método: Realiza el procesamiento de los archivos pendientes
	 * @param idCountry - Long identificador del país
	 * @throws BusinessException en caso de error procesando los archivos
	 * @author gfandino
	 */
	public void processFiles(Long idCountry) throws BusinessException  ;

}
