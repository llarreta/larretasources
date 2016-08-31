package co.com.directv.sdii.model.pojo;


/**
 * Kpi entity. @author MyEclipse Persistence Tools
 */

public class Kpi implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 2528558244451782169L;
	private Long id;
	private KpiType kpiType;
	private String code;
	private String name;
	private String description;
	private String status;

	// Constructors

	/** default constructor */
	public Kpi() {
	}

	/** minimal constructor */
	public Kpi(KpiType kpiType, String code, String name, String status) {
		this.kpiType = kpiType;
		this.code = code;
		this.name = name;
		this.status = status;
	}

	/** full constructor */
	public Kpi(KpiType kpiType, String code, String name, String description,
			String status) {
		this.kpiType = kpiType;
		this.code = code;
		this.name = name;
		this.description = description;
		this.status = status;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public KpiType getKpiType() {
		return this.kpiType;
	}

	public void setKpiType(KpiType kpiType) {
		this.kpiType = kpiType;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}