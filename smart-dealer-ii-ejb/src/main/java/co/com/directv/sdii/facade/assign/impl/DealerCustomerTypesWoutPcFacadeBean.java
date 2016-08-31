package co.com.directv.sdii.facade.assign.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.assign.DealerCustomerTypesWoutPcBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.assign.DealerCustomerTypesWoutPcFacadeBeanLocal;
import co.com.directv.sdii.model.vo.DealerCustomerTypesWoutPcVO;

/**
 * Implementación de la fachada de las operaciones Tipo CRUD DealerCustomerTypesWoutPc
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.assign.DealerCustomerTypesWoutPcFacadeLocal
 */
@Stateless(name="DealerCustomerTypesWoutPcFacadeLocal",mappedName="ejb/DealerCustomerTypesWoutPcFacadeLocal")
public class DealerCustomerTypesWoutPcFacadeBean implements DealerCustomerTypesWoutPcFacadeBeanLocal {

	
    @EJB(name="DealerCustomerTypesWoutPcBusinessBeanLocal", beanInterface=DealerCustomerTypesWoutPcBusinessBeanLocal.class)
    private DealerCustomerTypesWoutPcBusinessBeanLocal businessDealerCustomerTypesWoutPc;
    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.DealerCustomerTypesWoutPcFacadeLocal#getAllDealerCustomerTypesWoutPcs()
     */
    public List<DealerCustomerTypesWoutPcVO> getAllDealerCustomerTypesWoutPcs() throws BusinessException {
    	return businessDealerCustomerTypesWoutPc.getAllDealerCustomerTypesWoutPcs();
    }

    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.DealerCustomerTypesWoutPcFacadeLocal#getDealerCustomerTypesWoutPcsByID(java.lang.Long)
     */
    public DealerCustomerTypesWoutPcVO getDealerCustomerTypesWoutPcByID(Long id) throws BusinessException {
    	return businessDealerCustomerTypesWoutPc.getDealerCustomerTypesWoutPcByID(id);
    }

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.DealerCustomerTypesWoutPcFacadeLocal#createDealerCustomerTypesWoutPc(co.com.directv.sdii.model.vo.DealerCustomerTypesWoutPcVO)
	 */
	public void createDealerCustomerTypesWoutPc(DealerCustomerTypesWoutPcVO obj) throws BusinessException {
		businessDealerCustomerTypesWoutPc.createDealerCustomerTypesWoutPc(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.DealerCustomerTypesWoutPcFacadeLocal#updateDealerCustomerTypesWoutPc(co.com.directv.sdii.model.vo.DealerCustomerTypesWoutPcVO)
	 */
	public void updateDealerCustomerTypesWoutPc(DealerCustomerTypesWoutPcVO obj) throws BusinessException {
		businessDealerCustomerTypesWoutPc.updateDealerCustomerTypesWoutPc(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.DealerCustomerTypesWoutPcFacadeLocal#deleteDealerCustomerTypesWoutPc(co.com.directv.sdii.model.vo.DealerCustomerTypesWoutPcVO)
	 */
	public void deleteDealerCustomerTypesWoutPc(DealerCustomerTypesWoutPcVO obj) throws BusinessException {
		businessDealerCustomerTypesWoutPc.deleteDealerCustomerTypesWoutPc(obj);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.assign.DealerCustomerTypesWoutPcFacadeBeanLocal#getAllActiveByDealerId(java.lang.Long)
	 */
	@Override
	public List<DealerCustomerTypesWoutPcVO> getAllDealerCustomerTypesWoutPcConfigurationByDealerIdAndCountryId(Long dealerId, Long countryId) throws BusinessException {
		return businessDealerCustomerTypesWoutPc.getAllDealerCustomerTypesWoutPcConfigurationByDealerIdAndCountryId(dealerId, countryId);
	}
	
	/*
	 * Req-0096 - Requerimiento Consolidado Asignador
	 * 
	 * Nueva Pantalla de Configuracion de Dealers por Tipo de Cliente Carga solapa del acordeon  “Tipos de cliente”
	 * 
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.assign.getAllDealerCustomerTypesByCustomerCategoryId#getAllActiveByDealerId(java.lang.Long)
	 */
	@Override
	public List<DealerCustomerTypesWoutPcVO> getAllDealerCustomerTypesByCustomerCategoryId(	Long dalerId, 
																							Long countryId, 
																							Long customerCategoryId,
																							Long businessAreaId) 
    throws BusinessException { 
		return businessDealerCustomerTypesWoutPc.getAllDealerCustomerTypesByCustomerCategoryId(	dalerId,
																								countryId, 
																								customerCategoryId,
																								businessAreaId);
	}	

}
