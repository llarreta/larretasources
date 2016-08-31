package co.com.directv.sdii.facade.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.AdjustmentRequestDTO;
import co.com.directv.sdii.model.pojo.collection.AdjustmentElementsResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.dto.AdjustmenElementsRequestDTO;
import co.com.directv.sdii.model.dto.collection.AdjustmentElementCollDTO;
import co.com.directv.sdii.model.vo.AdjustmentStatusVO;
import co.com.directv.sdii.model.vo.AdjustmentTypeVO;
import co.com.directv.sdii.model.vo.AdjustmentVO;
import co.com.directv.sdii.model.vo.SerializedVO;
import co.com.directv.sdii.reports.dto.AdjustmentElementDTO;

/**
 * 
 * Interfaz de la fachada con las operaciones Tipo CRUD de la Entidad Adjustment.
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface AdjustmentFacadeBeanLocal {

	/**
	 * Metodo:  Permite crear en el sistema un objeto Adjustment
	 * @param obj - AdjustmentVO  objeto que encapsula la información de un AdjustmentVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	/**
	 * Metodo:  Permite crear en el sistema un objeto Adjustment
	 * @param obj - AdjustmentVO  objeto que encapsula la información de un AdjustmentVO
	 * @param userId usuario, que realiza el ajuste
	 * @return
	 * @throws BusinessException en caso de error al ejecutar la operación
	 */
	public Long createAdjustment(AdjustmentVO obj, Long userId) throws BusinessException;
	
	/**
	 * Metodo: Permite actualizar en el sistema la información de un Adjustment
	 * @param obj - AdjustmentVO  objeto que encapsula la información de un AdjustmentVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateAdjustment(AdjustmentVO obj,Long userId) throws BusinessException;
	
	/**
	 * Metodo: Permite borra la información de un Adjustment
	 * @param obj - AdjustmentVO  información del AdjustmentVO a ser borrado
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void deleteAdjustment(AdjustmentVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Permite consultar la información de un Adjustment por su identificador
	 * @param id - Long identificador del Adjustment a ser consultado
	 * @return objeto con la información del AdjustmentVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public AdjustmentVO getAdjustmentByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar la información de todos los Adjustment almacenados en la persistencia
	 * @return List<AdjustmentVO> Lista con los AdjustmentVO existentes, una lista vacia en caso que no existan AdjustmentVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public List<AdjustmentVO> getAllAdjustments() throws BusinessException;
	
	/**
	 * Metodo:  Obtiene todos los tipos de ajuste
	 * @return List<AdjustmentVO> Lista con los AdjustmentTypeVO  existentes, una lista vacia en caso que no existan AdjustmentTypeVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author waguilera
	 */
	public List<AdjustmentTypeVO> getAllAdjustmentsTypes() throws BusinessException;

	/**
	 * Metodo: Crea  ajustes sobre elementos serializados 
	 * @param listAdjustmentElementsVO elementos asociados al ajuste
	 * @param adjustmentVO ajuste que se desea realizar
	 * @param userId uaurio que realiza el ajuste
	 * @return
	 * @throws BusinessException
	 * @author waguilera
	 */
	public AdjustmentVO createAdjustmentSerializedElement(List<AdjustmentElementDTO> listAdjustmentElementsVO, AdjustmentVO adjustmentVO, Long userId) throws BusinessException;
	
	/**
	 * Metodo: Crea un ajuste sobre elementos no serializados
	 * @param listAdjustmentElementsVO lista de elementos involucrados en el ajuste
	 * @param adjustmentVO ajuste
	 * @param userId usuario que crea el ajuste
	 * @return
	 * @throws BusinessException
	 */
	public AdjustmentVO createAdjustmentNotSerializedElement(List<AdjustmentElementDTO> listAdjustmentElementsVO, AdjustmentVO adjustmentVO, Long userId) throws BusinessException;

	/**
	 * 
	 * Metodo: Consulta un elemento por serial y verifica si posee ubicacion
	 * @param serialCode serial que desea buscar
	 * @param userId usuario que busca el serial
	 * @throws BusinessException <tipo> <descripcion>
	 * @author 
	 */
	public SerializedVO findSerializedElement(String serialCode, Long userId) throws BusinessException;
	
	/**
	 * Metodo encargado de consultar los elementos correspondientes a un ajuste
	 * para su autorizacion.  Previamente se chequea que el ajuste no se encuentre
	 * en estado 'Autorizando'.
	 * @param request
	 * @param requestCollInfo
	 * @return Objeto con la informacion de los elementos para el ajuste
	 * @throws BusinessException
	 */
	public AdjustmentElementCollDTO getCheckAdjustmentElementsForAuthorization(AdjustmenElementsRequestDTO request, RequestCollectionInfo requestCollInfo) throws BusinessException;
	
	/**
	 * Metodo encargado de consultar los elementos correspondientes a un ajuste
	 * para su autorizacion
	 * @param request
	 * @param requestCollInfo
	 * @return Objeto con la informacion de los elementos para el ajuste
	 * @throws BusinessException
	 */
	public AdjustmentElementCollDTO getAdjustmentElementsForAuthorization(AdjustmenElementsRequestDTO request, RequestCollectionInfo requestCollInfo) throws BusinessException;
	
	
	public AdjustmentElementsResponse searchAdjustmentsBySearchParameters(AdjustmentRequestDTO params, RequestCollectionInfo requestCollInfo) throws BusinessException;
	
	/**
	 * 
	 * Metodo encargado de realizar el ajuste para los elementos indicados
	 * @param request
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */		
	public void createAdjustmentElementsAuthorization(AdjustmenElementsRequestDTO request) throws BusinessException;
	
	public void approvalAllElementsOfAdjustment(AdjustmenElementsRequestDTO request) throws BusinessException ;

	/**
	 * 
	 * Metodo encargado de consultar los estados para un ajuste
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */	
	public List<AdjustmentStatusVO> getAllAdjustmentStatus() throws BusinessException;
	
}
