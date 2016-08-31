package co.com.directv.sdii.model.pojo;

import java.util.HashSet;
import java.util.Set;

import co.com.directv.sdii.audit.Auditable;

/**
 * Menu entity. @author MyEclipse Persistence Tools
 */

public class Menu implements java.io.Serializable,Auditable, Comparable<Menu> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3674501663807700387L;
	// Fields

	private Long id;
	private Long fatherMenuId;
	private String menuCode;
	private String name;
	private Long menuOrder;
	private Set<MenuItem> menuItems = new HashSet<MenuItem>(0);

	// Constructors

	/** default constructor */
	public Menu() {
	}

	/** minimal constructor */
	public Menu(String menuCode, String name) {
		this.menuCode = menuCode;
		this.name = name;
	}
	/** minimal constructor */
	public Menu(Long fatherMenuId, String menuCode, String name, Set<MenuItem> menuItems) {
		this.fatherMenuId = fatherMenuId;
		this.menuCode = menuCode;
		this.name = name;
		this.menuItems = menuItems;
	}
	/** full constructor */
	public Menu(Long fatherMenuId, String menuCode, String name, Set<MenuItem> menuItems,Long menuOrder) {
		this.fatherMenuId = fatherMenuId;
		this.menuCode = menuCode;
		this.name = name;
		this.menuItems = menuItems;
		this.menuOrder = menuOrder;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	public String getMenuCode() {
		return this.menuCode;
	}

	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<MenuItem> getMenuItems() {
		return this.menuItems;
	}

	public void setMenuItems(Set<MenuItem> menuItems) {
		this.menuItems = menuItems;
	}

	public Long getFatherMenuId() {
		return fatherMenuId;
	}

	public void setFatherMenuId(Long fatherMenuId) {
		this.fatherMenuId = fatherMenuId;
	}

	public Long getMenuOrder() {
		return menuOrder;
	}

	public void setMenuOrder(Long menuOrder) {
		this.menuOrder = menuOrder;
	}

	@Override
	public String toString() {
		return "Menu [id=" + id + ", menuCode=" + menuCode + "]";
	}

	@Override
	public int compareTo(Menu o) {
		if(this.menuOrder == null){
			return -1;
		}
		
		if(o.menuOrder == null){
			return 1;
		}
		
		if(this.menuOrder.longValue() < o.menuOrder.longValue()){
			return -1;
		}
		
		if(this.menuOrder.longValue() > o.menuOrder.longValue()){
			return 1;
		}
		return 0;
	}

}