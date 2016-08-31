package co.com.directv.sdii.facade.file;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;

/**
 * 
 * Se define esta interface para ser usada desde Quartz con el fin
 * de programar un proceso que importe las Workorders de IBS 
 * 
 * Fecha de Creación: 10/06/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.allocator.AllocatorFacadeLocal
 */
@Local
public interface FileProcessorFacadeBeanLocal  {

	

	/**
	 * Método: Realiza el procesamiento de los archivos pendientes
	 * @param idCountry - Long identificador del país
	 * @throws BusinessException en caso de error procesando los archivos
	 * @author gfandino
	 */
	public void processFiles(Long idCountry) throws BusinessException  ;
}
