/**
 * 
 */
package co.com.directv.sdii.reports.commands.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.common.enumerations.ApplicationTextEnum;
import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.stock.WarehouseBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.dealers.DealersFacadeBeanLocal;
import co.com.directv.sdii.facade.stock.ReferenceElementItemFacadeBeanLocal;
import co.com.directv.sdii.facade.stock.ReferenceFacadeBeanLocal;
import co.com.directv.sdii.model.pojo.Warehouse;
import co.com.directv.sdii.model.pojo.collection.ReferenceElementItemsResponse;
import co.com.directv.sdii.model.vo.DealerMediaContactVO;
import co.com.directv.sdii.model.vo.DealerVO;
import co.com.directv.sdii.model.vo.RefConfirmationVO;
import co.com.directv.sdii.model.vo.RefInconsistencyVO;
import co.com.directv.sdii.model.vo.RefRecieveDataVO;
import co.com.directv.sdii.model.vo.ReferenceElementItemVO;
import co.com.directv.sdii.model.vo.ReferenceModificationVO;
import co.com.directv.sdii.model.vo.ReferenceVO;
import co.com.directv.sdii.model.vo.SpecialCommentVO;
import co.com.directv.sdii.model.vo.WarehouseVO;
import co.com.directv.sdii.reports.commands.BaseCommand;
import co.com.directv.sdii.reports.commands.CMDPreLoadReferenceToPrintByIDLocal;
import co.com.directv.sdii.reports.commands.ICommand;
import co.com.directv.sdii.reports.dto.PreLoadGenericToPrintDTO;
import co.com.directv.sdii.reports.dto.ReferencePDFDTO;
import co.com.directv.sdii.reports.dto.ReferencePDFDTOInconsistency;
import co.com.directv.sdii.reports.dto.ReferencePDFDTOModification;
import co.com.directv.sdii.reports.dto.ReferencePDFDTOnoSeralized;
import co.com.directv.sdii.reports.dto.ReportReferencePDFDTO;

/**
 * @author cduarte
 * 
 */
@Stateless(name = "CMDPreLoadReferenceToPrintByID", mappedName = "ejb/CMDPreLoadReferenceToPrintByID")
public class CMDPreLoadReferenceToPrintByID extends BaseCommand implements
		ICommand,CMDPreLoadReferenceToPrintByIDLocal {

	private List<String> fieldList = new ArrayList<String>();
	private List<PreLoadGenericToPrintDTO> response = null;
	private static String DATOS_BASICOS = ApplicationTextEnum.BASIC_FACTS.getApplicationTextValue();
	private static String BODEGAS = ApplicationTextEnum.WAREHOUSE.getApplicationTextValue();
	private static String DATOS_ENVIO = ApplicationTextEnum.SHIPPING_DATA.getApplicationTextValue();
	private static String DATOS_INGRESO = ApplicationTextEnum.JOIN_DATA.getApplicationTextValue();
	private static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	private static String DATE_FORMAT2 = "yyyy-MM-dd";

	@EJB
	private ReferenceFacadeBeanLocal referenceFacadeBean;

	@EJB
	private DealersFacadeBeanLocal dealerFacadeBean;

	@EJB
	private ReferenceElementItemFacadeBeanLocal refElementsFacade;
	
	@EJB(name = "WarehouseBusinessBeanLocal", beanInterface = WarehouseBusinessBeanLocal.class)
	private WarehouseBusinessBeanLocal warehouseBusinessBean;
	
	public CMDPreLoadReferenceToPrintByID() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.com.directv.sdii.excelgen.ICommand#execute(java.lang.String)
	 */
	//Modificado para Requerimiento: CC057
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> execute(String args) throws BusinessException {

		try {
			response = new ArrayList<PreLoadGenericToPrintDTO>();
			List<ReferencePDFDTO> refPDFDTOlist = new ArrayList<ReferencePDFDTO>(); 				
		    List<ReferencePDFDTOnoSeralized> refPDFDTOnoSerializedlist = new ArrayList<ReferencePDFDTOnoSeralized>();
		    List<ReferencePDFDTOModification> refPDFDTOModificationList = new ArrayList<ReferencePDFDTOModification>();
		    List<ReferencePDFDTOInconsistency> refPDFDTOInconsistencyList = new ArrayList<ReferencePDFDTOInconsistency>();
			ReferenceVO referenceVO;
			HashMap<String, String> map = getParams(args);
			Long idReference = null;
			String strIdReference = map.get(CodesBusinessEntityEnum.FILE_PARAM_REFERENCE_ID.getCodeEntity());
			String strTemp="";
			StringBuffer stringBuffer = null;
			
			if (strIdReference != null && !strIdReference.isEmpty())
				idReference = Long.parseLong(strIdReference);

			referenceVO = referenceFacadeBean.getReferenceByID(idReference);

			// Se obtiene la informacion para la generacion del reporte por tipo
			// de datos
			if (referenceVO != null) {

				// DATOS_BASICOS
				savePreLoadReferenceToPrintDTO(response,"1", DATOS_BASICOS,
						ApplicationTextEnum.CONSECUTIVE_REFERRAL.getApplicationTextValue()+":",""+idReference);
				savePreLoadReferenceToPrintDTO(response,"0", DATOS_BASICOS,
						ApplicationTextEnum.COMPLETION_DATE_CREATION_REMISSION.getApplicationTextValue()+":",""+dateToString(referenceVO.getCreationReferenceDate(),DATE_FORMAT));
				
				stringBuffer = new StringBuffer();
				for (SpecialCommentVO specialComment : referenceVO.getSpecialComments()) {
					stringBuffer.append(specialComment.getCommentDescription() + "\n");
				}
				savePreLoadReferenceToPrintDTO(response,"0", DATOS_BASICOS,
						ApplicationTextEnum.SPECIAL_COMMENTS.getApplicationTextValue()+":",""+validatBlankObject(stringBuffer.toString()));
				
				
				savePreLoadReferenceToPrintDTO(response,"0", DATOS_BASICOS,
						ApplicationTextEnum.CREATING_USER_REMISSION.getApplicationTextValue()+":",""+validatBlankObject(referenceVO.getCreationUserName()));
				strTemp = "";
				if(referenceVO.getReferenceStatus() != null)
					strTemp="" + referenceVO.getReferenceStatus().getRefStatusName();
				savePreLoadReferenceToPrintDTO(response,"0", DATOS_BASICOS,
						ApplicationTextEnum.STATUS_REMISSION.getApplicationTextValue()+":",strTemp);
				
				int statusRecepted = 0;
			    if(referenceVO.getReferenceStatus().getRefStatusCode().equals(CodesBusinessEntityEnum.REFERENCE_STATUS_RECEPTED.getCodeEntity())){
			    	statusRecepted = 1;
			    }
				
				// BODEGAS
				strTemp = getWareHouseName(referenceVO.getWarehouseBySourceWh());
				savePreLoadReferenceToPrintDTO(response,"1", BODEGAS,
						ApplicationTextEnum.ORIGIN_LOCATION.getApplicationTextValue()+":",strTemp);
				
				strTemp = getWareHouseName(referenceVO.getTargetTransitWh());
				savePreLoadReferenceToPrintDTO(response,"0", BODEGAS,
						ApplicationTextEnum.LOCATION_TRANSIT.getApplicationTextValue()+":",strTemp);
				
				strTemp = getWareHouseName(referenceVO.getWarehouseByTargetWh());
				savePreLoadReferenceToPrintDTO(response,"0", BODEGAS,
						ApplicationTextEnum.DESTINY_LOCATION.getApplicationTextValue()+":",strTemp);
				
				strTemp = "";
				if(referenceVO.getWarehouseByTargetWh() != null)
					strTemp="" + referenceVO.getWarehouseByTargetWh().getWhAddress();
				savePreLoadReferenceToPrintDTO(response,"0", BODEGAS,
						ApplicationTextEnum.COMPANY_ADDRESS_BRANCH_TARGET.getApplicationTextValue()+":",strTemp);

				String telephoneNumber = "";
				if (referenceVO.getWarehouseByTargetWh().getDealerId() != null) {
					DealerVO dealerVO = dealerFacadeBean.getDealerByID(referenceVO.getWarehouseByTargetWh().getDealerId().getId());
					for (DealerMediaContactVO mediaContact : dealerVO.getMediaContacts()) {
						if(mediaContact.getMediaContactType()!= null)
							if(mediaContact.getMediaContactType().getMediaCode()!= null)
								if (mediaContact.getMediaContactType().getMediaCode().equalsIgnoreCase(CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_TELEP_CODE.getCodeEntity())) {
									if(mediaContact.getMediaContactValue()!= null)
										telephoneNumber = mediaContact.getMediaContactValue();
								}
					}
				}
				savePreLoadReferenceToPrintDTO(response,"0", BODEGAS,
						ApplicationTextEnum.PHONE_NUMBER.getApplicationTextValue()+":",""+telephoneNumber);

				strTemp = "";
				if(referenceVO.getWarehouseByTargetWh() != null)
					strTemp="" + referenceVO.getWarehouseByTargetWh().getWhResponsible();
				savePreLoadReferenceToPrintDTO(response,"0", BODEGAS,
						ApplicationTextEnum.RESPONSIBLE_WAREHOUSE_RECIPIENT.getApplicationTextValue()+":",""+strTemp);

				// DATOS_ENVIO
				savePreLoadReferenceToPrintDTO(response,"1", DATOS_ENVIO,
						ApplicationTextEnum.DATE_DISPATCH_ITEMS.getApplicationTextValue()+":",""+dateToString(referenceVO.getShippingDate(),DATE_FORMAT2));
				strTemp = referenceVO.getTransportCompany() != null ? referenceVO.getTransportCompany().getCompanyCode() : "";
				savePreLoadReferenceToPrintDTO(response,"0",DATOS_ENVIO,
						ApplicationTextEnum.CONVEYOR_COMPANY_CODE.getApplicationTextValue()+":",strTemp);
				savePreLoadReferenceToPrintDTO(response,"0", DATOS_ENVIO,
						ApplicationTextEnum.GUIDE_NUMBER.getApplicationTextValue()+":",""+validatBlankObject(referenceVO.getTransportGuide()));
				savePreLoadReferenceToPrintDTO(response,"0", DATOS_ENVIO,
						ApplicationTextEnum.DRIVER_NAME.getApplicationTextValue()+":",""+validatBlankObject(referenceVO.getDriverName()));
				savePreLoadReferenceToPrintDTO(response,"0", DATOS_ENVIO,
						ApplicationTextEnum.VEHICLE_PLATE.getApplicationTextValue()+":",""+validatBlankObject(referenceVO.getVehiclePlate()));
				savePreLoadReferenceToPrintDTO(response,"0", DATOS_ENVIO,
						ApplicationTextEnum.NUMBER_UNITS_SHIPPED.getApplicationTextValue()+":",""+validatBlankObject(referenceVO.getSendUnits()));
				savePreLoadReferenceToPrintDTO(response,"0", DATOS_ENVIO,
						ApplicationTextEnum.VOLUME.getApplicationTextValue()+":",""+validatBlankObject(referenceVO.getVolume()));
				savePreLoadReferenceToPrintDTO(response,"0", DATOS_ENVIO,
						ApplicationTextEnum.EXPECTED_DATE_DELIVERY_ITEMS.getApplicationTextValue()+":",""+dateToString(referenceVO.getDeliveryDate(),DATE_FORMAT));
				strTemp = "";
				if(referenceVO.getShippingUserId() != null)
					strTemp="" + referenceVO.getShippingUserId().getName();
				savePreLoadReferenceToPrintDTO(response,"0", DATOS_ENVIO,
						ApplicationTextEnum.USER_REGISTERS_SHIPPING.getApplicationTextValue()+":",strTemp);

				// DATOS_INGRESO
				RefRecieveDataVO refRecieveDataVO = referenceFacadeBean.getRefRecieveDataByReferenceId(idReference);
				if(refRecieveDataVO!=null){
					savePreLoadReferenceToPrintDTO(response,"1", DATOS_INGRESO,
							ApplicationTextEnum.DATE_RECEIPT_ITEMS.getApplicationTextValue()+":",""+dateToString(refRecieveDataVO.getArrivalDate(),DATE_FORMAT2));
					savePreLoadReferenceToPrintDTO(response,"0", DATOS_INGRESO,
							ApplicationTextEnum.NUMBER_UNITS_RECEIVED.getApplicationTextValue()+":",""+refRecieveDataVO.getRecieveQty());
					strTemp = "";
					if(refRecieveDataVO.getUserSend() != null)
						strTemp="" + refRecieveDataVO.getUserSend().getName();
					savePreLoadReferenceToPrintDTO(response,"0", DATOS_INGRESO,
							ApplicationTextEnum.USER_RECORDS_REVENUE.getApplicationTextValue()+":",strTemp);
				}
				
				//ELEMENTS
				ReferenceElementItemsResponse refElements = refElementsFacade.getReferenceElementItemsByReferenceID(idReference,null);
				
				for (ReferenceElementItemVO refElement : refElements.getReferenceElementItemsVO()) {
					
					if (refElement.getNotSerializedElement() != null) { //NOT SERIALIZED
						ReferencePDFDTOnoSeralized refDTOnoSerialized = new ReferencePDFDTOnoSeralized();
						
						if(refElement.getElement() != null && refElement.getElement().getElementType() != null){
							refDTOnoSerialized.setCodeTypeElement(refElement.getElement().getElementType().getTypeElementCode());//Codigo tipo del elemento
							refDTOnoSerialized.setTypeElement(refElement.getElement().getElementType().getTypeElementName());//Tipo del elemento
						}

						if(refElement.getElement() != null && refElement.getElement().getElementType() != null && refElement.getElement().getElementType().getMeasureUnit() != null){									
							refDTOnoSerialized.setMeasureUnit(refElement.getElement().getElementType().getMeasureUnit().getUnitName());
						}
						
						if(refElement.getElement() != null && refElement.getRefQuantity() != null){
							refDTOnoSerialized.setAmountRemitted(refElement.getRefQuantity().toString()); //Cantidad Remitida del elemento
						}
						
						/*if(refElement.getItemStatus() != null){
							strTemp="" + refElement.getItemStatus().getStatusName();
						}*/
						
						List<RefConfirmationVO> refConfirmations = refElement.getRefConfirmations();
						if (refConfirmations != null && !refConfirmations.isEmpty() && statusRecepted == 1 ) {
							for (RefConfirmationVO refConfirmationVO : refConfirmations) {                     
								refDTOnoSerialized.setPartialConfirmationDate(dateToString(refConfirmationVO.getComfirmDate(),DATE_FORMAT));//FECHA_DE_CONFIR_PARCIAL																					
								refDTOnoSerialized.setPartialAmount(validatBlankObject(refConfirmationVO.getConfirmedQuantity()));//CANTIDAD_PARCIAL							
							}
						}else{
							refDTOnoSerialized.setPartialAmount(" ");
							refDTOnoSerialized.setPartialConfirmationDate(" ");
						}
						
						refPDFDTOnoSerializedlist.add(refDTOnoSerialized);
						
					}else if (refElement.getSerializeElement() != null){ //SERIALIZED
						ReferencePDFDTO refDTO = new ReferencePDFDTO();
						
						if(refElement.getElement() != null){
							if(refElement.getElement().getElementType() != null){
								refDTO.setTypeElement(refElement.getElement().getElementType().getTypeElementName());
								refDTO.setCodeTypeElement(refElement.getElement().getElementType().getTypeElementCode());
							}else{
								refDTO.setCodeTypeElement(" ");
								refDTO.setTypeElement(" ");
							}
						}
						
						if(refElement.getSerializeElement() != null){
							if (refElement.getSerializeElement().getIrd() != null){
									refDTO.setRdiElement(refElement.getSerializeElement().getIrd());
							}else{
								refDTO.setRdiElement(" ");
							}			
							
							if (refElement.getSerializeElement().getSerialCode()!=null){
								refDTO.setSerialElement(refElement.getSerializeElement().getSerialCode());
							}else{
								refDTO.setSerialElement(" ");
							}
							
							if (refElement.getSerializeElement().getSerialized()!=null && refElement.getSerializeElement().getSerialized().getSerialCode()!=null){
								refDTO.setSerialElementVinculado(refElement.getSerializeElement().getSerialized().getSerialCode());
							}else{
								refDTO.setSerialElementVinculado(" ");
							}
							
						}else{
							refDTO.setSerialElementVinculado(" ");
							refDTO.setSerialElement(" ");
							refDTO.setRdiElement(" ");						
						}

						if (refElement.getItemStatus() != null)
							refDTO.setStatusElement(refElement.getItemStatus().getStatusName());
						else{ 
							refDTO.setStatusElement("");
						}
						
						refPDFDTOlist.add(refDTO);
					}
					
				}
							
				// MODIFICACIONES
				List<ReferenceModificationVO> modifications = referenceFacadeBean.getReferenceModificationsByReferenceID(referenceVO);
				if (modifications != null) {
					for (ReferenceModificationVO obj : modifications) {
						ReferencePDFDTOModification referencePDFDTOModification = new ReferencePDFDTOModification();
						if(obj.getUserModification() != null){							
							referencePDFDTOModification.setDateModified(""+dateToString(obj.getModificationDate(),DATE_FORMAT));
						}
						if(obj.getUserModification() != null){							
							referencePDFDTOModification.setUserModified(""+obj.getUserModification().getName());
						}
						if(obj.getReferenceModType() != null){
							referencePDFDTOModification.setModificationType(""+obj.getReferenceModType().getRefModTypeName());
						}
						
						refPDFDTOModificationList.add(referencePDFDTOModification);						
					}
				}

				// INCONSISTENCIAS
				List<RefInconsistencyVO> inconsistencies = referenceFacadeBean.getReferenceInconsistencyByReferenceID(idReference);
				if (inconsistencies != null) {
					for (RefInconsistencyVO inconsisten : inconsistencies) {
						ReferencePDFDTOInconsistency referencePDFDTOInconsistency = new ReferencePDFDTOInconsistency();
						referencePDFDTOInconsistency.setModificationType(""+validatBlankObject(inconsisten.getId()));
						referencePDFDTOInconsistency.setObservations(""+validatBlankObject(inconsisten.getRefIncComments()));
						if(inconsisten.getInconsistencyCreationUser() != null){
							referencePDFDTOInconsistency.setRegistrationUser(""+inconsisten.getInconsistencyCreationUser().getName());
						}
						referencePDFDTOInconsistency.setRegistrationDate(""+dateToString(inconsisten.getRefIncDate(),DATE_FORMAT));
						referencePDFDTOInconsistency.setResponseInconsistency(""+validatBlankObject(inconsisten.getAnswer()));
						if(inconsisten.getAnswerInsconsistencyUser() != null){							
							referencePDFDTOInconsistency.setResponseUser(""+inconsisten.getAnswerInsconsistencyUser().getName());
						}else{
							referencePDFDTOInconsistency.setResponseUser("");
						}
						referencePDFDTOInconsistency.setResponseDate(""+dateToString(inconsisten.getAnswerDate(),DATE_FORMAT));
						referencePDFDTOInconsistency.setInconsistencyStatus(""+inconsisten.getRefIncStatus().getRefIncStatusName());	
						
						refPDFDTOInconsistencyList.add(referencePDFDTOInconsistency);
					}
				}
			}
			
			ReportReferencePDFDTO repRef = new ReportReferencePDFDTO();
			repRef.setPreLoad(response);
			repRef.setRefPDF(refPDFDTOlist);                         
			repRef.setRefPDFnoS(refPDFDTOnoSerializedlist);
			repRef.setRefPDFDTOModificationList(refPDFDTOModificationList);
			repRef.setRefPDFDTOInconsistencyList(refPDFDTOInconsistencyList);
			List<ReportReferencePDFDTO>	resp = new ArrayList<ReportReferencePDFDTO>();
			resp.add(repRef);
			return (List<T>) resp;
		} catch (Throwable e) {
			throw this.manageException(e);
		}
	}
	
	private String validatBlankObject(Object obj){
		if(obj!=null){
			return obj.toString();
		}else{
			return "";
		}
	}

	@Override
	public List<String> getFieldList() {
		return fieldList;
	}

	@Override
	public void setFieldList(List<String> fieldList) {
		this.fieldList = fieldList;
	}

	private void savePreLoadReferenceToPrintDTO(
			List<PreLoadGenericToPrintDTO> response, String type,
			String typeDesc, String subTitle, String Field) {
		PreLoadGenericToPrintDTO dto = new PreLoadGenericToPrintDTO();
		dto.setTypeTitle(type);
		dto.setTitle(typeDesc);
		dto.setSubTitle(subTitle);
		if(Field == null || Field.equals("null"))
			Field=" ";
		dto.setDescription(Field);
		response.add(dto);
	}
	
	private String getWareHouseName(Warehouse warehouse) throws BusinessException{
		WarehouseVO warehouseVO = null;
		String warehouseLocation = "";

		if(warehouse != null){
			warehouseVO = UtilsBusiness.copyObject(WarehouseVO.class, warehouse);
			warehouseBusinessBean.genWareHouseName(warehouseVO);
			warehouseLocation=warehouseVO.getWarehouseName();
		}
		
		return warehouseLocation; 
	}
	
	private String dateToString(Date date, String dateFormat){
		if(date==null)
			return "";
		return UtilsBusiness.dateToString(date,dateFormat).replaceAll(" 00:00:00", "");
	}
	
}