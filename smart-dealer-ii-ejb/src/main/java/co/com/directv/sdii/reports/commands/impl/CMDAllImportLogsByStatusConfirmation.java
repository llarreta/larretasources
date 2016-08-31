/**
 * 
 */
package co.com.directv.sdii.reports.commands.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.stock.ImportLogFacadeBeanLocal;
import co.com.directv.sdii.model.dto.ModifyImportLogDTO;
import co.com.directv.sdii.model.pojo.collection.ImportLogResponse;
import co.com.directv.sdii.model.vo.ImportLogVO;
import co.com.directv.sdii.reports.commands.BaseCommand;
import co.com.directv.sdii.reports.commands.CMDAllImportLogsByStatusConfirmationLocal;
import co.com.directv.sdii.reports.commands.ICommand;
import co.com.directv.sdii.reports.dto.ImportLogDTO;

/**
 * @author jvelezmu
 *
 */
@Stateless(name="CMDAllImportLogsByStatusConfirmation", mappedName="ejb/CMDAllImportLogsByStatusConfirmation")
public class CMDAllImportLogsByStatusConfirmation  extends BaseCommand implements ICommand,CMDAllImportLogsByStatusConfirmationLocal {

	private List<String> fieldList = new ArrayList<String>();
	
	@EJB
	private ImportLogFacadeBeanLocal importLogFacadeBeanLocal;
	
	public CMDAllImportLogsByStatusConfirmation(){
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
			Long dealerId = null;
			Long elementTypeId = null;
			Long impLogId = null;
			Boolean serialized = null;
			
			String strDealerId = map.get("dealerId");
			String strElementTypeId = map.get("elementTypeId");
			String serialCode = map.get("serialCode");
			String StrImpLogId = map.get("impLogId");
			String strSerialized = map.get("serialized");
			
			if (strDealerId != null && !strDealerId.isEmpty())
				dealerId = Long.parseLong(strDealerId);
			
			if (strElementTypeId != null && !strElementTypeId.isEmpty())
				elementTypeId = Long.parseLong(strElementTypeId);

			if (StrImpLogId != null && !StrImpLogId.isEmpty())
				impLogId = Long.parseLong(StrImpLogId);

			if (strSerialized != null && !strSerialized.isEmpty())
				serialized = Boolean.parseBoolean(strSerialized);
			ModifyImportLogDTO modifyImportLogCriteria = new ModifyImportLogDTO();
			modifyImportLogCriteria.setDealerId(dealerId);
			modifyImportLogCriteria.setElementTypeId(elementTypeId);
			modifyImportLogCriteria.setSerialNumber(serialCode);
			modifyImportLogCriteria.setImportLogId(impLogId);
			modifyImportLogCriteria.setSerialized(serialized);
			r = importLogFacadeBeanLocal.getImportLogsToConfirm(modifyImportLogCriteria, null);
			
			List<ImportLogDTO> response = new ArrayList<ImportLogDTO>();
			if( r.getImportLogVO() != null){
				for( ImportLogVO vo : r.getImportLogVO() ){
					ImportLogDTO dto = new ImportLogDTO();
					dto.setId(vo.getId());
					dto.setCreationDate( UtilsBusiness.formatDate( vo.getCreationDate(),UtilsBusiness.DATE_FORMAT) );
					dto.setCreationUserName( ( vo.getUser() != null && vo.getUser().getName() != null ) ? vo.getUser().getName() : "" );
					dto.setDeliveryDate( vo.getDeliveryDate() != null ? vo.getDeliveryDate() : null );
					dto.setImportLogStatusName( vo.getImportLogStatus().getStatusName() );
					dto.setPurchaseOrder( vo.getPurchaseOrder() != null ? vo.getPurchaseOrder() : "" );
					dto.setImportDoc( vo.getImportDoc() != null ? vo.getImportDoc() : "" );
					response.add(dto);
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
		this.fieldList=fieldList;
	}

}
