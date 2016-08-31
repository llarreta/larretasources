package co.com.directv.sdii.ws.business.stock;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.collection.InventoryElementGroupDTOResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;


/**
 * Servicio web que expone las operaciones relacionadas con inventarios
 * 
 * Servicio web que expone las operaciones relacionadas con inventarios
 * 
 * Fecha de Creación: 28/05/2012
 * @author jjimenezh <a href="mailto:waguilera@intergrupo.com">e-mail</a>
 * @version 1.0
 *
 */
@WebService(name="InventoryHspWS",targetNamespace="http://stock.business.ws.sdii.directv.com.co/")
public interface IInventoryHspWS {

	/**
	 * Operacion encarargada de retornar los elementos en bodegas de stock de un pais
	 * agrupados por dealer, ubicacion y tipo de elemento, 
	 * @param countryCode
	 * @return
	 * @throws BusinessException
	 * @author waguilera
	 */
	@WebMethod(operationName="getElementGroupStock", action="getElementGroupStock", exclude = false)
	public InventoryElementGroupDTOResponse getElementGroupStock(@WebParam(name="countryCode") String countryCode, @WebParam(name="requestCollInfo") RequestCollectionInfo requestCollInfo) throws BusinessException;
	
	/**
	 * Operacion encarargada de retornar los elementos en bodegas de transito de registros
	 * de importación y remisiones de un pais
	 * agrupados por dealer, ubicacion y tipo de elemento, 
	 * 
	 * @param countryCode
	 * @param requestCollInfo
	 * @return 
	 * @throws BusinessException
	 * @author waguilera
	 */
	@WebMethod(operationName="getElementGroupTransit", action="getElementGroupTransit", exclude = false)
	public InventoryElementGroupDTOResponse getElementGroupTransit(@WebParam(name="countryCode") String countryCode, @WebParam(name="requestCollInfo") RequestCollectionInfo requestCollInfo) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar las wo del dia anterior atendidas y con esta informacion genera un reporte en un ftp
	 * @param countryId
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	@WebMethod(operationName="sendWorkOrdersForLastDayReport", action="sendWorkOrdersForLastDayReport", exclude = false)
	public void sendWorkOrdersForLastDayReport(@WebParam(name="countryId") Long countryId) throws BusinessException;
	
}
