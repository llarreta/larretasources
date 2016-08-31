package co.com.directv.sdii.facade.core;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.dto.ContactDTO;
import co.com.directv.sdii.model.vo.ContactVO;

/**
 * 
 * Interfaz de la fachada con las operaciones Tipo CRUD de la Entidad Contact.
 * 
 * Fecha de Creación: Nov 3, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ContactFacadeBeanLocal {

	/**
	 * Metodo:  Control Cambios Generacion de Contacts.
	 * Permite crear en el sistema un objeto Contact
	 * @param obj - ContactVO  objeto que encapsula la información de un ContactVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createContact(ContactVO obj) throws BusinessException;
	
	/**
	 * Metodo:  
	 * Crea un intento de contacto.
	 * @param obj - ContactDTO  objeto que encapsula la informacion necesara para crear un contact.
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createTryContact(ContactDTO contactDTO) throws BusinessException;
	
	/**
	 * Metodo: Control Cambios Generacion de Contacts.
	 * Permite actualizar en el sistema la información de un Contact
	 * @param obj - ContactVO  objeto que encapsula la información de un ContactVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateContact(ContactVO obj) throws BusinessException;
	
	/**
	 * Metodo: Control Cambios Generacion de Contacts.
	 * Permite borra la información de un Contact
	 * @param obj - ContactVO  información del ContactVO a ser borrado
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void deleteContact(ContactVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Control Cambios Generacion de Contacts.
	 * Permite consultar la información de un Contact por su identificador
	 * @param id - Long identificador del Contact a ser consultado
	 * @return objeto con la información del ContactVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public ContactVO getContactByID(Long id) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Control Cambios Generacion de Contacts.
	 * Retorna los Contacts generados por el cambio de estdo
	 * de la WorkOrder.
	 * @param contactDTO ContactDTO - dealerId Long(Id del dealer), woCode String(Codigo de la WO)
	 * @return List<ContactVO> - Retorna el Listado de Contacts
	 * @throws BusinessException
	 * @author jalopez
	 */
	public List<ContactVO> getContactsByWorkOrder(ContactDTO contactDTO) throws BusinessException;
	
	/**
	 * Metodo: Retorna los Contacts generados por el cambio de estado de la WorkOrder.
	 * @param woCode String(Codigo de la WO)
	 * @return List<ContactVO> - Retorna el Listado de Contacts
	 * @throws BusinessException
	 * @author ssanabri
	 */	
	public List<ContactVO> getContactsTriesByWorkOrder(String woCode) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Control Cambios Generacion de Contacts.
	 * Realiza la creacion del Contact en SDII, realiza
	 * las consultas respectivas para obtener los
	 * datos necesarios para su creacion
	 * @param contactDTO ContactDTO workOrderId Long - id de la WO, 
	 * woStatusHistoryId Long - id de la woStatusHistory
	 * @throws BusinessException
	 * @author jalopez
	 */
	public void createContactCore( ContactDTO contactDTO ) throws BusinessException;
	
}
