package co.com.directv.sdii.ejb.business.dealers;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.TrainingVO;

/**
 *
 * Interfaz de las operaciones Tipo CRUD (Create,Read, Update, Delete) de la
 * Entidad Training
 *
 * Fecha de Creación: Mar 5, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 *
 * @see
 */
@Local
public interface TrainingCRUDBeanLocal {

	public void createTraining(TrainingVO obj) throws BusinessException;

	public TrainingVO getTrainingByID(Long id) throws BusinessException;

	public void updateTraining(TrainingVO obj) throws BusinessException;

	public void deleteTraining(TrainingVO obj) throws BusinessException;

	public List<TrainingVO> getAllTraining() throws BusinessException;
	
	public List<TrainingVO> getTrainingsByEmployeeId(Long idEmployee) throws BusinessException;
	
	public void deleteTrainingsByEmployeeId(Long employeeId) throws BusinessException;
}
