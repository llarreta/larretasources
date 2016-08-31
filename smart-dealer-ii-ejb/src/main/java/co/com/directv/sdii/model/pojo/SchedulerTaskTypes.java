package co.com.directv.sdii.model.pojo;

/**
 * Pojo Table SCHEDULER_TASK_TYPES
 * 
 * Fecha de Creación: 8/05/2012
 * @author cduarte <a href="mailto:cduarte@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
public class SchedulerTaskTypes implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 470111272399884490L;

	private Long id;
	private String code;
	private String name;
	
	public SchedulerTaskTypes() {
		super();
	}

	public SchedulerTaskTypes(Long id, String code, String name) {
		super();
		this.id = id;
		this.code = code;
		this.name = name;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}