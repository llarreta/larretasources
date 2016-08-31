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

import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.stock.ImportLogFacadeBeanLocal;
import co.com.directv.sdii.model.dto.ModifyImportLogDTO;
import co.com.directv.sdii.model.pojo.collection.ImportLogResponse;
import co.com.directv.sdii.model.vo.ImportLogVO;
import co.com.directv.sdii.reports.commands.BaseCommand;
import co.com.directv.sdii.reports.commands.CMDImportLogInconsistencesByCriteriaLocal;
import co.com.directv.sdii.reports.commands.ICommand;
import co.com.directv.sdii.reports.dto.ImportLogDTO;

/**
 * @author jvelezmu
 *
 */
@Stateless(name="CMDImportLogInconsistencesByCriteria", mappedName="ejb/CMDImportLogInconsistencesByCriteria")
public class CMDImportLogInconsistencesByCriteria extends BaseCommand  implements ICommand,CMDImportLogInconsistencesByCriteriaLocal {

	private List<String> fieldList = new ArrayList<String>();
	
	@EJB
	private ImportLogFacadeBeanLocal importLogFacadeBeanLocal;
	
	public CMDImportLogInconsistencesByCriteria(){
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
			
			Date creationDateINI = null;
			Date creationDateEND = null;

			try {
				creationDateINI = map.get("creationDateINI") == null || map.get("creationDateINI").isEmpty() ? null : df.parse(map.get("creationDateINI"));
				creationDateINI = map.get("creationDateEND") == null || map.get("creationDateEND").isEmpty() ? null : df.parse(map.get("creationDateEND"));
			} catch (Exception e) {
				throw new BusinessException("== Error convirtiendo la Fecha ==", e);
			}
			
			Long dealerId= null;
			String strdealerId = map.get("dealerId"); 
			if (strdealerId != null && !strdealerId.isEmpty())
				dealerId = Long.parseLong(strdealerId);
			
			String strPurchaseOrder = map.get("purchaseOrder");
			String strImportDoc = map.get("importDoc");
			
			ModifyImportLogDTO dto = new ModifyImportLogDTO();
			dto.setCreationDateINI(creationDateINI);
			dto.setCreationDateINI(creationDateEND);
			dto.setDealerId(dealerId);
			dto.setPurchaseOrder(strPurchaseOrder);
			dto.setImportDoc(strImportDoc);
			
			r = importLogFacadeBeanLocal.getImportLogInconsistencesByCriteria(dto, null);
			List<ImportLogDTO> response = new ArrayList<ImportLogDTO>();
			if( r.getImportLogVO() != null ){
				for( ImportLogVO vo : r.getImportLogVO() ){
					ImportLogDTO importLogDTO = new ImportLogDTO();
					importLogDTO.setId( vo.getId() );
					importLogDTO.setCreationDate( UtilsBusiness.formatDate( vo.getCreationDate(),UtilsBusiness.DATE_FORMAT) );
					importLogDTO.setDeliveryDate( UtilsBusiness.formatDate( vo.getDeliveryDate(),UtilsBusiness.DATE_FORMAT) );
					importLogDTO.setImportLogStatusName( vo.getImportLogStatus().getStatusName() );
					importLogDTO.setShippingDate( UtilsBusiness.formatDate( vo.getShippingDate(),UtilsBusiness.DATE_FORMAT) );
					response.add(importLogDTO);
				}
			}
			return (List<T>) response;
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
