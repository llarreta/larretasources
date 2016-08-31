package co.com.directv.sdii.persistence.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;

/**
 *  
 * Esta clase implementa una fábrica de conexiones a las diferentes fuentes de
 * datos.
 *
 * @author Jimmy Vélez Muñoz
 * @author Joan Lopez
 */
public final class ConnectionFactory {
	private final static HibernateUtil hu;
	
	static {
		hu = HibernateUtil.getInstance();
	}

    private ConnectionFactory(){

    }
        
	public static Session getSession(){
		return hu.getSessionFactory().getCurrentSession();
	}
	
	public static SessionFactory getSessionFactory(){
		return hu.getSessionFactory();
	}
	
	/**
	 * Metodo:Se usa en el caso de los procesos batch, 
	 * por ejemplo cuando se tiene que hacer inserts o updates masivos
	 * @return StatelessSession
	 * @author jalopez
	 */
	public static StatelessSession getStatelessSession(){
		return hu.getSessionFactory().openStatelessSession();
	}
}
