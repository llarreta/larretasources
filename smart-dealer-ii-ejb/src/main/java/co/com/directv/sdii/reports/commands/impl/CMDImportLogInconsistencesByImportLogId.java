package co.com.directv.sdii.reports.commands.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.security.SecurityFacadeBeanLocal;
import co.com.directv.sdii.facade.stock.ImportLogFacadeBeanLocal;
import co.com.directv.sdii.facade.stock.ImportLogItemFacadeBeanLocal;
import co.com.directv.sdii.model.pojo.collection.ImportLogInconsistencyResponse;
import co.com.directv.sdii.model.vo.ImportLogInconsistencyVO;
import co.com.directv.sdii.model.vo.ImportLogItemVO;
import co.com.directv.sdii.model.vo.UserVO;
import co.com.directv.sdii.reports.commands.BaseCommand;
import co.com.directv.sdii.reports.commands.CMDImportLogInconsistencesByImportLogIdLocal;
import co.com.directv.sdii.reports.commands.ICommand;
import co.com.directv.sdii.reports.dto.ImportLogInconsistencyDTO;


@Stateless(name="CMDImportLogInconsistencesByImportLogId", mappedName="ejb/CMDImportLogInconsistencesByImportLogId")
public class CMDImportLogInconsistencesByImportLogId extends BaseCommand  implements ICommand,CMDImportLogInconsistencesByImportLogIdLocal {
	
private List<String> fieldList = new ArrayList<String>();
	
	@EJB
	private ImportLogFacadeBeanLocal importLogFacadeBeanLocal;
	
	@EJB
	private ImportLogItemFacadeBeanLocal importLogItemFacadeBeanLocal;
	
	@EJB
	private SecurityFacadeBeanLocal userFacadeBeanLocal;
	
	public CMDImportLogInconsistencesByImportLogId(){
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.excelgen.ICommand#execute(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	//Modificado para Requerimiento: CC057
	public <T> List<T> execute(String args) throws BusinessException {
		try{
			ImportLogInconsistencyResponse r;
			HashMap<String,String> map = getParams(args);
			
			Long importLogId= null;
			String strImpotLogId = map.get("importLogId");
			
			if (strImpotLogId != null && !strImpotLogId.isEmpty())
				importLogId = Long.parseLong(strImpotLogId);
			
			
			
			r = importLogFacadeBeanLocal.getImportLogInconsistenciesByImportLogId(importLogId,  null);
			List<ImportLogInconsistencyDTO> response = new ArrayList<ImportLogInconsistencyDTO>();
			if( r.getImportLogInconsistencyVO() != null ){
				for( ImportLogInconsistencyVO vo : r.getImportLogInconsistencyVO() ){
					ImportLogInconsistencyDTO importLogInconsistencyDTO = new ImportLogInconsistencyDTO();
					ImportLogItemVO ili = importLogItemFacadeBeanLocal.getImportLogItemByID( vo.getImportLogItem());
					UserVO u = userFacadeBeanLocal.getUserById(vo.getUser());
					importLogInconsistencyDTO.setElement( ili != null && ili.getElement() != null && ili.getElement().getElementType() != null && ili.getElement().getElementType().getTypeElementName() != null ? ili.getElement().getElementType().getTypeElementName() : "");
					importLogInconsistencyDTO.setInconsistencyType( vo.getInconsistencyType() != null && vo.getInconsistencyType().getIncTypeName() != null ? vo.getInconsistencyType().getIncTypeName() : "" );
					importLogInconsistencyDTO.setObsInconsistency(vo.getComments() != null ? vo.getComments() : "" );
					importLogInconsistencyDTO.setUserCreate(u != null  && u.getName() != null ? u.getName() : "" );
					importLogInconsistencyDTO.setDateIn(vo.getInconsistencyDate());
					importLogInconsistencyDTO.setStatus(vo.getInconsistencyStatus() != null && vo.getInconsistencyStatus().getIncStatusName() != null ? vo.getInconsistencyStatus().getIncStatusName() : ""  );
					importLogInconsistencyDTO.setInconsistencyResponse(vo.getAnswer() != null ? vo.getAnswer() : "");
					importLogInconsistencyDTO.setElementTypeCode(ili != null && ili.getElement() != null && ili.getElement().getElementType() != null && ili.getElement().getElementType().getTypeElementCode() != null ? ili.getElement().getElementType().getTypeElementCode() : "");
					response.add(importLogInconsistencyDTO);
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
