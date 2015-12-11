package ar.com.larreta.commons.domain.audit;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlTransient;

import ar.com.larreta.commons.domain.Entity;
import ar.com.larreta.commons.utils.xml.XMLHelper;

@MappedSuperclass
public abstract class AuditableEntity extends Entity {

	protected Set<EntityChangeHistory> changesHistory;

	@Transient
	@XmlTransient
	public abstract Set<EntityChangeHistory> getChangesHistory();

	public void setChangesHistory(Set<EntityChangeHistory> changesHistory) {
		this.changesHistory = changesHistory;
	}
	
	/**
	 * Agrega un cambio en el historial de la entidad actual
	 * @param changeHistory
	 */
	public void addChange(EntityChangeHistory changeHistory){
		if (changesHistory==null){
			changesHistory = new HashSet<EntityChangeHistory>();
		}
		changesHistory.add(changeHistory);
	}
	
	@Override
	public String toString() {
		return XMLHelper.javaToXML(this);
	}
	
	/**
	 * Retorna una nueva instancia de la clase  que registra los cambios para la entidad actual
	 * @return
	 */
	public abstract EntityChangeHistory newEntityChangeHistoryInstance();
	
}
