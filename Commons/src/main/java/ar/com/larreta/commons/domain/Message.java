package ar.com.larreta.commons.domain;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import ar.com.larreta.commons.utils.DateUtils;

@Entity
@Table(name = "message")
public class Message extends BigParametricEntity {
	
	public static final String DATE = "date";

	private User from;
	private Date date;
	private User to;
	
	@Basic
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	@Transient
	public String getAgo(){
		return DateUtils.getAgo(getDate());
	}

	@ManyToOne (fetch=FetchType.LAZY, targetEntity=User.class)
	@JoinColumn (name="idFrom")
	public User getFrom() {
		return from;
	}

	public void setFrom(User from) {
		this.from = from;
	}

	@ManyToOne (fetch=FetchType.LAZY, targetEntity=User.class)
	@JoinColumn (name="idTo")
	public User getTo() {
		return to;
	}

	public void setTo(User to) {
		this.to = to;
	}
	
}
