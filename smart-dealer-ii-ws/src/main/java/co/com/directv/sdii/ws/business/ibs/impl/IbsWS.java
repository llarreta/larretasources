package co.com.directv.sdii.ws.business.ibs.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.core.CoreWOFacadeLocal;
import co.com.directv.sdii.ws.business.ibs.IIibsWS;

/**
 * 
 * Fecha de Creación: 02/09/2010
 * @author jvelezmu <a href="mailto:jvelezmu@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@MTOM(threshold=3072)
@WebService(serviceName="IbsService",
		endpointInterface="co.com.directv.sdii.ws.business.ibs.IIibsWS",
		targetNamespace="http://ibs.business.ws.sdii.directv.com.co/",
		portName="IbsWSPort")	
@Stateless()
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class IbsWS implements IIibsWS{
	
	@EJB
	private CoreWOFacadeLocal businessCore;
	
	
//	/* (non-Javadoc)
//	 * @see co.com.directv.sdii.ws.business.ibs.IIibsWS#changeRealizationDateIBS(java.lang.Long,java.lang.Long,java.lang.String,java.lang.String)
//	 */
//	@Override
//	@TransactionAttribute(TransactionAttributeType.REQUIRED)
//	public Long changeRealizationDateIBS(Long customerID, Long workOrderNumer, String newRealizationDate, String workOrderCountryId) throws BusinessException{
//		return businessCore.changeRealizationDateIBS(customerID, workOrderNumer, newRealizationDate, workOrderCountryId);
//	}
	
	/**
	 * Permite adicionar un servicio a la WO. Retorna el mensaje de resultado
	 * @param workOrder - WorkOrderVO
	 * @param service - ServiceVO
	 * @param decoSerialNumber - String
	 * @return String
	 * @throws BusinessException En caso de error al tratar de agregar un servicio a una wordorder
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * @author gfandino
	 */
	public void addServiceToWorkOrderFromIbs(
			Long workOrderId,
			Long serviceId,
			String decoSerialNumber,
			String countryCode,
			Long userId) throws BusinessException{
		businessCore.addServiceToWorkOrder(workOrderId, serviceId, decoSerialNumber, countryCode, userId);
	}


}
