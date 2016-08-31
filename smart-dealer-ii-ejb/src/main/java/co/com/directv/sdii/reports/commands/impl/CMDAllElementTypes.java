/**
 * 
 */
package co.com.directv.sdii.reports.commands.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.stock.ElementTypeFacadeBeanLocal;
import co.com.directv.sdii.model.pojo.collection.ElementTypeResponse;
import co.com.directv.sdii.model.vo.ElementTypeVO;
import co.com.directv.sdii.reports.commands.BaseCommand;
import co.com.directv.sdii.reports.commands.CMDAllElementTypesLocal;
import co.com.directv.sdii.reports.commands.ICommand;
import co.com.directv.sdii.reports.dto.ElementTypeDTO;

/**
 * @author jvelezmu
 *
 */
@Stateless(name="CMDAllElementTypes", mappedName="ejb/CMDAllElementTypes")
public class CMDAllElementTypes  extends BaseCommand implements ICommand,CMDAllElementTypesLocal {

	private List<String> fieldList = new ArrayList<String>();
	
	@EJB
	private ElementTypeFacadeBeanLocal elementTypeFacadeBeanLocal;
	
	public CMDAllElementTypes(){
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.excelgen.ICommand#execute(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> execute(String args) throws BusinessException {
		try{
			ElementTypeResponse r;
			HashMap<String,String> map = getParams(args);
			String elementTypeCode = map.get("elementTypeCode") == null || map.get("elementTypeCode").isEmpty() ? null : map.get("elementTypeCode");
			Long idElementModel = map.get("idElementModel") == null || map.get("idElementModel").isEmpty() ? -1 : Long.valueOf(map.get("idElementModel"));
			r = elementTypeFacadeBeanLocal.getElementTypesByElementModelIdAndAllStatusPage(idElementModel,elementTypeCode,null);
			List<ElementTypeVO> listTmp = r.getElementTypeVO();
			List<ElementTypeDTO> response = new ArrayList<ElementTypeDTO>();
			if(listTmp!=null){
				for(ElementTypeVO tmp : listTmp){
					ElementTypeDTO tmpDTO = new ElementTypeDTO();
					tmpDTO.setElementBrand(tmp.getElementBrand().getBrandName());
					tmpDTO.setElementClass(tmp.getElementClassName());
					tmpDTO.setElementModel(tmp.getElementModelName());
					tmpDTO.setIsActive(tmp.getIsActive());
					tmpDTO.setIsSerialized(tmp.getIsSerialized());
					tmpDTO.setMeasureUnit(tmp.getMeasureUnitName());
					tmpDTO.setTypeElementCode(tmp.getTypeElementCode());
					tmpDTO.setTypeElementDescription(tmp.getTypeElementDescription());
					tmpDTO.setTypeElementName(tmp.getTypeElementName());
					tmpDTO.setTypeRegEx(tmp.getTypeRegEx());
					response.add(tmpDTO);
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
