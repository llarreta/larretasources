/**
 * Creado 21/08/2010 17:17:08
 */
package co.com.directv.sdii.ejb.business.broker.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;
import org.example.allocatorrequest.Request;

import com.oracle.xmlns.mer_sdii_jws.bpmallocator.allocatorprocess.AllocatorProcess;

import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.enumerations.WsdlLocationsEnum;
import co.com.directv.sdii.common.locator.ServiceLocator;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.allocator.AllocatorBeanLocal;
import co.com.directv.sdii.ejb.business.broker.AllocatorBPMServiceBrokerLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.exceptions.ServiceLocatorException;
import co.com.directv.sdii.model.pojo.ServiceHour;
import co.com.directv.sdii.model.pojo.WorkOrderAgenda;
import co.com.directv.sdii.model.vo.DealerVO;
import co.com.directv.sdii.model.vo.WorkOrderVO;
import co.com.directv.sdii.persistence.dao.config.WorkOrderAgendaDAOLocal;

/**
 * Implementa las operaciones para invocar el servicio web que expone
 * los procesos de negocio
 * 
 * Fecha de Creación: 21/08/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.ejb.business.broker.AllocatorBPMServiceBrokerLocal
 */
@Stateless(name="AllocatorBPMServiceBrokerLocal",mappedName="ejb/AllocatorBPMServiceBrokerLocal")
public class AllocatorBPMServiceBrokerImpl extends BusinessBase implements AllocatorBPMServiceBrokerLocal {

	private final static Logger log = UtilsBusiness.getLog4J(AllocatorBPMServiceBrokerImpl.class);

	@EJB(name="WorkOrderAgendaDAOLocal", beanInterface=WorkOrderAgendaDAOLocal.class)
	private WorkOrderAgendaDAOLocal daoWorkOrderAgenda;

	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.allocator.AllocatorBPMServiceBrokerLocal#startWorkorderAllocation(java.util.List, co.com.directv.sdii.model.vo.WorkOrderVO)
	 */
	public DealerVO startWorkorderAllocation(List<DealerVO> dealers, WorkOrderVO workorder)throws BusinessException{
		log.info("== Inicia startWorkorderAllocation/AllocatorBPMServiceBrokerImpl ==");
		try {
			AllocatorProcess service = getBPMServicePort();
			Request request = buildInicioRequest(dealers, workorder);

			co.com.directv.sdii.ws.business.allocator.DealerVO bpmAssignedDealer = service.process(request);
			DealerVO assignedDealer = new DealerVO();
			assignedDealer.setId(bpmAssignedDealer.getId());
			
			return assignedDealer;
		} catch(Throwable ex){
			log.debug("== Error al tratar de ejecutar la operación startWorkorderAllocation/AllocatorBPMServiceBrokerImpl ==");
			throw this.manageException(ex);
		} finally {
			log.info("== Termina startWorkorderAllocation/AllocatorBPMServiceBrokerImpl ==");
		}
		
	}

	/**
	 * Metodo: Crea el objeto con la información del request para la invocación del
	 * servicio web para el proceso de negocio
	 * @param dealers Lista con la información de los dealers preseleccionados
	 * @param workorder información de la work order
	 * @return Objeto que será usado para la invocación del servicio web de
	 * @throws DAOServiceException en caso de error al consultar la base de datos
	 * @throws DAOSQLException en caso de error al consultar la base de datos
	 * @throws BusinessException en caso de error de reglas de negocio
	 * @throws IllegalAccessException en caso que no funcione la copia de propiedades entre beans para
	 * la invocación del servicio
	 * @throws InvocationTargetException en caso que no funcione la copia de propiedades entre beans para
	 * la invocación del servicio
	 * @author jjimenezh
	 * 2010-10-07 jjimenezh Se realiza modificación: la fecha de agendamiento y la jornada no son obligatorias, por tanto si no 
	 * están registradas en el sistema se enviarán al proceso de nogocio nulas
	 * @throws PropertiesException 
	 */
	private Request buildInicioRequest(List<DealerVO> dealers, WorkOrderVO workorder) throws DAOServiceException, DAOSQLException, BusinessException, IllegalAccessException, InvocationTargetException, PropertiesException {
		Request request = new Request();

		WorkOrderAgenda woAgenda = daoWorkOrderAgenda.getLastWorkOrderAgendaByWoID(workorder.getId());

		if(woAgenda == null){
			log.warn("Se intentó asignar una work order que no tiene agenda, workOrderId: " + workorder.getId() + " sin emabrgo se sigue con el proceso de asignación");
			//throw new BusinessException(ErrorBusinessMessages.WORKORDER_AGENDA_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages.WORKORDER_AGENDA_DOES_NOT_EXIST.getMessage());
			request.setAgendaDate(UtilsBusiness.dateToGregorianCalendar(AllocatorBeanLocal.NULL_AGENDATION_DATE));
			request.setServiceHour(buildBpmServiceHour(null));
		}else{

			request.setAgendaDate(UtilsBusiness.dateToGregorianCalendar(woAgenda.getAgendationDate()));
			ServiceHour serviceHour = woAgenda.getServiceHour();
			if(serviceHour == null){
				log.error("No se ha asignado jornada para el registro de agenda con id: " + woAgenda.getId());
				throw new BusinessException(ErrorBusinessMessages.SERVICE_HOUR_BY_COUNTRY_NOT_FOUND.getCode(), ErrorBusinessMessages.SERVICE_HOUR_BY_COUNTRY_NOT_FOUND.getMessage());
			}
			co.com.directv.sdii.ws.business.allocator.ServiceHourVO bpmServiceHour;
			bpmServiceHour = buildBpmServiceHour(serviceHour);
			request.setServiceHour(bpmServiceHour);
		}
		//Copiando la lista de elementos de un tipo a otro:
		co.com.directv.sdii.ws.business.allocator.DealerVO bpmDealer = null;
		List<co.com.directv.sdii.ws.business.allocator.DealerVO> bpmDealers = new ArrayList<co.com.directv.sdii.ws.business.allocator.DealerVO>();

		for (DealerVO dealerVO : dealers) {
			bpmDealer = new co.com.directv.sdii.ws.business.allocator.DealerVO();
			bpmDealer = buildBpmDealer(dealerVO);
			bpmDealers.add(bpmDealer);
		}

		co.com.directv.sdii.ws.business.allocator.WorkOrderVO bpmWorkOrder; 
		bpmWorkOrder = buildBpmWorkOrder(workorder);
		
		request.setServiceAddress(WsdlLocationsEnum.ALLOCATOR_SDII_WSDL_LOCATION.getWsdlLocation());
		request.setWo(bpmWorkOrder);
		
		return request;
	}

	private co.com.directv.sdii.ws.business.allocator.WorkOrderVO buildBpmWorkOrder(
			WorkOrderVO workorder) {
		co.com.directv.sdii.ws.business.allocator.WorkOrderVO bpmWorkOrder = new co.com.directv.sdii.ws.business.allocator.WorkOrderVO();
		bpmWorkOrder.setId(workorder.getId());
		return bpmWorkOrder;
	}

	private co.com.directv.sdii.ws.business.allocator.ServiceHourVO buildBpmServiceHour(ServiceHour serviceHour) {
		co.com.directv.sdii.ws.business.allocator.ServiceHourVO bmpServiceHour = new co.com.directv.sdii.ws.business.allocator.ServiceHourVO();
		if(serviceHour == null){
			bmpServiceHour.setId(AllocatorBeanLocal.NULL_SERVICE_HOUR_ID);
		}else{
			bmpServiceHour.setId(serviceHour.getId());
		}
		return bmpServiceHour;
	}

	private co.com.directv.sdii.ws.business.allocator.DealerVO buildBpmDealer(
			DealerVO dealerVO) {
		co.com.directv.sdii.ws.business.allocator.DealerVO bpmDealer = new co.com.directv.sdii.ws.business.allocator.DealerVO();
		bpmDealer.setId(dealerVO.getId());
		return bpmDealer;
	}

	/**
	 * Metodo: Obtiene el puerto para la invocación del serivicio web de proceso de negocio
	 * @return Puerto para la invocación del servicio web
	 * configuración del servicio web
	 * @author jjimenez 
	 * @throws ServiceLocatorException 
	 */
	public AllocatorProcess getBPMServicePort() throws ServiceLocatorException {
		AllocatorProcess service = ServiceLocator.getInstance().getBPMAllocatorService();
		return service;
	}

}
