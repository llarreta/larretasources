package co.com.directv.sdii.facade.stock.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.stock.RefInconsistencyBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.stock.RefInconsistencyFacadeBeanLocal;
import co.com.directv.sdii.model.pojo.collection.RefInconsistencyResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.vo.RefInconsistencyVO;
import co.com.directv.sdii.model.vo.ReferenceVO;
import co.com.directv.sdii.model.vo.ReportedElementVO;
import co.com.directv.sdii.ws.model.dto.ReportedElementForValidationDTO;

/**
 * Implementación de la fachada de las operaciones Tipo CRUD RefInconsistency
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.stock.RefInconsistencyFacadeLocal
 */
@Stateless(name="RefInconsistencyFacadeLocal",mappedName="ejb/RefInconsistencyFacadeLocal")
public class RefInconsistencyFacadeBean implements RefInconsistencyFacadeBeanLocal {

		
    @EJB(name="RefInconsistencyBusinessBeanLocal", beanInterface=RefInconsistencyBusinessBeanLocal.class)
    private RefInconsistencyBusinessBeanLocal businessRefInconsistency;

   
    /* (non-Javadoc)
     * @see #{$business_package_name$}.RefInconsistencyFacadeLocal#getAllRefInconsistencys()
     */
    public List<RefInconsistencyVO> getAllRefInconsistencys() throws BusinessException {
    	return businessRefInconsistency.getAllRefInconsistencys();
    }

    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.RefInconsistencyFacadeLocal#getRefInconsistencysByID(java.lang.Long)
     */
    public RefInconsistencyVO getRefInconsistencyByID(Long id) throws BusinessException {
    	return businessRefInconsistency.getRefInconsistencyByID(id);
    }

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.RefInconsistencyFacadeBeanLocal#createRefInconsistency(co.com.directv.sdii.model.vo.RefInconsistencyVO, boolean, boolean)
	 */
    public void saveRefInconsistency(RefInconsistencyVO refInconsistency, List<ReportedElementVO> reportedElements, Long userId) throws BusinessException {
		businessRefInconsistency.saveRefInconsistency(refInconsistency, reportedElements, userId);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.RefInconsistencyFacadeLocal#updateRefInconsistency(co.com.directv.sdii.model.vo.RefInconsistencyVO)
	 */
	public void updateRefInconsistency(RefInconsistencyVO obj) throws BusinessException {
		businessRefInconsistency.updateRefInconsistency(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.RefInconsistencyFacadeLocal#deleteRefInconsistency(co.com.directv.sdii.model.vo.RefInconsistencyVO)
	 */
	public void deleteRefInconsistency(RefInconsistencyVO obj) throws BusinessException {
		businessRefInconsistency.deleteRefInconsistency(obj);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.RefInconsistencyFacadeBeanLocal#getRefInconsistencysByReferenceID(java.lang.Long)
	 */
	public List<RefInconsistencyVO> getRefInconsistencysByReferenceID(Long refID)
			throws BusinessException {
		return businessRefInconsistency.getRefInconsistencysByReferenceID(refID);
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.RefInconsistencyFacadeBeanLocal#getReferenceInconsistenciesOpenedByReferenceId(java.lang.Long)
	 */
	public RefInconsistencyResponse getReferenceInconsistenciesOpenedByReferenceId(Long refID, RequestCollectionInfo requestCollInfo) throws BusinessException {
		return businessRefInconsistency.getReferenceInconsistenciesOpenedByReferenceId(refID, requestCollInfo);
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.RefInconsistencyFacadeBeanLocal#getReferenceInconsistenciesClosedByReferenceId(java.lang.Long)
	 */
	public RefInconsistencyResponse getReferenceInconsistenciesClosedByReferenceId(Long refID,RequestCollectionInfo requestCollInfo) throws BusinessException {
		return businessRefInconsistency.getReferenceInconsistenciesClosedByReferenceId(refID, requestCollInfo);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.RefInconsistencyFacadeBeanLocal#validateReportedElementsForLessElementsInRefInc(java.util.List)
	 */
	@Override
	public void validateReportedElementsForLessElementsInRefInc(
			List<ReportedElementForValidationDTO> elementsToValidate)
			throws BusinessException {
		businessRefInconsistency.validateReportedElementsForLessElementsInRefInc(elementsToValidate);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.RefInconsistencyFacadeBeanLocal#getGeneratedReferencesByRefIncId(java.lang.Long)
	 */
	@Override
	public List<ReferenceVO> getGeneratedReferencesByRefIncId(Long refIncId)
			throws BusinessException {
		return businessRefInconsistency.getGeneratedReferencesByRefIncId(refIncId);
	}
	
}
