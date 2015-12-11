package ar.com.larreta.commons.utils;

import java.io.Serializable;
import java.util.Date;

public class Key implements Serializable {

	private Class type;
	private Long id;
	private Date lastExecute;
	public Class getType() {
		return type;
	}
	public void setType(Class type) {
		this.type = type;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getLastExecute() {
		return lastExecute;
	}
	public void setLastExecute(Date lastExecute) {
		this.lastExecute = lastExecute;
	}
	
	
	
}
