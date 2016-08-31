/**
 * Creado 8/07/2010 11:16:16
 */
package co.com.ig.common.error.persistence.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import co.com.directv.sdii.exceptions.DAOServiceException;

/**
 * Clase base dao  
 * 
 * Fecha de Creación: 8/07/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
public class BaseDao {
	
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	/**
	 * Metodo: realiza el flush sobre la sesión administrada por el sistema
	 * @param session sessión sobre la que se realizará el flush
	 * @author jjimenezh
	 */
	public void doFlush(Session session){
		session.flush();
	}
	
	/**
	 * Metodo: Obtiene una sesión con la persistencia y se asegura que no se retorne nulo
	 * @return Sesión de persistencia
	 * @throws DAOServiceException en caso de error al tratar de obtener la sesión o que se obtenga nula
	 * @author jjimenezh
	 */
	public Session getSession(){
		Session session = sessionFactory.getCurrentSession();
		if (session == null) {
            throw new IllegalStateException("No se pudo obtener la sessión de hibernate");
        }
		return session;
	}
}
