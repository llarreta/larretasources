package co.com.directv.sdii.ejb.business.stock;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.collection.InventoryElementGroupDTOResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;


/**
 * 
 * Interfaz de la capa de negocio con las operaciones relacionadas con inventarios
 * 
 * Fecha de Creación: 28/05/2012
 * @author waguilera <a href="mailto:waguilera@intergrupo.com">e-mail</a>
 * @version 1.0
 */
 
@Local
public interface InventoryHspBusinessBeanLocal {

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
	
}
