package ar.com.larreta.commons.domain.audit;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import ar.com.larreta.commons.domain.User;

@Entity
@Table(name = "userAccessHistory")
@XmlRootElement
public class UserAccessHistory extends ar.com.larreta.commons.domain.Entity {
	
	private User user;
	private Date userAccessDate;
	private String detail;
	
	@ManyToOne (fetch=FetchType.LAZY, targetEntity=User.class)
	@JoinColumn (name="idUser")	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

	@Basic
	public Date getUserAccessDate() {
		return userAccessDate;
	}
	public void setUserAccessDate(Date userAccessDate) {
		this.userAccessDate = userAccessDate;
	}

	@Basic
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
}
