package co.com.directv.sdii.persistence.dao.config.impl;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.Alliance;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.config.AllianceDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad de Alliance
 * 
 * Fecha de Creacion: Mar 23, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.Alliance
 * @see co.com.directv.sdii.persistence.dao.config.AllianceDAOLocal
 */
@Stateless(name="AllianceDAOLocal",mappedName="ejb/AllianceDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class AllianceDAO extends BaseDao implements AllianceDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(AllianceDAO.class);

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.AllianceDAOLocal#createAlliance(co.com.directv.sdii.model.pojo.Alliance)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createAlliance(Alliance obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createAlliance/AllianceDAO ==");
        Session session = getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error creando el Alliance ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createAlliance/AllianceDAO ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.AllianceDAOLocal#getAllianceByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Alliance getAllianceByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getAllianceByID/AllianceDAO ==");
        Session session = getSession();
        try {
            StringBuffer queryBuffer = new StringBuffer();
            queryBuffer.append("from Alliance alliance ");
            queryBuffer.append("where alliance.id = ");
            queryBuffer.append(id);
            //Object obj = session.createQuery("select alliance from Alliance alliance where alliance.id = " + id + " ").uniqueResult();
            Object obj = session.createQuery(queryBuffer.toString()).uniqueResult();
            if (obj != null) {
                return (Alliance) obj;
            }
            return null;
        } catch (Throwable ex) {
            log.error("== Error consultando el Alliance por ID ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllianceByID/AllianceDAO ==");
        }
    }

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.AllianceDAOLocal#updateAlliance(co.com.directv.sdii.model.pojo.Alliance)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateAlliance(Alliance obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia updateAlliance/AllianceDAO ==");
        Session session = getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error actualizando el Alliance ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateAlliance/AllianceDAO ==");
        }

    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.AllianceDAOLocal#deleteAlliance(co.com.directv.sdii.model.pojo.Alliance)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteAlliance(Alliance obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia deleteAlliance/AllianceDAO ==");
        Session session = getSession();
        try {
            /*StringBuffer queryBuffer = new StringBuffer();
            queryBuffer.append("delete from ");
            queryBuffer.append(Alliance.class.getName());
            queryBuffer.append(" a where a.id = :anAllId");
            //Query query = session.createQuery("delete from " + Alliance.class.getName() + " a where a.id = :anAllId");
            Query query = session.createQuery(queryBuffer.toString());
            query.setLong("anAllId", obj.getId());*/
            
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            session.delete(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error borrando el Alliance ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteAlliance/AllianceDAO ==");
        }

    }

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.AllianceDAOLocal#getAll()
     */
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Alliance> getAll() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAll/AllianceDAO ==");
        Session session = getSession();

        try {
            StringBuffer queryBuffer = new StringBuffer();
            queryBuffer.append("from Alliance a ");
            queryBuffer.append("order by a.allianceName asc ");
            //Query query = session.createQuery("from Alliance a order by a.allianceName asc");
            Query query = session.createQuery(queryBuffer.toString());
            return query.list();
        } catch (Throwable ex) {
            log.error("== Error consultandos todas las Alliance ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAll/AllianceDAO ==");
        }
    }

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.AllianceDAOLocal#getAllianceByCode(java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Alliance getAllianceByCode(String code) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllianceByCode/AllianceDAO ==");
        Session session = getSession();

        try {
            StringBuffer queryBuffer = new StringBuffer();
            queryBuffer.append("from ");
            queryBuffer.append(Alliance.class.getName());
            queryBuffer.append(" alliance where alliance.allianceCode = :allianceCode ");
            queryBuffer.append("order by alliance.allianceName asc");
            Query query = session.createQuery(queryBuffer.toString());
            /*Query query = session.createQuery("from "+Alliance.class.getName()+" alliance where alliance.allianceCode = :allianceCode " +
            		"order by a.allianceName asc");*/
            query.setString("allianceCode", code);
            Alliance alliance = null;
            if(query.list()!=null && query.list().size()>0){
                alliance = (Alliance) query.list().get(0);
            }
            return alliance;

        } catch (Throwable ex) {
            log.error("== Error consultando Alliance por código ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllianceByCode/AllianceDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.AllianceDAOLocal#getAllianceByName(java.lang.String)
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Alliance> getAllianceByName(String name) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllianceByName/AllianceDAO ==");
        Session session = getSession();

        try {
            StringBuffer queryBuffer = new StringBuffer();
            queryBuffer.append("from ");
            queryBuffer.append(Alliance.class.getName());
            queryBuffer.append(" alliance where alliance.allianceName = :allianceName ");
            queryBuffer.append("order by alliance.allianceName asc");
            Query query = session.createQuery(queryBuffer.toString());
            /*Query query = session.createQuery("from "+Alliance.class.getName()+" alliance where alliance.allianceName = :allianceName " +
            		"order by a.allianceName asc");*/
            query.setString("allianceName", name);
            return query.list();

        } catch (Throwable ex) {
            log.error("== Error consultando Alliance por nombre ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllianceByName/AllianceDAO ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.AllianceDAOLocal#getAllianceByDate(java.util.Date, java.util.Date)
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Alliance> getAllianceByDate(Date init, Date end) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllianceByDate/AllianceDAO ==");
        Session session = getSession();

        try {
            StringBuffer queryBuffer = new StringBuffer();
            queryBuffer.append("from ");
            queryBuffer.append(Alliance.class.getName());
            queryBuffer.append(" alliance where alliance.initDate between :initDate and :endDate ");
            queryBuffer.append("order by alliance.allianceName asc");
            Query query = session.createQuery(queryBuffer.toString());
            /*Query query = session.createQuery("from "+Alliance.class.getName()+" alliance where alliance.initDate between :initDate and :endDate " +
            		"order by a.allianceName asc");*/
            query.setDate("initDate", init);
            query.setDate("endDate", end);
            return query.list();

        } catch (Throwable ex) {
            log.error("== Error consultando Alliance por rango de fechas ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllianceByDate/AllianceDAO ==");
        }
    }

    /*
     * (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.AllianceDAOLocal#getAllAllianceByCountryId(java.lang.Long)
     */
	@SuppressWarnings("unchecked")
	public List<Alliance> getAllAllianceByCountryId(Long countryId)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getAllAllianceByCountryId/AllianceDAO ==");
        
		if(countryId == null || countryId <= 0L){
        	return getAll();
        }
		
		Session session = getSession();

        try {
        	StringBuffer queryBuffer = new StringBuffer();
        	queryBuffer.append("from Alliance a ");
        	queryBuffer.append("where ");
        	queryBuffer.append("a.country.id = :aCountryId order by a.allianceName asc ");
        	Query query = session.createQuery(queryBuffer.toString());
            //Query query = session.createQuery("from Alliance a where a.country.id = :aCountryId order by a.allianceName asc");
            query.setLong("aCountryId", countryId);
            List<Alliance> result = query.list();
            return result;
        } catch (Throwable ex) {
            log.error("== Error consultando Alliance por país ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllAllianceByCountryId/AllianceDAO ==");
        }
	}

}
