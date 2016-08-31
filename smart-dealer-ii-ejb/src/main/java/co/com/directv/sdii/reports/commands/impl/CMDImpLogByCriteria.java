/**
 * 
 */
package co.com.directv.sdii.reports.commands.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.stock.ImportLogFacadeBeanLocal;
import co.com.directv.sdii.model.dto.ModifyImportLogDTO;
import co.com.directv.sdii.model.pojo.collection.ImportLogResponse;
import co.com.directv.sdii.reports.commands.BaseCommand;
import co.com.directv.sdii.reports.commands.CMDImpLogByCriteriaLocal;
import co.com.directv.sdii.reports.commands.ICommand;

/**
 * @author jvelezmu
 *
 */
@Stateless(name="CMDImpLogByCriteria", mappedName="ejb/CMDImpLogByCriteria")
public class CMDImpLogByCriteria extends BaseCommand  implements ICommand,CMDImpLogByCriteriaLocal {

	private List<String> fieldList = new ArrayList<String>();
	
	@EJB
	private ImportLogFacadeBeanLocal importLogFacadeBeanLocal;
	
	public CMDImpLogByCriteria(){
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.excelgen.ICommand#execute(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> execute(String args) throws BusinessException {
		try{
			ImportLogResponse r;
			HashMap<String,String> map = getParams(args);
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			
			Long importLogId = null;
			String strImportLogId = map.get("importLogId"); 
			if (strImportLogId != null && !strImportLogId.isEmpty())
				importLogId = Long.parseLong(strImportLogId);

			String importLogStatus = map.get("importLogStatus");
			Date shippingDate = null, creationDate = null, deliveryDate = null, creationDateINI = null, creationDateEND = null;

			try {
				shippingDate = map.get("shippingDate") == null || map.get("shippingDate").isEmpty() ? null : df.parse(map.get("shippingDate"));
				creationDate = map.get("creationDate") == null || map.get("creationDate").isEmpty() ? null : df.parse(map.get("creationDate"));
				deliveryDate = map.get("deliveryDate") == null || map.get("deliveryDate").isEmpty() ? null : df.parse(map.get("deliveryDate"));
				creationDateINI = map.get("creationDateINI") == null || map.get("creationDateINI").isEmpty() ? null : df.parse(map.get("creationDateINI"));
				creationDateEND = map.get("creationDateEND") == null || map.get("creationDateEND").isEmpty() ? null : df.parse(map.get("creationDateEND"));
			} catch (Exception e) {
				throw new BusinessException("== Error convirtiendo la Fecha ==", e);
			}
			
			Long dealerId= null;
			String strdealerId = map.get("dealerId"); 
			if (strdealerId != null && !strdealerId.isEmpty())
				dealerId = Long.parseLong(strdealerId);
			
			
			Long elementTypeId= null;
			String strElementTypeId = map.get("elementTypeId"); 
			if (strElementTypeId != null && !strElementTypeId.isEmpty())
				elementTypeId = Long.parseLong(strElementTypeId);
			
			
			String strSerialNumber = map.get("serialNumber");
			
			String strPurchaseOrder = map.get("purchaseOrder");
			
			String strImportDoc = map.get("importDoc");
			
			String strLegalDoc = map.get("legalDoc");
			
			Long supplierId= null;
			String strSupplierId = map.get("supplierId"); 
			if (strSupplierId != null && !strSupplierId.isEmpty())
				supplierId = Long.parseLong(strSupplierId);
			
			Boolean isSerialized = null;
			String strSerialized = map.get("serialized"); 
			if (strSerialized != null && !strSerialized.isEmpty())
				isSerialized = strSerialized.trim().equals("true");
			
			String user = map.get("userId");
			Long userId = null;
			if(user != null){
				userId = Long.parseLong(user);
			}
			
			
			ModifyImportLogDTO dto = new ModifyImportLogDTO();
			dto.setCreationDate(creationDate);
			dto.setDeliveryDate(deliveryDate);
			dto.setImportLogId(importLogId);
			dto.setImportLogStatusCode(importLogStatus);
			dto.setShippingDate(shippingDate);
			dto.setDealerId(dealerId);
			dto.setElementTypeId(elementTypeId);
			dto.setSerialNumber(strSerialNumber);
			dto.setCreationDateINI(creationDateINI);
			dto.setCreationDateEND(creationDateEND);
			dto.setPurchaseOrder(strPurchaseOrder);
			dto.setImportDoc(strImportDoc);
			dto.setLegalDoc(strLegalDoc);
			dto.setSupplierId(supplierId);
			dto.setSerialized(isSerialized);
			
			
			r = importLogFacadeBeanLocal.getImportLogByCriteria(dto, userId, null);
			return (List<T>) r.getImportLogVO();
		} catch(Throwable e){
			throw this.manageException(e);
		}
	}
	
	@Override
	public List<String> getFieldList() {
		return fieldList;
	}
	
	@Override
	public void setFieldList(List<String> fieldList) {
		this.fieldList=fieldList;
	}

}
