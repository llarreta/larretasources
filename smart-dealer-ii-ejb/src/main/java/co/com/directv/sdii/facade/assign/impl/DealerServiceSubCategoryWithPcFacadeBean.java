package co.com.directv.sdii.facade.assign.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.assign.DealerServiceSubCategoryWithPcBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.assign.DealerServiceSubCategoryWithPcFacadeBeanLocal;
import co.com.directv.sdii.model.vo.DealerServiceSubCategoryWithPcVO;
import co.com.directv.sdii.model.vo.ServiceTypeVO;
import co.com.directv.sdii.reports.dto.DealerServiceSubCategoryWithPcDTO;

/**
 * Implementación de la fachada de las operaciones Tipo CRUD DealerServiceSubCategoryWithPc
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.assign.DealerServiceSubCategoryWithPcFacadeLocal
 */
@Stateless(name="DealerServiceSubCategoryWithPcFacadeLocal",mappedName="ejb/DealerServiceSubCategoryWithPcFacadeLocal")
public class DealerServiceSubCategoryWithPcFacadeBean implements DealerServiceSubCategoryWithPcFacadeBeanLocal {

		
    @EJB(name="DealerServiceSubCategoryWithPcBusinessBeanLocal", beanInterface=DealerServiceSubCategoryWithPcBusinessBeanLocal.class)
    private DealerServiceSubCategoryWithPcBusinessBeanLocal businessDealerServiceSubCategoryWithPc;
    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.DealerServiceSubCategoryWithPcFacadeLocal#getAllDealerServiceSubCategoryWithPcs()
     */
    public List<DealerServiceSubCategoryWithPcVO> getAllDealerServiceSubCategoryWithPcs() throws BusinessException {
    	return businessDealerServiceSubCategoryWithPc.getAllDealerServiceSubCategoryWithPcs();
    }

    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.DealerServiceSubCategoryWithPcFacadeLocal#getDealerServiceSubCategoryWithPcsByID(java.lang.Long)
     */
    public DealerServiceSubCategoryWithPcVO getDealerServiceSubCategoryWithPcByID(Long id) throws BusinessException {
    	return businessDealerServiceSubCategoryWithPc.getDealerServiceSubCategoryWithPcByID(id);
    }

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.DealerServiceSubCategoryWithPcFacadeLocal#createDealerServiceSubCategoryWithPc(co.com.directv.sdii.model.vo.DealerServiceSubCategoryWithPcVO)
	 */
	public void createDealerServiceSubCategoryWithPc(DealerServiceSubCategoryWithPcVO obj) throws BusinessException {
		businessDealerServiceSubCategoryWithPc.createDealerServiceSubCategoryWithPc(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.DealerServiceSubCategoryWithPcFacadeLocal#updateDealerServiceSubCategoryWithPc(co.com.directv.sdii.model.vo.DealerServiceSubCategoryWithPcVO)
	 */
	public void updateDealerServiceSubCategoryWithPc(DealerServiceSubCategoryWithPcVO obj) throws BusinessException {
		businessDealerServiceSubCategoryWithPc.updateDealerServiceSubCategoryWithPc(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.DealerServiceSubCategoryWithPcFacadeLocal#deleteDealerServiceSubCategoryWithPc(co.com.directv.sdii.model.vo.DealerServiceSubCategoryWithPcVO)
	 */
	public void deleteDealerServiceSubCategoryWithPc(DealerServiceSubCategoryWithPcVO obj) throws BusinessException {
		businessDealerServiceSubCategoryWithPc.deleteDealerServiceSubCategoryWithPc(obj);
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.assign.DealerServiceSubCategoryWithPcFacadeBeanLocal#getDealerServSubCatPcByCountryAndActive(java.lang.Long)
	 */
	@Override
	public List<DealerServiceSubCategoryWithPcDTO> getDealerServSubCatPcByCountryAndActive(
			Long idCountry) throws BusinessException {
		return businessDealerServiceSubCategoryWithPc.getDealerServSubCatPcByCountryAndActive(idCountry);
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.assign.DealerServiceSubCategoryWithPcFacadeBeanLocal#getDealerServiceSubCategoriesWithPcTree(java.lang.Long)
	 */
	@Override
	public List<ServiceTypeVO> getDealerServiceSubCategoriesWithPcTree(Long dealerCoverageId) throws BusinessException {
		return businessDealerServiceSubCategoryWithPc.getDealerServiceSubCategoriesWithPcTree(dealerCoverageId);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.assign.DealerServiceSubCategoryWithPcFacadeBeanLocal#updateDealerServiceSubCategoriesWithPcConfiguration(java.util.List)
	 */
	@Override
	public void updateDealerServiceSubCategoriesWithPcConfiguration(
			List<DealerServiceSubCategoryWithPcVO> dealerServiceSubCategoriesWithPc)
			throws BusinessException {
		businessDealerServiceSubCategoryWithPc.updateDealerServiceSubCategoriesWithPcConfiguration(dealerServiceSubCategoriesWithPc);
	}

}
