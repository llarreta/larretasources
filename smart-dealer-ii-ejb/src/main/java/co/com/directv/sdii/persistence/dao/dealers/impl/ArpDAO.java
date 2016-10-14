package co.com.directv.sdii.persistence.dao.dealers.impl;

import java.util.List;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.Arp;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.dealers.ArpDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad de Arp
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.Arp
 * @see co.com.directv.sdii.model.hbm.Arp.hbm.xml
 */
@Stateless(name="ArpDAOLocal",mappedName="ejb/ArpDAOLocal")
public class ArpDAO extends BaseDao implements ArpDAOLocal {

	private final static Logger log = UtilsBusiness.getLog4J(ArpDAO.class);

    /**
     * Metodo: Consultar Arp por ID
     * @param id - Long
     * @return Arp
     * @throws DAOServiceException
     * @throws DAOSQLException 
     */
    public Arp getArpByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getArpByID/ArpDAO ==");
        Session session = getSession();

        try {
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Arp.class.getName());
        	stringQuery.append(" arp where arp.id = :id");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("id", id.toString());
            
            Object obj = query.uniqueResult();
            return (Arp)obj;
            
        } catch (Throwable ex) {
            log.debug("== Error consultando el Arp por ID ==");
            throw manageException(ex);
        } finally {
            log.debug("== Termina getArpByID/ArpDAO ==");
        }
    }

    /**
     * Metodo: Consultar Arp por Codigo
     * @param code - String
     * @return Arp
     * @throws DAOServiceException
     * @throws DAOSQLException 
     */
    public Arp getArpByCode(String code) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getArpByCode/ArpDAO ==");
        Session session = getSession();
        try {
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Arp.class.getName());
        	stringQuery.append(" arp where arp.arpCode = :code");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("code", code);
            Object obj = query.uniqueResult();
            
            return (Arp)obj;
        } catch (Throwable ex) {
            log.debug("== Error consultando el Arp por Code ==");
            throw manageException(ex);
        } finally {
            log.debug("== Termina getArpByCode/ArpDAO ==");
        }
    }

    /**
     * Metodo: Consultar todos los Arp
     * @return List<Arp>
     * @throws DAOServiceException
     * @throws DAOSQLException 
     */
    @SuppressWarnings("unchecked")
	public List<Arp> getAllArp() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllArp/ArpDAO ==");
        Session session = getSession();

        try {
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Arp.class.getName());
            return session.createQuery("from "+Arp.class.getName()).list();
            
        } catch (Throwable ex) {
            log.debug("== Error consultando todos los Arp ==");
            throw manageException(ex);
        } finally {
            log.debug("== Termina getAllArp/ArpDAO ==");
        }
    }
    
    /*
     * (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.dealers.ArpDAOLocal#getAllArpByCountryId(java.lang.Long)
     */
    public List<Arp> getAllArpByCountryId(Long countryId) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllArpByCountryId/ArpDAO ==");
        
        if(countryId == null || countryId <= 0L){
        	return getAllArp();
        }
        Session session = getSession();

        try {
            
            StringBuffer sb = new StringBuffer("from ");
            sb.append(Arp.class.getName());
        	sb.append(" arp where arp.country.id = :aCountryId");
        	
        	Query query = session.createQuery(sb.toString());
        	query.setLong("aCountryId", countryId);
        
        	List<Arp> result = query.list();
            return result;
            
        } catch (Throwable ex) {
            log.debug("== Error consultando todos los Arp getAllArpByCountryId==");
            throw manageException(ex);
        } finally {
            log.debug("== Termina getAllArpByCountryId/ArpDAO ==");
        }
    }
}
