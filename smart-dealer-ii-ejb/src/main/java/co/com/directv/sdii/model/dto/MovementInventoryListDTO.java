package co.com.directv.sdii.model.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Fecha de Creación: 30/11/2012
 * @author aharker <a href="mailto:aharker@intergrupo.com">e-mail</a>
 * @version 1.0
 * @see
 */
public class MovementInventoryListDTO implements Serializable {

	/**
	 * atributo encargado de traer el id de la work order de los movimientos que se realizaran de inventario
	 * @author aharker
	 */
	private Long woId;
	
	/**
	 * atributo encargado de traer el id de usuario de los movimientos que se realizaran de inventario
	 * @author aharker
	 */
	private Long userId;
	
	/**
	 * atributo enhcargado de tener el id del cliente al cual se le realizara el movimiento de inventario
	 * @author aharker
	 */
	private Long customerId;
	
	/**
	 * atributo encargado de tener todos los movimientos de inventario para una work order de un cliente
	 * @author aharker
	 */
	private List<MovementInventoryDTO> movementInventoryDTOList;
	
	/**
	 * atributo encargado de tener la informacion de vinculacion de elementros traida de vista360 para un cliente.
	 * @author aharker
	 */
	private List<CustomerProductDTO> customerProductDTO;

	public Long getWoId() {
		return woId;
	}

	public void setWoId(Long woId) {
		this.woId = woId;
	}

	public List<CustomerProductDTO> getCustomerProductDTO() {
		return customerProductDTO;
	}

	public void setCustomerProductDTO(List<CustomerProductDTO> customerProductDTO) {
		this.customerProductDTO = customerProductDTO;
	}

	public List<MovementInventoryDTO> getMovementInventoryDTOList() {
		return movementInventoryDTOList;
	}

	public void setMovementInventoryDTOList(
			List<MovementInventoryDTO> movementInventoryDTOList) {
		this.movementInventoryDTOList = movementInventoryDTOList;
	}

	public MovementInventoryListDTO(
			List<MovementInventoryDTO> movementInventoryDTOList) {
		super();
		this.movementInventoryDTOList = movementInventoryDTOList;
	}

	public MovementInventoryListDTO() {
		super();
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public MovementInventoryListDTO(Long userId, Long customerId,
			List<MovementInventoryDTO> movementInventoryDTOList) {
		super();
		this.userId = userId;
		this.customerId = customerId;
		this.movementInventoryDTOList = movementInventoryDTOList;
	}

	public MovementInventoryListDTO( MovementInventoryListDTO copy ) {
		super();
		this.userId = copy.userId;
		this.customerId = copy.customerId;
		this.movementInventoryDTOList = copy.movementInventoryDTOList;
	}
	
	
}
