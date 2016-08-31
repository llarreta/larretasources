package co.com.directv.sdii.ws.business.stock.test;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.IBSMoveResourceBetweenCustAndDealerDTO;
import co.com.directv.sdii.model.vo.WorkOrderServiceElementVO;

/**
 * 
 * 
 * Fecha de Creación: 25/06/2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@WebService(name="StockTestWS",targetNamespace="http://core.business.ws.sdii.directv.com.co/")
public interface IStockTestWS {
	
	/**
	 * Metodo: Notifica a IBS los elementos utilizados en la prestación de un servicio
	 * Tiene como precondición que se haya invocado la operación <code>reportRecoveryOfSerializedElements</code>
	 * @param dealerId identificador del dealer que atendió la WO
	 * @param customerId identificador del cliente asociado a la WO
	 * @param countryId identificador del país de la Wo
	 * @param installationSerializedElements lista con los elementos utilizados en la atención de la WO
	 * @throws BusinessException En caso de error al realizar la notificación
	 * @author jjimenezh
	 */
	@WebMethod(operationName="notify2IbsRecoveryOfSerializedElements", action="notify2IbsRecoveryOfSerializedElements", exclude = false)
	public List<IBSMoveResourceBetweenCustAndDealerDTO> notify2IbsRecoveryOfSerializedElements(@WebParam(name="dealerId")Long dealerId, @WebParam(name="customerId")Long customerId, @WebParam(name="countryId")Long countryId, @WebParam(name="installationSerializedElements")List<WorkOrderServiceElementVO> installationSerializedElements)throws BusinessException;

	/**
	 * Metodo: Notifica a IBS los elementos recuperados en la prestación de un servicio, tiene como precondición
	 * que se haya invocado la operación <code>reportSerializedElementsUsedInService</code>
	 * @param dealerId identificador de la compañía instaladora
	 * @param customerId identificador del cliente
	 * @param countryId identificador del país
	 * @param installationSerializedElements elementos utilizados en la prestación de los servicios
	 * @throws BusinessException En caso de error al notificar los elementos usados en la prestación de un servicio
	 * @author jjimenezh
	 */
	@WebMethod(operationName="notify2IbsSerializedElementsUsedInService", action="notify2IbsSerializedElementsUsedInService", exclude = false)
	public List<IBSMoveResourceBetweenCustAndDealerDTO> notify2IbsSerializedElementsUsedInService(@WebParam(name="dealerId")Long dealerId, @WebParam(name="customerId")Long customerId, @WebParam(name="countryId")Long countryId, @WebParam(name="installationSerializedElements")List<WorkOrderServiceElementVO> installationSerializedElements)throws BusinessException;
	
}
