package co.com.directv.sdii.ejb.business.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.collection.RefQuantityControlItemsResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.vo.ElementVO;
import co.com.directv.sdii.model.vo.RefQuantityControlItemVO;
import co.com.directv.sdii.model.vo.ReferenceVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD de la Entidad RefQuantityControlItem.
 * 
 * Fecha de Creación: Jul 12, 2011
 * @author gfandino <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface RefQuantityControlItemBusinessBeanLocal {

	/**
	 * Metodo:  Persiste la información de un objeto RefQuantityControlItemVO
	 * @param obj objeto que encapsula la información de un RefQuantityControlItemVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author gfandino
	 */
	public void createRefQuantityControlItem(RefQuantityControlItemVO obj) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un RefQuantityControlItemVO
	 * @param obj objeto que encapsula la información de un RefQuantityControlItemVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author gfandino
	 */
	public void updateRefQuantityControlItem(RefQuantityControlItemVO obj) throws BusinessException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un RefQuantityControlItemVO
	 * @param obj información del RefQuantityControlItemVO a ser borrado
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author gfandino
	 */
	public void deleteRefQuantityControlItem(RefQuantityControlItemVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene la información de un RefQuantityControlItemVO por su identificador
	 * @param id identificador del RefQuantityControlItemVO a ser consultado
	 * @return objeto con la información del RefQuantityControlItemVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author gfandino
	 */
	public RefQuantityControlItemVO getRefQuantityControlItemByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los RefQuantityControlItemVO almacenados en la persistencia
	 * @return Lista con los RefQuantityControlItemVO existentes, una lista vacia en caso que no existan RefQuantityControlItemVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author gfandino
	 */
	public List<RefQuantityControlItemVO> getAllRefQuantityControlItems() throws BusinessException;
	
	/**
	 * Método: Obtiene la información del contrl de cantidades para uan remisión
	 * @param refId - Long identificador de la remisión
	 * @param requestCollInfo - RequestCollectionInfo datos de la paginación
	 * @return RefQuantityControlItemsResponse datos paginados
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author gfandino
	 */
	public RefQuantityControlItemsResponse getRefQtyCtrlItemsByReference(RefQuantityControlItemVO refId,RequestCollectionInfo requestCollInfo)throws BusinessException;
	
	/**
	 * Método: Permite guardar el control de cantidades
	 * @param reference -  Remisión a la que pertenece la lista de control de cantidades
	 * @param qtyCtrlItems - List<RefQuantityControlItemVO>  Lista de control de cantidades
	 * @throws BusinessException en caso de error guardando el control de cantidades
	 * @author gfandino
	 */
	public void saveQtyRefCtrlItems(ReferenceVO reference, List<RefQuantityControlItemVO> qtyCtrlItems)throws BusinessException;
	
	/**
     * Método: UC30 (sección 21 prototipo) Se consultan las remisiones por la bodega de origen
     * @param idWhSource - Long identificador de la bodega de origen
     * @param requestCollInfo - RequestCollectionInfo datos de la paginación
     * @return RefQuantityControlItemsResponse con los datos de las remisiones precargadas paginadas
     * @throws BusinessException en caso de error en la consulta
     * @author gfandino
     */
    public RefQuantityControlItemsResponse getReferencesPreloadByCreationProcess(Long idWhSource,RequestCollectionInfo requestCollInfo)throws BusinessException;
	
    /**
	 * Método: UC30 consulta el control de cantidad para un tipo de elemento en una remisión
	 * @param elementType - Long Identificador del tipo de elemento
	 * @param reference - Long Identificador de la remisión
	 * @return RefQuantityControlItemVO
	 * @throws BusinessException en caso de error consultando el contrl de cantidad por tipo de elemento y remisión
	 * @author gfandino
	 */
	public RefQuantityControlItemVO getRefQuantityControlItemByElmtTypeAndRef(Long elementType, Long reference)throws BusinessException;
	
	/**
	 * 
	 * Metodo: Genera los controles de cantidad de un tipo de elemento asociado a remision
	 * @param element
	 * @param reference
	 * @param quantity
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	public void generateRefQuantiyControls(ElementVO element , ReferenceVO reference,Double quantity) throws BusinessException;

	/**
	 * Metodo: valida la existencia de diferencias entre las cantidades solicitadas
	 * y cantidades existentes en una remisión
	 * @param referenceId identificador de la referencia
	 * @return verdadero si no se encuentran diferencias en las cantidades 
	 * @throws BusinessException en caso de error al ejecutar la consulta
	 * @author wjimenez
	 */
	public boolean validateExistenceOfReferenceQuantityDifferences(Long referenceId) throws BusinessException;
	
}
