package co.com.directv.sdii.persistence.dao.dealers.impl;

import java.util.List;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.ContractType;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.dealers.ContractTypesDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad de ContractTypes
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.ContractTypes
 * @see co.com.directv.sdii.model.hbm.ContractTypes.hbm.xml
 */
@Stateless(name="ContractTypesDAOLocal",mappedName="ejb/ContractTypesDAOLocal")
public class ContractTypesDAO extends BaseDao implements ContractTypesDAOLocal {
	
    private final static Logger log = UtilsBusiness.getLog4J(ContractTypesDAO.class);

    /**
     * Metodo: Consultar ContractTypes por ID
     * @param id
     * @return ContractTypes
     * @throws DAOServiceException
     * @throws DAOSQLException <tipo> <descripcion>
     * @author jalopez
     */
    public ContractType getContractTypesByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getContractTypesByID/ContractTypesDAO ==");
        Session session = getSession();
        ContractType obj = null;
       
        try {
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ContractType.class.getName());
        	stringQuery.append(" ct where ct.id = :aCtId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("aCtId", id);

            obj = (ContractType) query.uniqueResult();

            return obj;
        } catch (Throwable ex){
            log.debug("== Error consultando el ContractTypes por ID ==");
            throw manageException(ex);
        } finally {
           log.debug("== Termina getContractTypesByID/ContractTypesDAO ==");
        }
    }	
    
    /**
	 * Metodo: Consultar ContractTypes por Codigo
	 * @param code
	 * @return ContractTypes
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jalopez
	 */
    public ContractType getContractTypesByCode(String code) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getContractTypesByCode/ContractTypesDAO ==");
        Session session = getSession();
        ContractType obj = null;
       
        try {
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ContractType.class.getName());
        	stringQuery.append(" ct where ct.contractTypeCode = :aCtCode");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("aCtCode", code);

            obj = (ContractType) query.uniqueResult();

            return obj;
        } catch (Throwable ex){
            log.debug("== Error consultando el ContractTypes por Code ==");
            throw manageException(ex);
        } finally {
           log.debug("== Termina getContractTypesByCode/ContractTypesDAO ==");
        }
    }
	
    /**
	 * Metodo: Consultar todos los ContractTypes
	 * @return List<ContractTypes>
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jalopez
	 */
    @SuppressWarnings("unchecked")
	public List<ContractType> getAllContractTypes() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllContractTypes/ContractTypesDAO ==");
        Session session = getSession();
        List<ContractType> list = null;
        
        try {
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ContractType.class.getName());
        	Query query = session.createQuery(stringQuery.toString());
        	
            list = query.list();

            return list;
        } catch (Throwable ex){
            log.debug("== Error consultando todos los ContractTypes ==");
            throw manageException(ex);
        } finally {
           log.debug("== Termina getAllContractTypes/ContractTypesDAO ==");
        }
    }

    /*
     * (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.dealers.ContractTypesDAOLocal#getAllContractTypesByCountryId(java.lang.Long)
     */
	@SuppressWarnings("unchecked")
	public List<ContractType> getAllContractTypesByCountryId(Long countryId)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getAllContractTypesByCountryId/ContractTypesDAO ==");
        
		if(countryId == null || countryId <= 0L){
        	return getAllContractTypes();
        }
		Session session = getSession();
        List<ContractType> list = null;
        
        try {
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ContractType.class.getName());
        	stringQuery.append(" ct where ct.country.id = :aCountryId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("aCountryId", countryId);
            list = query.list();

            return list;
        } catch (Throwable ex){
            log.debug("== Error consultando todos los ContractTypes ==");
            throw manageException(ex);
        } finally {
           log.debug("== Termina getAllContractTypesByCountryId/ContractTypesDAO ==");
        }
	}
}