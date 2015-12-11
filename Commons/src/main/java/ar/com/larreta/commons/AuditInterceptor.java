package ar.com.larreta.commons;

import java.io.Serializable;

import org.hibernate.EmptyInterceptor;
import org.hibernate.Interceptor;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.larreta.commons.domain.audit.AuditableEntity;
import ar.com.larreta.commons.services.AuditService;

public interface AuditInterceptor extends AppObject, Interceptor {
	
	public void onDelete(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types);

	public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types);
}
