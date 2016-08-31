package co.com.directv.sdii.assign.assignment.fileprocessor.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import co.com.directv.sdii.assign.assignment.fileprocessor.BasicFileProcessor;
import co.com.directv.sdii.assign.assignment.fileprocessor.DealerServiceSubcatWthPcFileProcessorLocal;
import co.com.directv.sdii.common.enumerations.ApplicationTextEnum;
import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.assign.DealerCoverageBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.assign.DealerServiceSubCategoryWithPcBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.config.ServiceCategoryBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.dealers.DealersCRUDLocal;
import co.com.directv.sdii.ejb.business.dealers.PostalCodesCRUDBeanLocal;
import co.com.directv.sdii.ejb.business.file.DTO.FileRecordDTO;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.pojo.ServiceCategory;
import co.com.directv.sdii.model.vo.DealerCoverageVO;
import co.com.directv.sdii.model.vo.DealerServiceSubCategoryWithPcVO;
import co.com.directv.sdii.model.vo.DealerVO;
import co.com.directv.sdii.model.vo.PostalCodeVO;
import co.com.directv.sdii.model.vo.ServiceCategoryVO;

/**
 * 
 * procesador para el cargue y actualización de la configuración de Atención
 * de sub categorías de servicio por cada cobertura de la compañía.
 * 
 * Fecha de Creación: 25/07/2011
 * @author wjimenez <a href="mailto:wjimenez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Stateless(name="DealerServiceSubcatWthPcFileProcessorLocal",mappedName="ejb/DealerServiceSubcatWthPcFileProcessorLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class DealerServiceSubcatWthPcFileProcessor extends BasicFileProcessor implements DealerServiceSubcatWthPcFileProcessorLocal {

	@EJB(name="DealersCRUDLocal", beanInterface=DealersCRUDLocal.class)
    private DealersCRUDLocal dealerBusiness;
	
	@EJB(name="PostalCodesCRUDBeanLocal", beanInterface=PostalCodesCRUDBeanLocal.class)
    private PostalCodesCRUDBeanLocal postalCodeBusiness;
	
	@EJB(name="DealerCoverageBusinessBeanLocal", beanInterface=DealerCoverageBusinessBeanLocal.class)
    private DealerCoverageBusinessBeanLocal dealerCoverageBusiness;
	
	@EJB(name="ServiceCategoryBusinessBeanLocal", beanInterface=ServiceCategoryBusinessBeanLocal.class)
    private ServiceCategoryBusinessBeanLocal serviceCategoryBusiness;
	
	@EJB(name="DealerServiceSubCategoryWithPcBusinessBeanLocal", beanInterface=DealerServiceSubCategoryWithPcBusinessBeanLocal.class)
    private DealerServiceSubCategoryWithPcBusinessBeanLocal dealerServiceSubCategoryWithPcBusiness;
	
	private static int POS_DEALER_CODE = 0;
	private static int POS_DEPOT_CODE = 1;
	private static int POS_POSTAL_CODE = 2;
	private static int POS_COD_SERVICE_CAT = 3;
	
	public DealerServiceSubcatWthPcFileProcessor() {
		String[] columnTitles = new String[]	{
				ApplicationTextEnum.DEALER_CODE.getApplicationTextValue(),
				ApplicationTextEnum.DEALER_DEPOT_CODE.getApplicationTextValue(),
				ApplicationTextEnum.POSTAL_CODE.getApplicationTextValue(),
				ApplicationTextEnum.SERVICE_SUBCAT_CODE.getApplicationTextValue()
		};
		setColumnTitles(columnTitles);
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.assign.assignment.fileprocessor.BasicFileProcessor#processData(java.util.List)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void processRecord(FileRecordDTO fileRecordDTO) throws BusinessException {
		DealerServiceSubCategoryWithPcVO dealerServiceSubcatWithPc = new DealerServiceSubCategoryWithPcVO();
		String[] record = fileRecordDTO.getRowData();
		
		Long dealerCode = getLongValue(record[POS_DEALER_CODE], getColumnTitles()[POS_DEALER_CODE]);
		String depot = record[POS_DEPOT_CODE];
		Long countryId = getUploadFile().getCountry().getId();
		DealerVO dealerVO = dealerBusiness.getDealerByDepotCodeAndDealerCodeAndCountryId(dealerCode, depot,countryId);
		
		if(dealerVO == null) {
			throw new BusinessException("no se encuentra el dealer con código " + dealerCode + " y depot " + depot);
		}
		
		String postalCode = getStringFromNumericValue(record[POS_POSTAL_CODE], getColumnTitles()[POS_POSTAL_CODE]);
		PostalCodeVO postalCodeVO = postalCodeBusiness.getPostalCodesByCodeByCountryId(postalCode, countryId);
		if(postalCodeVO == null) {
			throw new BusinessException("no se encuentra el código postal con código " + postalCode + " e idPais = " + countryId);
		}
		
		DealerCoverageVO dealerCoverageVO = dealerCoverageBusiness.getDealerCoverageByDealerIdAndPostalCodeId(dealerVO.getId(), postalCodeVO.getId());
		if(dealerCoverageVO == null) {
			throw new BusinessException("Se está intentando cargar un registro DealerServiceSubCategoryWithPc desde archivo para una cobertura que no está configurada");
		}
		
		dealerServiceSubcatWithPc.setDealerCoverage(dealerCoverageVO);
		
		String trueVal = null;
		try {
			trueVal = CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity();
		} catch (PropertiesException e) {
			throw new BusinessException("no se pudo leer la propiedad BOOLEAN_TRUE. " + e.getMessage(), e);
		}
		
		String serviceCategoryCode = getStringFromNumericValue(record[POS_COD_SERVICE_CAT], getColumnTitles()[POS_COD_SERVICE_CAT]);
		ServiceCategoryVO serviceCategoryVO = serviceCategoryBusiness.getServiceCategoryByCode(serviceCategoryCode);
		if(serviceCategoryVO == null) {
			throw new BusinessException("no se encuentra el código de subcategoría de servicio {" + serviceCategoryCode + "}");
		}
		dealerServiceSubcatWithPc.setServiceCategory(UtilsBusiness.copyObject(ServiceCategory.class, serviceCategoryVO));
		
		dealerServiceSubcatWithPc.setStatus(trueVal);
		
		dealerServiceSubcatWithPc.setUser(getUploadFile().getUser());
		dealerServiceSubcatWithPc.setDateLastChange(UtilsBusiness.fechaActual());
		
		DealerServiceSubCategoryWithPcVO dealerServiceSubcatWithPcPersisted = dealerServiceSubCategoryWithPcBusiness.getByDealerCoverageIdAndServiceCategotyId(dealerCoverageVO.getId(), serviceCategoryVO.getId());
		if(dealerServiceSubcatWithPcPersisted != null) {
			dealerServiceSubcatWithPc.setId(dealerServiceSubcatWithPcPersisted.getId());
		}
		
		dealerServiceSubCategoryWithPcBusiness.updateDealerServiceSubCategoryWithPc(dealerServiceSubcatWithPc);
		
	}
	
	@Override
	public boolean validateFile() throws BusinessException {
		return true;
	}	

}
