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
import co.com.directv.sdii.model.pojo.Skill;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.config.SkillDAOLocal;
import co.com.directv.sdii.persistence.hibernate.ConnectionFactory;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad de Skill
 * 
 * Fecha de Creacion: Mar 23, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.Skill
 * @see co.com.directv.sdii.persistence.dao.config.SkillDAOLocal
 */
@Stateless(name="SkillDAOLocal",mappedName="ejb/SkillDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class SkillDAO extends BaseDao implements SkillDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(SkillDAO.class);

    /**
     * Crea una Skill en el sistema
     * @param obj - Skill
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createSkill(Skill obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createSkill/DAOSkillBean ==");
        Session session = ConnectionFactory.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error creando Skill ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createSkill/DAOSkillBean ==");
        }
    }

    /**
     * Obtiene un skill con el id especificado
     * @param id - Long
     * @return - Skill
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Skill getSkillByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getSkillByID/DAOSkillBean ==");
        Session session = ConnectionFactory.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("from ");
            stringQuery.append(Skill.class.getName());
            stringQuery.append(" skill where skill.id = ");
            stringQuery.append(id);
            Object obj = session.createQuery(stringQuery.toString()).uniqueResult();
            //Object obj = session.createQuery("from "+Skill.class.getName()+" skill where skill.id = " + id + " ").uniqueResult();
            if (obj != null) {
                return (Skill) obj;
            }
            return null;
        } catch (Throwable ex) {
            log.debug("== Error consultando Skill por ID ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getSkillByID/DAOSkillBean ==");
        }
    }

    /**
     * Actualiza un skill especificado
     * @param obj - Skill
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateSkill(Skill obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia updateSkill/DAOSkillBean ==");
        Session session = ConnectionFactory.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error actualizando Skill ==");
            throw this.manageException(ex);
        }finally {
            log.debug("== Termina updateSkill/DAOSkillBean ==");
        }

    }

    /**
     * Elimina un skill del sistema
     * @param obj - Skill
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteSkill(Skill obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia deleteSkill/DAOSkillBean ==");
        Session session = ConnectionFactory.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery =  new StringBuffer();
            stringQuery.append("delete from ");
            stringQuery.append(Skill.class.getName());
            stringQuery.append(" sk where sk.id = :aSkId");
            Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("delete from "+ Skill.class.getName() + " sk where sk.id = :aSkId");
            query.setLong("aSkId", obj.getId());
            query.executeUpdate();
            this.doFlush(session);
            
        }catch (Throwable ex) {
            log.debug("== Error eliminando Skill  ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteSkill/DAOSkillBean ==");
        }

    }

    /**
     * Obtiene todos los skills del sistema
     * @return - List<Skill>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Skill> getAll() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAll/DAOSkillBean ==");
        Session session = ConnectionFactory.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery =  new StringBuffer();
            stringQuery.append("from ");
            stringQuery.append(Skill.class.getName());
            Query query = session.createQuery(stringQuery.toString());
            return query.list();
        }catch (Throwable ex) {
            log.debug("== Error consultando todas las Skill ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAll/DAOSkillBean ==");
        }
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.SkillDAOLocal#getSkillByCode(java.lang.String)
     */
    public Skill getSkillByCode(String code) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getSkillByCode/DAOSkillBean ==");
        Session session = ConnectionFactory.getSession();
        Skill result = null;
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery =  new StringBuffer();
            stringQuery.append("from ");
            stringQuery.append(Skill.class.getName());
            stringQuery.append(" sk where sk.skillCode = :aSkCode");
            Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("from "+ Skill.class.getName() + " sk where sk.skillCode = :aSkCode");
            query.setString("aSkCode", code);
            result = (Skill) query.uniqueResult();

            return result;
        } catch (Throwable ex) {
            log.debug("== Error consultando Skill por código  ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getSkillByCode/DAOSkillBean ==");
        }
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.SkillDAOLocal#getSkillByName(java.lang.String)
     */
    @SuppressWarnings("unchecked")
	public List<Skill> getSkillByName(String name) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getSkillByName/DAOSkillBean ==");
        Session session = ConnectionFactory.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery =  new StringBuffer();
            stringQuery.append("from ");
            stringQuery.append( Skill.class.getName());
            stringQuery.append(" sk where sk.skillName = :aSkName");
            Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("from " + Skill.class.getName() + " sk where sk.skillName = :aSkName");
            query.setString("aSkName", name);

            return query.list();
        }catch (Throwable ex) {
            log.debug("== Error consultando Skill por nombre ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getSkillByName/DAOSkillBean ==");
        }
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.SkillDAOLocal#getSkillByTypeId(java.lang.Long)
     */
    @SuppressWarnings("unchecked")
	public List<Skill> getSkillByTypeId(Long skillTypeId) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getSkillByTypeId/DAOSkillBean ==");
        Session session = ConnectionFactory.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery =  new StringBuffer();
            stringQuery.append("from ");
            stringQuery.append(Skill.class.getName());
            stringQuery.append(" sk where sk.skillType.id = :aSkTypeId");
            stringQuery.append(" order by sk.id asc ");
            Query query = session.createQuery(stringQuery.toString());
           
            query.setLong("aSkTypeId", skillTypeId);

            return query.list();
        }catch (Throwable ex) {
            log.debug("== Error consultando Skill por Tipo de Skill ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getSkillByTypeId/DAOSkillBean ==");
        }
    }

    /*
     * (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.SkillDAOLocal#getAllSkillsByCountryId(java.lang.Long)
     */
	@SuppressWarnings("unchecked")
	@Deprecated
	public List<Skill> getAllSkillsByCountryId(Long countryId)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getAllSkillsByCountryId/DAOSkillBean ==");
        
		if(countryId == null || countryId <= 0L){
        	return getAll();
        }
		
		Session session = ConnectionFactory.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery =  new StringBuffer();
            stringQuery.append("from ");
            stringQuery.append(Skill.class.getName());
            stringQuery.append(" s order by s.skillName");
            Query query = session.createQuery(stringQuery.toString());
            List<Skill> result = query.list();
            return result;
        }catch (Throwable ex) {
            log.debug("== Error consultando Skill por país ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllSkillsByCountryId/DAOSkillBean ==");
        }
	}
	
	/*
     * (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.SkillDAOLocal#getAllSkillsByCountryId(java.lang.Long)
     */
	@SuppressWarnings("unchecked")
	@Deprecated
	public List<Skill> getAllSkillsBySkillTypeAndCountryId(Long skillTypeId, Long countryId)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getAllSkillsBySkillTypeAndCountryId/DAOSkillBean ==");
        
		if(countryId == null || countryId <= 0L){
        	return getAll();
        }
		
		Session session = ConnectionFactory.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            
            StringBuffer stringQuery =  new StringBuffer();
            stringQuery.append("from ");
            stringQuery.append(Skill.class.getName());
            stringQuery.append(" s where s.skillType.id = :aSkTypeId order by s.skillName");
            Query query = session.createQuery(stringQuery.toString());
                        
            query.setLong("aSkTypeId", skillTypeId);
            
            List<Skill> result = query.list();
            return result;
        }catch (Throwable ex) {
            log.debug("== Error consultando Skill por TipoSkill y país ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getAllSkillsBySkillTypeAndCountryId/DAOSkillBean ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.SkillDAOLocal#getAllSkillsBySkillTypeAndCountryIdOrderById(java.lang.Long, java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Deprecated
	public List<Skill> getAllSkillsBySkillTypeAndCountryIdOrderById(Long skillTypeId, Long countryId)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getAllSkillsBySkillTypeAndCountryIdOrderById/DAOSkillBean ==");
		Session session = ConnectionFactory.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("from ");
            stringQuery.append(Skill.class.getName());
            stringQuery.append(" s where ");
            stringQuery.append("s.skillType.id = :aSkTypeId ");
            stringQuery.append("order by s.id asc ");
            
            Query query = session.createQuery(stringQuery.toString());
            
            query.setLong("aSkTypeId", skillTypeId);
            List<Skill> result = query.list();
            return result;
        }catch (Throwable ex) {
            log.debug("== Error consultando Skill por Tipo de Skill y país ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllSkillsBySkillTypeAndCountryIdOrderById/DAOSkillBean ==");
        }
	}
}
