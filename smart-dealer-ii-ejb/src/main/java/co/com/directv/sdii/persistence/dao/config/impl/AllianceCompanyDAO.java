package co.com.directv.sdii.persistence.dao.config.impl;

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
import co.com.directv.sdii.model.pojo.AllianceCompany;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.config.AllianceCompanyDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad de AllianceCompany
 * 
 * Fecha de Creacion: Mar 23, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.AllianceCompany
 * @see co.com.directv.sdii.persistence.dao.config.AllianceCompanyDAOLocal
 */
@Stateless(name="AllianceCompanyDAOLocal",mappedName="ejb/AllianceCompanyDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class AllianceCompanyDAO extends BaseDao implements AllianceCompanyDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(AllianceCompanyDAO.class);
    
    /**
     * Crea una compa�ia en el sistema
     * @param obj - AllianceCompany
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createAllianceCompany(AllianceCompany obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createAllianceCompany/DAOAllianceCompanyBean ==");
        Session session = getSession();
        try {
            
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error creando el AllianceCompany ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createAllianceCompany/DAOAllianceCompanyBean ==");
        }
    }

    /**
     * Obtiene un alliancecompany con el id especificado
     * @param id - Long
     * @return - AllianceCompany
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public AllianceCompany getAllianceCompanyByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getAllianceCompanyByID/DAOAllianceCompanyBean ==");
        Session session = getSession();
        try {
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("select alliancecompany from AllianceCompany alliancecompany ");
        	stringQuery.append("where ");
        	stringQuery.append("alliancecompany.id = ");
        	stringQuery.append(id);
        	Object obj = session.createQuery(stringQuery.toString()).uniqueResult();
            //Object obj = session.createQuery("select alliancecompany from AllianceCompany alliancecompany where alliancecompany.id = " + id + " ").uniqueResult();
            if (obj != null) {
                return (AllianceCompany) obj;
            }
            return null;
        } catch (Throwable ex) {
            log.error("== Error consultando el AllianceCompany por ID ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllianceCompanyByID/DAOAllianceCompanyBean ==");
        }
    }

    /**
     * Actualiza un alliancecompany especificado
     * @param obj - AllianceCompany
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateAllianceCompany(AllianceCompany obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia updateAllianceCompany/DAOAllianceCompanyBean ==");
        Session session = getSession();

        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error actualizando el AllianceCompany ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateAllianceCompany/DAOAllianceCompanyBean ==");
        }

    }

    /**
     * Elimina un alliancecompany del sistema
     * @param obj - AllianceCompany
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteAllianceCompany(AllianceCompany obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia deleteAllianceCompany/DAOAllianceCompanyBean ==");
        Session session = getSession();

        try {
            session.delete(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error borrando el AllianceCompany ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteAllianceCompany/DAOAllianceCompanyBean ==");
        }

    }

    /**
     * Obtiene todos los alliancecompanys del sistema
     * @return - List<AllianceCompany>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<AllianceCompany> getAll() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAll/DAOAllianceCompanyBean ==");
        Session session = getSession();

        try {
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("from AllianceCompany ac ");
        	stringQuery.append("order by ac.companyName");
        	Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("from AllianceCompany ac order by ac.companyName");
            return query.list();
        } catch (Throwable ex) {
            log.error("== Error consultando las AllianceCompany ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAll/DAOAllianceCompanyBean ==");
        }
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.AllianceCompanyDAOLocal#getAllianceCompanyByCode(java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public AllianceCompany getAllianceCompanyByCode(String code) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllianceCompanyByCode/AllianceDAO ==");
        Session session = getSession();

        try {
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(AllianceCompany.class.getName());
        	stringQuery.append(" allianceCompany where allianceCompany.companyCode = :companyCode");
        	Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("from "+AllianceCompany.class.getName()+" allianceCompany where allianceCompany.companyCode = :companyCode");
            query.setString("companyCode", code);
            AllianceCompany allianceCompany = null;
            if(query.list()!=null && query.list().size()>0){
                allianceCompany = (AllianceCompany) query.list().get(0);
            }
            return allianceCompany;

        } catch (Throwable ex) {
            log.error("== Error consultando el AllianceCompany por Código ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllianceCompanyByCode/AllianceDAO ==");
        }
    }
  
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.AllianceCompanyDAOLocal#getAllAllianceCompanyByCountryId(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<AllianceCompany> getAllAllianceCompanyByCountryId(Long countryId)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getAllAllianceCompanyByCountryId/DAOAllianceCompanyBean ==");
        
		if(countryId == null || countryId <= 0L){
        	return getAll();
        }
		Session session = getSession();

        try {
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("from AllianceCompany ac ");
        	stringQuery.append("where ");
        	stringQuery.append("ac.country.id = :aCountryId order by ac.companyName ");
        	Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("from AllianceCompany ac where ac.country.id = :aCountryId order by ac.companyName");
            query.setLong("aCountryId", countryId);
            List<AllianceCompany> result = query.list(); 
            return result;
        } catch (Throwable ex) {
            log.error("== Error consultando AllianceCompany por país ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllAllianceCompanyByCountryId/DAOAllianceCompanyBean ==");
        }
	}

}
