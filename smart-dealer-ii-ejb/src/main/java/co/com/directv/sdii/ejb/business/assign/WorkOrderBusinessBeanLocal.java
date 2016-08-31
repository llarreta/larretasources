package co.com.directv.sdii.ejb.business.assign;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.VerifyDesconectionSerialsDTO;
import co.com.directv.sdii.model.dto.WorkOrderInfoServiceVinculationDTO;
import co.com.directv.sdii.model.dto.WorkOrderTrayDTO;
import co.com.directv.sdii.model.pojo.WorkOrder;
import co.com.directv.sdii.model.pojo.WorkorderStatus;
import co.com.directv.sdii.model.vo.DealerVO;
import co.com.directv.sdii.model.vo.WorkOrderVO;

@Local
public interface WorkOrderBusinessBeanLocal {

	/**
	 * Metodo: Obtiene el dealerVO que tiene al menos una  workOrder que tiene asociado un 'countryIso2Code', un 'ibsCustomerCode', un 'postalCode' y un 'addressCode'
     * Si scheduleDate no es null entonces la workOrder tiene que tener como woProgrammingDate a scheduleDate. 
     * Si scheduleDate es null entonces la WorkOrder tiene como woProgrammingDate un dia posterior al dia de hoy.	 
     * @param countryIso2Code
	 * @param ibsCustomerCode
	 * @param postalCode
	 * @param scheduleDate
	 * @param addressCode
	 * @param address
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	public DealerVO otherServices2CustmerSkill(String countryIso2Code, String ibsCustomerCode, String postalCode, java.util.Date scheduleDate, String addressCode, String address) throws BusinessException ;
	

	/**
	 * Metodo: Obtiene el dealer de la ultima WO en estado realizada o finalizada de un Cliente.
	 * @param ibsCustomerCode String, codigo del cliente a consultar las WO
	 * @param countryCode , Codigo del pais
	 * @return List<DealerVO>, lista de dealers encontrados
	 * @author waltuzarra
	 * 
	 */	
	public List<DealerVO> getDealerFromLastWoFromCustomer(String ibsCustomerCode, Long countryCode)throws BusinessException ;

	/**
	 * Metodo:  1. Consultar La última Work order que se atendió al cliente, es decir que esté en estado 
	 * 		realizada o finalizada (Si no existe, se devuelve una lista vacía de dealers)<br>
	 * 2. Consultar la categoría de servicio del primer servicio asociado a esa Work Order (la consultada en el paso 1)
	 * 		en la base de datos la categoría del servicio se almacena en la tabla service_type<br>
	 * 3. Consultar si algunos de los servicios que se prestarán al cliente en esta oportunidad están configurados como que
	 * 		constituyen garantía de la categoría consultada en el paso 2, esto se almacena en la tabla
	 * 		services_are_warranties, si ninguno constituye garantía la habilidad devuelve una lista vacia de dealers<br>
	 * 4. Consultar el periodo de garantía de la categoría de servicio de esa WO (la consultada en el paso 1)
	 * 		en la tabla services_type_warranties<br>
	 * 5. Si la última WO consultada en el paso 1 tiene como fecha de atención o finalización una fecha superior
	 * 		a la fecha actual menos los días de periodo de garantía consultados en el paso 4, quiere decir
	 * 		que esa compañía debe atender la WO actual<br>
	 * 6. Si la compañía que atendió la WO del paso 1, está dentro de la lista de compañías que se recibieron, se
	 * 		devuelve una lista con solo esa compañía, si la compañía no está dentro de la lista que se recibe
	 * 		se devuelve una lista vacia.<br>
	 * 7. En caso que la habilidad reciba una lista vacía de compañías devolverá una lista
	 * 		con la compañía que se encontró en el paso 1.
	 * @param ibsCustomerCode
	 * @param countryId
	 * @param List<String> serviceCodes
	 * @return <tipo> <descripcion>
	 * @author waltuzarra
	 */	
	public List<DealerVO> getDealerFromWoWithWarranty(String ibsCustomerCode,
			Long countryCode, List<String> serviceCodes) throws BusinessException;	

	public WorkorderStatus getWorkorderStatusByIBS6StatusCode(String ibs6StatusCode) throws BusinessException;

	/**
	 * Metodo: <Descripcion>
	 * @param countryId
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	public void sendWorkOrdersForLastDayReport(Long countryId) throws BusinessException;
	
	public WorkOrderVO getWorkOrderByCode(String woCode) throws BusinessException;
	
	public List<WorkOrderTrayDTO> getWorkOrdersByWoIdForSimilarState(List<Long> workOrderIds, String codesStates) throws BusinessException;
	
    public WorkOrder getWorkOrderByID(Long id) throws BusinessException ;
    
    public List<WorkOrderVO> getWorkOrdersByWoIdForSimilarStateByCode(List<String> workOrderCodes, String codesStates) throws BusinessException;

    /**
     * Metodo que verifica los seriales que se agregaran a un servicio de desconexion en la atencion
     * @param request parametros necesarios para la validacion, id de work order, seriales ingresados, id del servicio asociado a la work order, id del 
     * 			cliente, codigo del cliente
     * @throws BusinessException
     * @author aharker
     */
	public void verifyDesconectionSerials(VerifyDesconectionSerialsDTO request) throws BusinessException;
	
	/**
	 * Metodo encargado de extraer el serial que se trajo de ibs para el servicio de una wo, si la wo no es de desconexion, retorna un String vacio
	 * @param woId
	 * @param woServiceId
	 * @return
	 * @throws BusinessException
	 * @author Aharker
	 */
	public String getSerialForAttentionDesconectionWO(Long woId, Long woServiceId) throws BusinessException;
}
