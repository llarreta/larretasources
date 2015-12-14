package ar.com.larreta.smarttrace.domain.history;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ar.com.larreta.commons.domain.audit.AuditableEntity;
import ar.com.larreta.commons.domain.audit.EntityChangeHistory;
import ar.com.larreta.smarttrace.domain.MaterialType;

@Entity
@Table(name = "materialTypeChangeHistory")
public class MaterialChangeHistory extends EntityChangeHistory {

	@ManyToOne (fetch=FetchType.LAZY, targetEntity=MaterialType.class)
	@JoinColumn (name="idAuditableEntity")	
	@Override
	public AuditableEntity getAuditableEntity() {
		return auditableEntity;
	}

}
