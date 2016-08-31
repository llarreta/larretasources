package co.com.directv.sdii.model.vo;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import co.com.directv.sdii.model.adapter.UtcTimestampAdapter;
import co.com.directv.sdii.model.pojo.User;

/**
 * 
 * User Value Object
 * 
 * Fecha de Creación: 28/05/2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.User
 */
public class UserVO extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6442975331243409272L;

	private List<MenuVO> userMenus;
	
	private Date currentDate;
	
	private Boolean showPageAlarmStock;
	
	private Boolean showPageAlarmReference;

	public List<MenuVO> getUserMenus() {
		return userMenus;
	}

	public void setUserMenus(List<MenuVO> userMenus) {
		this.userMenus = userMenus;
	}

	@XmlJavaTypeAdapter(UtcTimestampAdapter.class)
	public Date getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}

	
	public Boolean getShowPageAlarmStock() {
		return showPageAlarmStock;
	}

	public void setShowPageAlarmStock(Boolean showPageAlarmStock) {
		this.showPageAlarmStock = showPageAlarmStock;
	}

	public Boolean getShowPageAlarmReference() {
		return showPageAlarmReference;
	}

	public void setShowPageAlarmReference(Boolean showPageAlarmReference) {
		this.showPageAlarmReference = showPageAlarmReference;
	}
	
	
	
}
