package co.com.directv.sdii.facade.assign.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.assign.DealerCoverageBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.assign.DealerCoverageFacadeBeanLocal;
import co.com.directv.sdii.model.pojo.collection.DealerCoverageResponse;
import co.com.directv.sdii.model.pojo.collection.PostalCodeResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.vo.DealerCoverageVO;
import co.com.directv.sdii.reports.dto.DealerCoverageDTO;

/**
 * Implementación de la fachada de las operaciones Tipo CRUD DealerCoverage
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.assign.DealerCoverageFacadeLocal
 */
@Stateless(name="DealerCoverageFacadeLocal",mappedName="ejb/DealerCoverageFacadeLocal")
public class DealerCoverageFacadeBean implements DealerCoverageFacadeBeanLocal {

		
    @EJB(name="DealerCoverageBusinessBeanLocal", beanInterface=DealerCoverageBusinessBeanLocal.class)
    private DealerCoverageBusinessBeanLocal businessDealerCoverage;
    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.DealerCoverageFacadeLocal#getAllDealerCoverages()
     */
    public List<DealerCoverageVO> getAllDealerCoverages() throws BusinessException {
    	return businessDealerCoverage.getAllDealerCoverages();
    }

    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.DealerCoverageFacadeLocal#getDealerCoveragesByID(java.lang.Long)
     */
    public DealerCoverageVO getDealerCoverageByID(Long id) throws BusinessException {
    	return businessDealerCoverage.getDealerCoverageByID(id);
    }

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.DealerCoverageFacadeLocal#createDealerCoverage(co.com.directv.sdii.model.vo.DealerCoverageVO)
	 */
	public void createDealerCoverage(DealerCoverageVO obj) throws BusinessException {
		businessDealerCoverage.createDealerCoverage(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.DealerCoverageFacadeLocal#updateDealerCoverage(co.com.directv.sdii.model.vo.DealerCoverageVO)
	 */
	public void updateDealerCoverage(DealerCoverageVO obj) throws BusinessException {
		businessDealerCoverage.updateDealerCoverage(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.DealerCoverageFacadeLocal#deleteDealerCoverage(co.com.directv.sdii.model.vo.DealerCoverageVO)
	 */
	public void deleteDealerCoverage(DealerCoverageVO obj) throws BusinessException {
		businessDealerCoverage.deleteDealerCoverage(obj);
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.assign.DealerCoverageFacadeBeanLocal#getAllActiveByPostalCode(co.com.directv.sdii.model.pojo.PostalCode)
	 */
	@Override
	public List<DealerCoverageVO> getAllActiveByPostalCode(Long postalCodeId, String isSeller, String isInstaller) throws BusinessException {
		return businessDealerCoverage.getAllActiveByPostalCode(postalCodeId, isSeller, isInstaller);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.assign.DealerCoverageFacadeBeanLocal#updateDealersCoverage(java.util.List)
	 */
	@Override
	public void updateDealersCoverage(List<DealerCoverageVO> dealersCoverage) throws BusinessException{
		businessDealerCoverage.updateDealersCoverage(dealersCoverage);
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.assign.DealerCoverageFacadeBeanLocal#getMicrozonesActiveByDealerId(java.lang.Long)
	 */
	@Override
	public PostalCodeResponse getPostalCodesActiveByDealerId(Long dealerId, RequestCollectionInfo requestCollectionInfo) throws BusinessException {
		return businessDealerCoverage.getPostalCodesActiveByDealerId(dealerId, requestCollectionInfo);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.assign.DealerCoverageFacadeBeanLocal#getAllActiveByDealerId(java.lang.Long)
	 */
	@Override
	public DealerCoverageResponse getAllActiveByDealerId(Long dealerId, RequestCollectionInfo requestCollectionInfo) throws BusinessException {
		return businessDealerCoverage.getAllActiveByDealerId(dealerId, requestCollectionInfo);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.assign.DealerCoverageFacadeBeanLocal#getPostalCodesWitoutCoverageByDealerIdAndCityId(java.lang.Long, java.lang.Long, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	public PostalCodeResponse getPostalCodesWithoutCoverageByDealerIdAndCityId(Long dealerId, Long cityId,
			RequestCollectionInfo requestCollectionInfo) throws BusinessException {
		return businessDealerCoverage.getPostalCodesWithoutCoverageByDealerIdAndCityId(dealerId, cityId, requestCollectionInfo);
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.assign.DealerCoverageFacadeBeanLocal#getDealerCoverageByDealerIdAndPostalCodeId(java.lang.Long, java.lang.Long)
	 */
	@Override
	public DealerCoverageVO getDealerCoverageByDealerIdAndPostalCodeId(Long dealerId, Long postalCodeId) throws BusinessException {
		return businessDealerCoverage.getDealerCoverageByDealerIdAndPostalCodeId(dealerId, postalCodeId);
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.assign.DealerCoverageFacadeBeanLocal#getAllDealerCoveragesByActiveStatus()
	 */
	@Override
	public List<DealerCoverageDTO> getAllDealerCoverageByCountryAndActiveStatus(Long countryID)throws BusinessException {
		return businessDealerCoverage.getAllDealerCoverageByCountryAndActiveStatus(countryID);
	}
	
}
