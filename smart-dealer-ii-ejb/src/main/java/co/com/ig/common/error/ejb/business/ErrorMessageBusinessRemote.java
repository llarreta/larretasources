package co.com.ig.common.error.ejb.business;

import javax.ejb.Remote;



/**
 * Define la interface remota para poder obtener el bean mediante JNDI
 * desde cualquier lugar de la aplicación
 * 
 * Fecha de Creación: 7/07/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.error.ejb.business.ErrorMessageBusinessLocal
 */
@Remote
public interface ErrorMessageBusinessRemote extends ErrorMessageBusinessLocal {

}
