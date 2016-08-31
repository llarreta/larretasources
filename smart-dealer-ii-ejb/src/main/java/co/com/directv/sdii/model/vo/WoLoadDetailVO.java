/**
 * 
 */
package co.com.directv.sdii.model.vo;

import java.util.Date;

import co.com.directv.sdii.model.pojo.Country;
import co.com.directv.sdii.model.pojo.WoLoad;
import co.com.directv.sdii.model.pojo.WoLoadDetail;

/**
 * Encapsula la información de mensajes de carga de una Work order
 * 
 * @author jjimenezh
 *
 */
public class WoLoadDetailVO extends WoLoadDetail {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5867201581200312837L;

	public WoLoadDetailVO() {
		super();
	}

	public WoLoadDetailVO(String woCode, String customerCode, String errorCode,
			String errorMessage, Date loadDate, Country country, WoLoad woLoad) {
		super(woCode, customerCode, errorCode, errorMessage, loadDate, country, woLoad);
	}

	
	
}
