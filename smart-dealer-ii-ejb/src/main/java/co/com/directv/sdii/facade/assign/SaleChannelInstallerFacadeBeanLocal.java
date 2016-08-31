package co.com.directv.sdii.facade.assign;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.SaleChannelInstallerVO;

/**
 * 
 * Interfaz de la fachada con las operaciones Tipo CRUD de la Entidad SaleChannelInstaller.
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface SaleChannelInstallerFacadeBeanLocal {

	/**
	 * Metodo:  Permite crear en el sistema un objeto SaleChannelInstaller
	 * @param obj - SaleChannelInstallerVO  objeto que encapsula la información de un SaleChannelInstallerVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createSaleChannelInstaller(SaleChannelInstallerVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite actualizar en el sistema la información de un SaleChannelInstaller
	 * @param obj - SaleChannelInstallerVO  objeto que encapsula la información de un SaleChannelInstallerVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateSaleChannelInstaller(SaleChannelInstallerVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite borra la información de un SaleChannelInstaller
	 * @param obj - SaleChannelInstallerVO  información del SaleChannelInstallerVO a ser borrado
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void deleteSaleChannelInstaller(SaleChannelInstallerVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Permite consultar la información de un SaleChannelInstaller por su identificador
	 * @param id - Long identificador del SaleChannelInstaller a ser consultado
	 * @return objeto con la información del SaleChannelInstallerVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public SaleChannelInstallerVO getSaleChannelInstallerByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar la información de todos los SaleChannelInstaller almacenados en la persistencia
	 * @return List<SaleChannelInstallerVO> Lista con los SaleChannelInstallerVO existentes, una lista vacia en caso que no existan SaleChannelInstallerVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public List<SaleChannelInstallerVO> getAllSaleChannelInstallers() throws BusinessException;

}
