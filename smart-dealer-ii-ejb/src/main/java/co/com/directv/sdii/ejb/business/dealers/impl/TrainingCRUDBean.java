package co.com.directv.sdii.ejb.business.dealers.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.dealers.TrainingCRUDBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.Training;
import co.com.directv.sdii.model.vo.TrainingVO;
import co.com.directv.sdii.persistence.dao.dealers.TrainingDAOLocal;
import co.com.directv.sdii.rules.BusinessRuleValidationManager;

/**
 *
 * EJB que implementa las operaciones Tipo CRUD (Create,Read, Update, Delete) de la
 * Entidad Training
 *
 * Fecha de Creación: Mar 5, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 *
 * @see co.com.directv.sdii.persistence.dao.dealers.TrainingDAOLocal
 * @see co.com.directv.sdii.ejb.business.dealers.TrainingCRUDBeanLocal
 *
 */
@Stateless(name="TrainingCRUDBeanLocal",mappedName="ejb/TrainingCRUDBeanLocal")
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class TrainingCRUDBean extends BusinessBase implements TrainingCRUDBeanLocal {

	@EJB(name="TrainingDAOLocal",beanInterface=TrainingDAOLocal.class)
	private TrainingDAOLocal trainingDAOLocal;

    private final static Logger log = UtilsBusiness.getLog4J(TrainingCRUDBean.class);

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createTraining(TrainingVO obj) throws BusinessException {
		log.debug("== Inicia createTraining/EmployeeCRUDBean ==");
        try {
        	
        	if (obj == null) {
        		//log.error("Parametro obj nulo. No se puede crear Training");
        		log.error(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        		
        		//throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), "Parametro obj nulo. No se puede crear Training");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        
        	if (!BusinessRuleValidationManager.getInstance().isValid(obj)) {
        		//log.error("== Error en la Capa de Negocio debido a una Validacion de negocio ==");
        		log.error(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        		//throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),"No se puede crear Training, porque no se ha asignado uno o mas parametros obligatorios");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	Training training = UtilsBusiness.copyObject(Training.class, obj);
            trainingDAOLocal.createTraining(training);
            
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operacion createTraining/MediaContactTypesCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createTraining/CrewStatusCRUDBean ==");
        }

	}

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteTraining(TrainingVO obj) throws BusinessException {
		log.debug("== Inicia deleteTraining/EmployeeCRUDBean ==");
        try {
        	
        	if (obj == null) {
        		//log.error("Parametro obj nulo. No se puede eliminar Training");
        		log.error(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        		//throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), "Parametro obj nulo. No se puede crear Training");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
        	Training training = UtilsBusiness.copyObject(Training.class, obj);
        	trainingDAOLocal.deleteTraining(training);
        	
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operacion deleteTraining/MediaContactTypesCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteTraining/CrewStatusCRUDBean ==");
        }
	}

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<TrainingVO> getAllTraining() throws BusinessException {
		log.debug("== Inicia getAllTraining/EmployeeCRUDBean ==");
        try {
        	
        	List<Training> trainingVOs = trainingDAOLocal.getAllTraining();
        	if(trainingVOs == null)
        		return null;
        	return UtilsBusiness.convertList(trainingVOs, TrainingVO.class);
        	
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operacion getAllTraining/MediaContactTypesCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllTraining/CrewStatusCRUDBean ==");
        }
	}

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public TrainingVO getTrainingByID(Long id) throws BusinessException {
		log.debug("== Inicia getTrainingByID/EmployeeCRUDBean ==");
        try {
        	if (id == null) {
        		//log.error("Parametro id nulo. No se puede obtener Training By Id");
        		log.error(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        		//throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), "Parametro obj nulo. No se puede obtener Training By Id");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	Training training = trainingDAOLocal.getTrainingByID(id);
        	if(training == null)
        		throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
        	return UtilsBusiness.copyObject(TrainingVO.class, training);
        	
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operacion getTrainingByID/MediaContactTypesCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getTrainingByID/CrewStatusCRUDBean ==");
        }
	}

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateTraining(TrainingVO obj) throws BusinessException {
		log.debug("== Inicia updateTraining/EmployeeCRUDBean ==");
        try {
        	
        	if (obj == null) {
        		//log.error("Parametro obj nulo. No se puede actualizar Training");
        		log.error(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        		//throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), "Parametro obj nulo. No se puede actualizar Training");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        
        	if (!BusinessRuleValidationManager.getInstance().isValid(obj)) {
        		//log.error("== Error en la Capa de Negocio debido a una Validacion de negocio ==");
        		log.error(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        		//throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),"No se puede actualizar Training, porque no se ha asignado uno o mas parametros obligatorios");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	Training training = UtilsBusiness.copyObject(Training.class, obj);
            trainingDAOLocal.updateTraining(training);
            
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operacion updateTraining/MediaContactTypesCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateTraining/CrewStatusCRUDBean ==");
        }
	}

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteTrainingsByEmployeeId(Long employeeId)throws BusinessException {
    	log.debug("== Inicia deleteTrainingsByEmployeeId/EmployeeCRUDBean ==");
        try {
        	if (employeeId == null) {
        		//log.error("Parametro id nulo. No se puede eliminar Trainings By Employee Id");
        		log.error(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        		//throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), "Parametro id nulo. No se puede eliminar Trainings By Employee Id");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        		
        	}
        	trainingDAOLocal.deleteTrainingsByEmployeeId(employeeId);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operacion deleteTrainingsByEmployeeId/MediaContactTypesCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteTrainingsByEmployeeId/CrewStatusCRUDBean ==");
        }
		
	}

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<TrainingVO> getTrainingsByEmployeeId(Long idEmployee)throws BusinessException {
    	log.debug("== Inicia getTrainingsByEmployeeId/EmployeeCRUDBean ==");
        try {
        	
        	if (idEmployee == null) {
        		log.error(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        		//log.error("Parametro idEmployee nulo. No se puede obtener Training por Employee");
        		//throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), "Parametro idEmployee nulo. No se puede obtener Training por Employee");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        
        	List<Training> trainings = this.trainingDAOLocal.getTrainingsByEmployeeId(idEmployee);
        	if(trainings == null){
        		return null;
        	}
            
        	return UtilsBusiness.convertList(trainings, TrainingVO.class);
        	
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operacion getTrainingsByEmployeeId/MediaContactTypesCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getTrainingsByEmployeeId/CrewStatusCRUDBean ==");
        }
	}
}
