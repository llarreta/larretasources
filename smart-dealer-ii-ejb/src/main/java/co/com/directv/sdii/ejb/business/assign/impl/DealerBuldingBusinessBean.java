package co.com.directv.sdii.ejb.business.assign.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.assign.DealerBuldingBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.DealerBulding;
import co.com.directv.sdii.model.pojo.collection.DealerBuildingResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.vo.DealerBuldingVO;
import co.com.directv.sdii.persistence.dao.assign.DealerBuldingDAOLocal;
import co.com.directv.sdii.reports.dto.DealerBuildingDTO;
import co.com.directv.sdii.rules.BusinessRuleValidationManager;

/**
 * EJB que implementa las operaciones Tipo CRUD DealerBulding
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.assign.DealerBuldingDAOLocal
 * @see co.com.directv.sdii.ejb.business.assign.assign.DealerBuldingBusinessBeanLocal
 */
@Stateless(name="DealerBuldingBusinessBeanLocal",mappedName="ejb/DealerBuldingBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class DealerBuldingBusinessBean extends BusinessBase implements DealerBuldingBusinessBeanLocal {

    @EJB(name="DealerBuldingDAOLocal", beanInterface=DealerBuldingDAOLocal.class)
    private DealerBuldingDAOLocal daoDealerBulding;
    
    private final static Logger log = UtilsBusiness.getLog4J(DealerBuldingBusinessBean.class);

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.assign.assign.DealerBuldingBusinessBeanLocal#getAllDealerBuldings()
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<DealerBuldingVO> getAllDealerBuldings() throws BusinessException {
        log.debug("== Inicia getAllDealerBuldings/DealerBuldingBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoDealerBulding.getAllDealerBuldings(), DealerBuldingVO.class);

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getAllDealerBuldings/DealerBuldingBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllDealerBuldings/DealerBuldingBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.assign.assign.DealerBuldingBusinessBeanLocal#getDealerBuldingsByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public DealerBuldingVO getDealerBuldingByID(Long id) throws BusinessException {
        log.debug("== Inicia getDealerBuldingByID/DealerBuldingBusinessBean ==");
        UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            DealerBulding objPojo = daoDealerBulding.getDealerBuldingByID(id);
            if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            return UtilsBusiness.copyObject(DealerBuldingVO.class, objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getDealerBuldingByID/DealerBuldingBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getDealerBuldingByID/DealerBuldingBusinessBean ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.assign.DealerBuldingBusinessBeanLocal#createDealerBulding(co.com.directv.sdii.model.vo.DealerBuldingVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createDealerBulding(DealerBuldingVO obj) throws BusinessException {
        log.debug("== Inicia createDealerBulding/DealerBuldingBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
            DealerBulding objPojo =  UtilsBusiness.copyObject(DealerBulding.class, obj);
            daoDealerBulding.createDealerBulding(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación createDealerBulding/DealerBuldingBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createDealerBulding/DealerBuldingBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.assign.DealerBuldingBusinessBeanLocal#updateDealerBulding(co.com.directv.sdii.model.vo.DealerBuldingVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateDealerBulding(DealerBuldingVO obj) throws BusinessException {
        log.debug("== Inicia updateDealerBulding/DealerBuldingBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
            DealerBulding objPojo =  UtilsBusiness.copyObject(DealerBulding.class, obj);
            daoDealerBulding.updateDealerBulding(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación updateDealerBulding/DealerBuldingBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateDealerBulding/DealerBuldingBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.assign.DealerBuldingBusinessBeanLocal#deleteDealerBulding(co.com.directv.sdii.model.vo.DealerBuldingVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteDealerBulding(DealerBuldingVO obj) throws BusinessException {
        log.debug("== Inicia deleteDealerBulding/DealerBuldingBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            DealerBulding objPojo =  UtilsBusiness.copyObject(DealerBulding.class, obj);
            daoDealerBulding.deleteDealerBulding(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación deleteDealerBulding/DealerBuldingBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteDealerBulding/DealerBuldingBusinessBean ==");
        }
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.DealerBuldingBusinessBeanLocal#getDealerBuildingsByPostalCodeId(java.lang.Long, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	public DealerBuildingResponse getDealerBuildingsByPostalCodeId(Long postalCodeId, RequestCollectionInfo requestCollectionInfo)throws BusinessException {
		log.debug("== Inicia getDealerBuildingsByPostalCodeId/DealerBuldingBusinessBean ==");
		UtilsBusiness.assertNotNull(postalCodeId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		DealerBuildingResponse response = null;
		try {
			response = daoDealerBulding.getDealerBuildingsByPostalCodeId(postalCodeId, requestCollectionInfo);
			if (response != null && response.getDealerBuildings() != null) {
				response.setDealerBuildingsVO(UtilsBusiness.convertList(response.getDealerBuildings(), DealerBuldingVO.class));
				response.setDealerBuildings(null);
			}
		} catch (Throwable e) {
			log.error("== Error getDealerBuildingsByPostalCodeId/DealerBuldingBusinessBean == " + e.getMessage());
			throw this.manageException(e);
		} finally {
			log.debug("== Termina getDealerBuildingsByPostalCodeId/DealerBuldingBusinessBean ==");
		}
		return response;
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.DealerBuldingBusinessBeanLocal#updateDealerBuildings(java.util.List)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateDealerBuildings(List<DealerBuldingVO> dealerBuildings) throws BusinessException {
		
		log.debug("== Inicia updateDealerBuildings/DealerBuldingBusinessBean ==");
        UtilsBusiness.assertNotNull(dealerBuildings, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	//persistir las modificaciones a las configuraciones
        	for (DealerBuldingVO dealerBuildingVO : dealerBuildings) {
        		DealerBulding dealerBuilding =  UtilsBusiness.copyObject(DealerBulding.class, dealerBuildingVO);
        		dealerBuilding.setDateLastChange(UtilsBusiness.fechaActual());
    			daoDealerBulding.updateDealerBulding(dealerBuilding);
			}
        	
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación updateDealerBuildings/DealerBuldingBusinessBean == " + ex.getMessage());
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateDealerBuildings/DealerBuldingBusinessBean ==");
        }
		
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.DealerBuldingBusinessBeanLocal#getDealerBuldingsByCountryAndActive(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<DealerBuildingDTO> getDealerBuldingsByCountryAndActive(
			Long idCountry) throws BusinessException {
		log.debug("== Inicia getDealerBuldingsByCountryAndActive/DealerBuldingBusinessBean ==");
        try {
        	List<DealerBuildingDTO> result =  new ArrayList<DealerBuildingDTO>();
        	List<DealerBulding> resultPojo =  this.daoDealerBulding.getDealerBuldingsByCountryAndStatus(idCountry, CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity());
        	for(DealerBulding tmp : resultPojo){
        		DealerBuildingDTO dto = new DealerBuildingDTO();
        		//dto.setBuildAdd(tmp.getBuilding().getAddress());
        		dto.setBuildCode(tmp.getBuilding().getCode().toString());
        		dto.setBuildName(tmp.getBuilding().getName());
        		dto.setCityName(tmp.getDealerCoverage().getPostalCode().getCity().getCityName());
        		dto.setStateName(tmp.getDealerCoverage().getPostalCode().getCity().getState().getStateName());
        		dto.setDealerCode(tmp.getDealerCoverage().getDealer().getDealerCode().toString());
        		dto.setDepotCode(tmp.getDealerCoverage().getDealer().getDepotCode());
        		dto.setDealerName(tmp.getDealerCoverage().getDealer().getDealerName());
        		result.add(dto);
        	}
            return result;

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getDealerBuldingsByCountryAndActive/DealerBuldingBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getDealerBuldingsByCountryAndActive/DealerBuldingBusinessBean ==");
        }
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.DealerBuldingBusinessBeanLocal#getDealerBuildingByDealerCoverageIdAndBuildingId(java.lang.Long, java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public DealerBuldingVO getDealerBuildingByDealerCoverageIdAndBuildingId(
			Long dealerCoverageId, Long buildingId) throws BusinessException {
		log.debug("== Inicia getDealerBuildingByDealerCoverageIdAndBuildingId/DealerBuldingBusinessBean ==");
        UtilsBusiness.assertNotNull(dealerCoverageId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(buildingId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            DealerBulding objPojo = daoDealerBulding.getDealerBuildingByDealerCoverageIdAndBuildingId(dealerCoverageId, buildingId);
            return UtilsBusiness.copyObject(DealerBuldingVO.class, objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getDealerBuildingByDealerCoverageIdAndBuildingId/DealerBuldingBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getDealerBuildingByDealerCoverageIdAndBuildingId/DealerBuldingBusinessBean ==");
        }
	}
	
}
