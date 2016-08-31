package co.com.directv.sdii.assign.schedule.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import co.com.directv.sdii.model.adapter.UtcTimestampAdapter;

/**
 * Carga de trabajo de un d�a
 * @author jjimenezh
 * @version 1.0,  &nbsp; 26-may-2011 15:11:46
 */
public class DayWorkLoad implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5250161323923277581L;

	/**
	 * fecha del cálculo de la carga de trabajo
	 */
	private java.util.Date date;
	
	/**
	 * Lista con las cargas de trabajo por cada una de las jornadas del día
	 */
	private List<DayServiceHourWorkLoad> dayShWorkLoadList;

	public DayWorkLoad(){
		this.dayShWorkLoadList = new ArrayList<DayServiceHourWorkLoad>();
	}
	
	

	public DayWorkLoad(Date date) {
		super();
		this.date = date;
		this.dayShWorkLoadList = new ArrayList<DayServiceHourWorkLoad>();
	}


	@XmlJavaTypeAdapter(UtcTimestampAdapter.class)
	public java.util.Date getDate() {
		return date;
	}



	public void setDate(java.util.Date date) {
		this.date = date;
	}



	public List<DayServiceHourWorkLoad> getDayShWorkLoadList() {
		return dayShWorkLoadList;
	}



	public void setDayShWorkLoadList(List<DayServiceHourWorkLoad> dayShWorkLoadList) {
		this.dayShWorkLoadList = dayShWorkLoadList;
	}

	public void finalize() throws Throwable {

	}
}