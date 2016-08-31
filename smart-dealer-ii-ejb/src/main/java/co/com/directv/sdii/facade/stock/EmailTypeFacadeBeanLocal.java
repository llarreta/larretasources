package co.com.directv.sdii.facade.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.SendEmailDTO;
import co.com.directv.sdii.model.vo.EmailTypeVO;

/**
 * 
 * Interfaz de la fachada con las operaciones Tipo CRUD de la Entidad EmailType.
 * 
 * Fecha de Creación: jul 12, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface EmailTypeFacadeBeanLocal {

	/**
	 * Metodo:  Permite crear en el sistema un objeto EmailType
	 * @param obj - EmailTypeVO  objeto que encapsula la información de un EmailTypeVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author jjimenezh
	 */
	public void createEmailType(EmailTypeVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite actualizar en el sistema la información de un EmailType
	 * @param obj - EmailTypeVO  objeto que encapsula la información de un EmailTypeVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author jjimenezh
	 */
	public void updateEmailType(EmailTypeVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite borra la información de un EmailType
	 * @param obj - EmailTypeVO  información del EmailTypeVO a ser borrado
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author jjimenezh
	 */
	public void deleteEmailType(EmailTypeVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Permite consultar la información de un EmailType por su identificador
	 * @param id - Long identificador del EmailType a ser consultado
	 * @return objeto con la información del EmailTypeVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author jjimenezh
	 */
	public EmailTypeVO getEmailTypeByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar la información de todos los EmailType almacenados en la persistencia
	 * @return List<EmailTypeVO> Lista con los EmailTypeVO existentes, una lista vacia en caso que no existan EmailTypeVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author jjimenezh
	 */
	public List<EmailTypeVO> getAllEmailTypes() throws BusinessException;
	
	/**
	 * Metodo: Permite consultar la información de todos los EmailType almacenados en la persistencia
	 * @return List<EmailTypeVO> Lista con los EmailTypeVO existentes, una lista vacia en caso que no existan EmailTypeVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author jjimenezh
	 */
	public List<EmailTypeVO> getActiveEmailTypes() throws BusinessException;

	/**
	 * Metodo: obtiene un tipo de correo dado el código
	 * @param code código del tipo de correo
	 * @return tipo de correo con el código especificado, nulo en caso que no se encuentre el tipo 
	 * de correo con el código
	 * @throws BusinessException En caso de error al ejecutar la operación
	 * @author jjimenezh
	 */
	public EmailTypeVO getEmailTypeByCode(String code)throws BusinessException;
	
	/**
	 * Metodo: Permite enviar un correo electronico de acuerdo al código enviado
	 * @param sendEmailDTO Informacion necesaria para enviar correo de notificacion de novedad
	 * @param recipient Direccion a donde se va a enviar el correo electronico
	 * @throws BusinessException En caso que no se encuentre el tipo de email o que no se pueda enviar el mail
	 * @author jnova
	 */
	public void sendEmailByEmailTypeCode(SendEmailDTO sendEmailDTO, Long countryId) throws BusinessException;

}
