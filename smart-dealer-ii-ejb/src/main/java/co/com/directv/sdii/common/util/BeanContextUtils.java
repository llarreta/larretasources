package co.com.directv.sdii.common.util;

import javax.annotation.Resource;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

/**
 * Ofrece la utilidad de obtener beans del contexto a partir de sus nombres
 * 
 * Fecha de Creación: 10/06/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
public class BeanContextUtils {

	private static BeanContextUtils mySelf = new BeanContextUtils();
	
	@Resource
	private InitialContext context;
	
	private final static Logger log = UtilsBusiness.getLog4J(BeanContextUtils.class);
	
	public static BeanContextUtils getInstance(){
		return mySelf;
	}
	
	/**
	 * Metodo: Obtiene una referencia a un bean del contexto dado el tipo de interfaz que requiere
	 * @param <T> Interface que define las operaciones del bean que desea ser obtenido
	 * @param beanInterface Interface que define las operaciones del bean que desea ser obtenido por lo general la que tiene la anotación <code>@javax.ejb.Remote</code>
	 * @param ejbJndiName nombre del Jndi para obtener el bean, por lo general: <code>ejb/NombreBean</code>
	 * @return Referencia del EJB objeto que implementa la interface definida
	 * @author jjimenezh
	 */
	@SuppressWarnings("unchecked")
	public <T> T lookupEjb(Class<T> beanInterface, String ejbJndiName){
		T beanReference = null;
		
		String realEjbJndiName = ejbJndiName+"#"+beanInterface.getName();
		
		try{
			context = new InitialContext();
			beanReference = (T)context.lookup(realEjbJndiName);
		} catch (NamingException e) {
			e.printStackTrace();
		} finally {
            log.debug("== Termina lookupEjb/BeanContextUtils ==");
        }
		
		return beanReference;
	}
	
}
