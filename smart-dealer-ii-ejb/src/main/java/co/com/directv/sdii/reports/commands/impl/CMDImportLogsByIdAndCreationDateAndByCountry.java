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
import co.com.directv.sdii.model.pojo.collection.ImportLogResponse;
import co.com.directv.sdii.model.vo.ImportLogVO;
import co.com.directv.sdii.reports.commands.BaseCommand;
import co.com.directv.sdii.reports.commands.CMDImportLogsByIdAndCreationDateAndByCountryLocal;
import co.com.directv.sdii.reports.commands.ICommand;
import co.com.directv.sdii.reports.dto.ImportLogDTO;

/**
 * @author jvelezmu
 *
 */
@Stateless(name="CMDImportLogsByIdAndCreationDateAndByCountry", mappedName="ejb/CMDImportLogsByIdAndCreationDateAndByCountry")
public class CMDImportLogsByIdAndCreationDateAndByCountry extends BaseCommand  implements ICommand,CMDImportLogsByIdAndCreationDateAndByCountryLocal {

	private List<String> fieldList = new ArrayList<String>();
	
	@EJB
	private ImportLogFacadeBeanLocal importLogFacadeBeanLocal;
	
	public CMDImportLogsByIdAndCreationDateAndByCountry(){
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.excelgen.ICommand#execute(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> execute(String args) throws BusinessException {
		try{
			ImportLogResponse r;
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			HashMap<String,String> map = getParams(args);

			Long logisticOpId = null;
			String strLogisticOpId = map.get("logisticOpId"); 
			if (strLogisticOpId != null && !strLogisticOpId.isEmpty())
				logisticOpId = Long.parseLong(strLogisticOpId);

			Long importLogId = null;
			String strImportLogId = map.get("importLogId"); 
			if (strImportLogId != null && !strImportLogId.isEmpty())
				importLogId = Long.parseLong(strImportLogId);
			
			Date creationDate = null;

			try {
				creationDate = map.get("creationDate") == null || map.get("creationDate").isEmpty() ? null : df.parse(map.get("creationDate"));
			} catch (Exception e) {
				throw new BusinessException("== Error convirtiendo la Fecha ==", e);
			}

			r = importLogFacadeBeanLocal.getImportLogsByIdCreationDateAndLogisticOp(importLogId, creationDate, logisticOpId, null);
			
			List<ImportLogDTO> response = new ArrayList<ImportLogDTO>();
			if( r.getImportLogVO() != null ){
				for( ImportLogVO vo : r.getImportLogVO() ){
					ImportLogDTO dto = new ImportLogDTO();
					dto.setId( vo.getId() );
					dto.setCreationDate( UtilsBusiness.formatDate(vo.getCreationDate(),UtilsBusiness.DATE_FORMAT) );
					dto.setCreationUserName( ( vo.getUser() != null && vo.getUser().getName() != null ) ? vo.getUser().getName() : "" );
					dto.setImportLogStatusName( vo.getImportLogStatus().getStatusName() );
					response.add(dto);
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
