package co.com.directv.sdii.facade.assign.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.assign.kpi.dto.RequestSearchKpiResultsDTO;
import co.com.directv.sdii.ejb.business.assign.KpiResultBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.assign.KpiResultFacadeBeanLocal;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.pojo.collection.ResponseSearchKpiResultsResponse;
import co.com.directv.sdii.model.vo.KpiResultVO;

/**
 * Implementación de la fachada de las operaciones Tipo CRUD KpiResult
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.assign.KpiResultFacadeLocal
 */
@Stateless(name="KpiResultFacadeLocal",mappedName="ejb/KpiResultFacadeLocal")
public class KpiResultFacadeBean implements KpiResultFacadeBeanLocal {

		
    @EJB(name="KpiResultBusinessBeanLocal", beanInterface=KpiResultBusinessBeanLocal.class)
    private KpiResultBusinessBeanLocal businessKpiResult;
    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.KpiResultFacadeLocal#getAllKpiResults()
     */
    public List<KpiResultVO> getAllKpiResults() throws BusinessException {
    	return businessKpiResult.getAllKpiResults();
    }

    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.KpiResultFacadeLocal#getKpiResultsByID(java.lang.Long)
     */
    public KpiResultVO getKpiResultByID(Long id) throws BusinessException {
    	return businessKpiResult.getKpiResultByID(id);
    }

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.KpiResultFacadeLocal#createKpiResult(co.com.directv.sdii.model.vo.KpiResultVO)
	 */
	public void createKpiResult(KpiResultVO obj) throws BusinessException {
		businessKpiResult.createKpiResult(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.KpiResultFacadeLocal#updateKpiResult(co.com.directv.sdii.model.vo.KpiResultVO)
	 */
	public void updateKpiResult(KpiResultVO obj) throws BusinessException {
		businessKpiResult.updateKpiResult(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.KpiResultFacadeLocal#deleteKpiResult(co.com.directv.sdii.model.vo.KpiResultVO)
	 */
	public void deleteKpiResult(KpiResultVO obj) throws BusinessException {
		businessKpiResult.deleteKpiResult(obj);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.assign.KpiResultFacadeBeanLocal#getKpiResultByDealerIdAndBetweenDate(co.com.directv.sdii.assign.kpi.dto.RequestSearchKpiResultsDTO, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	public ResponseSearchKpiResultsResponse getKpiResultDealersByDealerIdAndBetweenDate(RequestSearchKpiResultsDTO request, RequestCollectionInfo requestCollInfo ) throws BusinessException {
		return businessKpiResult.getKpiResultDealersByDealerIdAndBetweenDate(request,requestCollInfo);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.assign.KpiResultFacadeBeanLocal#getKpiResultByDealerIdAndBetweenDate(co.com.directv.sdii.assign.kpi.dto.RequestSearchKpiResultsDTO, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	public ResponseSearchKpiResultsResponse getKpiResultByDealerIdAndBetweenDate(RequestSearchKpiResultsDTO request) throws BusinessException {
		return businessKpiResult.getKpiResultByDealerIdAndBetweenDate(request);
	}

}
