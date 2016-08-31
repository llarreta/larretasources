package co.com.directv.sdii.persistence.dao.config.impl;

import java.util.List;

import javax.ejb.Local;
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
import co.com.directv.sdii.model.pojo.SystemParameter;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.config.SystemParameterDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad de SystemParameter
 * 
 * Fecha de Creacion: Mar 23, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.SystemParameter
 * @see co.com.directv.sdii.persistence.dao.config.SystemParameterDAOLocal
 */
@Stateless(name="SystemParameterDAOLocal",mappedName="ejb/SystemParameterDAOLocal")
@Local({co.com.directv.sdii.persistence.dao.config.SystemParameterDAOLocal.class})
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class SystemParameterDAO extends BaseDao implements SystemParameterDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(SystemParameterDAO.class);

    /**
     * Crea un parametro del sistema
     * @param obj - SystemParameter
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createSystemParameter(SystemParameter obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createSystemParameter/DAOSystemParameterBean ==");
        Session session = super.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error creando SystemParameter ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createSystemParameter/SystemParameterDAO ==");
        }
    }

    /**
     * Obtiene un systemparameter con el id especificado
     * @param id - Long
     * @return - SystemParameter
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public SystemParameter getSystemParameterByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getSystemParameterByID/SystemParameterDAO ==");
        Session session = super.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery =  new StringBuffer();
            stringQuery.append("select systemparameter from ");
            stringQuery.append(SystemParameter.class.getName());
            stringQuery.append(" systemparameter where systemparameter.id = ");
            stringQuery.append(id);
            Object obj = session.createQuery(stringQuery.toString()).uniqueResult();
            //Object obj = session.createQuery("select systemparameter from " + SystemParameter.class.getName() + " systemparameter where systemparameter.id = " + id + " ").uniqueResult();
            if (obj != null) {
                return (SystemParameter) obj;
            }
            return null;
        } catch (Throwable ex) {
            log.debug("== Error consultando SystemParameter por ID ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getSystemParameterByID/SystemParameterDAO ==");
        }
    }

    
    /**
     * Actualiza un systemparameter especificado
     * @param obj - SystemParameter
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateSystemParameter(SystemParameter obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia updateSystemParameter/SystemParameterDAO ==");
        Session session = super.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error actualizando SystemParameter ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina updateSystemParameter/SystemParameterDAO ==");
        }

    }

    /**
     * Elimina un systemparameter del sistema
     * @param obj - SystemParameter
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteSystemParameter(SystemParameter obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia deleteSystemParameter/SystemParameterDAO ==");
        Session session = super.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery =  new StringBuffer();
            stringQuery.append("delete from ");
            stringQuery.append(SystemParameter.class.getName());
            stringQuery.append(" sp where sp.id = :id");
            Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("delete from " + SystemParameter.class.getName() + " sp where sp.id = :id");
            query.setLong("id", obj.getId());
            query.executeUpdate();
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error eliminando SystemParameter ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina deleteSystemParameter/SystemParameterDAO ==");
        }
    }

    /**
     * Obtiene todos los systemparameters del sistema
     * @return - List<SystemParameter>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<SystemParameter> getAll() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAll/SystemParameterDAO ==");
        Session session = super.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery =  new StringBuffer();
            stringQuery.append("from ");
            stringQuery.append(SystemParameter.class.getName());
            Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("from " + SystemParameter.class.getName());
            return query.list();
        } catch (Throwable ex) {
            log.debug("== Error consultando todos los SystemParameter ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAll/SystemParameterDAO ==");
        }
    }

    /**
     * Obtiene un SystemParameter de acuerdo al codigo especificado
     * @param code - String
     * @return - SystemParameter
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    /*
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public SystemParameter getSystemParameterByCode(String code) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getSystemParameterByCode/SystemParameterDAO ==");
        Session session = super.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery =  new StringBuffer();
            stringQuery.append("select sp from ");
            stringQuery.append(SystemParameter.class.getName());
            stringQuery.append(" sp where sp.parameterCode = :parameterCode");
            Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("select sp from " + SystemParameter.class.getName() + " sp where sp.parameterCode = :parameterCode");
            query.setString("parameterCode", code);

            Object obj = query.uniqueResult();
            if (obj == null) {
                return null;
            }
            return (SystemParameter) obj;

        }catch (Throwable ex) {
            log.debug("== Error consultando SystemParameter por código ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getSystemParameterByCode/SystemParameterDAO ==");
        }
    }
    */

    /**
     * Obtiene un SystemParameter de acuerdo con el nombre especificado
     * @param code - String
     * @return - SystemParameter
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<SystemParameter> getSystemParameterByNameAndCountryId(String name, Long countryId) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getSystemParameterByName/SystemParameterDAO ==");
        Session session = super.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery =  new StringBuffer();
            stringQuery.append("select sp from ");
            stringQuery.append(SystemParameter.class.getName());
            stringQuery.append(" sp where upper(sp.parameterName) like :parameterName and sp.country.id = :aCountryId");
            Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("select sp from " + SystemParameter.class.getName() + " sp where sp.parameterName = :parameterName");
            query.setString("parameterName", "%" + name.toUpperCase() + "%");
            query.setLong("aCountryId", countryId);

            return query.list();

        } catch (Throwable ex) {
            log.debug("== Error consultando los SystemParameter por nombre ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getSystemParameterByName/SystemParameterDAO ==");
        }
    }
    
    /*
     * (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.config.SystemParameterDAOLocal#getAllSystemParametersByCountryId(java.lang.Long)
     */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<SystemParameter> getAllSystemParametersByCountryId(
			Long countryId) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getAllSystemParametersByCountryId/SystemParameterDAO ==");
        
		if(countryId == null || countryId <= 0L){
        	return getAll();
        }
		
		Session session = super.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery =  new StringBuffer();
            stringQuery.append("from ");
            stringQuery.append(SystemParameter.class.getName());
            stringQuery.append(" sp where sp.country.id = :aCountryId order by sp.parameterName");
            Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("from " + SystemParameter.class.getName() + " sp where sp.country.id = :aCountryId order by sp.parameterName");
            query.setLong("aCountryId", countryId);
            List<SystemParameter> result = query.list();
            return result;
        }catch (Throwable ex) {
            log.debug("== Error consultando todos los SystemParameter por país ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllSystemParametersByCountryId/SystemParameterDAO ==");
        }
	}
	
	/**
	 * 
	 * Metodo: Retorna el id de una entidad
	 * filtrando por el codigo.
	 * @param className String
	 * @param codeEntity String
	 * @param propertyName String
	 * @return Long
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jalopez
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Long getIdEntityByCodeEntity(String className,String codeEntity, String propertyName)throws DAOServiceException, DAOSQLException {
		
		log.debug("== Inicia getIdEntityByCodeEntity/SystemParameterDAO ==");
        
		Session session = super.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer queryBuffer = new StringBuffer();
            queryBuffer.append(" select cname.id");
            queryBuffer.append(" from ");
            queryBuffer.append(className);
            queryBuffer.append(" cname ");
            queryBuffer.append(" where cname.");            
            queryBuffer.append(propertyName);
            queryBuffer.append(" = :code ");
            
            
            Query query = session.createQuery(queryBuffer.toString());
            query.setString("code", codeEntity);
            Object id = query.uniqueResult();
            
            return (Long) id;
        } catch (Throwable ex) {
            log.debug("== Error consultando todos los SystemParameter ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getIdEntityByCodeEntity/SystemParameterDAO ==");
        }
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.SystemParameterDAOLocal#getSysParamByCodeAndCountryId(java.lang.String, java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public SystemParameter getSysParamByCodeAndCountryId(String parameterCode, Long countryId) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getSysParamByCodeAndCountryId/SystemParameterDAO ==");
        Session session = super.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer strBuffer = new StringBuffer("from ");
            strBuffer.append(SystemParameter.class.getName()+" sp ");
            strBuffer.append(" inner join fetch sp.parameterType pt ");
            strBuffer.append(" left join fetch sp.country cou ");
            strBuffer.append(" where sp.parameterCode = :parameterCode and sp.country.id = :aCountryId");
            
            
            Query query = session.createQuery(strBuffer.toString());
            query.setString("parameterCode", parameterCode);
            query.setLong("aCountryId", countryId);

            Object obj = query.uniqueResult();
            if (obj == null) {
                return null;
            }
            return (SystemParameter) obj;

        } catch (Throwable ex) {
            log.debug("== Error consultando todos los SystemParameter por código parametro y país ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getSysParamByCodeAndCountryId/SystemParameterDAO ==");
        }
	}

	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public SystemParameter getSysParamByCodeAndCountryCode(String parameterCode, String countryCode) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getSysParamByCodeAndCountryId/SystemParameterDAO ==");
        Session session = super.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer strBuffer = new StringBuffer("from ");
            strBuffer.append(SystemParameter.class.getName());
            strBuffer.append(" sp where sp.parameterCode = :parameterCode and sp.country.countryCode = :countryCode");
            
            
            Query query = session.createQuery(strBuffer.toString());
            query.setString("parameterCode", parameterCode);
            query.setString("countryCode", countryCode);

            Object obj = query.uniqueResult();
            if (obj == null) {
                return null;
            }
            return (SystemParameter) obj;

        } catch (Throwable ex) {
            log.debug("== Error consultando todos los SystemParameter por código parametro y país ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getSysParamByCodeAndCountryId/SystemParameterDAO ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.SystemParameterDAOLocal#getSysParamsByCode(java.lang.String)
	 */
	@Override
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<SystemParameter> getSysParamsByCode(String parameterCode)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getSysParamByCodeAndCountryId/SystemParameterDAO ==");
        Session session = super.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer strBuffer = new StringBuffer("from ");
            strBuffer.append(SystemParameter.class.getName());
            strBuffer.append(" sp where sp.parameterCode = :parameterCode");
            Query query = session.createQuery(strBuffer.toString());
            query.setString("parameterCode", parameterCode);
            List<SystemParameter> result = query.list();
            
            return result;

        } catch (Throwable ex) {
            log.debug("== Error consultando todos los SystemParameter por código parametro y país ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getSysParamByCodeAndCountryId/SystemParameterDAO ==");
        }
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public SystemParameter getSysParamsByCodeAndCountryIdNull(String parameterCode)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getSysParamByCodeAndCountryId/SystemParameterDAO ==");
        Session session = super.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer strBuffer = new StringBuffer("from ");
            strBuffer.append(SystemParameter.class.getName());
            strBuffer.append(" sp where sp.parameterCode = :parameterCode and sp.country IS NULL ");
            Query query = session.createQuery(strBuffer.toString());
            query.setString("parameterCode", parameterCode);
            SystemParameter result = (SystemParameter) query.uniqueResult();
            
            return result;

        } catch (Throwable ex) {
            log.debug("== Error consultando todos los SystemParameter por código parametro y país ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getSysParamByCodeAndCountryId/SystemParameterDAO ==");
        }
	}
}
