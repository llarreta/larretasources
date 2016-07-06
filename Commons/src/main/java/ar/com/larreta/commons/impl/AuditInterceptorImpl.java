package ar.com.larreta.commons.impl;

import java.io.Serializable;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.larreta.commons.AppObject;
import ar.com.larreta.commons.AppObjectImpl;
import ar.com.larreta.commons.AuditInterceptor;
import ar.com.larreta.commons.domain.Entity;
import ar.com.larreta.commons.domain.audit.AuditableEntity;
import ar.com.larreta.commons.logger.AppLogger;
import ar.com.larreta.commons.services.AuditService;

@Service(AuditInterceptorImpl.AUDIT_INTERCEPTOR)
public class AuditInterceptorImpl extends EmptyInterceptor implements AuditInterceptor {
	
	public static final String AUDIT_INTERCEPTOR = "auditInterceptor";

	@Autowired
	private AuditService auditService;
	
	private AppObject appObject = new AppObjectImpl(AuditInterceptorImpl.class);
	
	@Override
	public void onDelete(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
		super.onDelete(entity, id, state, propertyNames, types);
	}

	@Override
	public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
		if (entity instanceof AuditableEntity) {
			auditService.saveUpgrade((AuditableEntity) entity);
		}
		
		return super.onSave(entity, id, state, propertyNames, types);
	}

	@Override
	public String getEntityName(Object object) {
		if (object instanceof Entity) {
			Entity entity = (Entity) object;
			return entity.getPersistEntityName();
		}
		return super.getEntityName(object);
	}
	
	@Override
	public AppLogger getLog() {
		return appObject.getLog();
	}

	@Override
	public void setLog(AppLogger log) {
		appObject.setLog(log);
	}

	@Override
	public Long statisticsStart(String mark) {
		return appObject.statisticsStart(mark);
	}

	@Override
	public void statisticsStop(Long id) {
		appObject.statisticsStop(id);
	}

}
