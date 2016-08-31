package co.com.directv.sdii.assign.assignment.fileprocessor.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import co.com.directv.sdii.assign.assignment.fileprocessor.BasicFileProcessor;
import co.com.directv.sdii.assign.assignment.fileprocessor.DealerCoverageFileProcessorLocal;
import co.com.directv.sdii.common.enumerations.ApplicationTextEnum;
import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.assign.CoverageTypeBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.assign.DealerCoverageBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.dealers.DealersCRUDLocal;
import co.com.directv.sdii.ejb.business.dealers.PostalCodesCRUDBeanLocal;
import co.com.directv.sdii.ejb.business.file.DTO.FileRecordDTO;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.pojo.Dealer;
import co.com.directv.sdii.model.pojo.PostalCode;
import co.com.directv.sdii.model.vo.DealerCoverageVO;
import co.com.directv.sdii.model.vo.DealerVO;
import co.com.directv.sdii.model.vo.PostalCodeVO;

/**
 * 
 * procesador para el cargue y actualización de la cobertura de las compañías.
 * El archivo incluye la prioridad
 * 
 * Fecha de Creación: 25/07/2011
 * @author wjimenez <a href="mailto:wjimenez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Stateless(name="DealerCoverageFileProcessorLocal",mappedName="ejb/DealerCoverageFileProcessorLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class DealerCoverageFileProcessor extends BasicFileProcessor implements DealerCoverageFileProcessorLocal {

	@EJB(name="DealersCRUDLocal", beanInterface=DealersCRUDLocal.class)
    private DealersCRUDLocal dealerBusiness;
	
	@EJB(name="PostalCodesCRUDBeanLocal", beanInterface=PostalCodesCRUDBeanLocal.class)
    private PostalCodesCRUDBeanLocal postalCodeBusiness;
	
	@EJB(name="DealerCoverageBusinessBeanLocal", beanInterface=DealerCoverageBusinessBeanLocal.class)
    private DealerCoverageBusinessBeanLocal dealerCoverageBusiness;
	
	@EJB(name="CoverageTypeBusinessBeanLocal", beanInterface=CoverageTypeBusinessBeanLocal.class)
    private CoverageTypeBusinessBeanLocal coverageTypeBusiness;
	
	private static int POS_DEALER_CODE = 0;
	private static int POS_DEPOT_CODE = 1;
	private static int POS_POSTAL_CODE = 2;
	private static int POS_PERMANENT = 3;
	private static int POS_PRIORITY = 4;
	
	public DealerCoverageFileProcessor() {
		String[] columnTitles = new String[]	{
				ApplicationTextEnum.DEALER_CODE.getApplicationTextValue(),
				ApplicationTextEnum.DEALER_DEPOT_CODE.getApplicationTextValue(),
				ApplicationTextEnum.POSTAL_CODE.getApplicationTextValue(),
				ApplicationTextEnum.PERMANENT.getApplicationTextValue(),
				ApplicationTextEnum.PRIORITY.getApplicationTextValue()
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
		DealerCoverageVO dealerCoverage = new DealerCoverageVO();
		String[] record = fileRecordDTO.getRowData();
		
		Long dealerCode = getLongValue(record[POS_DEALER_CODE], getColumnTitles()[POS_DEALER_CODE]);
		String depot = record[POS_DEPOT_CODE];
		DealerVO dealerVO = dealerBusiness.getDealerByDepotCodeAndDealerCodeAndCountryId(dealerCode, depot,this.getUploadFile().getCountry().getId());

		if(dealerVO == null) {
			throw new BusinessException("no se encuentra el dealer con código " + dealerCode + " y depot " + depot);
		}
		
		dealerCoverage.setDealer(UtilsBusiness.copyObject(Dealer.class, dealerVO));
		
		String postalCode = "";
		try {
			postalCode = record[POS_POSTAL_CODE].split("\\.")[0];
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		Long countryId = getUploadFile().getCountry().getId();
		PostalCodeVO postalCodeVO = postalCodeBusiness.getPostalCodesByCodeByCountryId(postalCode, countryId);
		if(postalCodeVO == null) {
			throw new BusinessException("no se encuentra el código postal con código " + postalCode + " e idPais = " + countryId);
		}
		dealerCoverage.setPostalCode(UtilsBusiness.copyObject(PostalCode.class, postalCodeVO));
		
		String isPermanent = getValidatedStatusValue(record[POS_PERMANENT], getColumnTitles()[POS_PERMANENT]);
		
		String trueVal = null;
		try {
			trueVal = CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity();
		} catch (PropertiesException e) {
			throw new BusinessException("no se pudo leer la propiedad BOOLEAN_TRUE. " + e.getMessage(), e);
		}

		String falseVal = null;
		try {
			falseVal = CodesBusinessEntityEnum.BOOLEAN_FALSE.getCodeEntity();
		} catch (PropertiesException e) {
			throw new BusinessException("no se pudo leer la propiedad BOOLEAN_FALSE. " + e.getMessage(), e);
		}
		
		if(isPermanent==null || (!isPermanent.equals(trueVal) && !isPermanent.equals(falseVal))){
			throw new BusinessException("El registro no tiene un valor válido en el campo \"Permanente?\", el valor que trae es: "+isPermanent+" y debe tener valor S ó N");
		}
		
		if(isPermanent.equals(trueVal)) {
			dealerCoverage.setCoverageType(coverageTypeBusiness.getCoverageTypePermanent());
		} else {
			dealerCoverage.setCoverageType(coverageTypeBusiness.getCoverageTypeOccasional());
		}
		
		Long dealerPriority = getLongValue(record[POS_PRIORITY], getColumnTitles()[POS_PRIORITY]);
		dealerCoverage.setDealerPriority(dealerPriority);

		dealerCoverage.setCountry(getUploadFile().getCountry());
		dealerCoverage.setStatus(trueVal);
		
		dealerCoverage.setUser(getUploadFile().getUser());
		dealerCoverage.setDateLastChange(UtilsBusiness.fechaActual());
		
		List<DealerCoverageVO> dealerCoveragePersisted = dealerCoverageBusiness.getDealerCoverageByDealerIdPostalCodeIdCountryIdStatusActive(dealerVO.getId(), postalCodeVO.getId(), null, "");
		if(dealerCoveragePersisted != null && dealerCoveragePersisted.size()>0) {
			//por la llave de unicidad se puede tomar el primer objeto
			dealerCoverage.setId(dealerCoveragePersisted.get(0).getId());
		}
		
		dealerCoverageBusiness.updateDealerCoverage(dealerCoverage);
		
	}

	@Override
	public boolean validateFile() throws BusinessException {
		return true;
	}

}
