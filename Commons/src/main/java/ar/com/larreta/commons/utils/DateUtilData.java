package ar.com.larreta.commons.utils;

import java.io.Serializable;

public class DateUtilData implements Serializable {
	
	private Long years = new Long(0);
	private Long months = new Long(0);
	private Long days = new Long(0);
	private Long hours = new Long(0);
	private Long minutes = new Long(0);
	private Long seconds = new Long(0);
	private Long milliseconds = new Long(0);
	
	public Long getMilliseconds() {
		return milliseconds;
	}
	public void setMilliseconds(Long milliseconds) {
		this.milliseconds = milliseconds;
	}
	public Long getYears() {
		return years;
	}
	public void setYears(Long years) {
		this.years = years;
	}
	public Long getMonths() {
		return months;
	}
	public void setMonths(Long months) {
		this.months = months;
	}
	public Long getDays() {
		return days;
	}
	public void setDays(Long days) {
		this.days = days;
	}
	public Long getHours() {
		return hours;
	}
	public void setHours(Long hours) {
		this.hours = hours;
	}
	public Long getMinutes() {
		return minutes;
	}
	public void setMinutes(Long minutes) {
		this.minutes = minutes;
	}
	public Long getSeconds() {
		return seconds;
	}
	public void setSeconds(Long seconds) {
		this.seconds = seconds;
	}
	
	public Long getTotalMilliseconds(){
		return (years * DateUtils.ONE_YEAR) +
				(months * DateUtils.ONE_MOUNTH) +
				(days * DateUtils.ONE_DAY) +
				(hours * DateUtils.ONE_HOUR) +
				(minutes * DateUtils.ONE_MINUTE)+
				(seconds * DateUtils.ONE_SECOND) +
				milliseconds;
		
	}
}
