package co.com.directv.sdii.assign.assignment.fileprocessor.impl;


import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import co.com.directv.sdii.assign.assignment.fileprocessor.BasicFileProcessor;
import co.com.directv.sdii.assign.assignment.fileprocessor.DealerDetailsFileProcessorLocal;
import co.com.directv.sdii.common.enumerations.ApplicationTextEnum;
import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.ejb.business.assign.DealerDetailBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.dealers.DealersCRUDLocal;
import co.com.directv.sdii.ejb.business.file.DTO.FileRecordDTO;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.vo.DealerDetailVO;
import co.com.directv.sdii.model.vo.DealerVO;

/**
 * 
 * procesador para el cargue y actualización de los detalles de las compañías.
 * 
 * Fecha de Creación: 25/07/2011
 * @author wjimenez <a href="mailto:wjimenez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Stateless(name="DealerDetailsFileProcessorLocal",mappedName="ejb/DealerDetailsFileProcessorLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class DealerDetailsFileProcessor extends BasicFileProcessor implements DealerDetailsFileProcessorLocal {

	@EJB(name="DealersCRUDLocal", beanInterface=DealersCRUDLocal.class)
    private DealersCRUDLocal dealerBusiness;
	
	@EJB(name="DealerDetailBusinessBeanLocal", beanInterface=DealerDetailBusinessBeanLocal.class)
    private DealerDetailBusinessBeanLocal dealerDetailBusiness;
	
	public DealerDetailsFileProcessor() {
		String[] columnTitles = new String[] {
				ApplicationTextEnum.DEALER_CODE.getApplicationTextValue(),
				ApplicationTextEnum.DEPOT_CODE.getApplicationTextValue(),
				ApplicationTextEnum.TECHNICAL_WOX.getApplicationTextValue(),
				ApplicationTextEnum.COMP_WOX.getApplicationTextValue(),
				ApplicationTextEnum.ED_ATTEND.getApplicationTextValue(),
				ApplicationTextEnum.PAIR_OR_ODD_ATTEND.getApplicationTextValue()
			};
		setColumnTitles(columnTitles);
	}
	
	private static int CODIGO_DEALER = 0;
	private static int CODIGO_DEPOT = 1;
	private static int WO_X_TECNICO = 2;
	private static int WO_X_COMP = 3;
	private static int ATIENDE_EDIFICIO = 4;
	private static int ATIENDE_TIPO_PAR_IMPAR = 5;
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.assign.assignment.fileprocessor.BasicFileProcessor#processData(java.util.List)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void processRecord(FileRecordDTO fileRecordDTO) throws BusinessException {
		DealerDetailVO dealerDetail = new DealerDetailVO();
		String[] record = fileRecordDTO.getRowData();
		
		String codigoDealer = record[CODIGO_DEALER];
		String codigoDepot = record[CODIGO_DEPOT];
		String woTecnico = record[WO_X_TECNICO];
		String woComp = record[WO_X_COMP];
		String atiendeEdificio = record[ATIENDE_EDIFICIO];
		String attendTypeOddEven  = record[ATIENDE_TIPO_PAR_IMPAR];
		
		String codigoDealerTittle = getColumnTitles()[CODIGO_DEALER];
		String woTecnicoTittle = getColumnTitles()[WO_X_TECNICO];
		String woCompTittle = getColumnTitles()[WO_X_COMP];
		String atiendeEdificioTittle = getColumnTitles()[ATIENDE_EDIFICIO];
		
		atiendeEdificio = this.setValueDefaulAttendBuilding(atiendeEdificio);
		
		Long dealerCode = getLongValue(codigoDealer, codigoDealerTittle);
		DealerVO dealer = dealerBusiness.getDealerByDepotCodeAndDealerCodeAndCountryId(dealerCode, codigoDepot,this.getUploadFile().getCountry().getId());
		if(dealer == null) {
			throw new BusinessException("no se encuentra el dealer con código " + dealerCode + " y depot " + codigoDepot);
		}
		dealerDetail.setDealerId(dealer.getId());
		
		Long technicianWoQtyDay = getLongValue(woTecnico, woTecnicoTittle);
		dealerDetail.setTechnicianWoQtyDay(technicianWoQtyDay);
		
		Long companyWoQtyDay = getLongValue(woComp, woCompTittle);
		dealerDetail.setCompanyWoQtyDay(companyWoQtyDay);
		
		if(technicianWoQtyDay == null && companyWoQtyDay == null ) {
			throw new BusinessException("Se debe digitar cantidad de workOrder por Técnico o por Compañía.");
		}else if(technicianWoQtyDay.longValue() >0 && companyWoQtyDay.longValue() >0 ) {
			throw new BusinessException("Se debe digitar únicamente la cantidad de workOrder por Técnico o por Compañías.");
		}
		
		dealerDetail.setAttendBuildings(getValidatedStatusValue(atiendeEdificio, atiendeEdificioTittle));
		setAttendTypeOddEven(dealerDetail,attendTypeOddEven);
		dealerDetailBusiness.updateDealerDetail(dealerDetail);
		
	}
	
	/**Metodo encargado de colocar el valor default a la propiedad atiene edificio en caso de que no sea 
	 * diligenciado en el archivo
	 * 
	 * @throws BusinessException
	 */
	private String setValueDefaulAttendBuilding(String atiendeEdificio) throws BusinessException{
		String atiendeEdificioValue = "";
		try {
			if(atiendeEdificio==null||atiendeEdificio.equals("")){
				atiendeEdificioValue = CodesBusinessEntityEnum.BOOLEAN_FALSE.getCodeEntity();
			}else{
				atiendeEdificioValue = atiendeEdificio;
			}
			return atiendeEdificioValue;
		} catch (Throwable t) {
			this.manageException(t);
		}
		return atiendeEdificioValue;
		
	}
	
	@Override
	public boolean validateFile() throws BusinessException {
		return true;
	}
	
	private void setAttendTypeOddEven(DealerDetailVO dealerDetail,String attendTypeOddEven) throws BusinessException{
		try {
			
			if(attendTypeOddEven!=null && !attendTypeOddEven.equals("")){
					String codeEven= CodesBusinessEntityEnum.DEALER_DETAIL_ATTEND_TYPE_EVEN.getCodeEntity();
					String codeOdd= CodesBusinessEntityEnum.DEALER_DETAIL_ATTEND_TYPE_ODD.getCodeEntity();
					if(!attendTypeOddEven.equals(codeEven) 
					   && !attendTypeOddEven.equals(codeOdd)){
							throw new BusinessException("Se debe digitar únicamente "+codeEven+" o "+ codeOdd +" en atiende par o impar.");
					}
			}else{
				attendTypeOddEven=null;
			}
			dealerDetail.setAttendTypeOddEven(attendTypeOddEven);
			dealerDetailBusiness.generateAttendTypeOddEven(dealerDetail);
		} catch (PropertiesException e) {
			throw new BusinessException("No se ha parametrizado los valores DEALER_DETAIL_ATTEND_TYPE_EVEN o DEALER_DETAIL_ATTEND_TYPE_ODD.");
		}
	}
}
