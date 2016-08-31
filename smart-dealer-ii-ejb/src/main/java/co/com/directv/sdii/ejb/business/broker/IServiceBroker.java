/**
 * Creado 16/12/2010 10:08:09
 */
package co.com.directv.sdii.ejb.business.broker;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;

/**
 * Define los comportamientos comunes de los broker de servicios 
 * 
 * Fecha de Creación: 16/12/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.exceptions.BusinessException
 */
@Local
public interface IServiceBroker {

	/**
	 * Metodo: Administra la excepción producida por la invocación de un servicio WEB
	 * de un sistema externa
	 * @param e excepción lanzada por la invocación del servicio WEB
	 * @return Excepción administrada por la aplicación con la información arrojara por el
	 * sistema externo
	 * @author jjimenezh
	 */
	public BusinessException manageServiceException(Throwable e);
	
}
