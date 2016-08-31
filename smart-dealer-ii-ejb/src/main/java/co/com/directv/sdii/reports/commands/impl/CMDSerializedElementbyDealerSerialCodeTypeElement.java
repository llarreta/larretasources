/**
 * 
 */
package co.com.directv.sdii.reports.commands.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.common.enumerations.ApplicationTextEnum;
import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.stock.ElementFacadeBeanLocal;
import co.com.directv.sdii.model.dto.ElementSerializedLinkUnLinkFilterVO;
import co.com.directv.sdii.model.dto.ElementSerializedLinkUnLinkVO;
import co.com.directv.sdii.reports.commands.BaseCommand;
import co.com.directv.sdii.reports.commands.CMDSerializedElementbyDealerSerialCodeTypeElementLocal;
import co.com.directv.sdii.reports.commands.ICommand;

/**
 * Clase encargada de la generacion del reporte de elementos serializados 
 * @author waguilera
 *
 */
@Stateless(name="CMDSerializedElementbyDealerSerialCodeTypeElement", mappedName="ejb/CMDSerializedElementbyDealerSerialCodeTypeElement")
public class CMDSerializedElementbyDealerSerialCodeTypeElement extends BaseCommand  implements ICommand,CMDSerializedElementbyDealerSerialCodeTypeElementLocal {

	private List<String> fieldList = new ArrayList<String>();
	
	@EJB
	private ElementFacadeBeanLocal elementFacadeBean;
	
	public CMDSerializedElementbyDealerSerialCodeTypeElement(){
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.excelgen.ICommand#execute(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> execute(String args) throws BusinessException {
		try{
			ElementSerializedLinkUnLinkVO data;
			HashMap<String,String> map = getParams(args);
			
			ElementSerializedLinkUnLinkFilterVO elementSerializedLinkUnLinkFilterVO = new ElementSerializedLinkUnLinkFilterVO();
			Long idCompany = null;
			Long idBranch = null;
			Long idWarehouse = null;
			String serialCodeElement = null;
			String codeTypeElement = null;
			Long idCountry = null;
			
			String paramNameCompany = CodesBusinessEntityEnum.CMD_REPORT_PARAM_COMPANY_SERIALIZED_ELEMENT_BY_DEALER_SERIALCODE_TYPEELEMENT.getCodeEntity();
			String paramNameBranch = CodesBusinessEntityEnum.CMD_REPORT_PARAM_BRANCH_SERIALIZED_ELEMENT_BY_DEALER_SERIALCODE_TYPEELEMENT.getCodeEntity();
			String paramNameWarehouse = CodesBusinessEntityEnum.CMD_REPORT_PARAM_WAREHOUSE_SERIALIZED_ELEMENT_BY_DEALER_SERIALCODE_TYPEELEMENT.getCodeEntity();
			String paramNameSerialElement = CodesBusinessEntityEnum.CMD_REPORT_PARAM_SERIAL_SERIALIZED_ELEMENT_BY_DEALER_SERIALCODE_TYPEELEMENT.getCodeEntity();
			String paramNameCodeTypeElement = CodesBusinessEntityEnum.CMD_REPORT_PARAM_CODE_TYPE_SERIALIZED_ELEMENT_BY_DEALER_SERIALCODE_TYPEELEMENT.getCodeEntity();
			String paramNameCountry = CodesBusinessEntityEnum.CMD_REPORT_PARAM_COUNTRY_SERIALIZED_ELEMENT_BY_DEALER_SERIALCODE_TYPEELEMENT.getCodeEntity();
			
			
			idCompany = (map.get(paramNameCompany) == null || map.get(paramNameCompany).isEmpty() ? null : map.get(paramNameCompany))==null?null:new Long( map.get(paramNameCompany));
			idBranch = (map.get(paramNameBranch) == null || map.get(paramNameBranch).isEmpty() ? null : map.get(paramNameBranch))==null?null:new Long( map.get(paramNameBranch));
			idWarehouse = (map.get(paramNameWarehouse) == null || map.get(paramNameWarehouse).isEmpty() ? null : map.get(paramNameWarehouse))==null?null:new Long( map.get(paramNameWarehouse));
			serialCodeElement = map.get(paramNameSerialElement) == null || map.get(paramNameSerialElement).isEmpty() ? null : map.get(paramNameSerialElement);
			codeTypeElement = map.get(paramNameCodeTypeElement) == null || map.get(paramNameCodeTypeElement).isEmpty() ? null : map.get(paramNameCodeTypeElement);
			idCountry = (map.get(paramNameCountry) == null || map.get(paramNameCountry).isEmpty() ? null : map.get(paramNameCountry))==null?null:new Long( map.get(paramNameCountry));
			
			elementSerializedLinkUnLinkFilterVO = new ElementSerializedLinkUnLinkFilterVO(idCompany, idBranch, idWarehouse, serialCodeElement, codeTypeElement,idCountry);
			
			data = elementFacadeBean.getElementSerializedToLinkUnLink(elementSerializedLinkUnLinkFilterVO);
			List<ElementSerializedLinkUnLinkVO> lista = new ArrayList<ElementSerializedLinkUnLinkVO>();
			if(data!=null){
				lista.add(data);
			}
			return (List<T>) lista;
		}catch(Throwable e){
			throw this.manageException(e);
		}
	}
	
	
	@Override
	public List<String> getFieldList() {
		fieldList.add(ApplicationTextEnum.COMPANY_CODE.getApplicationTextValue());
		fieldList.add(ApplicationTextEnum.COMPANY_NAME.getApplicationTextValue());
		fieldList.add(ApplicationTextEnum.BRANCH_CODE.getApplicationTextValue());
		fieldList.add(ApplicationTextEnum.BRANCH_NAME.getApplicationTextValue());
		return fieldList;
	}
	
	@Override
	public void setFieldList(List<String> fieldList) {
		this.fieldList=fieldList;
	}

}
