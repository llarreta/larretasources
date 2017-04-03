package ar.com.larreta.prototypes;

import org.apache.log4j.Logger;

import ar.com.larreta.tools.SpringUtils;

/**
 * Capaz de ser representada mediante un JSON 
 */
public abstract class JSONable {
	
	private final static Logger LOGGER = Logger.getLogger(JSONable.class);

	@Override
	public String toString() {
		return SpringUtils.getJSONParser().parse(this);
	}
}
