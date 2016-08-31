package co.com.directv.sdii.ejb.business.core;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.ElementDTO;
import co.com.directv.sdii.model.dto.InventoryDTO;

/**
 * 
 * Interfaz que define los métodos de negocio de los servicios de Core
 * que interactuan con el modulo de inventarios.
 * 
 * Fecha de Creación: 1/08/2011
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface CoreWOInventoryBusinessLocal {
	
	/**
	 * 
	 * Metodo: Retorna la informacion de un Elemento
	 * filtrando por el serial
	 * @param InventoryDTO inventoryDTO
	 * @return ElementDTO
	 * @throws BusinessException
	 * @author jalopez
	 */
	public ElementDTO getElementBySerialCode(InventoryDTO inventoryDTO) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Retorna la informacion de un Elemento
	 * filtrando por el serial invocando servicios
	 * de inventarios del sistema externo
	 * @param InventoryDTO inventoryDTO
	 * @return ElementDTO
	 * @throws BusinessException
	 * @author jalopez
	 */
	public ElementDTO getElementBySerialCodeExternal(InventoryDTO inventoryDTO) throws BusinessException;
	
}
