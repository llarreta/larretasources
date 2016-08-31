package co.com.directv.sdii.reports.commands.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.stock.ReferenceElementItemFacadeBeanLocal;
import co.com.directv.sdii.model.pojo.collection.ReferenceElementItemsResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.reports.commands.BaseCommand;
import co.com.directv.sdii.reports.commands.CMDGetElementNotSerializedFromWarehouseLocal;
import co.com.directv.sdii.reports.commands.ICommand;

/**
 * Comando encargado de realizar la consulta de una remision para generar el excel
 * 
 * Fecha de Creación: 19/12/2011
 * @author cduarte <a href="mailto:cduarte@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
@Stateless(name = "CMDGetElementNotSerializedFromWarehouse", mappedName = "ejb/CMDGetElementNotSerializedFromWarehouse")
public class CMDGetElementNotSerializedFromWarehouse extends BaseCommand implements ICommand,CMDGetElementNotSerializedFromWarehouseLocal {
	
	private List<String> fieldList = new ArrayList<String>();
	
	@EJB
    private ReferenceElementItemFacadeBeanLocal ejbRefElement;
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> execute(String args) throws BusinessException {
		try{
			
			RequestCollectionInfo requestCollInfo = null;
			Long referenceID = null;
			boolean isPrepaid=false; 
			Long elementTypeId = 0L; 
			Long elementModelId=0L;
			
			//referenceId
			HashMap<String, String> map = getParams(args);
			String strTemp = map.get(CodesBusinessEntityEnum.FILE_PARAM_REFERENCE_ID.getCodeEntity());
			if (strTemp != null && !strTemp.isEmpty()){
				referenceID = Long.parseLong(strTemp) ;
			}
			
			//isPrepaid
			strTemp = map.get(CodesBusinessEntityEnum.FILE_PARAM_IS_PREPAID.getCodeEntity());
			if (strTemp != null && !strTemp.isEmpty()){
				isPrepaid = Boolean.parseBoolean(strTemp.trim());
			}
			
			//elementTypeId
			strTemp = map.get(CodesBusinessEntityEnum.FILE_PARAM_ELEMENT_TYPE_ID.getCodeEntity());
			if (strTemp != null && !strTemp.isEmpty()){
				elementTypeId = Long.parseLong(strTemp) ;
			}
			
			//elementModelId
			strTemp = map.get(CodesBusinessEntityEnum.CMD_REPORT_QUANTITY_WAREHOUSE_ELEMENT_FILTER_ELEMENT_MODEL_ID.getCodeEntity());
			if (strTemp != null && !strTemp.isEmpty()){
				elementModelId = Long.parseLong(strTemp) ;
			}
			
			ReferenceElementItemsResponse reponse = ejbRefElement.getElementNotSerializedFromWarehouse(requestCollInfo,
																									   referenceID,
																									   isPrepaid, 
																									   elementTypeId, 
																									   elementModelId);
			if( reponse != null )
				return (List<T>) reponse.getReferenceElementItemsVO();
			else
				return null;
		} catch (Exception e) {
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
