package co.com.directv.sdii.facade.assign.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.assign.DealerDetailBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.assign.DealerDetailFacadeBeanLocal;
import co.com.directv.sdii.model.vo.DealerDetailVO;
import co.com.directv.sdii.reports.dto.DealerDetailDTO;

/**
 * Implementación de la fachada de las operaciones Tipo CRUD DealerDetail
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.assign.DealerDetailFacadeLocal
 */
@Stateless(name="DealerDetailFacadeLocal",mappedName="ejb/DealerDetailFacadeLocal")
public class DealerDetailFacadeBean implements DealerDetailFacadeBeanLocal {

		
    @EJB(name="DealerDetailBusinessBeanLocal", beanInterface=DealerDetailBusinessBeanLocal.class)
    private DealerDetailBusinessBeanLocal businessDealerDetail;
    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.DealerDetailFacadeLocal#getAllDealerDetails()
     */
    public List<DealerDetailVO> getAllDealerDetails() throws BusinessException {
    	return businessDealerDetail.getAllDealerDetails();
    }

    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.DealerDetailFacadeLocal#getDealerDetailsByID(java.lang.Long)
     */
    public DealerDetailVO getDealerDetailByID(Long id) throws BusinessException {
    	return businessDealerDetail.getDealerDetailByID(id);
    }

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.DealerDetailFacadeLocal#createDealerDetail(co.com.directv.sdii.model.vo.DealerDetailVO)
	 */
	public void createDealerDetail(DealerDetailVO obj) throws BusinessException {
		businessDealerDetail.createDealerDetail(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.DealerDetailFacadeLocal#updateDealerDetail(co.com.directv.sdii.model.vo.DealerDetailVO)
	 */
	public void updateDealerDetail(DealerDetailVO obj) throws BusinessException {
		businessDealerDetail.updateDealerDetail(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.DealerDetailFacadeLocal#deleteDealerDetail(co.com.directv.sdii.model.vo.DealerDetailVO)
	 */
	public void deleteDealerDetail(DealerDetailVO obj) throws BusinessException {
		businessDealerDetail.deleteDealerDetail(obj);
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.assign.DealerDetailFacadeBeanLocal#getAllDealerDetailsByCountry(java.lang.Long)
	 */
	@Override
	public List<DealerDetailDTO> getAllDealerDetailsByCountry(Long countryId)
			throws BusinessException {
		return businessDealerDetail.getAllDealerDetailsByCountry(countryId);
	}
}
