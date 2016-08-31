package co.com.directv.sdii.facade.assign.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.assign.DealerBuldingBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.assign.DealerBuldingFacadeBeanLocal;
import co.com.directv.sdii.model.pojo.collection.DealerBuildingResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.vo.DealerBuldingVO;
import co.com.directv.sdii.reports.dto.DealerBuildingDTO;

/**
 * Implementación de la fachada de las operaciones Tipo CRUD DealerBulding
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.assign.DealerBuldingFacadeLocal
 */
@Stateless(name="DealerBuldingFacadeLocal",mappedName="ejb/DealerBuldingFacadeLocal")
public class DealerBuldingFacadeBean implements DealerBuldingFacadeBeanLocal {

		
    @EJB(name="DealerBuldingBusinessBeanLocal", beanInterface=DealerBuldingBusinessBeanLocal.class)
    private DealerBuldingBusinessBeanLocal businessDealerBulding;
    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.DealerBuldingFacadeLocal#getAllDealerBuldings()
     */
    public List<DealerBuldingVO> getAllDealerBuldings() throws BusinessException {
    	return businessDealerBulding.getAllDealerBuldings();
    }
    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.DealerBuldingFacadeLocal#getDealerBuldingsByID(java.lang.Long)
     */
    public DealerBuldingVO getDealerBuldingByID(Long id) throws BusinessException {
    	return businessDealerBulding.getDealerBuldingByID(id);
    }

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.DealerBuldingFacadeLocal#createDealerBulding(co.com.directv.sdii.model.vo.DealerBuldingVO)
	 */
	public void createDealerBulding(DealerBuldingVO obj) throws BusinessException {
		businessDealerBulding.createDealerBulding(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.DealerBuldingFacadeLocal#updateDealerBulding(co.com.directv.sdii.model.vo.DealerBuldingVO)
	 */
	public void updateDealerBulding(DealerBuldingVO obj) throws BusinessException {
		businessDealerBulding.updateDealerBulding(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.DealerBuldingFacadeLocal#deleteDealerBulding(co.com.directv.sdii.model.vo.DealerBuldingVO)
	 */
	public void deleteDealerBulding(DealerBuldingVO obj) throws BusinessException {
		businessDealerBulding.deleteDealerBulding(obj);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.assign.DealerBuldingFacadeBeanLocal#getDealerBuildingsByPostalCodeId(java.lang.Long, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	public DealerBuildingResponse getDealerBuildingsByPostalCodeId(Long postalCodeId, RequestCollectionInfo requestCollectionInfo)throws BusinessException {
		return businessDealerBulding.getDealerBuildingsByPostalCodeId(postalCodeId, requestCollectionInfo);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.assign.DealerBuldingFacadeBeanLocal#updateDealerBuildings(java.util.List)
	 */
	@Override
	public void updateDealerBuildings(List<DealerBuldingVO> dealerBuildings) throws BusinessException {
		businessDealerBulding.updateDealerBuildings(dealerBuildings);
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.assign.DealerBuldingFacadeBeanLocal#getDealerBuldingsByCountryAndActive(java.lang.Long)
	 */
	@Override
	public List<DealerBuildingDTO> getDealerBuldingsByCountryAndActive(
			Long idCountry) throws BusinessException {
		return businessDealerBulding.getDealerBuldingsByCountryAndActive(idCountry);
	}
	
}
