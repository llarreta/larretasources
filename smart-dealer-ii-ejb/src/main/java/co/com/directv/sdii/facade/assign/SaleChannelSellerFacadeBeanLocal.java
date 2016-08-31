package co.com.directv.sdii.facade.assign;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.SaleChannelSellerVO;

/**
 * 
 * Interfaz de la fachada con las operaciones Tipo CRUD de la Entidad SaleChannelSeller.
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface SaleChannelSellerFacadeBeanLocal {

	/**
	 * Metodo:  Permite crear en el sistema un objeto SaleChannelSeller
	 * @param obj - SaleChannelSellerVO  objeto que encapsula la información de un SaleChannelSellerVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createSaleChannelSeller(SaleChannelSellerVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite actualizar en el sistema la información de un SaleChannelSeller
	 * @param obj - SaleChannelSellerVO  objeto que encapsula la información de un SaleChannelSellerVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateSaleChannelSeller(SaleChannelSellerVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite borra la información de un SaleChannelSeller
	 * @param obj - SaleChannelSellerVO  información del SaleChannelSellerVO a ser borrado
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void deleteSaleChannelSeller(SaleChannelSellerVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Permite consultar la información de un SaleChannelSeller por su identificador
	 * @param id - Long identificador del SaleChannelSeller a ser consultado
	 * @return objeto con la información del SaleChannelSellerVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public SaleChannelSellerVO getSaleChannelSellerByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar la información de todos los SaleChannelSeller almacenados en la persistencia
	 * @return List<SaleChannelSellerVO> Lista con los SaleChannelSellerVO existentes, una lista vacia en caso que no existan SaleChannelSellerVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public List<SaleChannelSellerVO> getAllSaleChannelSellers() throws BusinessException;

}
