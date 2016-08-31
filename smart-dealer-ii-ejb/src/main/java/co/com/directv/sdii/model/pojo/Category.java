package co.com.directv.sdii.model.pojo;


/**
 * Category entity. @author MyEclipse Persistence Tools
 */

public class Category implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -7392314626195203185L;
	private Long 		id;
	private Long 		countryId;
	private Category 	category;
	private String 		categoryCode;
	private Long 		ibsCode;
	private String 		categoryName;
	private String 		filterCriteria;
	
	// Constructors

	/** default constructor */
	public Category() {
	}

	/**
	 * 
	 * @param categoryCode
	 * @param categoryName
	 */
	public Category(String categoryCode, String categoryName) {
		this.categoryCode = categoryCode;
		this.categoryName = categoryName;
	}

	/**
	 * 
	 * @param countryId
	 * @param category
	 * @param categoryCode
	 * @param categoryName
	 * @param ibsCode
	 */
	public Category(Long countryId,Category category,
			String categoryCode, String categoryName,Long ibsCode) {
		this.countryId = countryId;
		this.category = category;
		this.categoryCode = categoryCode;
		this.categoryName = categoryName;
		this.ibsCode=ibsCode;
	}
	
	// Property accessors

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Long getIbsCode() {
		return ibsCode;
	}

	public void setIbsCode(Long ibsCode) {
		this.ibsCode = ibsCode;
	}
	
	public String getFilterCriteria(){
		return filterCriteria;
	}

	public void setFilterCriteria(String filter){
		this.filterCriteria = filter;
	}
	
	@Override
	public String toString() {
		return "Category [categoryCode=" + categoryCode + ", id=" + id + "]";
	}

}