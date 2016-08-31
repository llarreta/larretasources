package co.com.directv.sdii.facade.stock.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.stock.WarehouseTypeBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.stock.WarehouseTypeFacadeBeanLocal;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.pojo.collection.WareheouseTypeResponse;
import co.com.directv.sdii.model.vo.WarehouseTypeVO;

/**
 * Implementación de la fachada de las operaciones Tipo CRUD WarehouseType
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.stock.WarehouseTypeFacadeLocal
 */
@Stateless(name="WarehouseTypeFacadeLocal",mappedName="ejb/WarehouseTypeFacadeLocal")
public class WarehouseTypeFacadeBean implements WarehouseTypeFacadeBeanLocal {

		
    @EJB(name="WarehouseTypeBusinessBeanLocal", beanInterface=WarehouseTypeBusinessBeanLocal.class)
    private WarehouseTypeBusinessBeanLocal businessWarehouseType;

   
    /* (non-Javadoc)
     * @see #{$business_package_name$}.WarehouseTypeFacadeLocal#getAllWarehouseTypes()
     */
    public List<WarehouseTypeVO> getAllWarehouseTypes() throws BusinessException {
    	return businessWarehouseType.getAllWarehouseTypes();
    }
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.stock.WarehouseTypeFacadeBeanLocal#getAllWarehouseTypes(java.lang.String, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
     */
    public WareheouseTypeResponse getAllWarehouseTypes(String code,RequestCollectionInfo requestCollInfo) throws BusinessException{
    	return businessWarehouseType.getAllWarehouseTypes(code,requestCollInfo);
    }

    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.WarehouseTypeFacadeLocal#getWarehouseTypesByID(java.lang.Long)
     */
    public WarehouseTypeVO getWarehouseTypeByID(Long id) throws BusinessException {
    	return businessWarehouseType.getWarehouseTypeByID(id);
    }

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.WarehouseTypeFacadeLocal#createWarehouseType(co.com.directv.sdii.model.vo.WarehouseTypeVO)
	 */
	public void createWarehouseType(WarehouseTypeVO obj) throws BusinessException {
		businessWarehouseType.createWarehouseType(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.WarehouseTypeFacadeLocal#updateWarehouseType(co.com.directv.sdii.model.vo.WarehouseTypeVO)
	 */
	public void updateWarehouseType(WarehouseTypeVO obj) throws BusinessException {
		businessWarehouseType.updateWarehouseType(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.WarehouseTypeFacadeLocal#deleteWarehouseType(co.com.directv.sdii.model.vo.WarehouseTypeVO)
	 */
	public void deleteWarehouseType(WarehouseTypeVO obj) throws BusinessException {
		businessWarehouseType.deleteWarehouseType(obj);
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.WarehouseTypeFacadeBeanLocal#getWarehouseTypeByCode(java.lang.String)
	 */
	public WarehouseTypeVO getWarehouseTypeByCode(String code)
			throws BusinessException {
		return businessWarehouseType.getWarehouseTypeByCode(code);
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.WarehouseTypeFacadeBeanLocal#getWarehouseTypeActive()
	 */
	@Override
	public List<WarehouseTypeVO> getWarehouseTypeActive()
			throws BusinessException {
		return businessWarehouseType.getWarehouseTypeActive();
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.WarehouseTypeFacadeBeanLocal#getWarehouseTypeActive(co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	public WareheouseTypeResponse getWarehouseTypeActive(RequestCollectionInfo requestCollInfo)throws BusinessException{
		return businessWarehouseType.getWarehouseTypeActive(requestCollInfo);
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.WarehouseTypeFacadeBeanLocal#getWarehouseTypeNoActive()
	 */
	@Override
	public List<WarehouseTypeVO> getWarehouseTypeNoActive()
			throws BusinessException {
		return businessWarehouseType.getWarehouseTypeNoActive();
	}

	@Override
	public List<WarehouseTypeVO> getAllWarehouseTypesActive()
			throws BusinessException {
		return businessWarehouseType.getAllWarehouseTypesActive();
	}
	
	@Override
	public List<WarehouseTypeVO> getAllWarehouseTypesByDealerID(Long dealerId)
			throws BusinessException {
		return businessWarehouseType.getAllWarehouseTypesByDealerID(dealerId);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.WarehouseTypeFacadeBeanLocal#getAllWarehouseTypesActiveAndNotVirtual()
	 */
	@Override
	public List<WarehouseTypeVO> getAllWarehouseTypesActiveAndNotVirtual()
			throws BusinessException {
		return businessWarehouseType.getAllWarehouseTypesActiveAndNotVirtual();
	}
	
}
