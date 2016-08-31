package co.com.directv.sdii.facade.stock.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.stock.TransportCompanyBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.stock.TransportCompanyFacadeBeanLocal;
import co.com.directv.sdii.model.dto.collection.RequestCollectionInfoDTO;
import co.com.directv.sdii.model.dto.collection.TransportCompanyDTO;
import co.com.directv.sdii.model.vo.TransportCompanyVO;

/**
 * Implementación de la fachada de las operaciones Tipo CRUD TransportCompany
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.stock.TransportCompanyFacadeLocal
 */
@Stateless(name="TransportCompanyFacadeLocal",mappedName="ejb/TransportCompanyFacadeLocal")
public class TransportCompanyFacadeBean implements TransportCompanyFacadeBeanLocal {

		
    @EJB(name="TransportCompanyBusinessBeanLocal", beanInterface=TransportCompanyBusinessBeanLocal.class)
    private TransportCompanyBusinessBeanLocal businessTransportCompany;
   
  
    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.stock.TransportCompanyFacadeBeanLocal#getTransportCompaniesByCountryId(java.lang.Long)
     */
    @Override
    public List<TransportCompanyVO> getTransportCompaniesByCountryId( Long countryId) throws BusinessException {
    	return businessTransportCompany.getTransportCompaniesByCountryId(countryId);
    }

    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.TransportCompanyFacadeLocal#getTransportCompanysByID(java.lang.Long)
     */
    public TransportCompanyVO getTransportCompanyByID(Long id) throws BusinessException {
    	return businessTransportCompany.getTransportCompanyByID(id);
    }

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.TransportCompanyFacadeLocal#createTransportCompany(co.com.directv.sdii.model.vo.TransportCompanyVO)
	 */
	public void createTransportCompany(TransportCompanyVO obj) throws BusinessException {
		businessTransportCompany.createTransportCompany(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.TransportCompanyFacadeLocal#updateTransportCompany(co.com.directv.sdii.model.vo.TransportCompanyVO)
	 */
	public void updateTransportCompany(TransportCompanyVO obj) throws BusinessException {
		businessTransportCompany.updateTransportCompany(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.TransportCompanyFacadeLocal#deleteTransportCompany(co.com.directv.sdii.model.vo.TransportCompanyVO)
	 */
	public void deleteTransportCompany(TransportCompanyVO obj) throws BusinessException {
		businessTransportCompany.deleteTransportCompany(obj);
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.TransportCompanyFacadeBeanLocal#getTransportCompanyByCode(java.lang.String)
	 */
	public TransportCompanyVO getTransportCompanyByCode(String code)
			throws BusinessException {
		return businessTransportCompany.getTransportCompanyByCode(code);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.TransportCompanyFacadeBeanLocal#getActiveTransportCompaniesByCountryId(java.lang.Long)
	 */
	@Override
	public TransportCompanyDTO getActiveTransportCompaniesByCountryId(Long countryId, RequestCollectionInfoDTO requestCollInfo)
			throws BusinessException {
		return businessTransportCompany.getActiveTransportCompaniesByCountryId(countryId, requestCollInfo);
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.TransportCompanyFacadeBeanLocal#getTransportCompanyByCodeAndCountryId(java.lang.String, java.lang.Long)
	 */
	@Override
	public TransportCompanyVO getTransportCompanyByCodeAndCountryId(
			String code, Long countryId) throws BusinessException {
		return businessTransportCompany.getTransportCompanyByCodeAndCountryId(code, countryId);
	}


	@Override
	public TransportCompanyDTO getAllTransportCompaniesByCountryId(
			Long countryId, RequestCollectionInfoDTO requestCollInfo)
			throws BusinessException {
			return businessTransportCompany.getAllTransportCompaniesByCountryId(countryId, requestCollInfo);
	}
}
