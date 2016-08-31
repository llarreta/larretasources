package co.com.directv.sdii.ejb.business.assign.impl;

import java.util.ArrayList;
import java.util.Arrays;
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
import co.com.directv.sdii.ejb.business.assign.DealerDetailBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.pojo.DealerDetail;
import co.com.directv.sdii.model.vo.DealerDetailVO;
import co.com.directv.sdii.persistence.dao.assign.DealerDetailDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.CrewsDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.DealersDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.EmployeesCrewDAOLocal;
import co.com.directv.sdii.reports.dto.DealerDetailDTO;
import co.com.directv.sdii.rules.BusinessRuleValidationManager;

/**
 * EJB que implementa las operaciones Tipo CRUD DealerDetail
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.assign.DealerDetailDAOLocal
 * @see co.com.directv.sdii.ejb.business.assign.assign.DealerDetailBusinessBeanLocal
 */
@Stateless(name="DealerDetailBusinessBeanLocal",mappedName="ejb/DealerDetailBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class DealerDetailBusinessBean extends BusinessBase implements DealerDetailBusinessBeanLocal {

    @EJB(name="DealerDetailDAOLocal", beanInterface=DealerDetailDAOLocal.class)
    private DealerDetailDAOLocal daoDealerDetail;
    
    @EJB(name="DealersDAOLocal", beanInterface=DealersDAOLocal.class)
    private DealersDAOLocal daoDealers;
    
    @EJB(name="EmployeesCrewDAOLocal", beanInterface=EmployeesCrewDAOLocal.class)
    private EmployeesCrewDAOLocal employeesCrewDAO;
    
    @EJB(name="CrewsDAOLocal", beanInterface=CrewsDAOLocal.class)
    private CrewsDAOLocal crewDAO;
    
    private final static Logger log = UtilsBusiness.getLog4J(DealerDetailBusinessBean.class);

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.assign.assign.DealerDetailBusinessBeanLocal#getAllDealerDetails()
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<DealerDetailVO> getAllDealerDetails() throws BusinessException {
        log.debug("== Inicia getAllDealerDetails/DealerDetailBusinessBean ==");
        try {
        	List<DealerDetailVO> result =new ArrayList<DealerDetailVO>();
        	DealerDetailVO dealerDetailVO = null;
        	for (DealerDetail dealerDetail : daoDealerDetail.getAllDealerDetails()) {
        		dealerDetailVO = UtilsBusiness.copyObject(DealerDetailVO.class, dealerDetail);
        		generateAttendTypeOddEven(dealerDetailVO);
        		result.add(dealerDetailVO);
			}
        	return result;

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getAllDealerDetails/DealerDetailBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllDealerDetails/DealerDetailBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.assign.assign.DealerDetailBusinessBeanLocal#getDealerDetailsByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public DealerDetailVO getDealerDetailByID(Long id) throws BusinessException {
        log.debug("== Inicia getDealerDetailByID/DealerDetailBusinessBean ==");
        UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            DealerDetail objPojo = daoDealerDetail.getDealerDetailByID(id);
            DealerDetailVO dealerDetailVO = UtilsBusiness.copyObject(DealerDetailVO.class, objPojo);
            generateAttendTypeOddEven(dealerDetailVO);
            return dealerDetailVO;
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getDealerDetailByID/DealerDetailBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getDealerDetailByID/DealerDetailBusinessBean ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.assign.DealerDetailBusinessBeanLocal#createDealerDetail(co.com.directv.sdii.model.vo.DealerDetailVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createDealerDetail(DealerDetailVO obj) throws BusinessException {
        log.debug("== Inicia createDealerDetail/DealerDetailBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
            DealerDetail objPojo =  UtilsBusiness.copyObject(DealerDetail.class, obj);
            generateAttendTypeOddEven(obj,objPojo);
            daoDealerDetail.createDealerDetail(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación createDealerDetail/DealerDetailBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createDealerDetail/DealerDetailBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.assign.DealerDetailBusinessBeanLocal#updateDealerDetail(co.com.directv.sdii.model.vo.DealerDetailVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateDealerDetail(DealerDetailVO obj) throws BusinessException {
        log.debug("== Inicia updateDealerDetail/DealerDetailBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
            DealerDetail objPojo =  UtilsBusiness.copyObject(DealerDetail.class, obj);
            generateAttendTypeOddEven(obj,objPojo);
            daoDealerDetail.updateDealerDetail(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación updateDealerDetail/DealerDetailBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateDealerDetail/DealerDetailBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.assign.DealerDetailBusinessBeanLocal#deleteDealerDetail(co.com.directv.sdii.model.vo.DealerDetailVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteDealerDetail(DealerDetailVO obj) throws BusinessException {
        log.debug("== Inicia deleteDealerDetail/DealerDetailBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getDealerId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            DealerDetail objPojo =  UtilsBusiness.copyObject(DealerDetail.class, obj);
            generateAttendTypeOddEven(obj,objPojo);
            daoDealerDetail.deleteDealerDetail(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación deleteDealerDetail/DealerDetailBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteDealerDetail/DealerDetailBusinessBean ==");
        }
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.DealerDetailBusinessBeanLocal#getDealerDayCapacity(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Long getDealerDayCapacity(Long dealerId) throws BusinessException {
		log.debug("== Inicia getDealerDayCapacity/DealerDetailBusinessBean ==");
        try {
        	Long response = 0L;
        	DealerDetail dealerDetail = getDealerDetailByID(dealerId);
        	
        	if( dealerDetail != null ){
        		if( dealerDetail.getCompanyWoQtyDay() != null && dealerDetail.getCompanyWoQtyDay().longValue() > 0 ){
            		response = dealerDetail.getCompanyWoQtyDay();
            	} else if( dealerDetail.getTechnicianWoQtyDay() != null && dealerDetail.getTechnicianWoQtyDay().longValue() > 0 ){
            		response = dealerDetail.getTechnicianWoQtyDay() * this.employeesCrewDAO.getActiveTechniciansQtyByDealerIdAndActiveCrew(dealerId);
            	}
        	}else{
        		/* IN375115 
        		String dealerName=daoDealers.getDealerByID(dealerId).getDealerName();
        		throw new BusinessException(ErrorBusinessMessages.DEALER_DOES_NOT_HAVE_DETAILS.getCode(), ErrorBusinessMessages.DEALER_DOES_NOT_HAVE_DETAILS.getMessage(new String[]{"" + dealerName}),Arrays.asList(new String[]{"" + dealerName}));
        		*/
        	}
            return response;
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getDealerDayCapacity/DealerDetailBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getDealerDayCapacity/DealerDetailBusinessBean ==");
        }
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.DealerDetailBusinessBeanLocal#getAllDealerDetailsByCountry(java.lang.Long)
	 */
	@Override
	public List<DealerDetailDTO> getAllDealerDetailsByCountry(Long countryId)
			throws BusinessException {
		 log.debug("== Inicia getAllDealerDetailsByCountry/DealerDetailBusinessBean ==");
	        try {
	        	List<DealerDetailDTO> result = new ArrayList<DealerDetailDTO>();
	        	List<DealerDetailVO> resultVO = UtilsBusiness.convertList(daoDealerDetail.getAllDealerDetails(), DealerDetailVO.class);
	        	for(DealerDetailVO tmp : resultVO){
	        		DealerDetailDTO resultTmp = new DealerDetailDTO();
	        		resultTmp.setBuild(tmp.getAttendBuildings());
	        		resultTmp.setDealerCode(tmp.getDealer().getDealerCode().toString());
	        		resultTmp.setDealerName(tmp.getDealer().getDealerName());
	        		resultTmp.setDepotCode(tmp.getDealer().getDepotCode());
	        		resultTmp.setWoD(tmp.getCompanyWoQtyDay().toString());
	        		resultTmp.setWoT(tmp.getTechnicianWoQtyDay().toString());
	        		resultTmp.setAttendTypeOddEven(tmp.getAttendTypeOddEven());
	        		result.add(resultTmp);
	        	}
	            return result;

	        } catch(Throwable ex){
	        	log.error("== Error al tratar de ejecutar la operación getAllDealerDetailsByCountry/DealerDetailBusinessBean ==");
	        	throw this.manageException(ex);
	        } finally {
	            log.debug("== Termina getAllDealerDetailsByCountry/DealerDetailBusinessBean ==");
	        }
	}
	
	public void generateAttendTypeOddEven(DealerDetailVO dealerDetailVO) throws PropertiesException{
		
		if(dealerDetailVO != null && dealerDetailVO.getAttendTypeOddEven()!=null){
			if(dealerDetailVO.getAttendTypeOddEven().equals(CodesBusinessEntityEnum.DEALER_DETAIL_ATTEND_TYPE_EVEN.getCodeEntity())){
				dealerDetailVO.setAttendTypeEven(true);
	        }
	        
	        if(dealerDetailVO.getAttendTypeOddEven().equals(CodesBusinessEntityEnum.DEALER_DETAIL_ATTEND_TYPE_ODD.getCodeEntity())){
	        	dealerDetailVO.setAttendTypeOdd(true);
	        }
		}
        
	}
	
	private void generateAttendTypeOddEven(DealerDetailVO dealerDetailVO,DealerDetail dealerDetail) throws PropertiesException{
		
		if(dealerDetailVO != null){
			
			if((!dealerDetailVO.isAttendTypeEven() && !dealerDetailVO.isAttendTypeOdd()) || (dealerDetailVO.isAttendTypeEven() && dealerDetailVO.isAttendTypeOdd())){
				dealerDetail.setAttendTypeOddEven(null);
	        }else if(dealerDetailVO.isAttendTypeEven()){
	        	dealerDetail.setAttendTypeOddEven(CodesBusinessEntityEnum.DEALER_DETAIL_ATTEND_TYPE_EVEN.getCodeEntity());
	        }else if(dealerDetailVO.isAttendTypeOdd()){
	        	dealerDetail.setAttendTypeOddEven(CodesBusinessEntityEnum.DEALER_DETAIL_ATTEND_TYPE_ODD.getCodeEntity());
	        }
			
		}
        
	}
	
}
