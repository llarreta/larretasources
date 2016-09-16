package co.com.directv.sdii.model.pojo;

import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

/**
 * 
 * Entidad que guarda la información de control para las entidades
 * auditadas
 * 
 * Fecha de Creación: 15/09/2011
 * @author wjimenez <a href="mailto:wjimenez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@RevisionEntity
public class SdiiRevEntity implements java.io.Serializable {

	private static final long serialVersionUID = -7549760138410906186L;

	@RevisionNumber
	private Long id;

	@RevisionTimestamp
	private long timestamp;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

}