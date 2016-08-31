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
import co.com.directv.sdii.facade.stock.ElementTypeFacadeBeanLocal;
import co.com.directv.sdii.model.pojo.collection.ElementResponse;
import co.com.directv.sdii.model.vo.ElementTypeVO;
import co.com.directv.sdii.model.vo.ElementVO;
import co.com.directv.sdii.reports.commands.BaseCommand;
import co.com.directv.sdii.reports.commands.CMDElementsByElementTypeLocal;
import co.com.directv.sdii.reports.commands.ICommand;
import co.com.directv.sdii.reports.dto.ElementDTO;

/**
 * @author jvelezmu
 *
 */
@Stateless(name="CMDElementsByElementType", mappedName="ejb/CMDElementsByElementType")
public class CMDElementsByElementType extends BaseCommand  implements ICommand,CMDElementsByElementTypeLocal {

	private List<String> fieldList = new ArrayList<String>();
	
	@EJB
	private ElementFacadeBeanLocal elementFacadeBeanLocal;
	
	@EJB
	private ElementTypeFacadeBeanLocal elementTypeFacadeBeanLocal;
	
	public CMDElementsByElementType(){
	}
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.excelgen.ICommand#execute(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> execute(String args) throws BusinessException {
		try{
			ElementResponse r;
			HashMap<String,String> map = getParams(args);
			Long elementTypeId = null;
			String strElementTypeId = map.get("elementTypeId"); 
			if (strElementTypeId != null && !strElementTypeId.isEmpty())
				elementTypeId = Long.parseLong(strElementTypeId);
			
			ElementTypeVO elementType = elementTypeFacadeBeanLocal.getElementTypeByID(elementTypeId);
			r = elementFacadeBeanLocal.getElementsByElementType(elementType, null);
			List<ElementDTO> response = new ArrayList<ElementDTO>();
			String serializedCode;
			try{
				serializedCode = CodesBusinessEntityEnum.ELEMENT_TYPE_SERIALIZED.getCodeEntity();
			}catch (Exception e) {
				serializedCode = ApplicationTextEnum.ELEMENT_IS_SERIALIZED.getApplicationTextValue();
			}
			
			if( r.getElementsVO() != null ){
				for( ElementVO vo : r.getElementsVO() ){
					ElementDTO dto = new ElementDTO();
					dto.setElementTypeName( vo.getElementType() != null ? vo.getElementType().getTypeElementName() : "" );
					//dto.setElementBrandName( vo.getElementBrand() != null ? vo.getElementBrand().getBrandName() : "" );
					dto.setElementModelName( vo.getElementType().getElementModel() != null ? vo.getElementType().getElementModel().getModelName() : "" );
					//dto.setElementClassName( vo.getElementClass() != null ? vo.getElementClass().getElementClassName() : "" );
					dto.setIsSerialized( vo.getIsSerialized().equals(serializedCode) ? ApplicationTextEnum.SERIALIZED.getApplicationTextValue() : ApplicationTextEnum.NOT_SERIALIZED.getApplicationTextValue() );
					//dto.setMeasureUnitName( vo.getMeasureUnit() != null ? vo.getMeasureUnit().getUnitName() : "" );
					dto.setMeasureUnitName( vo.getElementType().getMeasureUnit() != null ? vo.getElementType().getMeasureUnit().getUnitName() : "" );
					dto.setWarrantyPeriod( vo.getWarrantyPeriod() );
					dto.setSupplierName( vo.getSupplier() != null ? vo.getSupplier().getSupplierName() : "" );
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
		
		fieldList.add(ApplicationTextEnum.ID.getApplicationTextValue());
		fieldList.add(ApplicationTextEnum.SERIALIZED.getApplicationTextValue());
		fieldList.add(ApplicationTextEnum.SERIAL.getApplicationTextValue());
		fieldList.add(ApplicationTextEnum.BATCH.getApplicationTextValue());
		fieldList.add(ApplicationTextEnum.BRAND.getApplicationTextValue());
		fieldList.add(ApplicationTextEnum.CLASS.getApplicationTextValue());
		fieldList.add(ApplicationTextEnum.MODEL.getApplicationTextValue());
		fieldList.add(ApplicationTextEnum.TYPE.getApplicationTextValue());
		fieldList.add(ApplicationTextEnum.SUPPLIER.getApplicationTextValue());
		fieldList.add(ApplicationTextEnum.WARRANTY_PERIOD.getApplicationTextValue());
		fieldList.add(ApplicationTextEnum.STATUS.getApplicationTextValue());
		fieldList.add(ApplicationTextEnum.UNIT_MEASURE.getApplicationTextValue());
		return fieldList;
	}
	
	@Override
	public void setFieldList(List<String> fieldList) {
		this.fieldList=fieldList;
	}
}
