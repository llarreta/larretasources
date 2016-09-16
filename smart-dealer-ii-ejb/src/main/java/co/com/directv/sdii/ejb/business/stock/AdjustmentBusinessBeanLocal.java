package co.com.directv.sdii.ejb.business.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.AdjustmenElementsRequestDTO;
import co.com.directv.sdii.model.dto.AdjustmentRequestDTO;
import co.com.directv.sdii.model.dto.MovementElementDTO;
import co.com.directv.sdii.model.dto.collection.AdjustmentElementCollDTO;
import co.com.directv.sdii.model.pojo.collection.AdjustmentElementsResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.vo.AdjustmentStatusVO;
import co.com.directv.sdii.model.vo.AdjustmentTypeVO;
import co.com.directv.sdii.model.vo.AdjustmentVO;
import co.com.directv.sdii.model.vo.SerializedVO;
import co.com.directv.sdii.reports.dto.AdjustmentElementDTO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD de la Entidad Adjustment.
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface AdjustmentBusinessBeanLocal {

	/**
	 * Metodo:  Persiste la información de un objeto AdjustmentVO
	 * @param obj objeto que encapsula la información de un AdjustmentVO
	 * @param userId id del usuario que crea el ajuste
	 * @return
	 * @throws BusinessException en caso de error al ejecutar la operación
	 */
	public Long createAdjustment(AdjustmentVO obj, Long userId) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un AdjustmentVO
	 * @param obj objeto que encapsula la información de un AdjustmentVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateAdjustment(AdjustmentVO obj,Long userId) throws BusinessException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un AdjustmentVO
	 * @param obj información del AdjustmentVO a ser borrado
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteAdjustment(AdjustmentVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene la información de un AdjustmentVO por su identificador
	 * @param id identificador del AdjustmentVO a ser consultado
	 * @return objeto con la información del AdjustmentVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public AdjustmentVO getAdjustmentByID(Long id) throws BusinessException;
	
	public AdjustmentVO getAdjustmentByIDNew(Long id) throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene la información de todos los AdjustmentVO almacenados en la persistencia
	 * @return Lista con los AdjustmentVO existentes, una lista vacia en caso que no existan AdjustmentVO en el sistema
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<AdjustmentVO> getAllAdjustments() throws BusinessException;
	
	/**
	 * 
	 * Metodo:  Obtiene todos los tipos de ajuste 
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author waguilera
	 */
	public List<AdjustmentTypeVO> getAllAdjustmentsTypes() throws BusinessException;
	
	/**
	 * Metodo: Crea un ajuste de entrada, salida o translado para un elemento serializado
	 * Valida el parametro del tipo de ajustes para tomar decisiones
	 * @param listAdjustmentElementsVO elementos involucrados en el ajuste de inventario
	 * @param adjustmentVO ajuste de inventario que se realiza
	 * @param userId Usuario que realiza el ajuste de inventario
	 * @return
	 * @throws BusinessException
	 */
	public AdjustmentVO createAdjustmentSerializedElement(List<AdjustmentElementDTO> listAdjustmentElementsVO, AdjustmentVO adjustmentVO, Long userId) throws BusinessException;

	/**
	 * Metodo: Crea un ajuste de entrada, salida o translado para un elemento serializado
	 * @param listAdjustmentElementsVO elementos involucrados en el ajuste de inventario
	 * @param adjustmentVO ajuste de inventario que se realiza
	 * @param userId Usuario que realiza el ajuste de inventario
	 * @return
	 * @throws BusinessException
	 */
	public AdjustmentVO createAdjustmentNotSerializedElement(List<AdjustmentElementDTO> listAdjustmentElementsVO, AdjustmentVO adjustmentVO, Long userId) throws BusinessException;

	/**
	 * 
	 * Metodo: Crea el ajuste de entrada en la bodega destino del movimiento que se va a mover para el caso que el elemento
	 * no este en la bodega de origen. Aplica para CU INV 40 que el los cliente no siempre estan los elementos NO serializados
	 * @param dto
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	public void createAdjustmentForEntry(MovementElementDTO dto) throws BusinessException;

	/**
	 * Metodo: Metodo para consultar un elemento por serial y determinar
	 * si posee una ubicacion
	 * @param serialCode Serial que se desea buscar en HSP+
	 * @param userId usuario que realiza la busqueda del serial
	 * @return
	 * @throws BusinessException
	 */
	public SerializedVO findSerializedElement(String serialCode, Long userId) throws BusinessException;	

	/**
	 * Metodo: Metodo para consultar los elementos correspondientes a un ajuste
	 * para su autorizacion.  Previamente se chequea que el ajuste no este en
	 * estado 'Autorizando'.
	 * @param request
	 * @param requestCollInfo
	 * @return
	 * @throws BusinessException
	 */
	public AdjustmentElementCollDTO getCheckAdjustmentElementsForAuthorization(AdjustmenElementsRequestDTO request, RequestCollectionInfo requestCollInfo) throws BusinessException;
	
	/**
	 * Metodo: Metodo para consultar los elementos correspondientes a un ajuste
	 * para su autorizacion
	 * @param request
	 * @param requestCollInfo
	 * @return
	 * @throws BusinessException
	 */
	public AdjustmentElementCollDTO getAdjustmentElementsForAuthorization(AdjustmenElementsRequestDTO request, RequestCollectionInfo requestCollInfo) throws BusinessException;

	/**
	 * 
	 * Metodo encargado de aprovar y realizar el ajuste para los elementos indicados, ademas modifica el estado completo del ajuste dependiendo de los elementos autorizados
	 * @param request
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */	
	public void createAdjustmentElementsAuthorization(AdjustmenElementsRequestDTO request) throws BusinessException;
	
		
	public AdjustmentElementsResponse searchAdjustmentsBySearchParameters(AdjustmentRequestDTO params, RequestCollectionInfo requestCollInfo) throws BusinessException;
	
	public void approvalAllElementsOfAdjustment(AdjustmenElementsRequestDTO request) throws BusinessException ;
	
	/**
	 * Metodo encargado de consultar los estados para un ajuste
	 * @throws BusinessException
	 * @author
	 */	
	public List<AdjustmentStatusVO> getAllAdjustmentStatus() throws BusinessException;
	
}
