package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;


/**
 * MenuItem entity. @author MyEclipse Persistence Tools
 */

public class MenuItem implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 4770716398136682322L;
	private Long id;
	private Long menuId;
	private String menuUrl;
	private String itemCode;
	private String name;
	private String description;
	private Long menuOrder;
	private String isVisible;

	// Constructors

	/** default constructor */
	public MenuItem() {
	}

	/** minimal constructor */
	public MenuItem(String menuUrl, String itemCode, String name) {
		this.menuUrl = menuUrl;
		this.itemCode = itemCode;
		this.name = name;
	}
	/** minimal constructor */
	public MenuItem(Long menuId, String menuUrl, String itemCode, String name,
			String description) {
		this.menuId = menuId;
		this.menuUrl = menuUrl;
		this.itemCode = itemCode;
		this.name = name;
		this.description = description;
	}
	/** full constructor */
	public MenuItem(Long menuId, String menuUrl, String itemCode, String name,
			String description,Long menuOrder) {
		this.menuId = menuId;
		this.menuUrl = menuUrl;
		this.itemCode = itemCode;
		this.name = name;
		this.description = description;
		this.menuOrder = menuOrder;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMenuId() {
		return this.menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public String getMenuUrl() {
		return this.menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public String getItemCode() {
		return this.itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
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

	public Long getMenuOrder() {
		return menuOrder;
	}

	public void setMenuOrder(Long menuOrder) {
		this.menuOrder = menuOrder;
	}

	public String getIsVisible() {
		return isVisible;
	}

	public void setIsVisible(String isVisible) {
		this.isVisible = isVisible;
	}

	@Override
	public String toString() {
		return "MenuItem [id=" + id + ", itemCode=" + itemCode + "]";
	}
	
	
}