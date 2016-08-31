package co.com.directv.sdii.ejb.business.broker.toa;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.exceptions.HelperException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.dto.WOAttentionsRequestDTO;
import co.com.directv.sdii.model.vo.EmployeeVO;
import co.com.directv.sdii.model.vo.IBSWorkOrderInfoVO;
import co.com.directv.sdii.persistence.dao.config.SystemParameterDAOLocal;
import co.com.directv.sdii.ws.model.dto.AssignRequestDTO;
import co.com.directv.sdii.ws.model.dto.CustWorkOrdersResponseDTO;
import co.com.directv.sdii.ws.model.dto.EditCustomerWorkOrderDTO;

import com.directvla.schema.businessdomain.manageworkforce.v1_0.AddServiceToWorkOrderRequest;
import com.directvla.schema.businessdomain.manageworkforce.v1_0.AddTelecomTechnicianRequest;
import com.directvla.schema.businessdomain.manageworkforce.v1_0.CancelWorkOrderRequest;
import com.directvla.schema.businessdomain.manageworkforce.v1_0.EditCustomerWorkOrderRequest;
import com.directvla.schema.businessdomain.manageworkforce.v1_0.EditTelecomTechnicianRequest;
import com.directvla.schema.businessdomain.manageworkforce.v1_0.RemoveWorkOrderServiceRequest;

@Local
public interface ManageWorkForceServiceLocalTOA {

	/**
	 * 
	 * Metodo: <Descripcion>
	 * @param customerKey
	 * @param countryCode
	 * @param woCollection
	 * @return CustWorkOrdersResponseDTO
	 * @author jjimenezh
	 */
	public CustWorkOrdersResponseDTO buildCustomerWorkOrdersResponseDtoFromIbsResult(String customerKey, String countryCode, com.directvla.schema.businessdomain.common.manageworkforce.v1_0.WorkOrderCollection woCollection);

	/**
	 * Metodo: <Descripcion>
	 * @param assignRequestDTO
	 * @param workOrder
	 * @return assignRequestDTO
	 */
	public AssignRequestDTO buildCustomerWorkOrdersResponseDtoFromIdResult(
			          AssignRequestDTO assignRequestDTO, com.directvla.schema.businessdomain.common.manageworkforce.v1_0.WorkOrder workOrder);
	
	/**
	 * 
	 * Metodo: <Descripcion>
	 * @param ibsWorkOrders
	 * @return List<IBSWorkOrderInfoVO>
	 * @author jjimenezh
	 */
	public List<IBSWorkOrderInfoVO> buildWorkOrdersInfoFromResult(	List<com.directvla.schema.businessdomain.common.manageworkforce.v1_0.WorkOrder> ibsWorkOrders);

	/**
	 * 
	 * Metodo: <Descripcion>
	 * @param ibsWo
	 * @return IBSWorkOrderInfoVO
	 * @author jjimenezh
	 */
	public IBSWorkOrderInfoVO buildIbsWoInfoFromIBSWo(com.directvla.schema.businessdomain.common.manageworkforce.v1_0.WorkOrder ibsWo);

	/**
	 * 
	 * Metodo: <Descripcion>
	 * @param customerKey
	 * @param ibsWorkOrderStatusCode
	 * @param countryCode
	 * @return GetCustomerWorkOrderByStatusRequest
	 * @author jjimenezh
	 */
	public com.directvla.schema.businessdomain.manageworkforce.v1_0.GetCustomerWorkOrderByStatusRequest buildGetCustomerWorkOrderByStatusRequest(String customerKey, String ibsWorkOrderStatusCode, String countryCode);
	
	/**
	 * 
	 * Metodo: <Descripcion>
	 * @param  assignRequestDTO
	 * @return GetCustomerWorkOrderByIdRequest
	 */
	public com.directvla.schema.businessdomain.manageworkforce.v1_0.GetCustomerWorkOrderByIdRequest buildGetCustomerWorkOrderByIdRequest(
			          AssignRequestDTO assignRequestDTO);
	
	/**
	 * 
	 * Metodo: <Descripcion>
	 * @param  assignRequestDTO
	 * @return GetCustomerWorkOrderByIdRequest
	 */
	public com.directvla.schema.businessdomain.manageworkforce.v1_0.GetCustomerWorkOrderByIdRequest buildGetCustomerWorkOrderByIdRequest(String countryCode,String woCode) ;
	
	/**
	 * 
	 * Metodo: <Descripcion>
	 * @param customerKey
	 * @param countryCode
	 * @return GetCustomerWorkOrderByStatusRequest
	 * @author jjimenezh
	 */
	public com.directvla.schema.businessdomain.manageworkforce.v1_0.GetCustomerWorkOrderByStatusRequest buildGetCustomerWorkOrderByStatusRequest(String customerKey, String countryCode);
	
	/**
	 * 
	 * Metodo: Construye el objeto request que es
	 * enviado al ivocar la operacion de CompleteWorkOrder
	 * @param attentionRequestDTO
	 * @return CompleteWorkOrderRequest
	 * @author jalopez
	 * @throws BusinessException 
	 */
	public com.directvla.schema.businessdomain.manageworkforce.v1_0.CompleteWorkOrderRequest buildCompleteWorkOrderRequest(WOAttentionsRequestDTO attentionRequestDTO) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Valida la informacion requerida para
	 * la invocacion del servicio de CompleteWorkOrder
	 * @param attentionRequestDTO
	 * @throws BusinessException
	 * @author jalopez
	 */
	public void validateCompleteWorkOrderRequest(WOAttentionsRequestDTO attentionRequestDTO) throws BusinessException;
	
	/**
	 * 
	 * Metodo: imprime el log de los
	 * parametros enviados al servicio.
	 * @param request CompleteWorkOrderRequest
	 * @author jalopez
	 */
	public void logCompleteWorkOrderRequest(com.directvla.schema.businessdomain.manageworkforce.v1_0.CompleteWorkOrderRequest request);
	
	/**
	 * 
	 * Metodo: imprime el log de los
	 * parametros enviados al servicio.
	 * @param request CompleteWorkOrderRequest
	 * @author jalopez
	 */
	public void logCustomerWorkOrdersRequest(com.directvla.schema.businessdomain.manageworkforce.v1_0.GetCustomerWorkOrderByStatusRequest request);

	/**
	 * Metodo: permite contruir buildRemoveWorkOrderServiceRequest
	 * @param woCode
	 * @param serviceCode
	 * @param countryCode
	 * @param systemParameters
	 * @return
	 * @throws NumberFormatException
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws PropertiesException <tipo> <descripcion>
	 * @author
	 */
	public RemoveWorkOrderServiceRequest buildRemoveWorkOrderServiceRequest(String woCode, String serviceCode, String countryCode, 
			SystemParameterDAOLocal systemParameters) throws NumberFormatException, DAOServiceException, DAOSQLException, PropertiesException;
	
	public EditCustomerWorkOrderRequest buildEditCustomerWorkOrderRequest(EditCustomerWorkOrderDTO editCustomerWorkOrderDTO) throws BusinessException;
	
	public AddServiceToWorkOrderRequest buildAddServiceToWorkOrderRequest(
			String workOrderCode, String serviceCode, String serial,
			String countryCode, SystemParameterDAOLocal systemParameters) throws NumberFormatException, DAOServiceException, DAOSQLException, PropertiesException;
	
	public CancelWorkOrderRequest buildCancelWorkOrderServiceRequest(
			String woCode, String comment, String reasonCode, String countryCode, String customerCode);
	
	public EditTelecomTechnicianRequest buildEditTelecomTechnicianRequest(EmployeeVO eVo) throws DAOServiceException, DAOSQLException, HelperException, PropertiesException;

	public AddTelecomTechnicianRequest buildAddTelecomTechnicianRequest(EmployeeVO eVo) throws DAOServiceException, DAOSQLException, HelperException, PropertiesException;

}
