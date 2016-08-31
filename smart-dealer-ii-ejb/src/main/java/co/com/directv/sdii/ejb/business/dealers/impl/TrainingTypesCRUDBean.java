package co.com.directv.sdii.ejb.business.dealers.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.dealers.TrainingTypesCRUDBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.TrainingType;
import co.com.directv.sdii.model.vo.TrainingTypeVO;
import co.com.directv.sdii.persistence.dao.dealers.TrainingTypesDAOLocal;

/**
 * 
 * EJB que implementa las operaciones Tipo CRUD (Read) de la
 * Entidad TrainingTypes
 * Solo implementa operaciones de consulta
 * 
 * Fecha de Creaci�n: Mar 5, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.dealers.TrainingTypesDAOLocal
 * @see co.com.directv.sdii.ejb.business.dealers.TrainingTypesCRUDBeanLocal
 * 
 */
@Stateless
public class TrainingTypesCRUDBean extends BusinessBase implements TrainingTypesCRUDBeanLocal {

	@EJB
	private TrainingTypesDAOLocal dao;

    private final static Logger log = UtilsBusiness.getLog4J(TrainingTypesCRUDBean.class);

	public List<TrainingTypeVO> getAllTrainingTypes() throws BusinessException {
		log.debug("== Inicia getAllTrainingTypes/TrainingTypesCRUDBean ==");
        try {
          
        	List<TrainingType> resultPojo = dao.getAllTrainingTypes();

        	if(resultPojo == null)
        		return null;        	
        	
        	List<TrainingTypeVO> resultVo = UtilsBusiness.convertList(resultPojo, TrainingTypeVO.class);
            return resultVo;

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getAllTrainingTypes/MediaContactTypesCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllTrainingTypes/CrewStatusCRUDBean ==");
        }
	}

	public TrainingTypeVO getTrainingTypesByCode(String code) throws BusinessException {
		log.debug("== Inicia getTrainingTypesByCode/TrainingTypesCRUDBean ==");
        try {
          
        	TrainingType trainingType = dao.getTrainingTypesByCode(code);

        	if(trainingType == null)
        		throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());        	
        	
        	TrainingTypeVO resultVo = UtilsBusiness.copyObject(TrainingTypeVO.class, trainingType);
            return resultVo;

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getTrainingTypesByCode/MediaContactTypesCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getTrainingTypesByCode/CrewStatusCRUDBean ==");
        }
	}

	public TrainingTypeVO getTrainingTypesByID(Long id) throws BusinessException {
		log.debug("== Inicia getTrainingTypesByID/TrainingTypesCRUDBean ==");
        try {
          
        	TrainingType trainingType = dao.getTrainingTypesByID(id);

        	if(trainingType == null)
        		throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());        	
        	
        	TrainingTypeVO resultVo = UtilsBusiness.copyObject(TrainingTypeVO.class, trainingType);
            return resultVo;

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getTrainingTypesByID/MediaContactTypesCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getTrainingTypesByID/CrewStatusCRUDBean ==");
        }
	}

	public List<TrainingTypeVO> getAllTrainingTypesByCountryId(Long countryId) throws BusinessException {
		log.debug("== Inicia getAllTrainingTypesByCountryId/TrainingTypesCRUDBean ==");
        try {
          
        	List<TrainingType> resultPojo = dao.getAllTrainingTypesByCountryId(countryId);

        	List<TrainingTypeVO> resultVo = UtilsBusiness.convertList(resultPojo, TrainingTypeVO.class);
            return resultVo;

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getAllTrainingTypesByCountryId/MediaContactTypesCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllTrainingTypesByCountryId/CrewStatusCRUDBean ==");
        }
	}
}
