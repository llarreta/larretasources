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
import co.com.directv.sdii.facade.dealers.EmployeeFacadeBeanLocal;
import co.com.directv.sdii.model.pojo.collection.EmployeePaginationResponse;
import co.com.directv.sdii.model.vo.EmployeeVO;
import co.com.directv.sdii.reports.commands.BaseCommand;
import co.com.directv.sdii.reports.commands.CMDEmployeeByChriteriaLocal;
import co.com.directv.sdii.reports.commands.ICommand;
import co.com.directv.sdii.reports.dto.EmployeeDTO;

/**
 * @author jvelezmu
 *
 */
@Stateless(name="CMDEmployeeByChriteria", mappedName="ejb/CMDEmployeeByChriteria")
public class CMDEmployeeByChriteria  extends BaseCommand implements ICommand,CMDEmployeeByChriteriaLocal {

	private List<String> fieldList = new ArrayList<String>();
	
	@EJB
    private EmployeeFacadeBeanLocal ejbEmployeeBean;
	
	public CMDEmployeeByChriteria(){
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.excelgen.ICommand#execute(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> execute(String args) throws BusinessException {
		try{
			EmployeePaginationResponse r;
			
			HashMap<String,String> map = getParams(args);
			Long typeId = ( map.get("typeId")!= null && !map.get("typeId").equals("") ) ? Long.parseLong(map.get("typeId").toString()) : null;
			String documentNumber = ( map.get("documentNumber")!= null && !map.get("documentNumber").equals("") )  ? map.get("documentNumber").toString() : null;
			String firstName = ( map.get("firstName")!= null && !map.get("firstName").equals("") ) ? map.get("firstName").toString() : null;
			String lastName = ( map.get("lastName")!= null && !map.get("lastName").equals("") ) ? map.get("lastName").toString() : null;
			String depotCode = ( map.get("depotCode")!= null && !map.get("depotCode").equals("") ) ? map.get("depotCode").toString() : null;
			Long dealerCode = ( map.get("dealerCode")!= null && !map.get("dealerCode").equals("") ) ? Long.parseLong(map.get("dealerCode").toString()) : null;
			Long dealerId = ( map.get("dealerId")!= null && !map.get("dealerId").equals("") ) ? Long.parseLong(map.get("dealerId").toString()) : null;
			Long countryId = ( map.get("countryId")!= null && !map.get("countryId").equals("") ) ? Long.parseLong(map.get("countryId").toString()) : null;
			
			r = ejbEmployeeBean.getEmployeeByChriteria(typeId, documentNumber, firstName, lastName, depotCode, dealerCode, dealerId, countryId, null);
			
			List<EmployeeDTO> response = new ArrayList<EmployeeDTO>();
			if( r.getEmployeeVOList() != null){
				for( EmployeeVO vo : r.getEmployeeVOList() ){
					EmployeeDTO dto = new EmployeeDTO();
					dto.setDealerCode(vo.getDealerCode());
					dto.setDealerName(vo.getDealerName());
					dto.setDocumentNumber(vo.getDocumentNumber());
					dto.setEmployeeStatusName(vo.getEmployeeStatus() != null ? vo.getEmployeeStatus().getStatusName() : null);
					dto.setEmployeeName( vo.getFirstName() + " " + vo.getLastName() );
					dto.setPositionName( vo.getPositionName() );
					response.add(dto);
				}
			}
			return (List<T>) response;
		} catch (Throwable e) {
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
