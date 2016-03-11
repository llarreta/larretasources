package ar.com.larreta.smarttrace.views;

import org.primefaces.model.TreeNode;

import ar.com.larreta.commons.controllers.Paginator;
import ar.com.larreta.commons.views.DataView;
import ar.com.larreta.smarttrace.controllers.ContainerPaginator;
import ar.com.larreta.smarttrace.domain.Container;

/**
 * @author ignacio.m.larreta
 *
 */
public class ContainerDataView extends DataView{
	
	/** TreeNode Principal */
	private TreeNode root;
	
	/** TreeNode Seleccionado */
	private TreeNode nodeSelected;
	
	/** Container Seleccionado */
	private Container containerSelected;

	/**
	 * Constructor
	 */
	public ContainerDataView() {
		paginator = newPaginator();
		paginator.setDataView(this);
	}
	
	/**
	 * Seteamos el paginador contenedor
	 */
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
	 * @return the containerSelect
	 */
	public Boolean getContainerSelect() {
		return containerSelected != null;
	}

	/**
	 * @return the fatherContainerSelect
	 */
	public Boolean getFatherContainerSelect() {
		return (containerSelected != null) && (containerSelected.getParentContainer() == null);
	}

}