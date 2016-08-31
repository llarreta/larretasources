package co.com.ig.common.error.pojo;


/**
 * ErrorCategory entity. @author MyEclipse Persistence Tools
 */

public class ErrorCategory implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 7866028971792716388L;
	private Long id;
	private String categoryName;
	private String categoryCode;

	// Constructors

	/** default constructor */
	public ErrorCategory() {
	}

	/** full constructor */
	public ErrorCategory(String categoryName, String categoryCode) {
		this.categoryName = categoryName;
		this.categoryCode = categoryCode;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCategoryName() {
		return this.categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryCode() {
		return this.categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}
}