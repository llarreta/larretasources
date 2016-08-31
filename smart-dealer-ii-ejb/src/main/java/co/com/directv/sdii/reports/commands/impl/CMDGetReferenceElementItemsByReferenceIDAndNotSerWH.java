package co.com.directv.sdii.reports.commands.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.stock.ReferenceElementItemFacadeBeanLocal;
import co.com.directv.sdii.model.pojo.collection.ReferenceElementItemsResponse;
import co.com.directv.sdii.reports.commands.BaseCommand;
import co.com.directv.sdii.reports.commands.CMDGetReferenceElementItemsByReferenceIDAndNotSerWHLocal;
import co.com.directv.sdii.reports.commands.ICommand;

/**
 * Session Bean implementation class CMDGetReferenceElementItemsByReferenceIDAndNotSerWH
 */
@Stateless(name = "CMDGetReferenceElementItemsByReferenceIDAndNotSerWH", mappedName = "ejb/CMDGetReferenceElementItemsByReferenceIDAndNotSerWH")
public class CMDGetReferenceElementItemsByReferenceIDAndNotSerWH extends BaseCommand implements ICommand,CMDGetReferenceElementItemsByReferenceIDAndNotSerWHLocal {

	private List<String> fieldList = new ArrayList<String>();
	
	@EJB
    private ReferenceElementItemFacadeBeanLocal ejbRefElement;
	
    /**
     * Default constructor. 
     */
    public CMDGetReferenceElementItemsByReferenceIDAndNotSerWH() {
        
    }

	/**
     * @see ICommand#getFieldList()
     */
    @Override
	public List<String> getFieldList() {
		return fieldList;
	}

	/**
     * @see ICommand#setFieldList(List<String>)
     */
    @Override
	public void setFieldList(List<String> fieldList) {
		this.fieldList=fieldList;
	}

	/**
     * @see ICommand#execute(String)
     */
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> execute(String args) throws BusinessException {
		try{
			Long refID = null;
			boolean isPrepaid = false;
			Long elementTypeId = null;
			Long modelId = null;
			HashMap<String, String> map = getParams(args);
			
			//Referencia
			String strIdReference = map.get("refId");
			if (strIdReference != null && !strIdReference.isEmpty()){
				refID = Long.parseLong(strIdReference) ;
			}
			
			//Es prepago
			String strIsPrepaid = map.get("isPrepaid");
			if (strIsPrepaid != null && !strIsPrepaid.isEmpty()){
				Boolean b = Boolean.parseBoolean(strIsPrepaid);
				isPrepaid = b.booleanValue();
			}
			
			//Tipo de elemento
			String strElementTypeId = map.get("elementTypeId");
			if (strElementTypeId != null && !strElementTypeId.isEmpty()){
				elementTypeId = Long.parseLong(strElementTypeId) ;
			}
			
			//Modelo
			String strModelId = map.get("modelId");
			if (strModelId != null && !strModelId.isEmpty()){
				modelId = Long.parseLong(strModelId) ;
			}
			
			ReferenceElementItemsResponse response = ejbRefElement.getElementNotSerializedFromWarehouse(null, refID, isPrepaid, elementTypeId, modelId);
			if( response != null )
				return (List<T>) response.getReferenceElementItemsVO();
			else
				return null;
		} catch (Exception e) {
			throw this.manageException(e);
		}
    }

}
