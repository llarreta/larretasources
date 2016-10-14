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
import co.com.directv.sdii.model.pojo.Training;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.dealers.TrainingDAOLocal;


/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad de Training
 * 
 * Fecha de Creación: Mar 4, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.Training
 * @see co.com.directv.sdii.model.hbm.Training.hbm.xml
 */
@Stateless(name="TrainingDAOLocal",mappedName="ejb/TrainingDAOLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class TrainingDAO extends BaseDao implements TrainingDAOLocal{
	
    private final static Logger log = UtilsBusiness.getLog4J(TrainingDAO.class);
    
    /**
     * Crea un tipo de entrenamiento asociado a un empleado en el sistema
     * @param obj - Training
     * @throws DAOServiceException
     * @throws DAOSQLException 
     * @author jalopez
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createTraining(Training obj) throws DAOServiceException, DAOSQLException {
    	
        log.debug("== Inicio createTraining/TrainingDAO ==");
            
        try {
        	Session session = this.getSession(); 	
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error en createTraining ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createTraining/TrainingDAO ==");
        }
    }

    /**
	 * Metodo: Consultar Training por ID
	 * @param id
	 * @return Training
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jalopez
	 */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Training getTrainingByID(Long id) throws DAOServiceException, DAOSQLException {
    	
        log.debug("== Inicio getTrainingByID/TrainingDAO ==");
        
        Training obj = null;
       
        try {
        	Session session = this.getSession();
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Training.class.getName());
        	stringQuery.append(" tr where tr.id = :aTrId");
        	Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("from " + Training.class.getName() + " tr where tr.id = :aTrId");
            query.setLong("aTrId", id);

            obj = (Training) query.uniqueResult();

            return obj;
        } catch (Throwable ex) {
            log.debug("== Error en getTrainingByID ==");
            throw this.manageException(ex);
        } finally {
           log.debug("== Termina getTrainingByID/TrainingDAO ==");
        }
    }

  
   /**
	 * Metodo: Actualizar Training
	 * @param obj
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jalopez
	 */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateTraining(Training obj) throws DAOServiceException, DAOSQLException {
    	
        log.debug("== Inicia updateTraining/TrainingDAO ==");
              
        try {
        	Session session = this.getSession();

            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error en updateTraining ==");
            throw this.manageException(ex);
        } finally {
           log.debug("== Termina updateTraining/TrainingDAO ==");
        }
        
    }

   /**
	 * Metodo: Eliminar Training
	 * @param obj
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jalopez
	 */ 
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteTraining(Training obj) throws DAOServiceException, DAOSQLException {
    	
        log.debug("== Inicia deleteTraining/TrainingDAO ==");
                
        try {
        	Session session = this.getSession();
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("delete from ");
        	stringQuery.append(Training.class.getName());
        	stringQuery.append(" t where t.id = :id");
        	Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("delete from " + Training.class.getName() + " t where t.id = :id");
            query.setLong("id", obj.getId());
            
            query.executeUpdate();
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error en deleteTraining ==");
            throw this.manageException(ex);
        } finally {
           log.debug("== Termina deleteTraining/TrainingDAO ==");
        }
    }

  /**
	 * Metodo: Consultar Todos los Training
	 * @return Training
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jalopez
	 */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Training> getAllTraining() throws DAOServiceException, DAOSQLException {
    	
        log.debug("== Inicia getAllTraining/TrainingDAO ==");
       
        List<Training> list = null;
        
        try {
        	Session session = this.getSession();
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Training.class.getName());
        	Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("from " + Training.class.getName());

            list = query.list();

            return list;
        } catch (Throwable ex) {
            log.debug("== Error en getAllTraining ==");
            throw this.manageException(ex);
        } finally {
           log.debug("== Termina getAllTraining/TrainingDAO ==");
        }
    }
    
    /**
     * Obtiene las capacitaciones de un empleado especifico
     * @param idEmployee - Id del empleado
     * @return Listado de capacitaciones de un empleado
     * @throws DAOServiceException
     * @throws DAOSQLException
     * @author jcasas
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Training> getTrainingsByEmployeeId(Long idEmployee) throws DAOServiceException, DAOSQLException{
    	log.debug("== Inicia getTrainingsByEmployeeId/TrainingDAO ==");
       
        List<Training> list = null;
        
        try {
        	Session session = this.getSession();
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Training.class.getName() );
        	stringQuery.append(" t where t.employee.id = :idEmployee");
        	Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("from " + Training.class.getName() + " t where t.employee.id = :idEmployee");
            query.setLong("idEmployee", idEmployee);

            list = query.list();
            return list;
        } catch (Throwable ex) {
            log.debug("== Error en getTrainingsByEmployeeId ==");
            throw this.manageException(ex);
        } finally {
           log.debug("== Termina getTrainingsByEmployeeId/TrainingDAO ==");
        }
    }
    
    /**
	 * Elimina todas las capacitaciones asociadas a un empleado especifico
	 * @param employeeId - Id del empleado al cual se le eliminan las capacitaciones 
	 * @throws DAOServiceException
	 * @throws DAOSQLException 
	 * @author jcasas
	 */ 
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteTrainingsByEmployeeId(Long employeeId) throws DAOServiceException, DAOSQLException {
    	
        log.debug("== Inicia deleteTrainingsByEmployeeId/TrainingDAO ==");
                
        try {
        	Session session = this.getSession();
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("delete from ");
        	stringQuery.append(Training.class.getName());
        	stringQuery.append(" t where t.employee.id = :employeeId");
        	Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("delete from " + Training.class.getName() + " t where t.employee.id = :employeeId");
            query.setLong("employeeId", employeeId);
            
            query.executeUpdate();
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error en deleteTrainingsByEmployeeId ==");
            throw this.manageException(ex);
        } finally {
           log.debug("== Termina deleteTrainingsByEmployeeId/TrainingDAO ==");
        }   
    }
}