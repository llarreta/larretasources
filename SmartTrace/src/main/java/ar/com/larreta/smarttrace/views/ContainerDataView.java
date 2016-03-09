package ar.com.larreta.smarttrace.views;

import java.util.List;

import org.primefaces.model.TreeNode;

import ar.com.larreta.commons.controllers.Paginator;
import ar.com.larreta.commons.views.DataView;
import ar.com.larreta.smarttrace.controllers.ContainerPaginator;
import ar.com.larreta.smarttrace.controllers.MaterialTypePaginator;
import ar.com.larreta.smarttrace.domain.Container;
import ar.com.larreta.smarttrace.domain.MaterialType;

/**
 * @author ignacio.m.larreta
 *
 */
public class ContainerDataView extends DataView{
	
	private TreeNode root;
	private TreeNode nodeSelected;
	private Container containerSelected;
	private Boolean containContainer;
	private Boolean containerSelect;
	private Boolean materialSelect;
	private Boolean fatherContainerSelect;
	private MaterialType materialSelected;
	private List<Container> containersToDelete;

	public ContainerDataView() {
		paginator = newPaginator();
		paginator.setDataView(this);
	}
	
	@Override
	protected Paginator newPaginator() {
		return new ContainerPaginator();
	}
	
	/**
	 * Nodo principal del arbol
	 * 
	 * @return the root
	 */
	public TreeNode getRoot() {
		return root;
	}

	/**
	 * @param root the root to set
	 */
	public void setRoot(TreeNode root) {
		this.root = root;
	}

	/**
	 * Nodo del arbol seleccionado
	 * 
	 * @return the nodeSelected
	 */
	public TreeNode getNodeSelected() {
		return nodeSelected;
	}

	/**
	 * @param nodeSelected the nodeSelected to set
	 */
	public void setNodeSelected(TreeNode nodeSelected) {
		this.nodeSelected = nodeSelected;
	}

	/**
	 * Container actualmente seleccionado
	 * 
	 * @return the containerSelected
	 */
	public Container getContainerSelected() {
		return containerSelected;
	}

	/**
	 * @param containerSelected the containerSelected to set
	 */
	public void setContainerSelected(Container containerSelected) {
		this.containerSelected = containerSelected;
	}

	/**
	 * Boolean para controlar la carga de hijos de un container en la vista
	 * 
	 * @return the containContainer
	 */
	public Boolean getContainContainer() {
		return containContainer;
	}

	/**
	 * @param containContainer the containContainer to set
	 */
	public void setContainContainer(Boolean containContainer) {
		this.containContainer = containContainer;
	}

	/**
	 * Material seleccionado en el caso de que no tenga un container
	 * @return the materialSelected
	 */
	public MaterialType getMaterialSelected() {
		return materialSelected;
	}

	/**
	 * @param materialSelected the materialSelected to set
	 */
	public void setMaterialSelected(MaterialType materialSelected) {
		this.materialSelected = materialSelected;
	}

	/**
	 * @return the containerSelect
	 */
	public Boolean getContainerSelect() {
		return containerSelect;
	}

	/**
	 * @param containerSelect the containerSelect to set
	 */
	public void setContainerSelect(Boolean containerSelect) {
		this.containerSelect = containerSelect;
	}

	/**
	 * @return the materialSelect
	 */
	public Boolean getMaterialSelect() {
		return materialSelect;
	}

	/**
	 * @param materialSelect the materialSelect to set
	 */
	public void setMaterialSelect(Boolean materialSelect) {
		this.materialSelect = materialSelect;
	}

	/**
	 * @return the fatherContainerSelect
	 */
	public Boolean getFatherContainerSelect() {
		if(super.selected != null && this.containerSelected != null){	
			if(((Container)super.selected).equals(this.containerSelected)){
				this.fatherContainerSelect = true;
			}else{
				this.fatherContainerSelect = false;
			}
		}
		return fatherContainerSelect;
	}

	/**
	 * @param fatherContainerSelect the fatherContainerSelect to set
	 */
	public void setFatherContainerSelect(Boolean fatherContainerSelect) {
		this.fatherContainerSelect = fatherContainerSelect;
	}

	public List<Container> getContainersToDelete() {
		return containersToDelete;
	}

	public void setContainersToDelete(List<Container> containersToDelete) {
		this.containersToDelete = containersToDelete;
	}
}