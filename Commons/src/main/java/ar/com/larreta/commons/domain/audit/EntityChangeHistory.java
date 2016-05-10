package ar.com.larreta.commons.domain.audit;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import ar.com.larreta.commons.domain.User;

@MappedSuperclass
public abstract class EntityChangeHistory extends ar.com.larreta.commons.domain.Entity{
	
	private User user;
	private Date changeDate;
	private String entityValues;
	private String changeAction;
	protected AuditableEntity auditableEntity;

	@Basic
	public String getChangeAction() {
		return changeAction;
	}

	public void setChangeAction(String changeAction) {
		this.changeAction = changeAction;
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
	public Date getChangeDate() {
		return changeDate;
	}

	public void setChangeDate(Date changeDate) {
		this.changeDate = changeDate;
	}
	
	@Basic
	public String getEntityValues() {
		return entityValues;
	}

	public void setEntityValues(String entityValues) {
		if (entityValues.length()>255){
			this.entityValues = entityValues.substring(0, 254);
		} else {
			this.entityValues = entityValues;
		}
	}

	@Transient
	public abstract AuditableEntity getAuditableEntity();

	public void setAuditableEntity(AuditableEntity auditableEntity){
		this.auditableEntity = auditableEntity;
	}
	
}
