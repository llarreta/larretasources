package co.com.directv.sdii.facade.stock.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.stock.AdjustmentBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.stock.AdjustmentFacadeBeanLocal;
import co.com.directv.sdii.model.dto.AdjustmenElementsRequestDTO;
import co.com.directv.sdii.model.dto.AdjustmentRequestDTO;
import co.com.directv.sdii.model.dto.collection.AdjustmentElementCollDTO;
import co.com.directv.sdii.model.pojo.collection.AdjustmentElementsResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.vo.AdjustmentStatusVO;
import co.com.directv.sdii.model.vo.AdjustmentTypeVO;
import co.com.directv.sdii.model.vo.AdjustmentVO;
import co.com.directv.sdii.model.vo.SerializedVO;
import co.com.directv.sdii.reports.dto.AdjustmentElementDTO;

/**
 * Implementación de la fachada de las operaciones Tipo CRUD Adjustment
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.stock.AdjustmentFacadeLocal
 */
@Stateless(name="AdjustmentFacadeLocal",mappedName="ejb/AdjustmentFacadeLocal")
public class AdjustmentFacadeBean implements AdjustmentFacadeBeanLocal {

		
    @EJB(name="AdjustmentBusinessBeanLocal", beanInterface=AdjustmentBusinessBeanLocal.class)
    private AdjustmentBusinessBeanLocal businessAdjustment;
    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.AdjustmentFacadeLocal#getAllAdjustments()
     */
    public List<AdjustmentVO> getAllAdjustments() throws BusinessException {
    	return businessAdjustment.getAllAdjustments();
    }

    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.AdjustmentFacadeLocal#getAdjustmentsByID(java.lang.Long)
     */
    public AdjustmentVO getAdjustmentByID(Long id) throws BusinessException {
    	return businessAdjustment.getAdjustmentByID(id);
    }

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.AdjustmentFacadeLocal#createAdjustment(co.com.directv.sdii.model.vo.AdjustmentVO)
	 */
	public Long createAdjustment(AdjustmentVO obj, Long userId) throws BusinessException {
		return businessAdjustment.createAdjustment(obj, userId);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.AdjustmentFacadeLocal#updateAdjustment(co.com.directv.sdii.model.vo.AdjustmentVO)
	 */
	public void updateAdjustment(AdjustmentVO obj,Long userId) throws BusinessException {
		businessAdjustment.updateAdjustment(obj,userId);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.AdjustmentFacadeLocal#deleteAdjustment(co.com.directv.sdii.model.vo.AdjustmentVO)
	 */
	public void deleteAdjustment(AdjustmentVO obj) throws BusinessException {
		businessAdjustment.deleteAdjustment(obj);
	}


	@Override
	public List<AdjustmentTypeVO> getAllAdjustmentsTypes()
			throws BusinessException {
		return businessAdjustment.getAllAdjustmentsTypes();
	}


	@Override
	public AdjustmentVO createAdjustmentSerializedElement(List<AdjustmentElementDTO> listAdjustmentElementsVO, AdjustmentVO adjustmentVO, Long userId) throws BusinessException {
		return businessAdjustment.createAdjustmentSerializedElement(listAdjustmentElementsVO, adjustmentVO, userId);
	}


	@Override
	public AdjustmentVO createAdjustmentNotSerializedElement(
			List<AdjustmentElementDTO> listAdjustmentElementsVO,
			AdjustmentVO adjustmentVO, Long userId) throws BusinessException {
		return businessAdjustment.createAdjustmentNotSerializedElement(listAdjustmentElementsVO, adjustmentVO, userId);
	}
	
	@Override
	public SerializedVO findSerializedElement(String serialCode, Long userId) throws BusinessException {
		
		return businessAdjustment.findSerializedElement(serialCode, userId);
	}	
	
	@Override
	public AdjustmentElementsResponse searchAdjustmentsBySearchParameters(AdjustmentRequestDTO params, RequestCollectionInfo requestCollInfo) throws BusinessException{
		return businessAdjustment.searchAdjustmentsBySearchParameters(params,requestCollInfo);
	}
	
	@Override
	public AdjustmentElementCollDTO getCheckAdjustmentElementsForAuthorization(AdjustmenElementsRequestDTO request, RequestCollectionInfo requestCollInfo) throws BusinessException {
		return businessAdjustment.getCheckAdjustmentElementsForAuthorization(request, requestCollInfo);
	}
	
	@Override
	public AdjustmentElementCollDTO getAdjustmentElementsForAuthorization(AdjustmenElementsRequestDTO request, RequestCollectionInfo requestCollInfo) throws BusinessException {
		return businessAdjustment.getAdjustmentElementsForAuthorization(request, requestCollInfo);
	}	

	@Override
	public void createAdjustmentElementsAuthorization(AdjustmenElementsRequestDTO request) throws BusinessException {
		businessAdjustment.createAdjustmentElementsAuthorization(request);
	}

	@Override
	public void approvalAllElementsOfAdjustment(AdjustmenElementsRequestDTO request) throws BusinessException {
			businessAdjustment.approvalAllElementsOfAdjustment(request);
		}
	
	@Override
	public List<AdjustmentStatusVO> getAllAdjustmentStatus() throws BusinessException {
		return businessAdjustment.getAllAdjustmentStatus();
	}
	
}
