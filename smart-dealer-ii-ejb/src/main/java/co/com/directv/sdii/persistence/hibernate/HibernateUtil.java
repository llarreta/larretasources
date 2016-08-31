package co.com.directv.sdii.persistence.hibernate;

import javax.ejb.EJB;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Interceptor;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

import co.com.directv.sdii.audit.AuditLogInterceptor;
import co.com.directv.sdii.common.util.Constantes;
import co.com.directv.sdii.common.util.PropertiesReader;
import co.com.directv.sdii.ejb.business.assign.BuildingBusinessBeanLocal;

/**
 *   
 * Esta clase implementa los métodos utilitarios para el manejo
 * de las sesiones de Hibernate.
 *
 * @author Jimmy Vélez Muñoz
 */
public final class HibernateUtil {

	private static HibernateUtil instance;
	private static SessionFactory sessionFactory = null;
	private static Configuration configuration;
	private static Interceptor interceptor;
	private final static Logger log = Logger.getLogger(HibernateUtil.class);

	private HibernateUtil(){
	}
	
	@EJB(name="BuildingBusinessBeanLocal", beanInterface=BuildingBusinessBeanLocal.class)
    private BuildingBusinessBeanLocal buildingBusiness;
	
	public static HibernateUtil getInstance(){
		if (instance == null)
			instance = new HibernateUtil();
		return instance;
	}

	public SessionFactory getSessionFactory() {		
		try {
            if (sessionFactory == null){
            	//configuration = new Configuration();
            	configuration = new AnnotationConfiguration();//Configuration();
            	interceptor = new AuditLogInterceptor();
            	configuration.setInterceptor(interceptor);     
                sessionFactory = configuration.configure("properties/hibernate.cfg.xml").setProperty("hibernate.connection.datasource", PropertiesReader.getInstance().getAppKey(Constantes.JNDI_DATA_SOURCE_NAME)).buildSessionFactory();
            	((AuditLogInterceptor)interceptor).setSessionFactory(sessionFactory);
            	if(sessionFactory==null){
            		throw new HibernateException("Error cargando configuracion de hibernate");
            	}
            }
		} catch (Throwable ex) {
			log.fatal("Error cargando configuracion de hibernate");
			throw new RuntimeException(ex);
		}
		return sessionFactory;
	}
	
}
