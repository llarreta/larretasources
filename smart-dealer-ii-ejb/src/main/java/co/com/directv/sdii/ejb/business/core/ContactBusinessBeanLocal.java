package co.com.directv.sdii.ejb.business.core;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.dto.ContactDTO;
import co.com.directv.sdii.model.vo.ContactVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD de la Entidad Contact.
 * 
 * Fecha de Creación: Nov 3, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ContactBusinessBeanLocal {

	/**
	 * Metodo:  Control Cambios Generacion de Contacts.
	 * Persiste la información de un objeto ContactVO
	 * @param obj objeto que encapsula la información de un ContactVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author jalopez
	 */
	public void createContact(ContactVO obj) throws BusinessException;

	/**
	 * Informa a IBS por medio de un WS externo (broker).
	 * Persiste la información en los contacts de HSP de una manera de identificar que son los intentos de contacto generados.
	 * 
	 * @param obj objeto que encapsula la información de un ContactVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author ssanabri
	 */
	public void createTryContact(ContactDTO contactDTO) throws BusinessException;
	
	/**
	 * Metodo: Control Cambios Generacion de Contacts.
	 * actualiza la información de un ContactVO
	 * @param obj objeto que encapsula la información de un ContactVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author jalopez
	 */
	public void updateContact(ContactVO obj) throws BusinessException;
	
	/**
	 * Metodo: Control Cambios Generacion de Contacts.
	 * Borra de la persistencia la información de un ContactVO
	 * @param obj información del ContactVO a ser borrado
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author jalopez
	 */
	public void deleteContact(ContactVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Control Cambios Generacion de Contacts.
	 * Obtiene la información de un ContactVO por su identificador
	 * @param id identificador del ContactVO a ser consultado
	 * @return objeto con la información del ContactVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author jalopez
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
	public List<ContactVO> getContactsWorkOrderByDealer(ContactDTO contactDTO) throws BusinessException;
	
	/**
	 * Metodo: 
	 * Retorna los intentos de contactos realizados para una WO ordenados por fecha descendentemente.
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
