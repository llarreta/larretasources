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
import co.com.directv.sdii.facade.stock.TransportCompanyFacadeBeanLocal;
import co.com.directv.sdii.model.dto.collection.TransportCompanyDTO;
import co.com.directv.sdii.model.vo.TransportCompanyVO;
import co.com.directv.sdii.reports.commands.BaseCommand;
import co.com.directv.sdii.reports.commands.CMDActiveTransportCompaniesByCountryIdLocal;
import co.com.directv.sdii.reports.commands.ICommand;
import co.com.directv.sdii.reports.dto.TranspCompanyDTO;

/**
 * @author jvelezmu
 *
 */
@Stateless(name="CMDActiveTransportCompaniesByCountryId", mappedName="ejb/CMDActiveTransportCompaniesByCountryId")
public class CMDActiveTransportCompaniesByCountryId extends BaseCommand implements ICommand,CMDActiveTransportCompaniesByCountryIdLocal{

	public CMDActiveTransportCompaniesByCountryId(){
	}
	private List<String> fieldList = new ArrayList<String>();
	
	@EJB
	private TransportCompanyFacadeBeanLocal transportCompanyFacadeBeanLocal; 
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.excelgen.ICommand#execute(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> execute(String args) throws BusinessException {
		try{
			TransportCompanyDTO r;
			HashMap<String,String> map = getParams(args);
			Long countryId = null;
			String strCountryId = map.get("countryId"); 
			if (strCountryId != null && !strCountryId.isEmpty())
				countryId = Long.parseLong(strCountryId);
			r = transportCompanyFacadeBeanLocal.getAllTransportCompaniesByCountryId(countryId, null);
			List<TranspCompanyDTO> response = new ArrayList<TranspCompanyDTO>();
			if( r.getTransportCompaniesVO() != null ){
				for( TransportCompanyVO vo : r.getTransportCompaniesVO() ){
					TranspCompanyDTO dto = new TranspCompanyDTO();
					dto.setCompanyAddress(vo.getCompanyAddress());
					dto.setCompanyCode(vo.getCompanyCode());
					dto.setCompanyDescription(vo.getCompanyDescription());
					dto.setCompanyName(vo.getCompanyName());
					dto.setCountryName( vo.getCountryCodeId() != null ? vo.getCountryCodeId().getCountryName() : "" );
					dto.setIsActive( vo.getIsActive() );
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
