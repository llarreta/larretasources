package co.com.directv.sdii.facade.schedule.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import co.com.directv.sdii.ejb.business.core.WoInfoEsbServiceBusinessLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.schedule.WoInfoEsbServiceFacadeLocal;
import co.com.directv.sdii.facade.schedule.WoInfoEsbServiceFacadeRemote;


/**
 * Session Bean implementation class WoInfoEsbServiceFacade
 */
@Stateless(name="WoInfoEsbServiceFacade")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class WoInfoEsbServiceFacade implements WoInfoEsbServiceFacadeRemote, WoInfoEsbServiceFacadeLocal {

	@EJB(name="WoInfoEsbServiceBusinessBean", beanInterface=WoInfoEsbServiceBusinessLocal.class)
	private WoInfoEsbServiceBusinessLocal woInfoEsbServiceBusinessLocal;
	
    public WoInfoEsbServiceFacade() {
    }

	/**
	 * Metodo que expone la funcionalidad de clasificar que work orders deben ser procesadas, enviarlas a las respectivas colas de mensajeria de core y
	 * asignador 
	 * @param countryId pais del cual se desea eviar a ejecucion los procesos de core y asignador
	 * @throws BusinessException
	 * @author Aharker
	 */
	@Override
	public void processCoreAndAllocator(Long countryId) throws BusinessException{
		woInfoEsbServiceBusinessLocal.prepareAndSendMessageForAllocatorAndCore(countryId);

	}

	/**
	 * Metodo que genera unh proceso encolado en base de datos para ser procesado por core
	 * @param wo work order que se descargo de IBS, esta se guardara en base de datos en XML
	 * @param countryId pais al que pertenece el proceso de Core que se desea ejecutar
	 * @throws BusinessException
	 * @author Aharker
	 */
	@Override
	public void createCoreProccess(co.com.directv.sdii.dto.esb.WorkOrder woibs, Long countryId) throws BusinessException {
		//woInfoEsbServiceBusinessLocal.createWoInfoEsbServiceForCore(woibs,countryId);
	}

	/**
	 * Metodo encargado de enviar los reportes del procesamiento de core y asignador por correo electronico
	 * @param countryId id del pais al que pertenece el proceso de Core que se desea ejecutar
	 * @throws BusinessException
	 * @author Aharker
	 */
	@Override
	public void sendEmailReportCoreAllocator(Long countryId) throws BusinessException {
		woInfoEsbServiceBusinessLocal.sendEmailReportCoreAllocator(countryId);
	}
	
}
