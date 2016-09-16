package co.com.directv.sdii.reports;

import java.util.List;

import co.com.directv.sdii.model.pojo.Employee;

public class VisitsReportItemExcel {

	Employee EmployeeResponsibleCrew;
	
	List<VisitsReportItem> visitsReportItems;
	
	public VisitsReportItemExcel() {
		// TODO Auto-generated constructor stub
	}

	public Employee getEmployeeResponsibleCrew() {
		return EmployeeResponsibleCrew;
	}

	public void setEmployeeResponsibleCrew(Employee employeeResponsibleCrew) {
		EmployeeResponsibleCrew = employeeResponsibleCrew;
	}

	public List<VisitsReportItem> getVisitsReportItems() {
		return visitsReportItems;
	}

	public void setVisitsReportItems(List<VisitsReportItem> visitsReportItems) {
		this.visitsReportItems = visitsReportItems;
	}

}
