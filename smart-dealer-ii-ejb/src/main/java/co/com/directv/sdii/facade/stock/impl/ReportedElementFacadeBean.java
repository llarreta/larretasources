package co.com.directv.sdii.facade.stock.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.stock.ReportedElementBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.stock.ReportedElementFacadeBeanLocal;
import co.com.directv.sdii.model.vo.ReportedElementVO;

/**
 * Implementación de la fachada de las operaciones Tipo CRUD ReportedElement
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.stock.ReportedElementFacadeLocal
 */
@Stateless(name="ReportedElementFacadeLocal",mappedName="ejb/ReportedElementFacadeLocal")
public class ReportedElementFacadeBean implements ReportedElementFacadeBeanLocal {

		
    @EJB(name="ReportedElementBusinessBeanLocal", beanInterface=ReportedElementBusinessBeanLocal.class)
    private ReportedElementBusinessBeanLocal businessReportedElement;
    
   
    /* (non-Javadoc)
     * @see #{$business_package_name$}.ReportedElementFacadeLocal#getAllReportedElements()
     */
    public List<ReportedElementVO> getAllReportedElements() throws BusinessException {
    	return businessReportedElement.getAllReportedElements();
    }

    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.ReportedElementFacadeLocal#getReportedElementsByID(java.lang.Long)
     */
    public ReportedElementVO getReportedElementByID(Long id) throws BusinessException {
    	return businessReportedElement.getReportedElementByID(id);
    }

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.ReportedElementFacadeLocal#createReportedElement(co.com.directv.sdii.model.vo.ReportedElementVO)
	 */
	public void createReportedElement(ReportedElementVO obj) throws BusinessException {
		businessReportedElement.createReportedElement(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.ReportedElementFacadeLocal#updateReportedElement(co.com.directv.sdii.model.vo.ReportedElementVO)
	 */
	public void updateReportedElement(ReportedElementVO obj) throws BusinessException {
		businessReportedElement.updateReportedElement(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.ReportedElementFacadeLocal#deleteReportedElement(co.com.directv.sdii.model.vo.ReportedElementVO)
	 */
	public void deleteReportedElement(ReportedElementVO obj) throws BusinessException {
		businessReportedElement.deleteReportedElement(obj);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.ReportedElementFacadeBeanLocal#getReportedElementsByRefInconsistencyId(java.lang.Long, boolean, boolean)
	 */
	@Override
	public List<ReportedElementVO> getReportedElementsByRefInconsistencyId(
			Long refInconsistencyId, boolean incluirSobrantes,
			boolean incluirFaltantes) throws BusinessException {
		return businessReportedElement.getReportedElementsByRefInconsistencyId(refInconsistencyId, incluirSobrantes, incluirFaltantes);
	}
	
}
