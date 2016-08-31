package co.com.directv.sdii.facade.assign.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.assign.DealerConfCoverageBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.assign.DealerConfCoverageFacadeBeanLocal;
import co.com.directv.sdii.model.pojo.collection.DealerCoverageResponse;
import co.com.directv.sdii.model.pojo.collection.PostalCodeResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.reports.dto.DealerConfCoverageDTO;

/**
 * Req-0096 - Requerimiento Consolidado Asignador
 * 
 * Implementación de la fachada de las operaciones Tipo CRUD DealerConfCoverage
 * 
 * Fecha de Creación: oct 04, 2013
 * @author ialessan
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.assign.DealerConfCoverageFacadeBeanLocal
 */
@Stateless(name="DealerConfCoverageFacadeBeanLocal",mappedName="ejb/DealerConfCoverageFacadeBeanLocal")
public class DealerConfCoverageFacadeBean implements DealerConfCoverageFacadeBeanLocal {

    
    @EJB(name="DealerConfCoverageBusinessBeanLocal", beanInterface=DealerConfCoverageBusinessBeanLocal.class)
    private DealerConfCoverageBusinessBeanLocal businessDealerConfCoverage;
	
	/*
	 * Req-0096 - Requerimiento Consolidado Asignador
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.assign.DealerCoverageFacadeBeanLocal#getAllActiveByDealerId(java.lang.Long)
	 * 
     * release_4.2.1.5_Spira 28731 – Quitar filtros de Departamento y Ciudad al “Cargar Configuración” – solapa de cobertura
     * modificación
     * ialessan
     * marzo 2014 
	 */
	@Override
	public DealerCoverageResponse getDealerConfCoverageByDealerIdCustomerCategoryIdAreaId(Long dealerId, Long cityId, RequestCollectionInfo requestCollectionInfo, 
			Long customerCategoryId, Long businessAreaId) throws BusinessException{
		return businessDealerConfCoverage.getDealerConfCoverageByDealerIdCustomerCategoryIdAreaId(dealerId, requestCollectionInfo, customerCategoryId, businessAreaId);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.assign.DealerCoverageFacadeBeanLocal#getPostalCodesWitoutCoverageByDealerIdAndCityId(java.lang.Long, java.lang.Long, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	public PostalCodeResponse getPostalCodesWithoutConfCoverageByDealerIdAndCityId(Long dealerId, Long cityId,
			RequestCollectionInfo requestCollectionInfo, Long customerCategoryId, Long businessAreaId) throws BusinessException {
		return businessDealerConfCoverage.getPostalCodesWithoutConfCoverageByDealerIdAndCityId(dealerId, cityId, requestCollectionInfo, customerCategoryId, businessAreaId);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.assign.DealerCoverageFacadeBeanLocal#getAllDealerCoveragesByActiveStatus()
	 */
	@Override
	public List<DealerConfCoverageDTO> getAllDealerConfCoverageByCountryAndActiveStatus(Long countryID)throws BusinessException {
		return businessDealerConfCoverage.getAllDealerConfCoverageByCountryAndActiveStatus(countryID);
	}
}
