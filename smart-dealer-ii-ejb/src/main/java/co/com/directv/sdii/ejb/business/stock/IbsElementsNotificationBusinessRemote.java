package co.com.directv.sdii.ejb.business.stock;

import javax.ejb.Remote;

/**
 * 
 * Interfaz remota a ser invocada por el job que realiza la lectura de la cola
 * de elementos que tienen que ser enviados a IBS para actualizar los estados 
 * 
 * Fecha de Creación: Oct 21, 2011
 * @author jnova <a href="mailto:jnova@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Remote
public interface IbsElementsNotificationBusinessRemote extends IbsElementsNotificationBusinessLocal{

}
