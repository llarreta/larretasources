package ar.com.larreta.persistence.model.impl;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.hibernate.annotations.Where;

import ar.com.larreta.persistence.dao.impl.MainEntity;
import ar.com.larreta.persistence.model.PersistenceEntity;

@MappedSuperclass
@Where(clause=PersistenceEntityImpl.NOT_DELETED)
public class PersistenceEntityImpl implements PersistenceEntity {
	
	public static final String NOT_DELETED = "deleted IS NULL";
	public static final String ID = "id";
	
	protected Long id;
	protected Date deleted;
	
	@Id
	public Long getId(){
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}
	
	@Basic
	public Date getDeleted(){
		return deleted;
	}

	public void setDeleted(Date deleted) {
		this.deleted = deleted;
	}
	
	@Override
	public boolean equals(Object object) {
		if ((object!=null) && (getEntityClassName().equals(MainEntity.getEntityName(object.getClass())))) {
			PersistenceEntityImpl entity = (PersistenceEntityImpl) object;
			return entity.getId().equals(getId());
		}
		return Boolean.FALSE;
	} 

	@Override
	public int hashCode() {
		return getId().hashCode();
	}
	
	@Transient
	public String getEntityClassName(){
		return MainEntity.getEntityName(this.getClass());
	}
	
	/**
	 * Util para redefinir el tipo de entidad a utilizar durante la persistencia
	 * Si es null asume que el tipo de entidad es el definido en el encabezado de la clase 
	 */
	@Transient
	public String getPersistEntityName(){
		return null;
	}

}
