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
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.stock.ImpLogModificationFacadeBeanLocal;
import co.com.directv.sdii.facade.stock.ImportLogFacadeBeanLocal;
import co.com.directv.sdii.facade.stock.ImportLogItemFacadeBeanLocal;
import co.com.directv.sdii.model.dto.ModifyImportLogDTO;
import co.com.directv.sdii.model.pojo.ImpLogConfirmation;
import co.com.directv.sdii.model.pojo.ImportLogInconsistency;
import co.com.directv.sdii.model.pojo.collection.ImportLogItemResponse;
import co.com.directv.sdii.model.pojo.collection.ImportLogResponse;
import co.com.directv.sdii.model.vo.ImpLogConfirmationVO;
import co.com.directv.sdii.model.vo.ImpLogModificationVO;
import co.com.directv.sdii.model.vo.ImportLogItemVO;
import co.com.directv.sdii.model.vo.ImportLogVO;
import co.com.directv.sdii.persistence.dao.security.UserDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.ImpLogConfirmationDAOLocal;
import co.com.directv.sdii.reports.commands.BaseCommand;
import co.com.directv.sdii.reports.commands.CMDPreLoadImportLogToPrintByIDLocal;
import co.com.directv.sdii.reports.commands.ICommand;
import co.com.directv.sdii.reports.dto.PreLoadGenericToPrintDTO;

/**
 * @author cduarte
 * 
 */
@Stateless(name = "CMDPreLoadImportLogToPrintByID", mappedName = "ejb/CMDPreLoadImportLogToPrintByID")
public class CMDPreLoadImportLogToPrintByID extends BaseCommand implements
		ICommand,CMDPreLoadImportLogToPrintByIDLocal {

	private List<String> fieldList = new ArrayList<String>();
	private static String  DATOS_BASICOS   = ApplicationTextEnum.BASIC_FACTS.getApplicationTextValue();
	private static String  ASERIALIZADOS   = ApplicationTextEnum.ELEMENTS_NOT_SERIALIZED.getApplicationTextValue();
	private static String  CONFIRMACION_ASERIALIZADOS   = ApplicationTextEnum.CONFIRMATIONS.getApplicationTextValue();
	private static String  SERIALIZADOS    = ApplicationTextEnum.ELEMENTS_SERIALIZED.getApplicationTextValue();
	private static String  MODIFICACIONES  = ApplicationTextEnum.MODIFICATIONS.getApplicationTextValue();
	private static String  INCONSISTENCIAS = ApplicationTextEnum.INCONSISTENCIES.getApplicationTextValue();
	private static int  INIT_ARRAY = 0;
	private static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	

	@EJB
	private ImportLogFacadeBeanLocal importLogFacadeBean;
	
	@EJB
	private ImpLogModificationFacadeBeanLocal impLogModificationFacadeBean;
	
	@EJB
	private ImportLogItemFacadeBeanLocal importLogItemFacadeBean;
	
	@EJB
	private UserDAOLocal daoUser;
	
	@EJB
    private ImpLogConfirmationDAOLocal daoImpLogConfirmation;
    
	public CMDPreLoadImportLogToPrintByID() {
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

			List<PreLoadGenericToPrintDTO> response = new ArrayList<PreLoadGenericToPrintDTO>();
			ImportLogResponse importLogResponse;
			ImportLogVO importLogVO;
			ImportLogItemResponse importLogItemResponse;
			HashMap<String, String> map = getParams(args);
			Long importLogId = null;
			String strImportLogId = map.get(CodesBusinessEntityEnum.FILE_PARAM_ID_IMPORT_LOG.getCodeEntity());
			String strTemp="";
			String strTempElementName="";
			
			String user = map.get("userId");
			Long userId = null;
			if(user != null){
				userId = Long.parseLong(user);
			}
			
			if (strImportLogId != null && !strImportLogId.isEmpty()){
				importLogId = Long.parseLong(strImportLogId);
			}
			
			ModifyImportLogDTO modifyImportLogDTO= new ModifyImportLogDTO();
			modifyImportLogDTO.setImportLogId(importLogId);
			importLogResponse = importLogFacadeBean.getImportLogByCriteria(modifyImportLogDTO, userId, null);
			if(importLogResponse.getImportLogVO() != null){
			
				//Se obtiene la informacion para la generacion del reporte por tipo de datos
				importLogVO=importLogResponse.getImportLogVO().get(INIT_ARRAY);

				//DATOS_BASICOS
				savePreLoadReferenceToPrintDTO(response, "1",DATOS_BASICOS,ApplicationTextEnum.CONSECUTIVE_IMPORT.getApplicationTextValue()+":",""+importLogId);
				savePreLoadReferenceToPrintDTO(response, "1",DATOS_BASICOS,ApplicationTextEnum.PURCHASE_ORDER.getApplicationTextValue()+":",""+importLogVO.getPurchaseOrder());
				
				strTemp="";
				if(importLogVO.getSupplier() != null)
					strTemp=""+importLogVO.getSupplier().getSupplierCode() + " - " +importLogVO.getSupplier().getSupplierName();
				savePreLoadReferenceToPrintDTO(response, "1",DATOS_BASICOS,ApplicationTextEnum.SUPPLIER.getApplicationTextValue()+":",strTemp);
				
				savePreLoadReferenceToPrintDTO(response, "1",DATOS_BASICOS,ApplicationTextEnum.ESTIMATED_DELIVERY_DATE.getApplicationTextValue()+":",""+UtilsBusiness.dateToString(importLogVO.getDeliveryDate()));
				
				savePreLoadReferenceToPrintDTO(response, "1",DATOS_BASICOS,ApplicationTextEnum.SHIPPING_DATE.getApplicationTextValue()+":",""+UtilsBusiness.dateToString(importLogVO.getShippingDate()));
				
				strTemp="";
				if(importLogVO.getImportLogStatus() != null)
					strTemp=""+importLogVO.getImportLogStatus().getStatusName();
				savePreLoadReferenceToPrintDTO(response, "1",DATOS_BASICOS,ApplicationTextEnum.REGISTRY_IMPORTS_STATUS.getApplicationTextValue()+":",strTemp);
				
				savePreLoadReferenceToPrintDTO(response, "1",DATOS_BASICOS,ApplicationTextEnum.CREATION_DATE.getApplicationTextValue()+":",""+dateToString(importLogVO.getCreationDate(),DATE_FORMAT));
				
				strTemp="";
				if(importLogVO.getUser() != null)
					strTemp=""+importLogVO.getUser().getName();
				savePreLoadReferenceToPrintDTO(response, "1",DATOS_BASICOS,ApplicationTextEnum.USER_CREATION.getApplicationTextValue()+":",strTemp);
				savePreLoadReferenceToPrintDTO(response, "1",DATOS_BASICOS,ApplicationTextEnum.LEGAL_DOCUMENT_TRANSPORT.getApplicationTextValue()+":",""+importLogVO.getImportDoc());
				
				//ASERIALIZADOS
				importLogItemResponse = importLogItemFacadeBean.getImportLogItemsByImportLogId(importLogId, null);
				
				for (ImportLogItemVO importLogItemVO : importLogItemResponse.getImportLogItemsVO()) {
					
					if(importLogItemVO.getNotSerializedVO() != null){
					
						strTemp="";
						strTempElementName="";
						if(importLogItemVO.getElement() != null)
							if(importLogItemVO.getElement().getElementType() != null){
								strTemp=""+importLogItemVO.getElement().getElementType().getTypeElementCode();
								strTempElementName=""+importLogItemVO.getElement().getElementType().getTypeElementName();
							}
						savePreLoadReferenceToPrintDTO(response, "2",ASERIALIZADOS,"-"+ApplicationTextEnum.ELEMENT_TYPE_CODE.getApplicationTextValue()+":",strTemp);
						savePreLoadReferenceToPrintDTO(response, "2",ASERIALIZADOS,"-"+ApplicationTextEnum.ELEMENT_TYPE.getApplicationTextValue()+":",strTempElementName);
						
						strTemp="";
						if(importLogItemVO.getElement() != null)
							if(importLogItemVO.getElement().getElementType() != null)
								if(importLogItemVO.getElement().getElementType().getMeasureUnit() != null)
									strTemp=""+importLogItemVO.getElement().getElementType().getMeasureUnit().getUnitName();
						savePreLoadReferenceToPrintDTO(response, "2",ASERIALIZADOS,ApplicationTextEnum.UNIT_MEASURE_ITEM.getApplicationTextValue()+":",strTemp);
						
						savePreLoadReferenceToPrintDTO(response, "2",ASERIALIZADOS,ApplicationTextEnum.EXPECTED_QUANTITY.getApplicationTextValue()+":",""+importLogItemVO.getAmountExpected());
						
						strTemp="";
						if(importLogItemVO.getItemStatus() != null)
							strTemp=""+importLogItemVO.getItemStatus().getStatusName();
						savePreLoadReferenceToPrintDTO(response, "2",ASERIALIZADOS,ApplicationTextEnum.STATUS_ELEMENT_NOT_SERIALIZED.getApplicationTextValue()+":",strTemp);
						
						List<ImpLogConfirmation> impLogConfPojo = daoImpLogConfirmation.getImpLogConfirmationsByImpLogItemId(importLogItemVO.getId());
			        	importLogItemVO.setImpLogConfirmations( UtilsBusiness.convertList(impLogConfPojo, ImpLogConfirmationVO.class)  );		
			        	
			        	List<ImpLogConfirmationVO> impLogConfirmationsVO = importLogItemVO.getImpLogConfirmations();
						
						if(impLogConfirmationsVO != null && !impLogConfirmationsVO.isEmpty()){
							int i = 1;
							savePreLoadReferenceToPrintDTO(response, "2",ASERIALIZADOS,"      "+ApplicationTextEnum.CONFIRMATIONS.getApplicationTextValue()+" ","");
							for (ImpLogConfirmationVO  impLogConfirmationVO : impLogConfirmationsVO) {
								savePreLoadReferenceToPrintDTO(response, "2",ASERIALIZADOS,ApplicationTextEnum.PARTIAL_CONFIRMATION_DATE.getApplicationTextValue()+" "+i+":",""+dateToString(impLogConfirmationVO.getConfirmationDate(),DATE_FORMAT));
								savePreLoadReferenceToPrintDTO(response, "2",ASERIALIZADOS,ApplicationTextEnum.PARTIAL_QUANTITY.getApplicationTextValue()+" "+i+":",""+impLogConfirmationVO.getConfirmedQuantity());
								i++;
							}
						}
						savePreLoadReferenceToPrintDTO(response,"2", ASERIALIZADOS,
								"________________________________________________________________________________________","________________________________________________");
					} 

				}
				
				//SERIALIZADOS
				//importLogItemResponse = importLogItemFacadeBean.getImportLogItemsByImportLogId(importLogId, null);
				for (ImportLogItemVO importLogItemVO : importLogItemResponse.getImportLogItemsVO()) {
					
					if(importLogItemVO.getSerializedVO() != null){
						
						//SERIALIZADOS
						strTemp="";
						strTempElementName="";
						if(importLogItemVO.getElement() != null)
							if(importLogItemVO.getElement().getElementType() != null){
								strTemp=""+importLogItemVO.getElement().getElementType().getTypeElementCode();
								strTempElementName=""+importLogItemVO.getElement().getElementType().getTypeElementName();
							}
						savePreLoadReferenceToPrintDTO(response, "4",SERIALIZADOS,ApplicationTextEnum.ELEMENT_TYPE_CODE.getApplicationTextValue()+":",strTemp);
						savePreLoadReferenceToPrintDTO(response, "4",SERIALIZADOS,ApplicationTextEnum.ELEMENT_TYPE.getApplicationTextValue()+":",strTempElementName);
						savePreLoadReferenceToPrintDTO(response, "4",SERIALIZADOS,ApplicationTextEnum.EXPECTED_QUANTITY.getApplicationTextValue()+":","1");
						
						strTemp="";
						if(importLogItemVO.getItemStatus() != null)
							strTemp=""+importLogItemVO.getItemStatus().getStatusName();
						savePreLoadReferenceToPrintDTO(response, "4",SERIALIZADOS,ApplicationTextEnum.ELEMENT_STATUS.getApplicationTextValue()+":",strTemp);
				        //Consecutivo del elemento
			            
						strTemp="";
						if(importLogItemVO.getSerializedVO() != null)
							strTemp=""+importLogItemVO.getSerializedVO().getSerialCode();
						savePreLoadReferenceToPrintDTO(response, "4",SERIALIZADOS,ApplicationTextEnum.ELEMENT_SERIAL.getApplicationTextValue()+":",strTemp);
			            
						strTemp="";
						if(importLogItemVO.getSerializedVO() != null)
							strTemp=""+importLogItemVO.getSerializedVO().getIrd();
						savePreLoadReferenceToPrintDTO(response, "4",SERIALIZADOS,ApplicationTextEnum.ELEMENT_RID.getApplicationTextValue()+":",strTemp);
			            
						strTemp="";
						if(importLogItemVO.getSerializedVO() != null)
							if(importLogItemVO.getSerializedVO().getSerialized() != null)
								strTemp=""+importLogItemVO.getSerializedVO().getSerialized().getSerialCode();
						savePreLoadReferenceToPrintDTO(response, "4",SERIALIZADOS,ApplicationTextEnum.SERIAL_LINKED_ELEMENT.getApplicationTextValue()+":",strTemp);
					    //Estado del elemento individual
						savePreLoadReferenceToPrintDTO(response,"4", SERIALIZADOS,
								"________________________________________________________________________________________","________________________________________________");
					}
				
				}		
				
				
				
				//MODIFICACIONES
				List<ImpLogModificationVO> impLogModificationsVO = impLogModificationFacadeBean.getImpLogModificationByImportLogID(importLogId);			
				if (impLogModificationsVO != null) {
					for (ImpLogModificationVO impLogModificationVO : impLogModificationsVO) {
						savePreLoadReferenceToPrintDTO(response, "5",MODIFICACIONES,ApplicationTextEnum.DATE_MODIFIED.getApplicationTextValue()+":",""+dateToString(impLogModificationVO.getModificationDate(),DATE_FORMAT));
						if(impLogModificationVO.getUser() != null) {
							strTemp=impLogModificationVO.getUser().getName();
							savePreLoadReferenceToPrintDTO(response, "5",MODIFICACIONES,ApplicationTextEnum.USER_MODIFIED.getApplicationTextValue()+":",strTemp);
						}
						strTemp="";
						if(impLogModificationVO.getImpLogModificationType() != null)
							strTemp=""+impLogModificationVO.getImpLogModificationType().getModTypeName();
						savePreLoadReferenceToPrintDTO(response, "5",MODIFICACIONES,ApplicationTextEnum.MODIFICATION_TYPE.getApplicationTextValue()+":",strTemp);
						savePreLoadReferenceToPrintDTO(response,"5", MODIFICACIONES,
								"________________________________________________________________________________________","________________________________________________");
					}
				}
	
				
				//INCONSISTENCIAS
				List<ImportLogInconsistency> importLogInconsistencies = importLogFacadeBean.getImportLogInconsistencysByImportLog(importLogId);
				if (importLogInconsistencies != null) {
					for (ImportLogInconsistency importLogInconsistency : importLogInconsistencies) {
						
						strTemp="";
						if(importLogInconsistency.getInconsistencyType() != null)
							strTemp=""+importLogInconsistency.getInconsistencyType().getIncTypeName();
						
						savePreLoadReferenceToPrintDTO(response, "6",INCONSISTENCIAS,ApplicationTextEnum.INCONSISTENCY_CODE.getApplicationTextValue()+":",strTemp);
						savePreLoadReferenceToPrintDTO(response, "6",INCONSISTENCIAS,ApplicationTextEnum.OBSERVATIONS_INCONSISTENCY.getApplicationTextValue()+":",""+importLogInconsistency.getComments());
						if(importLogInconsistency.getUser() != null) {
							strTemp=daoUser.getUserById(importLogInconsistency.getUser()).getName();
							savePreLoadReferenceToPrintDTO(response, "6",INCONSISTENCIAS,ApplicationTextEnum.USER_REGISTERS_INCONSISTENCY.getApplicationTextValue()+":",""+strTemp);
						}
						savePreLoadReferenceToPrintDTO(response, "6",INCONSISTENCIAS,ApplicationTextEnum.DATE_REGISTRATION_INCONSISTENCY.getApplicationTextValue()+":",""+dateToString(importLogInconsistency.getInconsistencyDate(),DATE_FORMAT));
						savePreLoadReferenceToPrintDTO(response, "6",INCONSISTENCIAS,ApplicationTextEnum.RESPONSE_INCONSISTENCY.getApplicationTextValue()+":",""+importLogInconsistency.getAnswer());
						if(importLogInconsistency.getUserClose() != null) {
							strTemp=daoUser.getUserById(importLogInconsistency.getUserClose()).getName();
							savePreLoadReferenceToPrintDTO(response, "6",INCONSISTENCIAS, ApplicationTextEnum.USER_RESPONDED_INCONSISTENCY.getApplicationTextValue()+":",""+strTemp);
						}
						savePreLoadReferenceToPrintDTO(response, "6",INCONSISTENCIAS,ApplicationTextEnum.REGISTRATION_DATE_RESPONSE_INCONSISTENCY.getApplicationTextValue()+":",dateToString(importLogInconsistency.getAnswerDate(), DATE_FORMAT));
						strTemp="";
						if(importLogInconsistency.getInconsistencyStatus() != null)
							strTemp=""+importLogInconsistency.getInconsistencyStatus().getIncStatusName();
						savePreLoadReferenceToPrintDTO(response, "6",INCONSISTENCIAS,ApplicationTextEnum.STATUS_INCONSISTENCY.getApplicationTextValue()+":",strTemp);

						savePreLoadReferenceToPrintDTO(response,"6", INCONSISTENCIAS,
								"________________________________________________________________________________________","________________________________________________");
					}
				}
			}
			return (List<T>) response;
		} catch (Throwable e) {
			throw this.manageException(e);
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
	
	private void savePreLoadReferenceToPrintDTO(List<PreLoadGenericToPrintDTO> response, 
			                                    String type,
			                                    String typeDesc,
			                                    String subTitle,
			                                    String Field){
		PreLoadGenericToPrintDTO dto = new PreLoadGenericToPrintDTO();
		dto.setTypeTitle(type);
		dto.setTitle(typeDesc);
		dto.setSubTitle(subTitle);
		if(Field == null || Field.equals("null"))
			Field=" ";
		dto.setDescription(Field);
		response.add(dto);
	}
	
	private String dateToString(Date date,String dateFormat){
		if(date==null)
			return "";
		return UtilsBusiness.dateToString(date,dateFormat).replaceAll(" 00:00:00", "");
	}
	
}
