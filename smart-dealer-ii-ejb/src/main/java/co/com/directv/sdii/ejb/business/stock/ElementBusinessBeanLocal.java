package co.com.directv.sdii.ejb.business.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.ElementSerializedLinkUnLinkFilterVO;
import co.com.directv.sdii.model.dto.ElementSerializedLinkUnLinkVO;
import co.com.directv.sdii.model.dto.LinkSerializedFilterVO;
import co.com.directv.sdii.model.dto.LinkSerializedVO;
import co.com.directv.sdii.model.dto.NotSerializedAjustmentVO;
import co.com.directv.sdii.model.dto.QAItemDTO;
import co.com.directv.sdii.model.dto.UpdateLinkedSerialsDTO;
import co.com.directv.sdii.model.dto.collection.CustomerElementsResponse;
import co.com.directv.sdii.model.dto.collection.NotSerializedAjustmentCollDTO;
import co.com.directv.sdii.model.dto.collection.RequestCollectionInfoDTO;
import co.com.directv.sdii.model.pojo.ImportLog;
import co.com.directv.sdii.model.pojo.ImportLogItem;
import co.com.directv.sdii.model.pojo.ItemStatus;
import co.com.directv.sdii.model.pojo.Serialized;
import co.com.directv.sdii.model.pojo.collection.ElementResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.vo.DealerVO;
import co.com.directv.sdii.model.vo.ElementClassVO;
import co.com.directv.sdii.model.vo.ElementModelVO;
import co.com.directv.sdii.model.vo.ElementStatusVO;
import co.com.directv.sdii.model.vo.ElementTypeVO;
import co.com.directv.sdii.model.vo.ElementVO;
import co.com.directv.sdii.model.vo.ImportLogVO;
import co.com.directv.sdii.model.vo.NotSerializedVO;
import co.com.directv.sdii.model.vo.SerializedVO;
import co.com.directv.sdii.model.vo.UserVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD de la Entidad Element.
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ElementBusinessBeanLocal {

	/**
	 * Metodo:  Persiste la información de un objeto ElementVO
	 * @param obj objeto que encapsula la información de un ElementVO
	 * @throws BusinessException en caso de error al tratar de ejecutar la creación de Element
	 * @author gfandino
	 */
	public void createElement(ElementVO obj) throws BusinessException;
	
	/**
	 * Metodo:  Persiste la información de un objeto Element Serializado
	 * @param element objeto que encapsula la información de un ElementVO
	 * @param serializado objeto que encapsula la información de un SerializedVO
	 * @throws BusinessException en caso de error al tratar de ejecutar la creación de Element
	 * @author gfandino
	 */
	public void createSerializedElement(ElementVO element,SerializedVO serializado) throws BusinessException;
	
	/**
	 * Metodo:  Persiste la información de un objeto Element No Serializado
	 * @param element objeto que encapsula la información de un ElementVO
	 * @param noSerializado objeto que encapsula la información de un NotSerializedVO
	 * @throws BusinessException en caso de error al tratar de ejecutar la creación de Element
	 * @author gfandino
	 */
	public void createNotSerializedElement(ElementVO element,NotSerializedVO noSerializado, Long impLogID) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de un ElementVO por su identificador
	 * @param id identificador del ElementVO a ser consultado
	 * @return objeto con la información del ElementVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de Element por ID
	 * @author gfandino
	 */
	public ElementVO getElementByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los ElementVO almacenados en la persistencia
	 * @return Lista con los ElementVO existentes, una lista vacia en caso que no existan ElementVO en el sistema
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de todos Element
	 * @author gfandino
	 */
	public List<ElementVO> getAllElements(Long countryId) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de los ElementVO correspondientes a
	 * ElementType 
	 * @param elementType
	 *            - ElementTypeVO. Si typeElementCode es nulo, se debe
	 *            especificar el id.
	 * @param RequestCollectionInfo requestCollInfo, parametros para la paginacion.
	 * @return ElementResponse, List<ElementVO> Lista de ElementVO cuyo ElementTypeVO (id o
	 *         código es el especificado). En caso contrio vacio
	 * @throws BusinessException
	 *             en caso de error al tratar de ejecutar la consulta de Element
	 *             por ElementType
	 * @author gfandino
	 */
	public ElementResponse getElementsByElementType(ElementTypeVO elementType, RequestCollectionInfo requestCollInfo) throws BusinessException;
	
	
	/**
	 * INV 18-Registrar control de calidad de elementos serializados desde archivo plano
	 * @author Leonardo Cardozo Cadavid - lcardozo@intergrupo.com
	 * @param importLog
	 * @param logisticOperator
	 * @param qaItemDTOs
	 * @param country - Long identificador del pa�s
	 * @throws BusinessException
	 */
	public void registerSerializedElementQualityControlAndByCountry(ImportLogVO importLog, DealerVO logisticOperator, List<QAItemDTO> qaItemDTOs,UserVO user,Long country) throws BusinessException;
	
	
	/**
	 * Metodo: Consultar el historial de los elementos en un edificio
	 * Caso de Uso Inv_22 Consultar el historial de los elementos en un edificio
	 * @param ibsCode String Codigo del edificio del cual se va a consultar el historial 
	 * @param RequestCollectionInfo requestCollInfo, parametros para la paginacion.
	 * @return List<HistoryElementsOfCustomerDTO> Lista de historial de elementos dado un codigo de edificio
	 * @throws BusinessException En caso de error en la consulta
	 * @author cduarte
	 */
	public CustomerElementsResponse getElementsHistoryOnBuildingCode(String ibsCode,Long countryId, RequestCollectionInfo requestCollInfo) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar los elementos NO serializados de una bodega junto con sus movimientos parciales
	 * CU 26
	 * @param warehouseId Long Id de la bodega - Obligatorio
	 * @return List<NotSerializedAjustmentDTO> Lista con los elementos que cumplen con el filtro junto con sus retiros parciales
	 * @throws BusinessException Error cuando se realiza la consulta de los elementos	 
	 * @author jnova
	 */
	public NotSerializedAjustmentCollDTO getWhElementsAndNotSerPartRetByWarehouseId(Long warehouseId, RequestCollectionInfoDTO requestCollInfo)throws BusinessException;
	
	/**
	 * Metodo: Obtiene dos listas de elementos para que el usuario seleccione uno de la primera
	 * lista para vincularle uno de la segunda lista
	 * CU 44
	 * @param LinkSerializedFilterVO VO Encapsula la informacion para filtrar la consulta de elementos
	 * @return LinkSerializedVO VO que encapsula dos listas de elementos que cumplen con el filtro enviado
	 * por el usuario
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta de elementos serializados
	 * de acuerdo al filtro
	 * @author jnova
	 */
	public LinkSerializedVO getElementsToLinkSerializedElements(LinkSerializedFilterVO criteria) throws BusinessException;
	
	/**
	 * Metodo: Vincula dos elementos si no tienen elementos vinculados
	 * CU 44
	 * @param elementOneId Long Id del elemento al que se le va a vincular el elemento
	 * @param elementTwoId Long Id del elemento que va a ser vinculado
	 * @throws BusinessException En caso que ocurra error al vincular los elementos	
	 * @author jnova
	 */
	public void linkSerializedElements(Long elementOneId, Long elementTwoId) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar los elementos NO serializados de una bodega junto con sus movimientos parciales
	 * eliminando informacion innecesaria del mensaje SOAP
	 * CU 26
	 * @param warehouseId Long Id de la bodega - Obligatorio
	 * @return List<NotSerializedAjustmentDTO> Lista con los elementos que cumplen con el filtro junto con sus retiros parciales
	 * @throws BusinessException Error cuando se realiza la consulta de los elementos	 
	 * @author jnova
	 */
	public List<NotSerializedAjustmentVO> getWhElementsAndNotSerPartRetByWarehouseIdMinInfo(Long warehouseId)throws BusinessException;


	/**
	 * CU_INV_53: Modificación de modelo de un elemento.
	 * 
	 * @param warehouse
	 * @param modElement
	 * @param modLinkedElement
	 * @throws BusinessException
	 * @author waguilera
	 */
	public void changeElementModel(Long idElement,
			String codTypeElement, Long idElementLinkElement,
			String codTypeElementLinkElement) throws BusinessException;
	
	
	
	/**
	 * 
	 * Metodo: CU_INV_44 - Vincular dos elementos serializados de una misma bodega
	 * @param elementSerializedLinkUnLinkFilterVO
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author waguilera
	 */
	public ElementSerializedLinkUnLinkVO getElementSerializedToLinkUnLink(ElementSerializedLinkUnLinkFilterVO elementSerializedLinkUnLinkFilterVO ) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Actualiza los seriales de los elementos vinculados
	 * @param updateLinkedSerialsDTO objeto que encapsula la informacion para realiza la actualizacion de seriales
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	public void updateLinkedSerials(UpdateLinkedSerialsDTO updateLinkedSerialsDTO) throws BusinessException;

	/**
	 * 
	 * Metodo: Almacena un elemento vinculado.
	 * @param element
	 * @param serializado
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 * @param objPojo 
	 * @param itemStatus 
	 */
	public ImportLogItem createSerializedLinkedElement(Serialized vinculado, ImportLog objPojo, ItemStatus itemStatus) throws BusinessException;
	

	/**
	 * 
	 * Metodo: Método utilitario encargado de invertir el elemento vinculado 
	 * en caso de que el elemento principal seal una smarcard, se deja publico para que 
	 * pueda ser utilizado por otros bussiness
	 * @param element
	 * @param serializado
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 * @param objPojo 
	 * @param itemStatus 
	 */
	public SerializedVO orderLinkedElement(SerializedVO serializedVO) throws BusinessException;



}
