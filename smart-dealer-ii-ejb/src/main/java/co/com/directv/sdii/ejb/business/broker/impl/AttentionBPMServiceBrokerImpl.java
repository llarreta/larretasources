/**
 * Creado 10/09/2010 11:43:38
 */
package co.com.directv.sdii.ejb.business.broker.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;
import org.example.attentionsschema.Request;

import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.enumerations.WsdlLocationsEnum;
import co.com.directv.sdii.common.locator.ServiceLocator;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.broker.AttentionBPMServiceBrokerLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.exceptions.ServiceLocatorException;
import co.com.directv.sdii.model.vo.WorkOrderServiceVO;
import co.com.directv.sdii.model.vo.WorkOrderVO;
import co.com.directv.sdii.ws.business.attentions.core.Service;
import co.com.directv.sdii.ws.business.attentions.core.WoAttentionsRequestDTO;
import co.com.directv.sdii.ws.business.attentions.core.WoServiceStatus;
import co.com.directv.sdii.ws.business.attentions.core.WorkOrderServiceElementVO;

import com.oracle.xmlns.attentions_jws.bpmattentions.attentionprocess.AttentionProcess;
import com.oracle.xmlns.attentions_jws.bpmattentions.attentionprocess.ProcessResponse;

/**
 * <Descripcion> 
 * 
 * Fecha de Creación: 10/09/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
@Stateless(name="AttentionBPMServiceBrokerLocal",mappedName="ejb/AttentionBPMServiceBrokerLocal")
public class AttentionBPMServiceBrokerImpl extends BusinessBase implements
		AttentionBPMServiceBrokerLocal {

	private final static Logger log = UtilsBusiness.getLog4J(AttentionBPMServiceBrokerImpl.class);
	
	public void attendWorkOrder(WorkOrderVO workorderVo, List<WorkOrderServiceVO> workorderServices, String comments, Boolean workOrderFinished) throws BusinessException {
		log.debug("== Inicia attendWorkOrder/AttentionBPMServiceBrokerImpl ==");
		try {
			AttentionProcess service = getBPMServicePort();
			
			Request request = buildRequest(workorderVo, workorderServices, comments, workOrderFinished);
			
			ProcessResponse response = service.process(request);
			
			String result = response.getResult();
			
			log.debug("El resultado de la atención de la work order con id: " + workorderVo.getId() + " es: " + result);
			
		} catch (Throwable ex) {
			log.debug("== Error al tratar de ejecutar la operacion AttentionBPMServiceBrokerImpl/attendWorkOrder");
			throw new BusinessException(ErrorBusinessMessages.WORKORDER_COULDNT_BE_ATTENDED.getCode(), ErrorBusinessMessages.WORKORDER_COULDNT_BE_ATTENDED.getMessage());
		} finally {
			log.debug("== Termina attendWorkOrder/AttentionBPMServiceBrokerImpl ==");
		}
	}
	
	
	private Request buildRequest(WorkOrderVO workorderVo, List<WorkOrderServiceVO> workorderServices, String comments, Boolean workOrderFinished) throws PropertiesException {
		Request request = new Request();
		WoAttentionsRequestDTO requestDto = new WoAttentionsRequestDTO();
		request.setRequestDTO(requestDto);
		List<co.com.directv.sdii.ws.business.attentions.core.WorkOrderServiceVO> attServices = convertWOServiceList(workorderServices);
		requestDto.getWorkorderServices().addAll(attServices);
		
		co.com.directv.sdii.ws.business.attentions.core.WorkOrderVO attWorkOrder = new co.com.directv.sdii.ws.business.attentions.core.WorkOrderVO();
		attWorkOrder.setId(workorderVo.getId());
		requestDto.setWorkorderVo(attWorkOrder);
		
		requestDto.setComments(comments);
		requestDto.setWorkOrderFinished(workOrderFinished);
		request.setServiceAddress(WsdlLocationsEnum.ATTENTION_SDII_WSDL_LOCATION.getWsdlLocation());
		
		return request;
	}


	private List<co.com.directv.sdii.ws.business.attentions.core.WorkOrderServiceVO> convertWOServiceList(
			List<WorkOrderServiceVO> workorderServices) {
		
		List<co.com.directv.sdii.ws.business.attentions.core.WorkOrderServiceVO> result = new ArrayList<co.com.directv.sdii.ws.business.attentions.core.WorkOrderServiceVO>();
		
		for (WorkOrderServiceVO workOrderServiceVO : workorderServices) {
			co.com.directv.sdii.ws.business.attentions.core.WorkOrderServiceVO item = new co.com.directv.sdii.ws.business.attentions.core.WorkOrderServiceVO();
			
			List<WorkOrderServiceElementVO> woServElements = convertWoServiceElementsList(workOrderServiceVO.getWoServiceElements());
			item.getWoServiceElements().addAll(woServElements);
			
			item.setId(workOrderServiceVO.getId());
			item.setSerialNumber(workOrderServiceVO.getSerialNumber());
			
			Service service = new Service();
			service.setId(workOrderServiceVO.getService() == null ? null : workOrderServiceVO.getService().getId());
			item.setService(service);
			//item.setServiceAttended(workOrderServiceVO.getServiceAttended()==null ? false : workOrderServiceVO.getServiceAttended());
			item.setServiceInclusionDate(UtilsBusiness.dateToGregorianCalendar(workOrderServiceVO.getServiceInclusionDate()));
			item.setWoId(workOrderServiceVO.getWoId());
			WoServiceStatus serviceStatus = new WoServiceStatus();
			serviceStatus.setId(workOrderServiceVO.getWoServiceStatus() == null ? null : workOrderServiceVO.getWoServiceStatus().getId());
			item.setWoServiceStatus(serviceStatus);
			
			result.add(item);
		}
		return result;
	}


	private List<WorkOrderServiceElementVO> convertWoServiceElementsList(
			List<co.com.directv.sdii.model.vo.WorkOrderServiceElementVO> woServiceElements) {
		
		List<WorkOrderServiceElementVO> result = new ArrayList<WorkOrderServiceElementVO>();
		for (co.com.directv.sdii.model.vo.WorkOrderServiceElementVO workOrderServiceElementVO : woServiceElements) {
			WorkOrderServiceElementVO item = new WorkOrderServiceElementVO();
			
			item.setElementTypeId(workOrderServiceElementVO.getElementTypeId());
			item.setNewElementSerial(workOrderServiceElementVO.getElementSerial());
			item.setObjectRecovery(workOrderServiceElementVO.isObjectRecovery());
			item.setDefectiveObject(workOrderServiceElementVO.isDefectiveObject());
			item.setOldElementSerial(workOrderServiceElementVO.getElementSerial());
			item.setQuantity(workOrderServiceElementVO.getQuantity());
			
			result.add(item);
		}
		return result;
	}


	/**
	 * Metodo: Obtiene el puerto para la invocación del serivicio web de proceso de negocio
	 * @return Puerto para la invocación del servicio web
	 * @author jjimenez 
	 * @throws ServiceLocatorException 
	 */
	public AttentionProcess getBPMServicePort() throws ServiceLocatorException{
		AttentionProcess service = ServiceLocator.getInstance().getBPMAttentionService();
		return service;
		
	}
}
