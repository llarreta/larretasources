package co.com.directv.sdii.assign.assignment.fileprocessor.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import co.com.directv.sdii.assign.assignment.fileprocessor.BasicFileProcessor;
import co.com.directv.sdii.assign.assignment.fileprocessor.DealerBuildingsFileProcessorLocal;
import co.com.directv.sdii.common.enumerations.ApplicationTextEnum;
import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.assign.BuildingBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.assign.DealerBuldingBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.assign.DealerCoverageBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.dealers.DealersCRUDLocal;
import co.com.directv.sdii.ejb.business.dealers.PostalCodesCRUDBeanLocal;
import co.com.directv.sdii.ejb.business.file.DTO.FileRecordDTO;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.pojo.Building;
import co.com.directv.sdii.model.vo.BuildingVO;
import co.com.directv.sdii.model.vo.DealerBuldingVO;
import co.com.directv.sdii.model.vo.DealerCoverageVO;
import co.com.directv.sdii.model.vo.DealerVO;
import co.com.directv.sdii.model.vo.PostalCodeVO;

/**
 * 
 * procesador para el cargue y actualización de la atención en edificios las compañías.
 * El archivo incluye la prioridad
 * 
 * Fecha de Creación: 25/07/2011
 * @author wjimenez <a href="mailto:wjimenez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Stateless(name="DealerBuildingsFileProcessorLocal",mappedName="ejb/DealerBuildingsFileProcessorLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class DealerBuildingsFileProcessor extends BasicFileProcessor implements DealerBuildingsFileProcessorLocal {

	@EJB(name="DealersCRUDLocal", beanInterface=DealersCRUDLocal.class)
    private DealersCRUDLocal dealerBusiness;
	
	@EJB(name="PostalCodesCRUDBeanLocal", beanInterface=PostalCodesCRUDBeanLocal.class)
    private PostalCodesCRUDBeanLocal postalCodeBusiness;
	
	@EJB(name="DealerCoverageBusinessBeanLocal", beanInterface=DealerCoverageBusinessBeanLocal.class)
    private DealerCoverageBusinessBeanLocal dealerCoverageBusiness;
	
	@EJB(name="DealerBuldingBusinessBeanLocal", beanInterface=DealerBuldingBusinessBeanLocal.class)
    private DealerBuldingBusinessBeanLocal dealerBuildingBusiness;
	
	@EJB(name="BuildingBusinessBeanLocal", beanInterface=BuildingBusinessBeanLocal.class)
    private BuildingBusinessBeanLocal buildingBusiness;
	
	private static int POS_DEALER_CODE = 0;
	private static int POS_DEPOT_CODE = 1;
	private static int POS_POSTAL_CODE = 2;
	private static int POS_BUILDING_CODE = 3;
	
	public DealerBuildingsFileProcessor() {
		String[] columnTitles = new String[]	{
				ApplicationTextEnum.DEALER_CODE.getApplicationTextValue(),
				ApplicationTextEnum.DEALER_DEPOT_CODE.getApplicationTextValue(),
				ApplicationTextEnum.POSTAL_CODE.getApplicationTextValue(),
				ApplicationTextEnum.BUILDING_CODE.getApplicationTextValue()
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
		
		DealerBuldingVO dealerBuildingVO = new DealerBuldingVO();
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
			throw new BusinessException("Se está intentando cargar un registro DealerBulding desde archivo para una cobertura que no está configurada. dealerId = " + dealerVO.getId() + " postalCodeId = " + postalCodeVO.getId());
		}
		
		dealerBuildingVO.setDealerCoverage(dealerCoverageVO);
		
		Long buildingCode = getLongValue(record[POS_BUILDING_CODE], getColumnTitles()[POS_BUILDING_CODE]);
		BuildingVO buildingVO = buildingBusiness.getBuildingByCode(buildingCode);
		if(buildingVO == null) {
			throw new BusinessException("Se está intentando cargar un registro DealerBulding desde archivo con un código de edificio no existente. código = " + buildingCode);
		}
		dealerBuildingVO.setBuilding(UtilsBusiness.copyObject(Building.class, buildingVO));
		
		String trueVal = null;
		try {
			trueVal = CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity();
		} catch (PropertiesException e) {
			throw new BusinessException("no se pudo leer la propiedad BOOLEAN_TRUE. " + e.getMessage(), e);
		}
		
		dealerBuildingVO.setStatus(trueVal);
		
		dealerBuildingVO.setUser(getUploadFile().getUser());
		dealerBuildingVO.setDateLastChange(UtilsBusiness.fechaActual());
		
		DealerBuldingVO dealerBuildingPersisted = dealerBuildingBusiness.getDealerBuildingByDealerCoverageIdAndBuildingId(dealerCoverageVO.getId(), buildingVO.getId());
		if(dealerBuildingPersisted != null) {
			dealerBuildingVO.setId(dealerBuildingPersisted.getId());
		}
		
		dealerBuildingBusiness.updateDealerBulding(dealerBuildingVO);
		
	}

	@Override
	public boolean validateFile() throws BusinessException {
		return true;
	}

}
