package co.com.directv.sdii.facade.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.pojo.collection.SuppliersResponse;
import co.com.directv.sdii.model.vo.SupplierVO;

/**
 * 
 * Interfaz de la fachada con las operaciones Tipo CRUD de la Entidad Supplier.
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface SupplierFacadeBeanLocal {

	/**
	 * Metodo:  Permite crear en el sistema un objeto Supplier
	 * @param obj - SupplierVO  objeto que encapsula la información de un SupplierVO
	 * @throws BusinessException en caso de error al ejecutar la creación de Supplier
	 * @author gfandino
	 */
	public void createSupplier(SupplierVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite actualizar en el sistema la información de un Supplier
	 * @param obj - SupplierVO  objeto que encapsula la información de un SupplierVO
	 * @throws BusinessException en caso de error al ejecutar la actualización de Supplier
	 * @author gfandino
	 */
	public void updateSupplier(SupplierVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite borra la información de un Supplier
	 * @param obj - SupplierVO  información del SupplierVO a ser borrado
	 * @throws BusinessException en caso de error al ejecutar la eliminación de Supplier
	 * @author gfandino
	 */
	public void deleteSupplier(SupplierVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Permite consultar la información de un Supplier por su identificador
	 * @param id - Long identificador del Supplier a ser consultado
	 * @return objeto con la información del SupplierVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la consulta de Supplier por ID
	 * @author gfandino
	 */
	public SupplierVO getSupplierByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar la información de todos los Supplier almacenados en la persistencia
	 * @param Long countryId, id del pais.
	 * @param RequestCollectionInfo requestCollInfo, parametros para la paginacion.
	 * @return SuppliersResponse, List<SupplierVO> Lista con los SupplierVO existentes, una lista vacia en caso que no existan SupplierVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la creación de todos los Supplier
	 * @author gfandino
	 */
	public SuppliersResponse getAllSuppliers(Long countryId, RequestCollectionInfo requestCollInfo) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de un SupplierVO por su código
	 * @param code - String Código del SupplierVO a ser consultado
	 * @return SupplierVO objeto con la información del SupplierVO dado su código, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la consulta de Supplier por código
	 * @author gfandino
	 */
	public SupplierVO getSupplierByCode(String code) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de un SupplierVO por su nit
	 * @param nit - String Nit del SupplierVO a ser consultado
	 * @return SupplierVO objeto con la información del SupplierVO dado su nit, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la consulta de Supplier por nit
	 * @author gfandino
	 */
	public SupplierVO getSupplierByNit(String nit) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de los SupplierVO asociados al país
	 * @param countryId - Long Identificador del país a consultar
	 * @return List<SupplierVO> Lista de SupplierVO dado un país, vacio en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la consulta de Supplier por país
	 * @author gfandino
	 */
	public List<SupplierVO> getSupplierByCountry(Long countryId) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de los SupplierVO activos
	 * @return List<SupplierVO> Lista de SupplierVO activos, vacio en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la consulta de Supplier activos
	 * @author gfandino
	 */
	public List<SupplierVO> getSupplierByActiveStatus() throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de los SupplierVO activos por país
	 * @param countryId - Long Identificador del país a consultar
	 * @return List<SupplierVO> Lista de SupplierVO activos, vacio en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la consulta de Supplier activos por país
	 * @author gfandino
	 */
	public List<SupplierVO> getSupplierByActiveStatus(Long countryId) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de los SupplierVO inactivos
	 * @return List<SupplierVO> Lista de SupplierVO inactivos, vacio en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la consulta de Supplier inactivos
	 * @author gfandino
	 */
	public List<SupplierVO> getSupplierByInactiveStatus() throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de los SupplierVO inactivos por país
	 * @param countryId - Long Identificador del país a consultar
	 * @return List<SupplierVO> Lista de SupplierVO inactivos, vacio en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la consulta de Supplier inactivos por país
	 * @author gfandino
	 */
	public List<SupplierVO> getSupplierByInactiveStatus(Long countryId) throws BusinessException;

}
