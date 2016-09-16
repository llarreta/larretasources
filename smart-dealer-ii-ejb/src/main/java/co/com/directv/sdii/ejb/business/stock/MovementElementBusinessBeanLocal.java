package co.com.directv.sdii.ejb.business.stock;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.MovementElementDTO;
import co.com.directv.sdii.model.pojo.MovementType;
import co.com.directv.sdii.model.pojo.RecordStatus;

/**
 * 
 * Interfaz de las operaciones para la realización de movimientos de elementos entre bodegas
 * 
 * Fecha de Creación: 20/12/2011
 * @author waguilera <a href="mailto:waguilera@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface MovementElementBusinessBeanLocal {

	/**
	 * Operación encargada de ubicar elementos en a una ubicación
	 * Metodo: <Descripcion>
	 * @throws BusinessException <tipo> <descripcion>
	 * @author waguilera
	 */
	public void moveSerializedElementToWarehouse(MovementElementDTO dto) throws BusinessException;
	
	/**
	 * Operación encargada de ubicar elementos en a una ubicación
	 * Metodo: <Descripcion>
	 * @throws BusinessException <tipo> <descripcion>
	 * @author waguilera
	 */
	public void moveSerializedElementBetweenWarehouse(MovementElementDTO dto) throws BusinessException;
	
	/**
	 * Metodo: Operación encargada de sacar elementos del inventario dejando
	 * el correspondiente historial del motivo de salida del
	 * elemento del inventario 
	 * @throws BusinessException <tipo> <descripcion>
	 * @author waguilera
	 */
	public void deleteSerializedElementInWarehouse(MovementElementDTO dto) throws BusinessException;
	
	/**
	 * Operación encargada de ubicar elementos en a una ubicación
	 * Metodo: <Descripcion>
	 * @throws BusinessException <tipo> <descripcion>
	 * @author waguilera
	 */
	public void moveNotSerializedElementToWarehouse(MovementElementDTO dto) throws BusinessException;
	
	/**
	 * Operación encargada de ubicar elementos en a una ubicación
	 * Metodo: <Descripcion>
	 * @throws BusinessException <tipo> <descripcion>
	 * @author waguilera
	 */
	public void moveNotSerializedElementBetweenWarehouse(MovementElementDTO dto) throws BusinessException;
	
	/**
	 * Operación encargada de ubicar elementos en a una ubicación
	 * Metodo: <Descripcion>
	 * @throws BusinessException <tipo> <descripcion>
	 * @author waguilera
	 */
	public void deleteNotSerializedElementInWarehouse(MovementElementDTO dto) throws BusinessException;
	
	
	/**
	 * Operación encargada de llenar los datos basicos del dto MovementElementDTO para procesos con muchos registros
	 * Metodo: <Descripcion>
	 * @throws BusinessException <tipo> <descripcion>
	 * @author waguilera
	 */
	public MovementElementDTO fillMovementTypeAndRecordStatus(String movementTypeCodeE, String movementTypeCodeS)
		throws BusinessException;
	
	public MovementElementDTO fillMovementTypeAndRecordStatusMassive(String movementTypeCodeE, String movementTypeCodeS)
			throws BusinessException;
	
	public MovementElementDTO fillMovementTypeAndRecordStatusMassive(MovementType movementTypeE, MovementType movementTypeS, RecordStatus recordStatusU, RecordStatus recordStatusH)
			throws BusinessException;
	
	public MovementElementDTO fillMovementTypeAndRecordStatusMassive(String movementTypeCodeE, String movementTypeCodeS,RecordStatus[] recordStatusU,RecordStatus[] recordStatusH)
			throws BusinessException;

	public void fillData(String movTypeCode, String movTypeCode2, MovementType movementTypeE, MovementType movementTypeS, RecordStatus recordStatusU, RecordStatus recordStatusH)throws BusinessException;
}
