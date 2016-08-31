package co.com.directv.sdii.facade.assign.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.assign.HistoDealerCoverageBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.assign.HistoDealerCoverageFacadeBeanLocal;
import co.com.directv.sdii.model.vo.HistoDealerCoverageVO;

/**
 * Implementación de la fachada de las operaciones Tipo CRUD HistoDealerCoverage
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.assign.HistoDealerCoverageFacadeLocal
 */
@Stateless(name="HistoDealerCoverageFacadeLocal",mappedName="ejb/HistoDealerCoverageFacadeLocal")
public class HistoDealerCoverageFacadeBean implements HistoDealerCoverageFacadeBeanLocal {

		
    @EJB(name="HistoDealerCoverageBusinessBeanLocal", beanInterface=HistoDealerCoverageBusinessBeanLocal.class)
    private HistoDealerCoverageBusinessBeanLocal businessHistoDealerCoverage;
    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.HistoDealerCoverageFacadeLocal#getAllHistoDealerCoverages()
     */
    public List<HistoDealerCoverageVO> getAllHistoDealerCoverages() throws BusinessException {
    	return businessHistoDealerCoverage.getAllHistoDealerCoverages();
    }

    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.HistoDealerCoverageFacadeLocal#getHistoDealerCoveragesByID(java.lang.Long)
     */
    public HistoDealerCoverageVO getHistoDealerCoverageByID(Long id) throws BusinessException {
    	return businessHistoDealerCoverage.getHistoDealerCoverageByID(id);
    }

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.HistoDealerCoverageFacadeLocal#createHistoDealerCoverage(co.com.directv.sdii.model.vo.HistoDealerCoverageVO)
	 */
	public void createHistoDealerCoverage(HistoDealerCoverageVO obj) throws BusinessException {
		businessHistoDealerCoverage.createHistoDealerCoverage(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.HistoDealerCoverageFacadeLocal#updateHistoDealerCoverage(co.com.directv.sdii.model.vo.HistoDealerCoverageVO)
	 */
	public void updateHistoDealerCoverage(HistoDealerCoverageVO obj) throws BusinessException {
		businessHistoDealerCoverage.updateHistoDealerCoverage(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.HistoDealerCoverageFacadeLocal#deleteHistoDealerCoverage(co.com.directv.sdii.model.vo.HistoDealerCoverageVO)
	 */
	public void deleteHistoDealerCoverage(HistoDealerCoverageVO obj) throws BusinessException {
		businessHistoDealerCoverage.deleteHistoDealerCoverage(obj);
	}
}
