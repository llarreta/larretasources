/**
 * Creado 18/02/2011 15:24:00
 */
package co.com.directv.sdii.ejb.business.broker;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.WOAttentionsRequestDTO;
import co.com.directv.sdii.model.vo.EmployeeVO;
import co.com.directv.sdii.ws.model.dto.AssignRequestDTO;
import co.com.directv.sdii.ws.model.dto.CustWorkOrdersResponseDTO;
import co.com.directv.sdii.ws.model.dto.EditCustomerWorkOrderDTO;

/**
 * Desacopla la comunicación con el ESB en la invocación de operaciones 
 * al WS de ManageWorkForce
 * 
 * Fecha de Creación: 18/02/2011
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see 
 */
@Local
public interface ManageWorkForceServiceBrokerLocal {

	
	/**
	 * Metodo: <Descripcion>
	 * @param customerKey
	 * @param ibsWorkOrderStatusCode
	 * @param countryCode
	 * @return CustWorkOrdersResponseDTO
	 * @throws BusinessException
	 * @author jjimenezh
	 */
	public CustWorkOrdersResponseDTO getCustomerWorkOrdersFromIBS(String customerKey, String ibsWorkOrderStatusCode, String countryCode) throws BusinessException;
	
	/**
	 * Metodo: <Descripcion>
	 * @param customerKey
	 * @param countryCode
	 * @return CustWorkOrdersResponseDTO
	 * @throws BusinessException
	 * @author jjimenezh
	 */
	public CustWorkOrdersResponseDTO getCustomerWorkOrdersFromIBS(String customerKey, String countryCode) throws BusinessException;	

	/**
	 * Metodo: <Descripcion>
	 * @param assignRequestDTO
	 * @param countryId
	 * @return AssignRequestDTO
	 * @throws BusinessException
     */
	public AssignRequestDTO getInfoCustomerWorkOrdersById(AssignRequestDTO assignRequestDTO, Long countryId) throws BusinessException;
	
	/**
	 * Metodo: Consulta la informacion principal de una WorkOrder
	 * @param countryCode
	 * @param woCode
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	public com.directvla.schema.businessdomain.common.manageworkforce.v1_0.WorkOrder getCustomerWorkOrdersById(String countryCode,String woCode) throws BusinessException;
	
	/**
	 * Metodo: <Descripcion>
	 * @param workOrderCode
	 * @param countryCode
	 * @return AssignRequestDTO
	 * @throws BusinessException
     */
	public AssignRequestDTO getCustomerWorkOrdersById(AssignRequestDTO assignRequestDTO) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Invoca la operacion de negocio de IBS
	 * para completar la finalizacion de la WorkOrder.
	 * @param attentionRequestDTO WoAttentionsRequestDTO
	 * @throws BusinessException
	 * @author cduarte
	 */
	public void completeWorkOrder(WOAttentionsRequestDTO attentionRequestDTO) throws BusinessException;

	/**
	 * Metodo: Permite actualizar o editar la workOrder en ibs
	 * @param editCustomerWorkOrderDTO
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	public String editCustomerWorkOrder(EditCustomerWorkOrderDTO editCustomerWorkOrderDTO) throws BusinessException;
	
	/**
	 * Este metodo esta enfocado a agregarle un servicio a una work order
	 * @param workOrderCode Codigo de la work order a la que se le va a colocar el servicio
	 * @param serviceCode Codigo del servicio que se le agregara a la work order
	 * @param serial 
	 * @param countryCode Codigo del pais de la work order
	 * @throws BusinessException 
	 * @author Aharker 
	 */
	public String addServiceToWorkOrder(String workOrderCode, String serviceCode, String serial, String countryCode) throws BusinessException;

	/**
	 * Este metodo esta enfocado a quitarle un servicio a una work order
	 * @param woCode Codigo de la work order a la que se le va a quitar el servicio
	 * @param serviceCode Codigo del servicio que se le quitara a la work order
	 * @param countryCode Codigo del pais de la work order
	 * @throws BusinessException
	 * @author Aharker 
	 */
	public String removeServiceFromWorkOrder(String woCode, String serviceCode,String countryCode)throws BusinessException;
	
	/**
	 * Este metodo esta enfocado a consumir la operacion de RESB de cancelar en IBS una Work Order
	 * @param woCode codigo de la work order que se desea cancelar
	 * @param comment 
	 * @param reasonCode codigo de la razon de cancelacion que se dejara en ESB
	 * @param countryCode codigo del pais de la work order
	 * @param customerCode codigo del cliente al que pertenece la Work order
	 * @throws BusinessException excepcion de negocio de HSP+ que encapsula el error que genere la cancelacion de la Work order en ESB
	 * @author Aharker 
	 */
	public void cancelWorkOrder(String woCode, String comment, String reasonCode, String countryCode, String customerCode)throws BusinessException;
	
	/**
	 * Objetivo: envia a ESB, los datos del tecnico editado en HSP+ y aguarda como respuesta el codigo ibs para el mismo.
	 * @param countryCode código del pais del técnico
	 * @param eVo datos del tècnico creado
	 * @throws BusinessException excepcion de negocio de HSP+ que encapsula el error que genere el alta del técnico en ESB
	 * @author Carlos López 
	 */
	public void editTelecomTechnician(EmployeeVO eVo)throws BusinessException;
	
	/**
	 * Objetivo: envia a ESB, los datos del tecnico creado en HSP+ y aguarda como respuesta el codigo ibs para el mismo.
	 * @param countryCode código del pais del técnico
	 * @param eVo datos del tècnico creado
	 * @throws BusinessException excepcion de negocio de HSP+ que encapsula el error que genere el alta del técnico en ESB
	 * @author Carlos López 
	 */
	public Long addTelecomTechnician(EmployeeVO eVo)throws BusinessException;


}
