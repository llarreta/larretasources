package co.com.directv.sdii.ejb.business.assign;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.assign.assignment.dto.SkillEvaluationDTO;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.DealerVO;
import co.com.directv.sdii.model.vo.SaleChannelSellerVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD de la Entidad SaleChannelSeller.
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface SaleChannelSellerBusinessBeanLocal {

	/**
	 * Metodo:  Persiste la información de un objeto SaleChannelSellerVO
	 * @param obj objeto que encapsula la información de un SaleChannelSellerVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createSaleChannelSeller(SaleChannelSellerVO obj) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un SaleChannelSellerVO
	 * @param obj objeto que encapsula la información de un SaleChannelSellerVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateSaleChannelSeller(SaleChannelSellerVO obj) throws BusinessException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un SaleChannelSellerVO
	 * @param obj información del SaleChannelSellerVO a ser borrado
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteSaleChannelSeller(SaleChannelSellerVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene la información de un SaleChannelSellerVO por su identificador
	 * @param id identificador del SaleChannelSellerVO a ser consultado
	 * @return objeto con la información del SaleChannelSellerVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public SaleChannelSellerVO getSaleChannelSellerByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los SaleChannelSellerVO almacenados en la persistencia
	 * @return Lista con los SaleChannelSellerVO existentes, una lista vacia en caso que no existan SaleChannelSellerVO en el sistema
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<SaleChannelSellerVO> getAllSaleChannelSellers() throws BusinessException;
	
	/**
	 * 
	 * Metodo:  A partir de los parametro obtiene los dealers que cumplen con la habilidad vende instala asociado
	 * @param parameters
	 * @return Lista de dealers que cumplen con la habilidad
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	public List<DealerVO> getDealersForSaleAndInstallDealerAssociatedSkill(SkillEvaluationDTO parameters) throws BusinessException;

}
