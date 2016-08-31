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
import co.com.directv.sdii.model.pojo.CustomerClassType;
import co.com.directv.sdii.model.pojo.CustomerType;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.config.CustomerClassTypeDAOLocal;
import co.com.directv.sdii.persistence.hibernate.ConnectionFactory;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad de CustomerCodeType
 * 
 * Fecha de Creacion: Mar 23, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.CustomerCodeType
 * @see co.com.directv.sdii.persistence.dao.config.CustomerCodeTypeDAOLocal
 */
@Stateless(name="CustomerClassTypeDAO",mappedName="ejb/CustomerClassTypeDAO")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class CustomerClassTypeDAO extends BaseDao implements CustomerClassTypeDAOLocal {

	private final static Logger log = UtilsBusiness.getLog4J(CustomerClassTypeDAO.class);
	
    /**
     * Crea un CustomerCodeType en el sistema
     * @param obj - CustomerCodeType
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
	public void createCustomerClassType(CustomerClassType obj)
			throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio createCustomerClassType/CustomerClassTypeDAO ==");
        Session session = ConnectionFactory.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            session.save(obj);
            this.doFlush(session);
        }catch (Throwable ex) {
            log.error("== Error creando createCustomerClassType ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createCustomerClassType/CustomerClassTypeDAO ==");
        }
		
	}

    /**
     * Elimina un customercodetype del sistema
     * @param obj - CustomerCodeType
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
	public void deleteCustomerClassType(CustomerClassType obj)
			throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia deleteCustomerCodeType/CustomerCodeTypeDAO ==");
        Session session = ConnectionFactory.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("delete from ");
            stringQuery.append(CustomerClassType.class.getName());
            stringQuery.append(" cct where cct.id = :aCctId");
            Query query = session.createQuery(stringQuery.toString());
            query.setLong("aCctId", obj.getId());

            query.executeUpdate();
            this.doFlush(session);
            
        }catch (Throwable ex) {
            log.error("== Error eliminando CustomerCodeType ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina deleteCustomerCodeType/CustomerCodeTypeDAO ==");
        }
		
	}

    /**
     * Obtiene todos los customercodetypes del sistema
     * @return - List<CustomerCodeType>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
	public List<CustomerClassType> getAll(Long countryId) throws DAOServiceException,
			DAOSQLException {
        log.debug("== Inicia getAll/CustomerCodeTypeDAO ==");
        Session session = ConnectionFactory.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append(" from ");
            stringQuery.append(CustomerClassType.class.getName());
            stringQuery.append(" cct where cct.customerClass.country.id = :countryId or cct.customerType.country.id = :countryId order by ");
            stringQuery.append(" cct.customerType.customerTypeName ");
            Query query = session.createQuery(stringQuery.toString());
            query.setLong("countryId", countryId);
            return query.list();
        }catch (Throwable ex) {
            log.error("== Error consultando todos los CustomerCodeType ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getAll/CustomerCodeTypeDAO ==");
        }
	}

	@Override
	public List<CustomerClassType> getCustomerClassTypeByCodeType(String code, Long countryId)
			throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getCustomerClassTypeByCodeType/CustomerClassTypeDAO ==");
        Session session = ConnectionFactory.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("from ");
            stringQuery.append(CustomerClassType.class.getName());
            stringQuery.append(" cct where cct.customerType.customerTypeCode = :ctc");
            stringQuery.append(" and cct.customerType.countryId=:countryId ");
            Query query = session.createQuery(stringQuery.toString());
            query.setString("ctc", code);
            query.setLong("countryId", countryId);
            return query.list();
        }catch (Throwable ex) {
            log.error("== Error en getCustomerClassTypeByCodeType ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getCustomerClassTypeByCodeType/CustomerClassTypeDAO ==");
        }
	}

	/**
	 * Metodo que busca las cambinaciones de tipo de cliente con clase de cliente dado un codigo de clase de cliente
	 * @param code codigo de la clase de cliente
	 * @param countryId pais en el cual se realiza la consulta
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author Aharker
	 */
	@Override
	public List<CustomerClassType> getCustomerClassTypeByCodeClass(String code, Long countryId)
			throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getCustomerClassTypeByCodeType/CustomerClassTypeDAO ==");
        Session session = ConnectionFactory.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("from ");
            stringQuery.append(CustomerClassType.class.getName());
            stringQuery.append(" cct where cct.customerClass.customerClassCode = :ctc");
            stringQuery.append(" and cct.customerClass.countryId=:countryId ");
            Query query = session.createQuery(stringQuery.toString());
            query.setString("ctc", code);
            query.setLong("countryId", countryId);
            return query.list();
        }catch (Throwable ex) {
            log.error("== Error en getCustomerClassTypeByCodeType ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getCustomerClassTypeByCodeType/CustomerClassTypeDAO ==");
        }
	}
	
	@Override
	public List<CustomerClassType> getCustomerClassTypeByCustomerClassId(
			Long custClassId) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getCustomerCodeTypeByCustomerClassId/CustomerCodeTypeDAO ==");
        Session session = ConnectionFactory.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("from ");
            stringQuery.append(CustomerClassType.class.getName());
            stringQuery.append(" cct where cct.customerClass.id = :aCctClassId");
            Query query = session.createQuery(stringQuery.toString());
            query.setLong("aCctClassId", custClassId);

            return query.list();
        }catch (Throwable ex) {
            log.error("== Error consultando los CustomerCodeType por id CustomerClass ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getCustomerCodeTypeByCustomerClassId/CustomerCodeTypeDAO ==");
        }
	}

    /**
     * Obtiene un customercodetype con el id especificado
     * @param id - Long
     * @return - CustomerCodeType
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
	public CustomerClassType getCustomerClassTypeByID(Long id)
			throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getCustomerClassTypeByID/CustomerClassTypeDAO ==");
        Session session = ConnectionFactory.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append(" from ");
            stringQuery.append(CustomerClassType.class.getName());
            stringQuery.append(" cct where cct.id = ");
            stringQuery.append(id);
            Object obj = session.createQuery(stringQuery.toString()).uniqueResult();
            if (obj != null) {
                return (CustomerClassType) obj;
            }
            return null;
        }catch (Throwable ex) {
            log.error("== Error consultando getCustomerClassTypeByID por ID  ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getCustomerClassTypeByID/CustomerClassTypeDAO ==");
        }
	}

	/**
	 * Metodo que busca la cambinacion de tipo de cliente con clase de cliente dado su id
	 * @param id id de busqueda de la paraja de clase y tipo de cliente
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author Aharker
	 */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
	public CustomerType getCustomerTypeByID(Long id)
			throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getCustomerClassTypeByID/CustomerClassTypeDAO ==");
        Session session = ConnectionFactory.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append(" from ");
            stringQuery.append(CustomerType.class.getName());
            stringQuery.append(" ct where ct.id = ");
            stringQuery.append(id);
            Object obj = session.createQuery(stringQuery.toString()).uniqueResult();
            if (obj != null) {
                return (CustomerType) obj;
            }
            return null;
        }catch (Throwable ex) {
            log.error("== Error consultando getCustomerClassTypeByID por ID  ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getCustomerClassTypeByID/CustomerClassTypeDAO ==");
        }
	}
    
	@Override
	public List<CustomerClassType> getCustomerClassTypeByNameType(String name, Long countryId)
			throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getCustomerClassTypeByNameType/CustomerCodeTypeDAO ==");
        Session session = ConnectionFactory.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append(" from ");
            stringQuery.append(CustomerClassType.class.getName());
            stringQuery.append(" cct where cct.customerType.customerTypeName = :aCctName ");
            stringQuery.append(" and cct.customerType.country.id = :countryId ");
            Query query = session.createQuery(stringQuery.toString());
            query.setString("aCctName", name);
            query.setLong("countryId", countryId);
            return query.list();
        }catch (Throwable ex) {
            log.error("== Error en getCustomerClassTypeByNameType ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getCustomerClassTypeByNameType/CustomerCodeTypeDAO ==");
        }
	}

    /**
     * Metodo que busca las cambinaciones de tipo de cliente con clase de cliente dado un nombre de clase de clinete
     * @param name nombre de la clase de cliente
     * @param countryId pais en el cual se realizara la busqueda
     * @return 
     * @throws DAOServiceException
     * @throws DAOSQLException
     * @author Aharker
     */
	@Override
	public List<CustomerClassType> getCustomerClassTypeByNameClass(String name, Long countryId)
			throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getCustomerCodeTypeByName/CustomerCodeTypeDAO ==");
        Session session = ConnectionFactory.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("from ");
            stringQuery.append(CustomerClassType.class.getName());
            stringQuery.append(" cct where cct.customerClass.customerClassName = :aCctName");
            stringQuery.append(" and cct.customerClass.country.id = :countryId ");
            Query query = session.createQuery(stringQuery.toString());
            query.setString("aCctName", name);
            query.setLong("countryId", countryId);
            return query.list();
        }catch (Throwable ex) {
            log.error("== Error consultando los CustomerCodeType por nombre ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getCustomerCodeTypeByName/CustomerCodeTypeDAO ==");
        }
	}
	
    /**
     * Metodo que busca las cambinaciones de tipo de cliente con clase de cliente dado un nombre de clase de cliente y el nombre de un tipo de cliente
     * @param nameType nombre del tipo de cliente
     * @param nameClass nombre de la clase de cliente
     * @param countryId pais en el cual se realizara la busqueda
     * @return
     * @throws DAOServiceException
     * @throws DAOSQLException
     * @author Aharker
     */
	@Override
	public List<CustomerClassType> getCustomerClassTypeByNameTypeAndNameClass(String nameType, String nameClass, Long countryId) throws DAOServiceException, DAOSQLException{
        log.debug("== Inicia getCustomerClassTypeByNameTypeAndNameClass/CustomerCodeTypeDAO ==");
        Session session = ConnectionFactory.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("from ");
            stringQuery.append(CustomerClassType.class.getName());
            stringQuery.append(" cct where cct.customerClass.customerClassName = :aCctName");
            stringQuery.append(" and cct.customerType.customerTypeName = :ctName");
            stringQuery.append(" and cct.customerType.country.id = :countryId ");
            stringQuery.append(" and cct.customerClass.country.id = :countryId ");
            Query query = session.createQuery(stringQuery.toString());
            query.setString("aCctName", nameClass);
            query.setString("ctName", nameType);
            query.setLong("countryId", countryId);
            return query.list();
        }catch (Throwable ex) {
            log.error("== Error consultando los getCustomerClassTypeByNameTypeAndNameClass por nombre ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getCustomerClassTypeByNameTypeAndNameClass/CustomerCodeTypeDAO ==");
        }
	}
	
    /**
     * Actualiza un customercodetype especificado
     * @param obj - CustomerCodeType
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
	public void updateCustomerClassType(CustomerClassType obj)
			throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia updateCustomerClassType/CustomerClassTypeDAO ==");
        Session session = ConnectionFactory.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            session.merge(obj);
            this.doFlush(session);
        }catch (Throwable ex) {
            log.error("== Error consultando updateCustomerClassType por ID ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina updateCustomerClassType/CustomerClassTypeDAO ==");
        }
	}

	@Override
	public CustomerClassType getCustomerClassTypeByCodeTypeAndClassCode(
			String typeCode, String classCode, Long countryId) throws DAOServiceException,
			DAOSQLException {
        log.debug("== Inicia getCustomerClassTypeByCodeType/CustomerClassTypeDAO ==");
        Session session = ConnectionFactory.getSession();

        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer stringQuery = new StringBuffer();
            stringQuery.append("from ");
            stringQuery.append(CustomerClassType.class.getName());
            stringQuery.append(" cct where cct.customerType.customerTypeCode = :ctc and cct.customerClass.customerClassCode = :ccc ");
            stringQuery.append(" and cct.customerType.country.id = :countryId and cct.customerClass.country.id = :countryId ");
            Query query = session.createQuery(stringQuery.toString());
            query.setString("ctc", typeCode);
            query.setString("ccc", classCode);
            query.setLong("countryId", countryId);
            return (CustomerClassType) query.uniqueResult();
        }catch (Throwable ex) {
            log.error("== Error en getCustomerClassTypeByCodeType ==");
            throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getCustomerClassTypeByCodeType/CustomerClassTypeDAO ==");
        }
	}

}
