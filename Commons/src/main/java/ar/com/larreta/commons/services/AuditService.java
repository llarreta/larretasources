package ar.com.larreta.commons.services;

import ar.com.larreta.commons.domain.audit.AuditableEntity;

public interface AuditService extends StandardService {
	
	public void saveUpgrade(AuditableEntity auditableEntity);

}
