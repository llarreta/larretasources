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

import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.SkillType;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.config.SkillTypeDAOLocal;
import co.com.directv.sdii.persistence.hibernate.ConnectionFactory;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad de SkillType
 * 
 * Fecha de Creacion: Mar 23, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.SkillType
 * @see co.com.directv.sdii.persistence.dao.config.SkillTypeDAOLocal
 */
@Stateless(name="SkillTypeDAOLocal",mappedName="ejb/SkillTypeDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class SkillTypeDAO extends BaseDao implements SkillTypeDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(SkillTypeDAO.class);

    /**
     * Crea un SkillType en el sistema
     * @param obj - SkillType
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createSkillType(SkillType obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createSkillType/DAOSkillTypeBean ==");
        Session session = ConnectionFactory.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error creando SkillType ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createSkillType/DAOSkillTypeBean ==");
        }
    }

    /**
     * Obtiene un skilltype con el id especificado
     * @param id - Long
     * @return - SkillType
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public SkillType getSkillTypeByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getSkillTypeByID/DAOSkillTypeBean ==");
        Session session = ConnectionFactory.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery =  new StringBuffer();
            stringQuery.append("select skilltype from ");
            stringQuery.append("SkillType skilltype ");
            stringQuery.append("where ");
            stringQuery.append("skilltype.id = ");
            stringQuery.append(id);
            Object obj = session.createQuery(stringQuery.toString()).uniqueResult();
            //Object obj = session.createQuery("select skilltype from SkillType skilltype where skilltype.id = " + id + " ").uniqueResult();
            if (obj != null) {
                return (SkillType) obj;
            }
            return null;
        }catch (Throwable ex) {
            log.debug("== Error consultando SkillType por ID ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getSkillTypeByID/DAOSkillTypeBean ==");
        }
    }

    /**
     * Actualiza un skilltype especificado
     * @param obj - SkillType
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateSkillType(SkillType obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia updateSkillType/DAOSkillTypeBean ==");
        Session session = ConnectionFactory.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            session.merge(obj);
            this.doFlush(session);
        }catch (Throwable ex) {
            log.debug("== Error actualizando SkillType ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateSkillType/DAOSkillTypeBean ==");
        }

    }

    /**
     * Elimina un skilltype del sistema
     * @param obj - SkillType
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteSkillType(SkillType obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia deleteSkillType/DAOSkillTypeBean ==");
        Session session = ConnectionFactory.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            session.delete(obj);
            this.doFlush(session);
        }catch (Throwable ex) {
            log.debug("== Error eliminando SkillType ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina deleteSkillType/DAOSkillTypeBean ==");
        }

    }

    /**
     * Obtiene todos los skilltypes del sistema
     * @return - List<SkillType>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<SkillType> getAll() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAll/DAOSkillTypeBean ==");
        Session session = ConnectionFactory.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            Query query = session.createQuery("from SkillType");
            return query.list();
        } catch (Throwable ex) {
            log.debug("== Error consultando todos los SkillType ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAll/DAOSkillTypeBean ==");
        }
    }

    /*
     * (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.SkillTypeDAOLocal#getAllSkillTypesByCountryId(java.lang.Long)
     */
	@SuppressWarnings("unchecked")
	public List<SkillType> getAllSkillTypesByCountryId(Long countryId)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getAllSkillTypesByCountryId/DAOSkillTypeBean ==");
        
		if(countryId == null || countryId <= 0L){
        	return getAll();
        }
		
		Session session = ConnectionFactory.getSession();

        try {
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("from SkillType st ");
        	stringQuery.append("where ");
        	stringQuery.append("st.country.id = :aCountryId order by st.skillTypeName");
        	Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("from SkillType st where st.country.id = :aCountryId order by st.skillTypeName");
            query.setLong("aCountryId", countryId);
            List<SkillType> result = query.list();
            return result;
        } catch (Throwable ex) {
            log.debug("== Error consultando SkillType por país ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllSkillTypesByCountryId/DAOSkillTypeBean ==");
        }
	}

	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.SkillTypeDAOLocal#getSkillTypeByCode(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<SkillType> getSkillTypeByCode(String code)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getSkillTypeByCode/DAOSkillTypeBean ==");
        
		Session session = ConnectionFactory.getSession();
        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from SkillType st ");
        	stringQuery.append("where ");
        	stringQuery.append("where ");
        	stringQuery.append("st.skillTypeCode = :skillTypeCode order by st.skillTypeName");        	
            Query query = session.createQuery(stringQuery.toString());
            query.setString("stringQuery", code);
            List<SkillType> result = query.list();
            return result;
        } catch (Throwable ex) {
            log.debug("== Error consultando SkillType por código ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getSkillTypeByCode/DAOSkillTypeBean ==");
        }
	}

	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.SkillTypeDAOLocal#getSkillTypeByCodeAndCountry(java.lang.String, java.lang.Long)
	 */
	public SkillType getSkillTypeByCodeAndCountry(String code, Long countryId)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getSkillTypeByCodeAndCountry/DAOSkillTypeBean ==");
        
		Session session = ConnectionFactory.getSession();
        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from SkillType st where ");
        	stringQuery.append("st.skillTypeCode = :skillTypeCode and st.country.id = :countryId");        	
            Query query = session.createQuery(stringQuery.toString());
            query.setString("skillTypeCode", code);
            query.setLong("countryId", countryId);
            SkillType result = (SkillType) query.uniqueResult();
            return result;
        } catch (Throwable ex) {
            log.debug("== Error consultando SkillType por código y país ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getSkillTypeByCodeAndCountry/DAOSkillTypeBean ==");
        }
	}

}
