package co.com.directv.sdii.ejb.business.core;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.dto.IbsContactDTO;
import co.com.directv.sdii.model.vo.IbsContactReasonVO;
import co.com.directv.sdii.model.vo.IbsContactVO;

/**
 * Interfaz de las operaciones Tipo CRUD de la Entidad IbsContact.
 * 
 * Fecha de Creación: 30/11/2012
 * @author cduarte <a href="mailto:cduarte@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
@Local
public interface IbsContactBusinessBeanLocal {

	/**
	 * Metodo:  Persiste la información de un objeto IbsContact
	 * @param obj objeto que encapsula la información de un IbsContact
	 * @throws BusinessException en caso de error al ejecutar la creación de IbsContact
	 * @author cduarte
	 */
	public void createIbsContact(IbsContactVO obj) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un IbsContactVO
	 * @param obj objeto que encapsula la información de un IbsContactVO
	 * @throws BusinessException en caso de error al ejecutar la actualización de IbsContact
	 * @author cduarte
	 */
	public void updateIbsContact(IbsContactVO obj) throws BusinessException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un IbsContactVO
	 * @param obj información del IbsContactVO a ser borrado
	 * @throws BusinessException en caso de error al ejecutar la eliminación de IbsContact
	 * @author cduarte
	 */
	public void deleteIbsContact(IbsContactVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene la información de un IbsContactVO por su identificador
	 * @param id identificador del IbsContactVO a ser consultado
	 * @return objeto con la información del AdjustmentModificationVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la consulta de IbsContact por ID
	 * @author cduarte
	 */
	public IbsContactVO getIbsContactByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de un IbsContactReason por su codigo
	 * @param id identificador del IbsContactReason a ser consultado
	 * @return objeto con la información del IbsContactReason dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public IbsContactReasonVO getIbsContactReasonByCode(String code,Long countryId) throws BusinessException;
	
	/**
	 * Metodo: Se consulta un IbsContact por codigo del ibscontact y el pais
	 * @param ibsContactCode
	 * @param countryId
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author
	 */
	public IbsContactVO getIbsContactByIbsContactCode(String ibsContactCode,Long countryId) throws BusinessException;
	
	/**
	 * Metodo: si exite el objeto IbsContact persistido se actualiza sino se crea
	 * @param obj <tipo> <descripcion>
	 * @author
	 */
	public void mergeIbsContact(IbsContactVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permtie obtener los contacts de ibs de una work order
	 * @param workOrderId
	 * @param countryId
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	public List<IbsContactDTO> getIbsContactDTOByWorkOrderId(Long workOrderId,Long countryId) throws BusinessException;
	

}
