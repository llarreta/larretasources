package co.com.directv.sdii.model.pojo;

import java.util.Date;

public class AuditExternalSystemSchedule implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 3014567718299181906L;
	
	private Long id;
	private Date actualDate;
	private String userNameSystemExternal;
	private User user;
	private String ipSystemExternal;
	private String urlSystemExternal;
	private String woCode;
	private Date scheduleDate;
	private Country country;
	
	public AuditExternalSystemSchedule() {
		super();
	}
	
	public AuditExternalSystemSchedule(Long id, Date actualDate,
			String userNameSystemExternal, User user, String ipSystemExternal,
			String woCode, Date scheduleDate, Country country) {
		super();
		this.id = id;
		this.actualDate = actualDate;
		this.userNameSystemExternal = userNameSystemExternal;
		this.user = user;
		this.ipSystemExternal = ipSystemExternal;
		this.woCode = woCode;
		this.scheduleDate = scheduleDate;
		this.country = country;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getActualDate() {
		return actualDate;
	}

	public void setActualDate(Date actualDate) {
		this.actualDate = actualDate;
	}

	public String getUserNameSystemExternal() {
		return userNameSystemExternal;
	}

	public void setUserNameSystemExternal(String userNameSystemExternal) {
		this.userNameSystemExternal = userNameSystemExternal;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getIpSystemExternal() {
		return ipSystemExternal;
	}

	public void setIpSystemExternal(String ipSystemExternal) {
		this.ipSystemExternal = ipSystemExternal;
	}
	
	public String getUrlSystemExternal() {
		return urlSystemExternal;
	}

	public void setUrlSystemExternal(String urlSystemExternal) {
		this.urlSystemExternal = urlSystemExternal;
	}

	public String getWoCode() {
		return woCode;
	}

	public void setWoCode(String woCode) {
		this.woCode = woCode;
	}

	public Date getScheduleDate() {
		return scheduleDate;
	}

	public void setScheduleDate(Date scheduleDate) {
		this.scheduleDate = scheduleDate;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}