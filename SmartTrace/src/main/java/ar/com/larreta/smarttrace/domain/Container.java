package ar.com.larreta.smarttrace.domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

/**
 * Representa un contenedor de materiales
 * Podria llegar a ser cualquiera de las diferentes formas de agrupacion de materiales
 * Ejm lote, container, pallet, etc
 */
@Entity
@Table(name = "container")
@Inheritance(strategy = InheritanceType.JOINED)
@Where(clause="deleted IS NULL")
@SQLDelete (sql="UPDATE Container SET deleted=CURRENT_TIMESTAMP WHERE id=?")
@XmlRootElement
public class Container extends ar.com.larreta.commons.domain.Entity {

	/**
	 * Codigo identificador del contenedor
	 */
	private String code;

	/**
	 * Descripcion del contenedor
	 */
	private String description;
	
	/**
	 * Material contenido
	 */
	private MaterialType materialType;
	
	/**
	 * Cantidad del material contenido
	 */
	private Long count;
	
	/**
	 * Fecha de elaboracion
	 */
	private Date elaboration;
	
	/**
	 * Fecha de expiracion
	 */
	private Date expiration;
	
	/**
	 * Contenedor padre, donde podria estar incluido el actual
	 */
	private Container parentContainer;
	
	/**
	 * Contenedores hijos, o contenedores que contiene este mismo
	 */
	private Collection<Container> childrenContainers;
	
	/**
	 * Pasos en los que este container fue utilizado como origen
	 */
	private Collection<Step> sourceSteps;
	
	/**
	 * Pasos en los que este container fue utilizado como destino
	 */
	private Collection<Step> targetSteps;
	
	@ManyToMany (fetch=FetchType.LAZY, targetEntity=Step.class)
	@JoinTable(name = "source", joinColumns = { @JoinColumn(name = "idContainerSource") }, 
		inverseJoinColumns = { @JoinColumn(name = "idStep") })
	public Collection<Step> getSourceSteps() {
		return sourceSteps;
	}

	public void setSourceSteps(Collection<Step> sourceSteps) {
		this.sourceSteps = sourceSteps;
	}

	@ManyToMany (fetch=FetchType.LAZY, targetEntity=Step.class)
	@JoinTable(name = "target", joinColumns = { @JoinColumn(name = "idContainerTarget") }, 
		inverseJoinColumns = { @JoinColumn(name = "idStep") })
	public Collection<Step> getTargetSteps() {
		return targetSteps;
	}

	public void setTargetSteps(Collection<Step> targetSteps) {
		this.targetSteps = targetSteps;
	}

	@Basic
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Basic
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@ManyToOne (fetch=FetchType.LAZY, targetEntity=MaterialType.class)
	@JoinColumn (name="idMaterialType")
	public MaterialType getMaterialType() {
		return materialType;
	}

	public void setMaterialType(MaterialType materialType) {
		this.materialType = materialType;
	}

	@Basic
	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	@Basic
	public Date getElaboration() {
		return elaboration;
	}

	public void setElaboration(Date elaboration) {
		this.elaboration = elaboration;
	}

	@Basic
	public Date getExpiration() {
		return expiration;
	}

	public void setExpiration(Date expiration) {
		this.expiration = expiration;
	}

	@ManyToOne (fetch=FetchType.LAZY, targetEntity=Container.class)
	@JoinColumn (name="idParent")
	public Container getParentContainer() {
		return parentContainer;
	}

	public void setParentContainer(Container parentContainer) {
		this.parentContainer = parentContainer;
	}

	@OneToMany (mappedBy="parentContainer", fetch=FetchType.LAZY, cascade=CascadeType.ALL, targetEntity=Container.class)
	@Where(clause="deleted IS NULL")
	public Collection<Container> getChildrenContainers() {
		return childrenContainers;
	}

	public void setChildrenContainers(Collection<Container> childrenContainers) {
		this.childrenContainers = childrenContainers;
	}

}
