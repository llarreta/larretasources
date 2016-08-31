package co.com.directv.sdii.model.dto;

import java.io.Serializable;

/**
 * DTO, enfocada al envio y recepcion de mensajes para el procesamiento en paralelo de core y asignador
 * @author Aharker
 *
 */
public class MessageCoreAllocatorDTO  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6684696456096343833L;
	
	private String woCode;
	private String processCode;//Si es de core y asignador
	private Long id;
	private String countryCode;
	private Long countryId;

	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public Long getCountryId() {
		return countryId;
	}
	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}
	public MessageCoreAllocatorDTO(String woCode, String processCode, Long id,
			String countryCode, Long countryId) {
		super();
		this.woCode = woCode;
		this.processCode = processCode;
		this.id = id;
		this.countryCode = countryCode;
		this.countryId = countryId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public MessageCoreAllocatorDTO(String woCode, String processCode, Long id) {
		super();
		this.woCode = woCode;
		this.processCode = processCode;
		this.id = id;
	}
	public String getWoCode() {
		return woCode;
	}
	public void setWoCode(String woCode) {
		this.woCode = woCode;
	}
	public String getProcessCode() {
		return processCode;
	}
	public void setProcessCode(String processCode) {
		this.processCode = processCode;
	}
	public MessageCoreAllocatorDTO(String woCode, String processCode) {
		super();
		this.woCode = woCode;
		this.processCode = processCode;
	}
	public MessageCoreAllocatorDTO() {
		super();
	}
	
	
	
}
