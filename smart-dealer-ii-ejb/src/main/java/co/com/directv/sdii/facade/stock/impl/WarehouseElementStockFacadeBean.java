package co.com.directv.sdii.facade.stock.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.stock.WarehouseElementStockBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.stock.WarehouseElementStockFacadeBeanLocal;
import co.com.directv.sdii.model.dto.collection.RequestCollectionInfoDTO;
import co.com.directv.sdii.model.dto.collection.WarehouseElementStockDTO;
import co.com.directv.sdii.model.vo.WarehouseElementStockVO;

/**
 * Implementación de la fachada de las operaciones Tipo CRUD WarehouseElementStock
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.stock.WarehouseElementStockFacadeLocal
 */
@Stateless(name="WarehouseElementStockFacadeLocal",mappedName="ejb/WarehouseElementStockFacadeLocal")
public class WarehouseElementStockFacadeBean implements WarehouseElementStockFacadeBeanLocal {

		
    @EJB(name="WarehouseElementStockBusinessBeanLocal", beanInterface=WarehouseElementStockBusinessBeanLocal.class)
    private WarehouseElementStockBusinessBeanLocal businessWarehouseElementStock;
    
   
    /* (non-Javadoc)
     * @see #{$business_package_name$}.WarehouseElementStockFacadeLocal#getAllWarehouseElementStocks()
     */
    public List<WarehouseElementStockVO> getAllWarehouseElementStocks() throws BusinessException {
    	return businessWarehouseElementStock.getAllWarehouseElementStocks();
    }

    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.WarehouseElementStockFacadeLocal#getWarehouseElementStocksByID(java.lang.Long)
     */
    public WarehouseElementStockVO getWarehouseElementStockByID(Long id) throws BusinessException {
    	return businessWarehouseElementStock.getWarehouseElementStockByID(id);
    }

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.WarehouseElementStockFacadeLocal#createWarehouseElementStock(co.com.directv.sdii.model.vo.WarehouseElementStockVO)
	 */
	public void createWarehouseElementStock(WarehouseElementStockVO obj) throws BusinessException {
		businessWarehouseElementStock.createWarehouseElementStock(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.WarehouseElementStockFacadeLocal#updateWarehouseElementStock(co.com.directv.sdii.model.vo.WarehouseElementStockVO)
	 */
	public void updateWarehouseElementStock(WarehouseElementStockVO obj) throws BusinessException {
		businessWarehouseElementStock.updateWarehouseElementStock(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.WarehouseElementStockFacadeLocal#deleteWarehouseElementStock(co.com.directv.sdii.model.vo.WarehouseElementStockVO)
	 */
	public void deleteWarehouseElementStock(WarehouseElementStockVO obj) throws BusinessException {
		businessWarehouseElementStock.deleteWarehouseElementStock(obj);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.WarehouseElementStockFacadeBeanLocal#getWarehouseElementStockByElementCode(java.lang.String)
	 */
	public List<WarehouseElementStockVO> getWarehouseElementStockByElementCode(
			String code) throws BusinessException {
		return businessWarehouseElementStock.getWarehouseElementStockByElementCode(code);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.WarehouseElementStockFacadeBeanLocal#getWarehElemStockByElemTypeCodeOrDealerCodeOrWhCode(java.lang.String, java.lang.String, java.lang.String)
	 */
	public WarehouseElementStockDTO getWarehElemStockByElemTypeCodeOrDealerCodeOrWhCode(
			String elementTypeCode, Long dealerId, String warehouseCode, Long countryId, RequestCollectionInfoDTO requestCollInfo, Long dealerBranchId)
			throws BusinessException {
		return businessWarehouseElementStock.getWarehElemStockByElemTypeCodeOrDealerCodeOrWhCode(elementTypeCode, dealerId, warehouseCode, countryId, requestCollInfo, dealerBranchId);
	}
}
