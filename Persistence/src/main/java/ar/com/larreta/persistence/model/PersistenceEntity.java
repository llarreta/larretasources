package ar.com.larreta.persistence.model;

import java.util.Date;

import ar.com.larreta.prototypes.Entity;

public interface PersistenceEntity extends Entity {
	public Date getDeleted();
	public void setDeleted(Date deleted);
}
