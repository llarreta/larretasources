package co.com.directv.sdii.facade.dealers.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.dealers.DealersCRUDLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.dealers.DealersFacadeBeanLocal;
import co.com.directv.sdii.model.dto.DealerInfoRequestDTO;
import co.com.directv.sdii.model.dto.DealerInfoResponseDTO;
import co.com.directv.sdii.model.dto.DealersCodesDTO;
import co.com.directv.sdii.model.dto.InfoDealerCountryWarehouseDTO;
import co.com.directv.sdii.model.vo.DealerVO;

/**
 * Implementación de la Session Facade de las operaciones a realizar para el módulo de Dealers
 * @author Jimmy Vélez Muñoz
 */
@Stateless(name="DealersFacadeBeanLocal",mappedName="ejb/DealersFacadeBeanLocal")
public class DealersFacadeBean implements DealersFacadeBeanLocal {

    @EJB(name="DealersCRUDLocal",beanInterface=DealersCRUDLocal.class)
    private DealersCRUDLocal dealersCRUDBean;

    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.dealers.DealersFacadeBeanLocal#createDealer(co.com.directv.sdii.model.vo.DealerVO)
     */
    @Override
    public void createDealer(DealerVO pVo) throws BusinessException {
        dealersCRUDBean.createDealer(pVo);
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.dealers.DealersFacadeBeanLocal#getDealerByID(java.lang.Long)
     */
    @Override
    public DealerVO getDealerByID(Long id) throws BusinessException {
        return dealersCRUDBean.getDealerByID(id);
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.dealers.DealersFacadeBeanLocal#getDealerByName(java.lang.String)
     */
    @Override
    public List<DealerVO> getDealerByName(String name) throws BusinessException {
        return dealersCRUDBean.getDealerByName(name);
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.dealers.DealersFacadeBeanLocal#updateDealer(co.com.directv.sdii.model.vo.DealerVO)
     */
    @Override
    public void updateDealer(DealerVO pVo) throws BusinessException {
        dealersCRUDBean.updateDealer(pVo);
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.dealers.DealersFacadeBeanLocal#deleteDealer(co.com.directv.sdii.model.vo.DealerVO)
     */
    @Override
    public void deleteDealer(DealerVO obj) throws BusinessException {
        dealersCRUDBean.deleteDealer(obj);
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.dealers.DealersFacadeBeanLocal#getAll()
     */
    @Override
    public List<DealerVO> getAll() throws BusinessException {
        return dealersCRUDBean.getAll();
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.dealers.DealersFacadeBeanLocal#getDealerByDepotID(java.lang.String)
     */
    @Override
    public DealerVO getDealerByDepotID(String id) throws BusinessException {
        return dealersCRUDBean.getDealerByDepotID(id);
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.dealers.DealersFacadeBeanLocal#createDealersFromIBS(java.util.List)
     */
    @Override
    public void createDealersFromIBS(List<DealersCodesDTO> dealersDTO) throws BusinessException {
        dealersCRUDBean.createDealer(dealersDTO);
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.dealers.DealersFacadeBeanLocal#getDealerByDepotCodeOrDealerCode(java.lang.String, java.lang.Long)
     */
    @Override
    public DealerVO getDealerByDepotCodeOrDealerCode(String depotCode, Long dealerCode) throws BusinessException {
        return dealersCRUDBean.getDealerByDepotCodeOrDealerCode(depotCode, dealerCode);
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.dealers.DealersFacadeBeanLocal#getAllDealersOnlyBasicInfo()
     */
    @Override
	public List<DealerVO> getAllDealersOnlyBasicInfo() throws BusinessException {
		return dealersCRUDBean.getAllDealersOnlyBasicInfo();
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.dealers.DealersFacadeBeanLocal#getAllDealersByCountryId(java.lang.Long)
	 */
	@Override
	public List<DealerVO> getAllDealersByCountryId(Long countryId, String isSeller, String isInstaller)
			throws BusinessException {
		return dealersCRUDBean.getAllDealersByCountryId(countryId, isSeller, isInstaller);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.dealers.DealersFacadeBeanLocal#getDealerBranchesByDealerId(java.lang.Long, java.lang.String, java.lang.String)
	 */
	@Override
	public List<DealerVO> getDealerBranchesByDealerIdAndFilter(Long dealerId, String isSeller, String isInstaller)
			throws BusinessException {
		return dealersCRUDBean.getDealerBranchesByDealerIdAndFilter(dealerId, isSeller, isInstaller);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.dealers.DealersFacadeBeanLocal#getDealerBranchesByDealerId(java.lang.Long)
	 */
	@Override
	public List<DealerVO> getDealerBranchesByDealerId(Long dealerId)
			throws BusinessException {
		return dealersCRUDBean.getDealerBranchesByDealerId(dealerId);
	}

	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.dealers.DealersFacadeBeanLocal#getDealerByDealerCodeAndType(java.lang.Long, java.lang.String)
	 */
	@Override
	public DealerVO getDealerByDealerCodeAndType(Long dealerId,String dealerType)
		throws BusinessException {
		return dealersCRUDBean.getDealerByCodeAndType(dealerId,dealerType);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.dealers.DealersFacadeBeanLocal#getDealersByDealerTypeCodeAndCountryId(java.lang.String, java.lang.Long)
	 */
	@Override
	public List<DealerVO> getDealersByDealerTypeCodeAndCountryId(
			String dealerTypeCode, Long countryId) throws BusinessException {
		return dealersCRUDBean.getDealersByDealerTypeCodeAndCountryId(dealerTypeCode, countryId);
	}
	
	@Override
	public List<DealerVO> getPrincipalsDealersByAndCountryId(Long countryId, String isSeller, String isInstaller) throws BusinessException {
		return dealersCRUDBean.getPrincipalsDealersByAndCountryId(countryId, isSeller, isInstaller);
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.dealers.DealersFacadeBeanLocal#updateDealerFromIbs(java.lang.Long)
	 */
	@Override
    public void updateDealerFromIbs(Long id) throws BusinessException {
        dealersCRUDBean.updateDealerFromIbs(id);
    }
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.dealers.DealersFacadeBeanLocal#getDealerByDepotCodeAndDealerCodeAndCountryId(java.lang.Long, java.lang.String, java.lang.Long)
	 */
	@Override
	public DealerVO getDealerByDepotCodeAndDealerCodeAndCountryId(Long dealerCode, String depotCode, Long countryId) throws BusinessException {
        return dealersCRUDBean.getDealerByDepotCodeAndDealerCodeAndCountryId(dealerCode,depotCode,countryId);
    }
	
	@Override
	public List<DealerVO> getMajorDealersByCountryId(Long countryId, String isSeller, String isInstaller)
			throws BusinessException {
		return dealersCRUDBean.getMajorDealersByCountryId(countryId, isSeller, isInstaller);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.dealers.DealersFacadeBeanLocal#getActiveMajorDealersAndBranchesByCountryId(java.lang.Long)
	 */
	@Override
	public List<DealerVO> getActiveMajorDealersAndBranchesByCountryId(
			Long countryId) throws BusinessException {
		return dealersCRUDBean.getActiveMajorDealersAndBranchesByCountryId(countryId);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.dealers.DealersFacadeBeanLocal#getActiveMajorDealersAndBranchesByUserId(java.lang.Long)
	 */
	@Override
	public List<DealerVO> getActiveMajorDealersAndBranchesByUserId(
			Long userId, String isSeller, String isInstaller) throws BusinessException {
		return dealersCRUDBean.getActiveMajorDealersAndBranchesByUserId(userId, isSeller, isInstaller);
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.dealers.DealersFacadeBeanLocal#getAllActiveByCountryIdAndIsAlloc(java.lang.Long)
	 */
	public List<DealerVO> getAllActiveByCountryIdAndIsAlloc(Long countryId, String isSeller, String isInstaller) throws BusinessException {
		return dealersCRUDBean.getAllActiveByCountryIdAndIsAlloc(countryId, isSeller, isInstaller);
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.dealers.DealersFacadeBeanLocal#getDealerBranchesByDealerId(java.util.List)
	 */
	@Override
	public List<DealerVO> getDealerBranchesByDealerId(List<Long> dealerIds)
			throws BusinessException {
		return dealersCRUDBean.getDealerBranchesByDealerId(dealerIds);
	}
	
	@Override
	public List<DealerVO> getDealersByWarehouseTypeCode(String warehouseType, Long countryId) throws BusinessException {
		return dealersCRUDBean.getDealersByWarehouseTypeCode(warehouseType, countryId);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.dealers.DealersFacadeBeanLocal#getAllActiveDealerByUserIdAndCodeLogisticsOperator(java.lang.Long, boolean)
	 */
	public List<DealerVO> getAllActiveDealerByUserIdAndCodeLogisticsOperatorAndWarehouseTypeCode(InfoDealerCountryWarehouseDTO infoDealerCountryWarehouseDTO) throws BusinessException{
		return dealersCRUDBean.getAllActiveDealerByUserIdAndCodeLogisticsOperatorAndWarehouseTypeCode(infoDealerCountryWarehouseDTO);
	}

	/**
	 * Metodo encargado de consultar los dealers existentes en HSP+ ordenados por nombre de dealer de forma ascendente
	 * @param getHSPDealers parametro de consulta, paginacion, codigo de pais y modo de respuesta (en archivo o en objetos)
	 * @return
	 * @throws BusinessException
	 * @author Aharker
	 */
	@Override
	public DealerInfoResponseDTO getHSPDealers(DealerInfoRequestDTO getHSPDealers) throws BusinessException {
		return dealersCRUDBean.getHSPDealers(getHSPDealers);
	}
	
}
