package co.com.directv.sdii.persistence.dao.dealers.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.Pension;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.dealers.PensionDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad de Pension
 * 
 * Fecha de Creaci�n: Mar 8, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.Pension
 * @see co.com.directv.sdii.model.hbm.Pension.hbm.xml
 */
@Stateless(name="PensionDAOLocal",mappedName="ejb/PensionDAOLocal")
public class PensionDAO extends BaseDao implements PensionDAOLocal {
	
    private final static Logger log = UtilsBusiness.getLog4J(PensionDAO.class);
    
   
    /**
     * Metodo: Consultar Pension por ID
     * @param id
     * @return Pension
     * @throws DAOServiceException
     * @throws DAOSQLException <tipo> <descripcion>
     * @author jalopez
     */
    public Pension getPensionByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getPensionByID/PensionDAO ==");
        
        Pension obj = null;
       
        try {
        	Session session = this.getSession();
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Pension.class.getName());
        	stringQuery.append(" pen where pen.id = :aPenId");
        	Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("from "+Pension.class.getName()+" pen where pen.id = :aPenId");
            query.setLong("aPenId", id);

            obj = (Pension) query.uniqueResult();

            return obj;
        } catch (Throwable ex) {
            log.debug("== Error en getPensionByID ==");
            throw this.manageException(ex);
        } finally {
           log.debug("== Termina getPensionByID/PensionDAO ==");
        }
    }	
    
    /**
	 * Metodo: Consultar Pension por Codigo
	 * @param code
	 * @return Pension
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jalopez
	 */
    public Pension getPensionByCode(String code) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getPensionByCode/PensionDAO ==");
        
        Pension obj = null;
       
        try {
        	Session session = this.getSession();
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Pension.class.getName());
        	stringQuery.append(" pen where pen.pensionCode = :aPenCode");
        	Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("from "+Pension.class.getName()+" pen where pen.pensionCode = :aPenCode");
            query.setString("aPenCode", code);

            obj = (Pension) query.uniqueResult();

            return obj;
        } catch (Throwable ex) {
            log.debug("== Error en getPensionByCode ==");
            throw this.manageException(ex);
        } finally {
           log.debug("== Termina getPensionByCode/PensionDAO ==");
        }
    }
	
    /**
	 * Metodo: Consultar todos los Pension
	 * @return List<Pension>
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jalopez
	 */
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Pension> getAllPension() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllPension/PensionDAO ==");
        
        List<Pension> list = null;
        
        try {
        	Session session = this.getSession();
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Pension.class.getName());
            Query query = session.createQuery("from " + Pension.class.getName());
            list = query.list();

            return list;
        } catch (Throwable ex) {
            log.debug("== Error en getAllPension ==");
            throw this.manageException(ex);
        } finally {
           log.debug("== Termina getAllPension/PensionDAO ==");
        }
    }
    
    /*
     * (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.dealers.PensionDAOLocal#getAllPensionByCountryId(java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Pension> getAllPensionByCountryId(Long countryId) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllPensionByCountryId/PensionDAO ==");
        
        if(countryId == null || countryId <= 0L){
        	return getAllPension();
        }        
        
        List<Pension> list = null;
        
        try {
        	Session session = this.getSession();
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Pension.class.getName());
        	stringQuery.append(" p where p.country.id= :aCountryId");
        	Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("from " + Pension.class.getName() + " p where p.country.id= :aCountryId");
            query.setLong("aCountryId", countryId);
            list = query.list();

            return list;
        } catch (Throwable ex) {
            log.debug("== Error en getAllPensionByCountryId ==");
            throw this.manageException(ex);
        } finally {
           log.debug("== Termina getAllPensionByCountryId/PensionDAO ==");
        }
    }
}