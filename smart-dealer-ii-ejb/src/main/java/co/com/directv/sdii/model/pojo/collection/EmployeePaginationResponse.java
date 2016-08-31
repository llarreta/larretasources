package co.com.directv.sdii.model.pojo.collection;

import java.util.List;

import co.com.directv.sdii.model.pojo.Employee;
import co.com.directv.sdii.model.vo.EmployeeVO;

public class EmployeePaginationResponse extends CollectionBase implements java.io.Serializable{

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6665496546042244638L;
	private List<Employee> employeeList;
	private List<EmployeeVO> employeeVOList;
	public List<Employee> getEmployeeList() {
		return employeeList;
	}
	public void setEmployeeList(List<Employee> employeeList) {
		this.employeeList = employeeList;
	}
	public List<EmployeeVO> getEmployeeVOList() {
		return employeeVOList;
	}
	public void setEmployeeVOList(List<EmployeeVO> employeeVOList) {
		this.employeeVOList = employeeVOList;
	}
	
	

}
