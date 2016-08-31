package co.com.directv.sdii.facade.dealers.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.dealers.TrainingTypesCRUDBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.dealers.TrainingTypesFacadeBeanLocal;
import co.com.directv.sdii.model.pojo.TrainingType;
import co.com.directv.sdii.model.vo.TrainingTypeVO;

/**
 * 
 * Facade para la gestion de las operaciones del CRUD
 * de la entidad TrainingTypes 
 * 
 * Fecha de Creaci�n: Mar 8, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.ejb.business.dealers.TrainingTypesCRUDBeanLocal
 * @see co.com.directv.sdii.facade.dealers.TrainingTypesFacadeBeanLocal
 */
@Stateless(name="TrainingTypesFacadeBeanLocal",mappedName="ejb/TrainingTypesFacadeBeanLocal")
public class TrainingTypesFacadeBean implements TrainingTypesFacadeBeanLocal {
	
	@EJB(name="TrainingTypesCRUDBeanLocal",beanInterface=TrainingTypesCRUDBeanLocal.class)
	private TrainingTypesCRUDBeanLocal business;

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.dealers.TrainingTypesFacadeBeanLocal#getAllTrainingTypes()
	 */
	public List<TrainingTypeVO> getAllTrainingTypes() throws BusinessException {
		return business.getAllTrainingTypes();
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.dealers.TrainingTypesFacadeBeanLocal#getTrainingTypesByCode(java.lang.String)
	 */
	public TrainingType getTrainingTypesByCode(String code) throws BusinessException {
		return business.getTrainingTypesByCode(code);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.dealers.TrainingTypesFacadeBeanLocal#getTrainingTypesByID(java.lang.Long)
	 */
	public TrainingType getTrainingTypesByID(Long id) throws BusinessException {
		return business.getTrainingTypesByID(id);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.dealers.TrainingTypesFacadeBeanLocal#getAllTrainingTypesByCountryId(java.lang.Long)
	 */
	public List<TrainingTypeVO> getAllTrainingTypesByCountryId(Long countryId)
			throws BusinessException {
		return business.getAllTrainingTypesByCountryId(countryId);
	}
}
