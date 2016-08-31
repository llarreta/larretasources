package co.com.directv.sdii.persistence.dao.dealers.impl;

import java.util.List;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.TrainingType;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.dealers.TrainingTypesDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad de TrainingTypes
 * 
 * Fecha de Creaci�n: Mar 8, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.TrainingTypes
 * @see co.com.directv.sdii.model.hbm.TrainingTypes.hbm.xml
 */
@Stateless(name="TrainingTypesDAOLocal",mappedName="ejb/TrainingTypesDAOLocal")
public class TrainingTypesDAO extends BaseDao implements TrainingTypesDAOLocal {
	
    private final static Logger log = UtilsBusiness.getLog4J(TrainingTypesDAO.class);

    /**
     * Metodo: Consultar TrainingTypes por ID
     * @param id
     * @return TrainingTypes
     * @throws DAOServiceException
     * @throws DAOSQLException <tipo> <descripcion>
     * @author jalopez
     */
    public TrainingType getTrainingTypesByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getTrainingTypesByID/TrainingTypesDAO ==");
       
        TrainingType obj = null;
       
        try {
        	Session session = this.getSession();
            StringBuffer stringQuery =  new StringBuffer();
            stringQuery.append("from ");
            stringQuery.append(TrainingType.class.getName());
            stringQuery.append(" tt where tt.id = :aTtId");
            Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("from " + TrainingType.class.getName() + " tt where tt.id = :aTtId");
            query.setLong("aTtId", id);

            obj = (TrainingType) query.uniqueResult();

            return obj;
        } catch (Throwable ex) {
            log.debug("== Error en getTrainingTypesByID ==");
            throw this.manageException(ex);
        } finally {
           log.debug("== Termina getTrainingTypesByID/TrainingTypesDAO ==");
        }
    }	
    
    /**
	 * Metodo: Consultar TrainingTypes por Codigo
	 * @param code
	 * @return TrainingTypes
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jalopez
	 */
    public TrainingType getTrainingTypesByCode(String code) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getTrainingTypesByCode/TrainingTypesDAO ==");
        
        TrainingType obj = null;
       
        try {
            Session session = this.getSession();
            StringBuffer stringQuery =  new StringBuffer();
            stringQuery.append("from ");
            stringQuery.append(TrainingType.class.getName());
            stringQuery.append(" tt where tt.trainingTypeCode = :aTtCode");
            Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("from " + TrainingType.class.getName() + " tt where tt.trainingTypeCode = :aTtCode");
            query.setString("aTtCode", code);

            obj = (TrainingType) query.uniqueResult();

            return obj;
        } catch (Throwable ex) {
            log.debug("== Error en getTrainingTypesByCode ==");
            throw this.manageException(ex);
        } finally {
           log.debug("== Termina getTrainingTypesByCode/TrainingTypesDAO ==");
        }
    }
	
    /**
	 * Metodo: Consultar todos los TrainingTypes
	 * @return List<TrainingTypes>
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jalopez
	 */
    @SuppressWarnings("unchecked")
	public List<TrainingType> getAllTrainingTypes() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllTrainingTypes/TrainingTypesDAO ==");
       
        List<TrainingType> list = null;
        
        try {
        	Session session = this.getSession();
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(TrainingType.class.getName());
        	Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("from " + TrainingType.class.getName());

            list = query.list();

            return list;
        } catch (Throwable ex) {
            log.debug("== Error en getAllTrainingTypes ==");
            throw this.manageException(ex);
        } finally {
           log.debug("== Termina getAllTrainingTypes/TrainingTypesDAO ==");
        }
    }

    /*
     * (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.dealers.TrainingTypesDAOLocal#getAllTrainingTypesByCountryId(java.lang.Long)
     */
	@SuppressWarnings("unchecked")
	public List<TrainingType> getAllTrainingTypesByCountryId(Long countryId)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getAllTrainingTypesByCountryId/TrainingTypesDAOLocal ==");
        
		if(countryId == null || countryId <= 0L){
        	return this.getAllTrainingTypes();
        }
		
		
        try {
        	Session session = this.getSession();
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(TrainingType.class.getName());
        	stringQuery.append(" tt where tt.country.id = :aCountryId");
        	Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("from " + TrainingType.class.getName() + " tt where tt.country.id = :aCountryId");
            query.setLong("aCountryId", countryId);
            return query.list();
        } catch (Throwable ex) {
            log.debug("== Error en getAllTrainingTypesByCountryId ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllTrainingTypesByCountryId/TrainingTypesDAOLocal ==");
        }
	}
}