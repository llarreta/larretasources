package co.com.directv.sdii.facade.schedule;
import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;

/**
 * 
 * Fachada del manejo de las tareas de procesamiento en paralelo de core y asignador
 * 
 * @author Aharker
 *
 */
@Local
public interface WoInfoEsbServiceFacadeLocal {

	/**
	 * Metodo que expone la funcionalidad de clasificar que work orders deben ser procesadas, enviarlas a las respectivas colas de mensajeria de core y
	 * asignador 
	 * @param countryId pais del cual se desea eviar a ejecucion los procesos de core y asignador
	 * @throws BusinessException
	 * @author Aharker
	 */
	public void processCoreAndAllocator(Long countryId) throws BusinessException;
	
	/**
	 * Metodo que genera unh proceso encolado en base de datos para ser procesado por core
	 * @param wo work order que se descargo de IBS, esta se guardara en base de datos en XML
	 * @param countryId pais al que pertenece el proceso de Core que se desea ejecutar
	 * @throws BusinessException
	 * @author Aharker
	 */
	public void createCoreProccess(co.com.directv.sdii.dto.esb.WorkOrder wo, Long countryId) throws BusinessException;
	
	/**
	 * Metodo encargado de enviar los reportes del procesamiento de core y asignador por correo electronico
	 * @param countryId id del pais al que pertenece el proceso de Core que se desea ejecutar
	 * @throws BusinessException
	 * @author Aharker
	 */
	public void sendEmailReportCoreAllocator(Long countryId)throws BusinessException;
	
}
