package co.com.directv.sdii.facade.assign.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.assign.DealerServiceSubCategoryBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.assign.DealerServiceSubCategoryFacadeBeanLocal;
import co.com.directv.sdii.model.vo.DealerServiceSubCategoryVO;
import co.com.directv.sdii.model.vo.ServiceTypeVO;

/**
 * Implementación de la fachada de las operaciones Tipo CRUD DealerServiceSubCategory
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.assign.DealerServiceSubCategoryFacadeLocal
 */
@Stateless(name="DealerServiceSubCategoryFacadeLocal",mappedName="ejb/DealerServiceSubCategoryFacadeLocal")
public class DealerServiceSubCategoryFacadeBean implements DealerServiceSubCategoryFacadeBeanLocal {

		
    @EJB(name="DealerServiceSubCategoryBusinessBeanLocal", beanInterface=DealerServiceSubCategoryBusinessBeanLocal.class)
    private DealerServiceSubCategoryBusinessBeanLocal businessDealerServiceSubCategory;
    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.DealerServiceSubCategoryFacadeLocal#getAllDealerServiceSubCategorys()
     */
    public List<DealerServiceSubCategoryVO> getAllDealerServiceSubCategorys() throws BusinessException {
    	return businessDealerServiceSubCategory.getAllDealerServiceSubCategorys();
    }

    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.DealerServiceSubCategoryFacadeLocal#getDealerServiceSubCategorysByID(java.lang.Long)
     */
    public DealerServiceSubCategoryVO getDealerServiceSubCategoryByID(Long id) throws BusinessException {
    	return businessDealerServiceSubCategory.getDealerServiceSubCategoryByID(id);
    }

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.DealerServiceSubCategoryFacadeLocal#createDealerServiceSubCategory(co.com.directv.sdii.model.vo.DealerServiceSubCategoryVO)
	 */
	public void createDealerServiceSubCategory(DealerServiceSubCategoryVO obj) throws BusinessException {
		businessDealerServiceSubCategory.createDealerServiceSubCategory(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.DealerServiceSubCategoryFacadeLocal#updateDealerServiceSubCategory(co.com.directv.sdii.model.vo.DealerServiceSubCategoryVO)
	 */
	public void updateDealerServiceSubCategory(DealerServiceSubCategoryVO obj) throws BusinessException {
		businessDealerServiceSubCategory.updateDealerServiceSubCategory(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.DealerServiceSubCategoryFacadeLocal#deleteDealerServiceSubCategory(co.com.directv.sdii.model.vo.DealerServiceSubCategoryVO)
	 */
	public void deleteDealerServiceSubCategory(DealerServiceSubCategoryVO obj) throws BusinessException {
		businessDealerServiceSubCategory.deleteDealerServiceSubCategory(obj);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.assign.DealerServiceSubCategoryFacadeBeanLocal#getAllConfigurationActiveByDealerId(java.lang.Long)
	 */
	@Override
	public List<DealerServiceSubCategoryVO> getAllConfigurationActiveByDealerIdAndCountryId(Long dealerId, Long countryId) throws BusinessException {
		return businessDealerServiceSubCategory.getAllConfigurationActiveByDealerIdAndCountryId(dealerId, countryId);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.assign.DealerServiceSubCategoryFacadeBeanLocal#getDealerServiceSubCategoriesTree(java.lang.Long, java.lang.Long)
	 */
	@Override
	public List<ServiceTypeVO> getDealerServiceSubCategoriesTree(Long dealerId,
			Long countryId) throws BusinessException {
		return businessDealerServiceSubCategory.getDealerServiceSubCategoriesTree(dealerId, countryId);
	}
	

	/*
	 * Req-0096 - Requerimiento Consolidado Asignador
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.assign.DealerServiceSubCategoryFacadeBeanLocal#getDealerServiceSubCategoriesTreeByDealerIdAndCustomerClassIdAndBusinessAreaId(java.lang.Long, java.lang.Long, Long businessAreaId, Long businessAreaId)
	 */
	@Override	
	public List<ServiceTypeVO> getDealerServiceSubCategoriesTreeByBusinessAreaId(	Long dealerId, 
																					Long countryId,  
																					Long businessAreaId,
																					Long customerCategoryId) 
    throws BusinessException{
		return businessDealerServiceSubCategory.getDealerServiceSubCategoriesTreeByBusinessAreaId(	dealerId, 
																									countryId,
																								   businessAreaId,
																								   customerCategoryId);
	}
	
}
