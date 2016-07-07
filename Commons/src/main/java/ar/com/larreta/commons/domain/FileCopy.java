package ar.com.larreta.commons.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "fileCopy")
public class FileCopy extends ar.com.larreta.commons.domain.Entity {

	private String from;
	private String to;
	
	@Basic @Column(name="fromField")
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	
	@Basic @Column(name="toField")
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}

	/*@Override
	public boolean equals(Object object) {
		if (object instanceof FileCopy) {
			FileCopy toCompare = (FileCopy) object;
			return toCompare.getFrom().equals(getFrom());
		}
		return Boolean.FALSE;
	}
	
	@Override
	public int hashCode() {
		return getFrom().hashCode();
	}*/
}
