package ar.com.larreta.commons.services.impl;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.com.larreta.commons.domain.audit.AuditableEntity;
import ar.com.larreta.commons.domain.audit.EntityChangeHistory;
import ar.com.larreta.commons.services.AuditService;
import ar.com.larreta.commons.utils.SessionUtils;

@Service
@Transactional
public class AuditServiceImpl extends StandardServiceImpl implements AuditService {
	
	private static final String UPDATE = "Update";
	private static final String CREATE = "Create";
	
	public void saveUpgrade(AuditableEntity auditableEntity){
		
		EntityChangeHistory entityChangeHistory = auditableEntity.newEntityChangeHistoryInstance();
		
		entityChangeHistory.setChangeAction(CREATE);
		if (isExist(auditableEntity)){
			entityChangeHistory.setChangeAction(UPDATE);
		} 

		entityChangeHistory.setEntityValues(auditableEntity.toString());
		entityChangeHistory.setChangeDate(new Date());
		
		if (SessionUtils.getActualUser()!=null) {
			entityChangeHistory.setUser(SessionUtils.getActualUser());
		}
		entityChangeHistory.setAuditableEntity(auditableEntity);
		dao.saveOrUpdate(entityChangeHistory);
		auditableEntity.addChange(entityChangeHistory);
	}


	
	

}
