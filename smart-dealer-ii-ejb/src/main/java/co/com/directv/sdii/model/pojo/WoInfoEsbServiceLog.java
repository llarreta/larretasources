package co.com.directv.sdii.model.pojo;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Entidad del tipo de log de eventos del procesamiento en paralelo de core y asignador
 * @author Aharker
 *
 */
public class WoInfoEsbServiceLog implements Serializable{
	
	private Long id;
	private WoInfoEsbService woInfoEsbService;
	private Long tryNumber;
	private String description;
	private WoInfoEsbNotificationType woInfoEsbNotificationType;
	private String notify;
	private Timestamp logDate;
	
	public WoInfoEsbServiceLog() {
		super();
	}
	public WoInfoEsbServiceLog(Long id, WoInfoEsbService woInfoEsbService,
			Long tryNumber, String description,
			WoInfoEsbNotificationType woInfoEsbNotificationType, String notify,
			Timestamp logDate) {
		super();
		this.id = id;
		this.woInfoEsbService = woInfoEsbService;
		this.tryNumber = tryNumber;
		this.description = description;
		this.woInfoEsbNotificationType = woInfoEsbNotificationType;
		this.notify = notify;
		this.logDate = logDate;
	}	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public WoInfoEsbService getWoInfoEsbService() {
		return woInfoEsbService;
	}
	public void setWoInfoEsbService(WoInfoEsbService woInfoEsbService) {
		this.woInfoEsbService = woInfoEsbService;
	}
	public Long getTryNumber() {
		return tryNumber;
	}
	public void setTryNumber(Long tryNumber) {
		this.tryNumber = tryNumber;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public WoInfoEsbNotificationType getWoInfoEsbNotificationType() {
		return woInfoEsbNotificationType;
	}
	public void setWoInfoEsbNotificationType(
			WoInfoEsbNotificationType woInfoEsbNotificationType) {
		this.woInfoEsbNotificationType = woInfoEsbNotificationType;
	}
	public String getNotify() {
		return notify;
	}
	public void setNotify(String notify) {
		this.notify = notify;
	}
	public Timestamp getLogDate() {
		return logDate;
	}
	public void setLogDate(Timestamp logDate) {
		this.logDate = logDate;
	}
}
