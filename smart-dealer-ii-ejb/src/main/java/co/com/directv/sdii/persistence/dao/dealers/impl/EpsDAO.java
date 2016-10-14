package co.com.directv.sdii.persistence.dao.dealers.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.Eps;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.dealers.EpsDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad de Eps
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.Eps
 * @see co.com.directv.sdii.model.hbm.Eps.hbm.xml
 */
@Stateless(name="EpsDAOLocal",mappedName="ejb/EpsDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class EpsDAO extends BaseDao implements EpsDAOLocal {

	private final static Logger log = UtilsBusiness.getLog4J(EpsDAO.class);

    /**
     * Metodo: <Descripcion>
     * @param id
     * @return Eps
     * @throws DAOServiceException
     * @throws DAOSQLException <tipo> <descripcion>
     * @author jalopez
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Eps getEpsByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getEpsByID/EpsDAO ==");
        
        try {
        	Session session = this.getSession();
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("select eps from ");
        	stringQuery.append(Eps.class.getName());
        	stringQuery.append(" eps where eps.id = :id");
        	Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("select eps from "+Eps.class.getName()+" eps where eps.id = :id");
            query.setLong("id", id);
            return (Eps)query.uniqueResult();
        } catch (Throwable ex) {
            log.debug("== Error en getEpsByID ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getEpsByID/EpsDAO ==");
        }
    }

    /**
     * Metodo: <Descripcion>
     * @param code
     * @return Eps
     * @throws DAOServiceException
     * @throws DAOSQLException <tipo> <descripcion>
     * @author jalopez
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Eps getEpsByCode(String code) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getEpsByCode/EpsDAO ==");
        
        try {
        	Session session = this.getSession();
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Eps.class.getName());
        	stringQuery.append(" eps where eps.epsCode = :epsCode");
        	Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("from "+Eps.class.getName()+" eps where eps.epsCode = :epsCode");
            query.setString("epsCode", code);
            return (Eps)query.uniqueResult();
        } catch (Throwable ex) {
            log.debug("== Error en getEpsByCode ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getEpsByCode/EpsDAO ==");
        }
    }

    /**
     * Metodo: Consultar todos los Eps
     * @return List<Eps>
     * @throws DAOServiceException
     * @throws DAOSQLException <tipo> <descripcion>
     * @author jalopez
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Eps> getAllEps() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllEps/EpsDAO ==");
        
        try {
        	Session session = this.getSession();
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Eps.class.getName());
        	stringQuery.append(" eps");
        	return session.createQuery(stringQuery.toString()).list();
           //return session.createQuery("from "+Eps.class.getName()+" eps").list();
        } catch (Throwable ex) {
            log.debug("== Error en getAllEps ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllEps/EpsDAO ==");
        }
    }
    
    /*
     * (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.dealers.EpsDAOLocal#getAllEpsByCountryId(java.lang.Long)
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<Eps> getAllEpsByCountryId(Long countryId) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllEpsByCountryId/EpsDAO ==");
        
        if(countryId == null || countryId <= 0L){
        	return getAllEps();
        }        
        
        try {
        	Session session = this.getSession();
           List<Eps> result = null;
           StringBuffer stringQuery =  new StringBuffer();
           stringQuery.append("from ");
           stringQuery.append(Eps.class.getName());
           stringQuery.append(" eps where eps.country.id = :aCountryId");
           Query query = session.createQuery(stringQuery.toString());
           //Query query = session.createQuery("from "+Eps.class.getName()+" eps where eps.country.id = :aCountryId");
           query.setLong("aCountryId", countryId);
           result = query.list();
           return result;
        } catch (Throwable ex) {
            log.debug("== Error en getAllEpsByCountryId ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllEpsByCountryId/EpsDAO ==");
        }
    }
}
