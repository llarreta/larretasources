package co.com.directv.sdii.model.dto;

import java.io.Serializable;

/**
 * //CC053 - HSP Reportes - CRUD Programacion.
 * @author martlago
 *
 */
public class ScheduleReportStatusDTO implements Serializable{

	private static final long serialVersionUID = 8951198239844869129L;
	private Long id;
	private String description;
	
	public ScheduleReportStatusDTO(){}
	
	public ScheduleReportStatusDTO(Long id, String description){
		this.id = id;
		this.description = description;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

}

