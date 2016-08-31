package co.com.directv.sdii.facade.dealers.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.dealers.TrainingCRUDBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.dealers.TrainingFacadeBeanLocal;
import co.com.directv.sdii.model.vo.TrainingVO;

/**
 *
 * Facade para la gestion de las operaciones del CRUD
 * de la entidad Training
 *
 * Fecha de Creación: Mar 5, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 *
 * @see co.com.directv.sdii.ejb.business.dealers.TrainingCRUDBeanLocal
 * @see co.com.directv.sdii.facade.dealers.TrainingFacadeBeanLocal
 */
@Stateless(name="TrainingFacadeBeanLocal",mappedName="ejb/TrainingFacadeBeanLocal")
public class TrainingFacadeBean implements TrainingFacadeBeanLocal {

	@EJB(name="TrainingCRUDBeanLocal",beanInterface=TrainingCRUDBeanLocal.class)
	private TrainingCRUDBeanLocal trainingCrudBean;

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.dealers.TrainingFacadeBeanLocal#createTraining(co.com.directv.sdii.model.vo.TrainingVO)
	 */
	public void createTraining(TrainingVO obj) throws BusinessException {
		trainingCrudBean.createTraining(obj);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.dealers.TrainingFacadeBeanLocal#deleteTraining(co.com.directv.sdii.model.vo.TrainingVO)
	 */
	public void deleteTraining(TrainingVO obj) throws BusinessException {
		trainingCrudBean.deleteTraining(obj);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.dealers.TrainingFacadeBeanLocal#getAllTraining()
	 */
	public List<TrainingVO> getAllTraining() throws BusinessException {
		return trainingCrudBean.getAllTraining();
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.dealers.TrainingFacadeBeanLocal#getTrainingByID(java.lang.Long)
	 */
	public TrainingVO getTrainingByID(Long id) throws BusinessException {
		return trainingCrudBean.getTrainingByID(id);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.dealers.TrainingFacadeBeanLocal#updateTraining(co.com.directv.sdii.model.vo.TrainingVO)
	 */
	public void updateTraining(TrainingVO obj) throws BusinessException {
		trainingCrudBean.updateTraining(obj);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.dealers.TrainingFacadeBeanLocal#deleteTrainingsByEmployeeId(java.lang.Long)
	 */
	public void deleteTrainingsByEmployeeId(Long employeeId) throws BusinessException {
		trainingCrudBean.deleteTrainingsByEmployeeId(employeeId);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.dealers.TrainingFacadeBeanLocal#getTrainingsByEmployeeId(java.lang.Long)
	 */
	public List<TrainingVO> getTrainingsByEmployeeId(Long idEmployee)throws BusinessException {
		return trainingCrudBean.getTrainingsByEmployeeId(idEmployee);
	}
}
