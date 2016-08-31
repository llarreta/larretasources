package co.com.directv.sdii.facade.stock;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.collection.InventoryElementGroupDTOResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;

/**
 * 
 * Fachada con las operaciones relacionadas con inventarios
 * 
 * Fecha de Creación: 28/05/2012
 * @author waguilera <a href="mailto:waguilera@intergrupo.com">e-mail</a>
 * @version 1.0
 */
@Local
public interface InventoryFacadeHspBeanLocal {

	/**
	 * Operacion encarargada de retornar los elementos en bodegas de stock de un pais
	 * agrupados por dealer, ubicacion y tipo de elemento, 
	 * @param countryCode
	 * @return
	 * @throws BusinessException
	 */
	public InventoryElementGroupDTOResponse getElementGroupStock(String countryCode, RequestCollectionInfo requestCollInfo)
			throws BusinessException;
	
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
	public InventoryElementGroupDTOResponse getElementGroupTransit(String countryCode, RequestCollectionInfo requestCollInfo)
			throws BusinessException;
	
	/**
	 * Metodo: Permite consultar las wo del dia anterior atendidas y con esta informacion genera un reporte en un ftp
	 * @param countryId
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	public void sendWorkOrdersForLastDayReport(Long countryId) throws BusinessException;
			
}
