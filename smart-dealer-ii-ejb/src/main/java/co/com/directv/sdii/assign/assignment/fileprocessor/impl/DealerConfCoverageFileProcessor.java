package co.com.directv.sdii.assign.assignment.fileprocessor.impl;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import co.com.directv.sdii.assign.assignment.fileprocessor.BasicFileProcessor;
import co.com.directv.sdii.assign.assignment.fileprocessor.DealerConfCoverageFileProcessorLocal;
import co.com.directv.sdii.common.enumerations.ApplicationTextEnum;
import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessArea.BusinessAreaCRUDLocal;
import co.com.directv.sdii.ejb.business.assign.CoverageTypeBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.assign.DealerConfCoverageBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.assign.DealerConfigurationBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.customer.CustomerCategoryCRUDLocal;
import co.com.directv.sdii.ejb.business.dealers.DealersCRUDLocal;
import co.com.directv.sdii.ejb.business.dealers.PostalCodesCRUDBeanLocal;
import co.com.directv.sdii.ejb.business.file.DTO.FileRecordDTO;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.facade.assign.AssignmentConfigurationFacadeBeanLocal;
import co.com.directv.sdii.model.pojo.Dealer;
import co.com.directv.sdii.model.pojo.PostalCode;
import co.com.directv.sdii.model.vo.BusinessAreaVO;
import co.com.directv.sdii.model.vo.CustomerCategoryVO;
import co.com.directv.sdii.model.vo.DealerConfCoverageVO;
import co.com.directv.sdii.model.vo.DealerCoverageVO;
import co.com.directv.sdii.model.vo.DealerVO;
import co.com.directv.sdii.model.vo.PostalCodeVO;

/**
 * 
 * procesador para el cargue y actualización de la config de cobertura de las compañías.
 * El archivo incluye la prioridad
 * 
 * Fecha de Creación: 09/01/2014
 * @author ssanabri
 * @version 1.0
 * 
 * @see
 */
@Stateless(name="DealerConfCoverageFileProcessorLocal",mappedName="ejb/DealerConfCoverageFileProcessorLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class DealerConfCoverageFileProcessor extends BasicFileProcessor implements DealerConfCoverageFileProcessorLocal {

	@EJB(name="DealersCRUDLocal", beanInterface=DealersCRUDLocal.class)
    private DealersCRUDLocal dealerBusiness;
	
	@EJB(name="CustomerCategoryCRUDLocal", beanInterface=CustomerCategoryCRUDLocal.class)
    private CustomerCategoryCRUDLocal customerCategoryBusiness;
    
	@EJB(name="BusinessAreaCRUDLocal", beanInterface=BusinessAreaCRUDLocal.class)
    private BusinessAreaCRUDLocal businessAreaBusiness;
    
    
	
	@EJB(name="PostalCodesCRUDBeanLocal", beanInterface=PostalCodesCRUDBeanLocal.class)
    private PostalCodesCRUDBeanLocal postalCodeBusiness;
	
	@EJB(name="DealerConfCoverageBusinessBeanLocal", beanInterface=DealerConfCoverageBusinessBeanLocal.class)
    private DealerConfCoverageBusinessBeanLocal dealerConfCoverageBusiness;

	@EJB(name="DealerConfigurationBusinessBeanLocal", beanInterface=DealerConfigurationBusinessBeanLocal.class)
    private DealerConfigurationBusinessBeanLocal dealerConfigurationBusiness;
	
	@EJB(name="CoverageTypeBusinessBeanLocal", beanInterface=CoverageTypeBusinessBeanLocal.class)
    private CoverageTypeBusinessBeanLocal coverageTypeBusiness;

	@EJB(name="AssignmentConfigurationFacadeBeanLocal", beanInterface=AssignmentConfigurationFacadeBeanLocal.class)
    private AssignmentConfigurationFacadeBeanLocal assignmentConfigurationFacadeBeanLocal;
	
	
	private static int POS_DEALER_CODE = 0;
	private static int POS_DEPOT_CODE = 1;
	private static int POS_BUSINESS_AREA_CODE = 2;
	private static int POS_CUSTOMER_CATEGORY_CODE = 3;
	private static int POS_POSTAL_CODE = 4;
	private static int POS_PERMANENT = 5;
	private static int POS_PRIORITY = 6;
	
	public DealerConfCoverageFileProcessor() {
		String[] columnTitles = new String[]	{
				ApplicationTextEnum.DEALER_CODE.getApplicationTextValue(),
				ApplicationTextEnum.DEALER_DEPOT_CODE.getApplicationTextValue(),
				ApplicationTextEnum.BUSINESS_AREA_CODE.getApplicationTextValue(),
				ApplicationTextEnum.CUSTOMER_CATEGORY_CODE.getApplicationTextValue(),
				ApplicationTextEnum.POSTAL_CODE.getApplicationTextValue(),
				ApplicationTextEnum.PERMANENT.getApplicationTextValue(),
				ApplicationTextEnum.PRIORITY.getApplicationTextValue()
		};
		setColumnTitles(columnTitles);
	}
	
	/*
	 * modificacion ialessan
	 * release_4.2.1.5_Spira_28780 - Configuracion Masiva de Cobertura  de  La  compañía  por  Clase de Cliente
	 * marzo 2014
	 * 
	 * 		
	 * modificacion ialessan
	 * Spira_29346_Old28780-ReplicarFuncPantallaACargaMasivaCobertura
	 * abril 2014
	 * 
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.assign.assignment.fileprocessor.BasicFileProcessor#processData(java.util.List)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void processRecord(FileRecordDTO fileRecordDTO) throws BusinessException {
		DealerConfCoverageVO dealerConfCoverageVO = new DealerConfCoverageVO();
		String[] record = fileRecordDTO.getRowData();
			
		String dealerCode           = record[POS_DEALER_CODE];		
		String depotCode 			= record[POS_DEPOT_CODE];
		String businessAreaCode 	= record[POS_BUSINESS_AREA_CODE];
		String customerCategoryCode = record[POS_CUSTOMER_CATEGORY_CODE];
		
		
		DealerVO dealerVO = dealerBusiness.getDealerByCode(dealerCode);		
		dealerConfCoverageVO.setDealer(UtilsBusiness.copyObject(Dealer.class, dealerVO));		
		if(dealerVO == null) {
			throw new BusinessException("No se encuentra el dealer con DealerCode:" + dealerCode + ", Depot:" + depotCode );
		}
		

		CustomerCategoryVO customerCategoryVO = customerCategoryBusiness.getCustomerCategorByCode(customerCategoryCode);
		if(customerCategoryVO == null) {
			throw new BusinessException("No se encuentra la categoria de cliente con CustomerCategoryCode:" + customerCategoryCode );
		}
		
		BusinessAreaVO businessAreaVO = businessAreaBusiness.getBusinessAreaByCode(businessAreaCode);
		if(businessAreaVO == null) {
			throw new BusinessException("No se encuentra el area de negocio con BusinessAreaCode:" + businessAreaCode );
		}

		//PostalCode
		String postalCode = "";		
		//tengo que poner en este metodo este cacheo porque "split" arroja excepcion en caso de ser una expresion invalida
		try {
			postalCode = record[POS_POSTAL_CODE].split("\\.")[0];
		} catch (Exception e1) {
			throw new BusinessException(ErrorBusinessMessages.PARAMS_INVALID_EXPRESSION.getCode(), ErrorBusinessMessages.PARAMS_INVALID_EXPRESSION.getMessage().concat(" Postal Code "));
		}
		
		Long countryId = getUploadFile().getCountry().getId();
		PostalCodeVO postalCodeVO = postalCodeBusiness.getPostalCodesByCodeByCountryId(postalCode, countryId);
		if(postalCodeVO == null) {
			throw new BusinessException("No se encuentra el código postal con código " + postalCode + " e idPais = " + countryId);
		}
		dealerConfCoverageVO.setPostalCode(UtilsBusiness.copyObject(PostalCode.class, postalCodeVO)); //1
		
		//Permanent
		String isPermanent = getValidatedStatusValue(record[POS_PERMANENT], getColumnTitles()[POS_PERMANENT]);		
		String trueVal = null;
		String falseVal = null;
		try {
			trueVal = CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity();
			falseVal = CodesBusinessEntityEnum.BOOLEAN_FALSE.getCodeEntity();
		} catch (PropertiesException e) {
			throw new BusinessException("No se pudo leer la propiedad BOOLEAN_TRUE o BOOLEAN_FALSE. " + e.getMessage(), e);
		}
		
		if(isPermanent==null || (!isPermanent.equals(trueVal) && !isPermanent.equals(falseVal))){
			throw new BusinessException("El registro no tiene un valor válido en el campo \"Permanente?\", el valor que trae es: "+isPermanent+" y debe tener valor S ó N");
		}
		
		if(isPermanent.equals(trueVal)) {
			dealerConfCoverageVO.setCoverageType(coverageTypeBusiness.getCoverageTypePermanent()); //2
		} else {
			dealerConfCoverageVO.setCoverageType(coverageTypeBusiness.getCoverageTypeOccasional());//2
		}
		
		List<DealerCoverageVO> dealerCoverageVOList = new ArrayList<DealerCoverageVO>();
		
		DealerCoverageVO dealerCoverageVO = new DealerCoverageVO();
		
		dealerCoverageVO.setPostalCode(UtilsBusiness.copyObject(PostalCode.class, postalCodeVO)); //1
		
		if(isPermanent.equals(trueVal)) {
			dealerCoverageVO.setCoverageType(coverageTypeBusiness.getCoverageTypePermanent()); //2
		} else {
			dealerCoverageVO.setCoverageType(coverageTypeBusiness.getCoverageTypeOccasional());//2
		}		
		//Priority
		Long dealerPriority = getLongValue(record[POS_PRIORITY], getColumnTitles()[POS_PRIORITY]);
		dealerCoverageVO.setDealerPriority(dealerPriority); //3
		dealerCoverageVO.setCountry(getUploadFile().getCountry());//4
		dealerCoverageVO.setStatus(trueVal);//5
		dealerCoverageVO.setUserId(getUploadFile().getUser().getId());//6
		dealerCoverageVO.setDateLastChange(UtilsBusiness.fechaActual());//7		
		dealerCoverageVO.setDealer(UtilsBusiness.copyObject(Dealer.class, dealerVO));
		
		dealerCoverageVOList.add(dealerCoverageVO);
		
		assignmentConfigurationFacadeBeanLocal.updateDealerConfCoverageConfiguration(   dealerCoverageVOList,
																						dealerVO.getId(),
																						customerCategoryVO.getId(),
																						businessAreaVO.getId(),
																						getUploadFile().getUser().getId()
																						);
	}
	
	@Override
	public boolean validateFile() throws BusinessException {
		return true;
	}
}
