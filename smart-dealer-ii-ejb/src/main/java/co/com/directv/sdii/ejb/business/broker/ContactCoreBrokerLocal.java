package co.com.directv.sdii.ejb.business.broker;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.ContactVO;

/**
 * 
 * Provee la definición de la operacion de Creacion de Contact que ofrece el servicio
 * de Core en un sistema externo, se especifica para reducir el acoplamiento
 * con componentes generados desde definiciones de servicios web de terceros
 *  
 * 
 * Fecha de Creación: 18/11/2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public interface ContactCoreBrokerLocal {

	/**
	 * 
	 * Metodo: Control Cambios Generacion de Contacts.
	 * Realiza la invocacion del Servicio de AddCustomerInquiry en IBS
	 * para la Creacion del Contact.
	 * @param contactVO - Contiene ls informacion que se envia a IBS
	 * @param countryCode String - Pais al cual pertenece la WO.
	 * @return String - Retorna el Codigo del Contact creado en IBS.
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	public String createContactCoreIBS(ContactVO contactVO, String countryCode) throws BusinessException; 
}
