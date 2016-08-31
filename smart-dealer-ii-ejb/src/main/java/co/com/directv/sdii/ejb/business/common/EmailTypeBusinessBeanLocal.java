package co.com.directv.sdii.ejb.business.common;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.common.enumerations.EmailTypesEnum;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.SendEmailDTO;
import co.com.directv.sdii.model.pojo.Dealer;
import co.com.directv.sdii.model.pojo.User;
import co.com.directv.sdii.model.vo.ElementVO;
import co.com.directv.sdii.model.vo.EmailTypeVO;
import co.com.directv.sdii.model.vo.UserVO;
import co.com.directv.sdii.model.vo.WarehouseVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD de la Entidad EmailType.
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface EmailTypeBusinessBeanLocal {

	/**
	 * Metodo:  Persiste la información de un objeto EmailTypeVO
	 * @param obj objeto que encapsula la información de un EmailTypeVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author jjimenezh
	 */
	public void createEmailType(EmailTypeVO obj) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un EmailTypeVO
	 * @param obj objeto que encapsula la información de un EmailTypeVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author jjimenezh
	 */
	public void updateEmailType(EmailTypeVO obj) throws BusinessException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un EmailTypeVO
	 * @param obj información del EmailTypeVO a ser borrado
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author jjimenezh
	 */
	public void deleteEmailType(EmailTypeVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene la información de un EmailTypeVO por su identificador
	 * @param id identificador del EmailTypeVO a ser consultado
	 * @return objeto con la información del EmailTypeVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author jjimenezh
	 */
	public EmailTypeVO getEmailTypeByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los EmailTypeVO almacenados en la persistencia
	 * @return Lista con los EmailTypeVO existentes, una lista vacia en caso que no existan EmailTypeVO en el sistema
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author jjimenezh
	 */
	public List<EmailTypeVO> getAllEmailTypes() throws BusinessException;

	/**
	 * Metodo: Obtiene la información de todos los EmailTypeVO almacenados en la persistencia
	 * @return Lista con los EmailTypeVO existentes, una lista vacia en caso que no existan EmailTypeVO en el sistema
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
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
	
	
	/**
	 * Metodo: Permite enviar un correo electronico de acuerdo al código enviado de manera asincrona
	 * @param sendEmailDTO Informacion necesaria para enviar correo de notificacion de novedad
	 * @param recipient Direccion a donde se va a enviar el correo electronico
	 * @throws BusinessException En caso que no se encuentre el tipo de email o que no se pueda enviar el mail
	 * @author jnova
	 */
	public void sendEmailByEmailTypeCodeAsic(SendEmailDTO sendEmailDTO, Long countryId) throws BusinessException;
	
	/**
	 * Metodo: Permite enviar un correo electronico de acuerdo al código enviado de manera asincrona con el texto del mail formateado
	 * @param sendEmailDTO Informacion necesaria para enviar correo de notificacion de novedad
	 * @param countryId parametro apra obtener parametros de sistema para envio de correos
	 * @throws BusinessException En caso que no se encuentre el tipo de email o que no se pueda enviar el mail
	 * @author jnova
	 */
	public void sendEmailByEmailTypeCodeAsicFormated(SendEmailDTO sendEmailDTO, Long countryId) throws BusinessException;
	
	/**
	 * Metodo: Envia un correo electronico con un formato según una novedad.
	 * @param sendEmailDTO
	 * @param countryId
	 * @throws BusinessException
	 * @author hcorredor
	 */
	public void sendEmailByNewness(SendEmailDTO sendEmailDTO, Long countryId) throws BusinessException;

	/**
	 * Metodo: UC Inv 08, envia un correo segun el tipo de Novidad
	 * @param allElements
	 * @param users
	 * @param dealer
	 * @param ete
	 * @throws BusinessException
	 */
	public void sendMailByEmailType(List<ElementVO> allElements, List<User> users, Dealer dealer, EmailTypesEnum ete) throws BusinessException;
	
	/**
	 * Metodo: envia un correo segun el tipo de Novidad y con el usuario de el movimiento
	 * @param allElements
	 * @param users
	 * @param dealer
	 * @param ete
	 * @param user
	 * @throws BusinessException
	 */
	public void sendMailByEmailType(List<ElementVO> allElements, List<User> users, Dealer dealer, EmailTypesEnum ete,UserVO userChange,WarehouseVO warehouseSource) throws BusinessException;
	
	public void sendEmailByEmailTypeCodeSinc(SendEmailDTO sendEmailDTO, final Long countryId)
	throws BusinessException;
}
