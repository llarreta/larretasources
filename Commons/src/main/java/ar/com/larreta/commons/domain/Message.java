package ar.com.larreta.commons.domain;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import ar.com.larreta.commons.utils.DateUtils;

@Entity
@Table(name = "message")
public class Message extends ParametricEntity {
	
	public static final String DATE = "date";

	private User from;
	private Date date;
	private User to;
	
	private byte[] extDescription;

	public void setMessage(String description) {
		if (description!=null){
			setExtDescription(description.getBytes());
		}
	}

	@Transient
	public String getMessage() {
		if ((extDescription!=null) && (extDescription.length>0)){
			return new String(extDescription);
		} 
		return getDescription();
	}

	
	@Lob @Basic
	public byte[] getExtDescription() {
		return extDescription;
	}

	public void setExtDescription(byte[] extDescription) {
		this.extDescription = extDescription;
	}

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
	
	@Transient
	public Boolean getIsVisible(){
		return ((getDescription()!=null) && (getDescription().trim().length()>0)) ||
				((extDescription!=null) && extDescription.length>0);
	}
}
