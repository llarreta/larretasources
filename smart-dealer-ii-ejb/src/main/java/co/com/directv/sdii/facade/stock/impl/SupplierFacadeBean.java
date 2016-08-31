package co.com.directv.sdii.facade.stock.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.stock.SupplierBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.stock.SupplierFacadeBeanLocal;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.pojo.collection.SuppliersResponse;
import co.com.directv.sdii.model.vo.SupplierVO;

/**
 * Implementación de la fachada de las operaciones Tipo CRUD Supplier
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.stock.SupplierFacadeLocal
 */
@Stateless(name="SupplierFacadeLocal",mappedName="ejb/SupplierFacadeLocal")
public class SupplierFacadeBean implements SupplierFacadeBeanLocal {

		
    @EJB(name="SupplierBusinessBeanLocal", beanInterface=SupplierBusinessBeanLocal.class)
    private SupplierBusinessBeanLocal businessSupplier;

   
    /* (non-Javadoc)
     * @see #{$business_package_name$}.SupplierFacadeLocal#getAllSuppliers()
     */
    public SuppliersResponse getAllSuppliers(Long countryId, RequestCollectionInfo requestCollInfo) throws BusinessException {
    	return businessSupplier.getAllSuppliers( countryId,requestCollInfo );
    }

    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.SupplierFacadeLocal#getSuppliersByID(java.lang.Long)
     */
    public SupplierVO getSupplierByID(Long id) throws BusinessException {
    	return businessSupplier.getSupplierByID(id);
    }

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.SupplierFacadeLocal#createSupplier(co.com.directv.sdii.model.vo.SupplierVO)
	 */
	public void createSupplier(SupplierVO obj) throws BusinessException {
		businessSupplier.createSupplier(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.SupplierFacadeLocal#updateSupplier(co.com.directv.sdii.model.vo.SupplierVO)
	 */
	public void updateSupplier(SupplierVO obj) throws BusinessException {
		businessSupplier.updateSupplier(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.SupplierFacadeLocal#deleteSupplier(co.com.directv.sdii.model.vo.SupplierVO)
	 */
	public void deleteSupplier(SupplierVO obj) throws BusinessException {
		businessSupplier.deleteSupplier(obj);
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.SupplierFacadeBeanLocal#getSupplierByCode(java.lang.String)
	 */
	@Override
	public SupplierVO getSupplierByCode(String code) throws BusinessException {
		return businessSupplier.getSupplierByCode(code);
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.SupplierFacadeBeanLocal#getSupplierByCountry(java.lang.Long)
	 */
	@Override
	public List<SupplierVO> getSupplierByCountry(Long countryId)
			throws BusinessException {
		return businessSupplier.getSupplierByCountry(countryId);
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.SupplierFacadeBeanLocal#getSupplierByNit(java.lang.String)
	 */
	@Override
	public SupplierVO getSupplierByNit(String nit) throws BusinessException {
		return businessSupplier.getSupplierByNit(nit);
	}


	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.SupplierFacadeBeanLocal#getSupplierByInactiveStatus()
	 */
	public List<SupplierVO> getSupplierByInactiveStatus()
			throws BusinessException {
		return businessSupplier.getSupplierByInactiveStatus();
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.SupplierFacadeBeanLocal#getSupplierByInactiveStatus(java.lang.Long)
	 */
	
	public List<SupplierVO> getSupplierByInactiveStatus(Long countryId)
			throws BusinessException {
		return businessSupplier.getSupplierByInactiveStatus(countryId);
	}


	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.SupplierFacadeBeanLocal#getSupplierByActiveStatus()
	 */
	public List<SupplierVO> getSupplierByActiveStatus()
			throws BusinessException {
		return businessSupplier.getSupplierByActiveStatus();
	}


	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.SupplierFacadeBeanLocal#getSupplierByActiveStatus(java.lang.Long)
	 */
	public List<SupplierVO> getSupplierByActiveStatus(Long countryId)
			throws BusinessException {
		return businessSupplier.getSupplierByActiveStatus(countryId);
	}
}
