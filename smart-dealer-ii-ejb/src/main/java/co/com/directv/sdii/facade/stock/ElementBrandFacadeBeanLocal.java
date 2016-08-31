package co.com.directv.sdii.facade.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.collection.ElementBrandResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.vo.ElementBrandVO;

/**
 * 
 * Interfaz de la fachada con las operaciones Tipo CRUD de la Entidad ElementBrand.
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ElementBrandFacadeBeanLocal {

	/**
	 * Metodo:  Permite crear en el sistema un objeto ElementBrand
	 * @param obj - ElementBrandVO  objeto que encapsula la información de un ElementBrandVO
	 * @throws BusinessException en caso de error al ejecutar la creación de ElementBrand
	 * @author gfandino
	 */
	public void createElementBrand(ElementBrandVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite actualizar en el sistema la información de un ElementBrand
	 * @param obj - ElementBrandVO  objeto que encapsula la información de un ElementBrandVO
	 * @throws BusinessException en caso de error al ejecutar la actualización de ElementBrand
	 * @author gfandino
	 */
	public void updateElementBrand(ElementBrandVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite borra la información de un ElementBrand
	 * @param obj - ElementBrandVO  información del ElementBrandVO a ser borrado
	 * @throws BusinessException en caso de error al ejecutar la eliminación de ElementBrand
	 * @author gfandino
	 */
	public void deleteElementBrand(ElementBrandVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Permite consultar la información de un ElementBrand por su identificador
	 * @param id - Long identificador del ElementBrand a ser consultado
	 * @return objeto con la información del ElementBrandVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la consulta de ElementBrand por ID
	 * @author gfandino
	 */ 
	public ElementBrandVO getElementBrandByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar la información de todos los ElementBrand almacenados en la persistencia
	 * @return List<ElementBrandVO> Lista con los ElementBrandVO existentes, una lista vacia en caso que no existan ElementBrandVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la consulta de todos los ElementBrand
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
	 * @param code - String Código de la marca del elemento
	 * @param requestCollInfo - RequestCollectionInfo infomración de la paginación
	 * @return ElementBrandResponse marcas de elemento paginadas
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de todos los ElementBrandVO en todos los estados
	 * @author gfandino
	 * 
	 */
	public ElementBrandResponse getElementBrandByAllStatuPage(
			String code, RequestCollectionInfo requestCollInfo)throws BusinessException;

}
