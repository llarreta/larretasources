package co.com.directv.sdii.ejb.business.assign;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.SaleChannelInstallerVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD de la Entidad SaleChannelInstaller.
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface SaleChannelInstallerBusinessBeanLocal {

	/**
	 * Metodo:  Persiste la información de un objeto SaleChannelInstallerVO
	 * @param obj objeto que encapsula la información de un SaleChannelInstallerVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createSaleChannelInstaller(SaleChannelInstallerVO obj) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un SaleChannelInstallerVO
	 * @param obj objeto que encapsula la información de un SaleChannelInstallerVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateSaleChannelInstaller(SaleChannelInstallerVO obj) throws BusinessException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un SaleChannelInstallerVO
	 * @param obj información del SaleChannelInstallerVO a ser borrado
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteSaleChannelInstaller(SaleChannelInstallerVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene la información de un SaleChannelInstallerVO por su identificador
	 * @param id identificador del SaleChannelInstallerVO a ser consultado
	 * @return objeto con la información del SaleChannelInstallerVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public SaleChannelInstallerVO getSaleChannelInstallerByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los SaleChannelInstallerVO almacenados en la persistencia
	 * @return Lista con los SaleChannelInstallerVO existentes, una lista vacia en caso que no existan SaleChannelInstallerVO en el sistema
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<SaleChannelInstallerVO> getAllSaleChannelInstallers() throws BusinessException;

}
