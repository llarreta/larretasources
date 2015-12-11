package ar.com.larreta.smarttrace.domain;

import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.primefaces.model.TreeNode;

/**
 * Representa un material a utilizarse por el sistema 
 * ya sea este materia prima, material intermedio generado por algun proceso, un producto final a comercializar, etc
 */
@Entity
@Table(name = "material")
@Inheritance(strategy = InheritanceType.JOINED)
@Where(clause="deleted IS NULL")
@SQLDelete (sql="UPDATE Material SET deleted=CURRENT_TIMESTAMP WHERE id=?")
@XmlRootElement
public class Material extends ar.com.larreta.commons.domain.Entity {
	
	/**
	 * Detalle del material
	 */
	private String description;
	
	/**
	 * Unidad de cuantificacion
	 */
	private UnitType unitType;
	
	/**
	 * Cantidad referida a la unidad de cuantificacion
	 */
	private Long count;
	
	/**
	 * Proveedor del material
	 */
	private Provider provider;
	
	/**
	 * Tipo del material
	 */
	private MaterialType materialType;

	/**
	 * Representa el elemento actual como nodo de un arbol
	 */
	private TreeNode node;
	
	/**
	 * Containers en los que fue incluido este material
	 */
	private Set<Container> containers;

	@ManyToOne (fetch=FetchType.LAZY, targetEntity=UnitType.class)
	@JoinColumn (name="idMaterialType")
	public MaterialType getMaterialType() {
		return materialType;
	}

	public void setMaterialType(MaterialType materialType) {
		this.materialType = materialType;
	}
	
	@OneToMany (mappedBy="material", fetch=FetchType.LAZY, cascade=CascadeType.ALL, targetEntity=Container.class)
	@Where(clause="deleted IS NULL")
	public Set<Container> getContainers() {
		return containers;
	}

	public void setContainers(Set<Container> containers) {
		this.containers = containers;
	}

	@Basic
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@ManyToOne (fetch=FetchType.LAZY, targetEntity=UnitType.class)
	@JoinColumn (name="idUnitType")
	public UnitType getUnitType() {
		return unitType;
	}

	public void setUnitType(UnitType unitType) {
		this.unitType = unitType;
	}

	@Basic
	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	@ManyToOne (fetch=FetchType.LAZY, targetEntity=Provider.class)
	@JoinColumn (name="idProvider")
	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}

//	@OneToMany (mappedBy="auditableEntity", fetch=FetchType.LAZY, cascade=CascadeType.ALL, targetEntity=MaterialChangeHistory.class)
//	public Set<EntityChangeHistory> getChangesHistory() {
//		return changesHistory;
//	}
//
//	@Override
//	public EntityChangeHistory newEntityChangeHistoryInstance() {
//		return new MaterialChangeHistory();
//	}
//	
//	@Override
//	public int hashCode() {
//		HashCodeBuilder hcb = new HashCodeBuilder();
//		hcb.append(id);
//		hcb.append(description);
//		hcb.append(unitType);
//		hcb.append(count);
//		hcb.append(provider);
//		hcb.append(materialType);
//		return hcb.toHashCode();
//	}
//
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj) {
//			return true;
//		}
//		if (!(obj instanceof Material)) {
//			return false;
//		}
//		Material that = (Material) obj;
//		EqualsBuilder eb = new EqualsBuilder();
//		eb.append(id, that.getId());
//		eb.append(description, that.getDescription());
//		eb.append(count, that.getCount());
//		eb.append(unitType, that.getUnitType());
//		eb.append(provider, that.getProvider());
//		eb.append(materialType, that.getMaterialType());
//		return eb.isEquals();
//	}
//	
	
	
}
