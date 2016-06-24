package ar.com.larreta.screens;

import javax.persistence.Basic;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;

@Entity
@Table(name = "outputPanel")
@DiscriminatorValue(value = "outputPanel")
@PrimaryKeyJoinColumn(name=ar.com.larreta.commons.domain.Entity.ID)
public class OutputPanel extends StandardContainer {
	
	private String autoUpdate = StringUtils.EMPTY;

	@Basic
	public String getAutoUpdate() {
		return autoUpdate;
	}

	public void setAutoUpdate(String autoUpdate) {
		this.autoUpdate = autoUpdate;
	}
	
}
