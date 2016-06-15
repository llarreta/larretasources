package ar.com.larreta.screens;

import javax.persistence.Basic;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;

@Entity
@Table(name = "form")
@DiscriminatorValue(value = "form")
@PrimaryKeyJoinColumn(name=ar.com.larreta.commons.domain.Entity.ID)
public class Form extends Container {

	private String acceptcharset = "UTF-8";

	@Basic
	public String getAcceptcharset() {
		return acceptcharset;
	}

	public void setAcceptcharset(String acceptcharset) {
		if (!StringUtils.isEmpty(acceptcharset)){
			this.acceptcharset = acceptcharset;
		}
	}
	
	
	
}
