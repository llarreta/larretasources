package ar.com.larreta.commons.utils;

import java.io.Serializable;
import java.util.Date;

public class Timer implements Serializable{
	
	private Date start;
	private Date finish;
	private DateUtilData data;

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getFinish() {
		return finish;
	}

	public void setFinish(Date finish) {
		this.finish = finish;
	}

	public Timer(){
		reset();
	}

	public void reset() {
		start = new Date();
	}
	
	public DateUtilData stop(){
		finish = new Date();
		data = DateUtils.getDateDifference(finish, start);
		return  data;
	}
	
	public DateUtilData getDateDifference(){
		return data;
	}
}
