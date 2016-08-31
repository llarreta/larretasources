package co.com.directv.sdii.ejb.business.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.collection.ElementBrandResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.vo.ElementBrandVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD de la Entidad ElementBrand.
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ElementBrandBusinessBeanLocal {

	/**
	 * Metodo:  Persiste la información de un objeto ElementBrandVO
	 * @param obj objeto que encapsula la información de un ElementBrandVO
	 * @throws BusinessException en caso de error al tratar de ejecutar la creación de ElementBrand 
	 * @author gfandino
	 */
	public void createElementBrand(ElementBrandVO obj) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un ElementBrandVO
	 * @param obj objeto que encapsula la información de un ElementBrandVO
	 * @throws BusinessException en caso de error al tratar de ejecutar la actualización de de ElementBrand
	 * @author
	 */
	public void updateElementBrand(ElementBrandVO obj) throws BusinessException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un ElementBrandVO
	 * @param obj información del ElementBrandVO a ser borrado
	 * @throws BusinessException en caso de error al tratar de ejecutar la eliminación de de ElementBrand
	 * @author gfandino
	 */
	public void deleteElementBrand(ElementBrandVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene la información de un ElementBrandVO por su identificador
	 * @param id identificador del ElementBrandVO a ser consultado
	 * @return objeto con la información del ElementBrandVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de ElementBrand por ID
	 * @author gfandino
	 */
	public ElementBrandVO getElementBrandByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los ElementBrandVO almacenados en la persistencia
	 * @return Lista con los ElementBrandVO existentes, una lista vacia en caso que no existan ElementBrandVO en el sistema
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de todos los ElementBrand
	 * @author gfandino
	 */
	public List<ElementBrandVO> getAllElementBrands() throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de un ElementBrandVO por su código
	 * @param code - String código del ElementBrandVO a ser consultado
	 * @return ElementBrandVO objeto con la información del ElementBrandVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de ElementBrand por código
	 * @author gfandino
	 */
	public ElementBrandVO getElementBrandByCode(String code) throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene la información de todos los ElementBrandVO almacenados en la persistencia con estado activo
	 * @return Lista con los ElementBrandVO existentes con estado activo, una lista vacia en caso que no existan ElementBrandVO en el sistema 
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de todos los ElementBrandVO con estado activo
	 * @author gfandino
	 */
	public List<ElementBrandVO> getElementBrandByActiveStatus() throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los ElementBrandVO almacenados en la persistencia con estado activo
	 * @param requestCollInfo - RequestCollectionInfo datos de la paginación
	 * @return ElementBrandResponse con la información de la consulta 
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de todos los ElementBrandVO con estado activo
	 * @author gfandino
	 */
	public ElementBrandResponse getElementBrandByActiveStatus(RequestCollectionInfo requestCollInfo) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los ElementBrandVO almacenados en la persistencia con estado inactivo
	 * @return Lista con los ElementBrandVO existentes con estado inactivo, una lista vacia en caso que no existan ElementBrandVO en el sistema 
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de todos los ElementBrandVO con estado inactivo
	 * @author gfandino
	 */
	public List<ElementBrandVO> getElementBrandByInActiveStatus() throws BusinessException;

	/**
	 * Método: Obtiene la infomración de las marcas de elemento en todos los estados
	 * @param code - String código de la marca del elemento
	 * @param requestCollInfo - RequestCollectionInfo infomración de la paginación
	 * @return ElementBrandResponse marcas de elemento paginadas
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de todos los ElementBrandVO en todos los estados
	 * @author gfandino
	 * 
	 */
	public ElementBrandResponse getElementBrandByAllStatuPage (
			String code, RequestCollectionInfo requestCollInfo) throws BusinessException;

}
