package ar.com.larreta.commons.domain.audit;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import ar.com.larreta.commons.domain.User;
import ar.com.larreta.commons.utils.DateUtilData;
import ar.com.larreta.commons.utils.Timer;

@Entity
@Table(name = "executionstatistics")
public class ExecutionStatistics extends ar.com.larreta.commons.domain.Entity {
	
	private static Integer MAX_SIZE = 1000;
	
	private User user;
	
	private Date start;
	private Date end;
	private Long milliseconds;
	
	private Timer timer;
	
	private String mark;
	
	@Basic
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	@Transient
	public Timer getTimer() {
		return timer;
	}
	public void setTimer(Timer timer) {
		this.timer = timer;
	}
	public void start() {
		setTimer(new Timer());
		setStart(timer.getStart());
	}

	
	@ManyToOne (fetch=FetchType.LAZY, targetEntity=User.class)
	@JoinColumn (name="idUser")
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	@Basic
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	
	@Basic
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}
	
	@Basic
	public Long getMilliseconds() {
		return milliseconds;
	}
	public void setMilliseconds(Long milliseconds) {
		this.milliseconds = milliseconds;
	}
	public void stop() {
		DateUtilData data = timer.stop();
		setEnd(timer.getFinish());
		setMilliseconds(data.getTotalMilliseconds());
	}
	
	@Transient
	public Boolean isActive(){
		return end==null;
	}

}
