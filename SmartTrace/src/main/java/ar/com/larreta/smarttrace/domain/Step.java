package ar.com.larreta.smarttrace.domain;

import java.util.Collection;
import java.util.Date;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import ar.com.larreta.commons.domain.audit.AuditableEntity;
import ar.com.larreta.commons.domain.audit.EntityChangeHistory;
import ar.com.larreta.smarttrace.domain.history.MaterialChangeHistory;
import ar.com.larreta.smarttrace.domain.history.StepChangeHistory;

/**
 * Representa un paso o accion realizada en un determinado proceso
 *
 */
@Entity
@Table(name = "step")
@Inheritance(strategy = InheritanceType.JOINED)
@Where(clause="deleted IS NULL")
@SQLDelete (sql="UPDATE Step SET deleted=CURRENT_TIMESTAMP WHERE id=?")
@XmlRootElement
public class Step extends AuditableEntity {
	
	/**
	 * Descripcion de la accion realizada
	 */
	private String description;
	
	/**
	 * Fecha y hora de realizacion del paso
	 */
	private Date execution;
	
	/**
	 * Materiales utilizados para la realizacion de la accion / paso
	 */
	private Collection<Container> source;
	
	/**
	 * Materiales surgidos como resultado de la realizacion de la accion / paso
	 */
	private Collection<Container> target;

	@Basic
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Basic
	public Date getExecution() {
		return execution;
	}

	public void setExecution(Date execution) {
		this.execution = execution;
	}

	@ManyToMany (fetch=FetchType.LAZY, targetEntity=Container.class)
	@JoinTable(name = "source", joinColumns = { @JoinColumn(name = "idStep") }, 
		inverseJoinColumns = { @JoinColumn(name = "idContainerSource") })
	public Collection<Container> getSource() {
		return source;
	}

	public void setSource(Collection<Container> source) {
		this.source = source;
	}

	@ManyToMany (fetch=FetchType.LAZY, targetEntity=Container.class)
	@JoinTable(name = "target", joinColumns = { @JoinColumn(name = "idStep") }, 
		inverseJoinColumns = { @JoinColumn(name = "idContainerTarget") })
	public Collection<Container> getTarget() {
		return target;
	}

	public void setTarget(Collection<Container> target) {
		this.target = target;
	}

	@OneToMany (mappedBy="auditableEntity", fetch=FetchType.LAZY, cascade=CascadeType.ALL, targetEntity=MaterialChangeHistory.class)
	public Set<EntityChangeHistory> getChangesHistory() {
		return changesHistory;
	}

	@Override
	public EntityChangeHistory newEntityChangeHistoryInstance() {
		return new StepChangeHistory();
	}

}
