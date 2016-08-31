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
import co.com.directv.sdii.facade.stock.SupplierFacadeBeanLocal;
import co.com.directv.sdii.model.pojo.collection.SuppliersResponse;
import co.com.directv.sdii.model.vo.SupplierVO;
import co.com.directv.sdii.reports.commands.BaseCommand;
import co.com.directv.sdii.reports.commands.CMDAllSuppliersLocal;
import co.com.directv.sdii.reports.commands.ICommand;
import co.com.directv.sdii.reports.dto.SupplierDTO;

/**
 * @author jvelezmu
 *
 */
@Stateless(name="CMDAllSuppliers", mappedName="ejb/CMDAllSuppliers")
public class CMDAllSuppliers  extends BaseCommand implements ICommand,CMDAllSuppliersLocal {

	private List<String> fieldList = new ArrayList<String>();
	
	@EJB
	private SupplierFacadeBeanLocal supplierFacadeBeanLocal;
	
	public CMDAllSuppliers(){
	}
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.excelgen.ICommand#execute(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> execute(String args) throws BusinessException {
		try{
			SuppliersResponse r;
			HashMap<String,String> map = getParams(args);
			Long countryId = null;
			String strCountryId = map.get("countryId"); 
			if (strCountryId != null && !strCountryId.isEmpty())
				countryId = Long.parseLong(strCountryId);
			r = supplierFacadeBeanLocal.getAllSuppliers(countryId, null);
			List<SupplierDTO> response = new ArrayList<SupplierDTO>();
			if( r.getSuppliersVO() != null ){
				for( SupplierVO vo : r.getSuppliersVO() ){
					SupplierDTO dto = new SupplierDTO();
					dto.setSupplierNit( vo.getSupplierNit() );
					dto.setSupplierName( vo.getSupplierName() );
					dto.setAddress( vo.getAddress() );
					dto.setDistrict( vo.getDistrict() );
					if( vo.getPostalCode() != null && vo.getPostalCode().getCity() != null ){
						dto.setCityName( vo.getPostalCode().getCity().getCityName() );
						dto.setStateName( vo.getPostalCode().getCity().getState() != null ? vo.getPostalCode().getCity().getState().getStateName() : "" );
					}
					dto.setCountryName( vo.getCountryId() != null ? vo.getCountryId().getCountryName() : "" );
					dto.setPostalCode( vo.getPostalCode() != null ? vo.getPostalCode().getPostalCodeCode() : "" );
					dto.setFax( vo.getFax() );
					dto.setPhoneNumber( vo.getPhoneNumber() );
					dto.setContactName( vo.getContactName() );
					dto.setIsActive( vo.getIsActive() );
					dto.setSupplierCode(vo.getSupplierCode());
					
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
