package co.com.directv.sdii.ejb.business.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.ejb.business.file.data.LoadMassiveSerializedAdjusmentData;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.MovementElementDTO;
import co.com.directv.sdii.model.pojo.AdjustmentElementsStatus;
import co.com.directv.sdii.model.pojo.ElementType;
import co.com.directv.sdii.model.pojo.User;
import co.com.directv.sdii.model.pojo.Warehouse;
import co.com.directv.sdii.model.vo.AdjustmentElementsVO;
import co.com.directv.sdii.model.vo.AdjustmentVO;
import co.com.directv.sdii.reports.dto.AdjustmentElementDTO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD de la Entidad AdjustmentElements.
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface AdjustmentElementsBusinessBeanLocal {

	
	
	/**
	 * Metodo: actualiza la información de un AdjustmentElementsVO
	 * @param obj objeto que encapsula la información de un AdjustmentElementsVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateAdjustmentElements(AdjustmentElementsVO obj) throws BusinessException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un AdjustmentElementsVO
	 * @param obj información del AdjustmentElementsVO a ser borrado
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteAdjustmentElements(AdjustmentElementsVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene la información de un AdjustmentElementsVO por su identificador
	 * @param id identificador del AdjustmentElementsVO a ser consultado
	 * @return objeto con la información del AdjustmentElementsVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public AdjustmentElementsVO getAdjustmentElementsByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los AdjustmentElementsVO almacenados en la persistencia
	 * @return Lista con los AdjustmentElementsVO existentes, una lista vacia en caso que no existan AdjustmentElementsVO en el sistema
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<AdjustmentElementsVO> getAllAdjustmentElementss() throws BusinessException;

	/**
	 * Metodo: Método encargado de relizar el ajuste de salida de un elementos serializado
	 * Crea el detalle del elemento y realiza el movimiento del inventario teniendo en cuenta si requiere o no autorización
	 * @param adjustmentVO ajuste el cual retira los elemnetos
	 * @param adjustmentElement elemento involucrado en el ajuste
	 * @param user usuario que realiza el ajuste
	 * @param warehouseAdjusTransit Bodega de transito para cuando requieren autorizacion
	 * @param dtoGenerics
	 * @throws BusinessException
	 */
	public void adjustmentOutputElementSerialized(AdjustmentVO adjustmentVO, AdjustmentElementDTO adjustmentElement, User user
			, Warehouse warehouseAdjusTransit,MovementElementDTO dtoGenerics) throws BusinessException;
	
	/**
	 * realiza la entrada de un elemento serializado en el inventario de HSP+
	 * @param adjustmentVO ajuste que realizara el ingreso al inventario
	 * @param adjustmentElement elemento involucrado en el ajuste
	 * @param user usuario que realiza el ajuste de inventario
	 * @param warehouseAdjusTransit bodega de transito donde quedan los elementos en caso de necesitar autorizacion
	 * @param dtoGenerics
	 * @throws BusinessException
	 */
	public void createInputElementSerialized(AdjustmentVO adjustmentVO, AdjustmentElementDTO adjustmentElement, User user
			, Warehouse warehouseAdjusTransit,MovementElementDTO dtoGenerics) throws BusinessException;

	/**
	 * Metodo: Metodo encargado de realizar el ajuste de traslado de elementos serializados.
	 * Este método realiza el ajuste de un solo elemento 
	 * @param adjustmentVO ajuste de traslado que se quiere realizar
	 * @param adjustmentElement elemento involucrado en el ajuste de traslado de inventario
	 * @param user usuario que realiza el ajuste
	 * @param warehouseAdjusTransit bodega de transito para los casos que necesitan aprovacion
	 * @param dtoGenerics
	 * @throws BusinessException
	 * @author waguilera
	 */
	public void adjustmentTransferElementSerialized(AdjustmentVO adjustmentVO, AdjustmentElementDTO adjustmentElement, User user
			, Warehouse warehouseAdjusTransit,MovementElementDTO dtoGenerics) throws BusinessException;

	/**
	 * 
	 * @param detailRegister detalle del elemento movido
	 * @param adjustmentVO ajuste que describe el movimiento entre bodegas de los elementos
	 * @param elementType tipo de elemento no serializado involucrado en el ajuste
	 * @param user usuario que realiza el ajuste
	 * @param warehouseAdjusTransit bodega de transito para los casos que necesitan aprovacion
	 * @param dtoGenerics
	 * @throws BusinessException
	 */
	public void executeAdjustmentInputNotSerialized(AdjustmentElementsVO detailRegister, AdjustmentVO adjustmentVO, ElementType elementType, User user,Warehouse warehouseAdjusTransit,MovementElementDTO dtoGenerics) throws BusinessException;

	/**
	 * Metodo encargado de realizar un ajuste de salida de elementos no serializados
	 * @param detailRegister elementos involucrados en el ajuste
	 * @param adjustmentVO ajuste el cual retira los elemnetos
	 * @param user usuario que realiza el ajuste
	 * @param warehouseAdjusTransit Bodega de transito para cuando requieren autorizacion
	 * @param dtoGenerics
	 * @throws BusinessException
	 */
	public void executeAdjustmentOutputNotSerialized(AdjustmentElementsVO detailRegister, AdjustmentVO adjustmentVO, User user,Warehouse warehouseAdjusTransit,MovementElementDTO dtoGenerics) throws BusinessException;
	
	/**
	 * Metodo encargado de realizar un ajuste de traslado entre bodegas de elementos no serializados
	 * @param detailRegister elementos involucrados en el ajuste
	 * @param adjustmentVO ajuste el cual retira los elemnetos
	 * @param user usuario que realiza el ajuste
	 * @param warehouseAdjusTransit Bodega de transito para cuando requieren autorizacion
	 * @param dtoGenerics
	 * @throws BusinessException
	 */
	public void executeAdjustmentTransferNotSerialized(AdjustmentElementsVO detailRegister, AdjustmentVO adjustmentVO, User user,Warehouse warehouseAdjusTransit,MovementElementDTO dtoGenerics) throws BusinessException;

	void adjustmentTransferElementSerializedMassive(AdjustmentVO adjustmentVO, AdjustmentElementDTO adjustmentElement, User user, Warehouse warehouseAdjusTransit, MovementElementDTO dtoGenerics,
			LoadMassiveSerializedAdjusmentData dataAux,AdjustmentElementsStatus adjustmentElementsStatus) throws BusinessException;
	
}
