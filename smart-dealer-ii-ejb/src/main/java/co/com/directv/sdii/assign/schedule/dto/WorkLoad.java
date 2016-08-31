package co.com.directv.sdii.assign.schedule.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import co.com.directv.sdii.model.adapter.UtcTimestampAdapter;
import co.com.directv.sdii.model.dto.WorkOrderCSRDTO;

/**
 * representa la carga de trabajo para n días
 * @author jjimenezh
 * @version 1.0,  &nbsp; 26-may-2011 15:11:48
 */
public class WorkLoad implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7865976490914206024L;
	/**
	 * fecha de inicio del cálculo de la carga de trabajo
	 */
	private java.util.Date startingDate;
	/**
	 * fecha de finalización del cálculo de carga de trabajo
	 */
	private java.util.Date endDate;
	/**
	 * cantidad de días sobre los que se realizó el cálculo de carga de trabajo
	 */
	private int dayCount;
	
	
	private List<DayWorkLoad> dayWorkLoads;

	private WorkOrderCSRDTO workOrderCSRDTO;
	
	public WorkLoad(){

	}

	public WorkLoad(Date startingDate, Date endDate, int dayCount,
			List<DayWorkLoad> dayWorkLoads) {
		super();
		this.startingDate = startingDate;
		this.endDate = endDate;
		this.dayCount = dayCount;
		this.dayWorkLoads = dayWorkLoads;
	}

	@XmlJavaTypeAdapter(UtcTimestampAdapter.class)
	public java.util.Date getStartingDate() {
		return startingDate;
	}



	public void setStartingDate(java.util.Date startingDate) {
		this.startingDate = startingDate;
	}


	@XmlJavaTypeAdapter(UtcTimestampAdapter.class)
	public java.util.Date getEndDate() {
		return endDate;
	}



	public void setEndDate(java.util.Date endDate) {
		this.endDate = endDate;
	}



	public int getDayCount() {
		return dayCount;
	}



	public void setDayCount(int dayCount) {
		this.dayCount = dayCount;
	}



	public List<DayWorkLoad> getDayWorkLoads() {
		return dayWorkLoads;
	}

	public void setDayWorkLoads(List<DayWorkLoad> dayWorkLoads) {
		this.dayWorkLoads = dayWorkLoads;
	}

	public void finalize() throws Throwable {

	}

	public WorkOrderCSRDTO getWorkOrderCSRDTO() {
		return workOrderCSRDTO;
	}

	public void setWorkOrderCSRDTO(WorkOrderCSRDTO workOrderCSRDTO) {
		this.workOrderCSRDTO = workOrderCSRDTO;
	}
	
}