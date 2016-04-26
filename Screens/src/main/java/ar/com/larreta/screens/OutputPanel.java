package ar.com.larreta.screens;

import org.apache.commons.lang.StringUtils;

public class OutputPanel extends Container {
	
	private String autoUpdate = StringUtils.EMPTY;

	public String getAutoUpdate() {
		return autoUpdate;
	}

	public void setAutoUpdate(String autoUpdate) {
		this.autoUpdate = autoUpdate;
	}
	
}
