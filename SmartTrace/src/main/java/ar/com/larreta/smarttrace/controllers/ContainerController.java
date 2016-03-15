package ar.com.larreta.smarttrace.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import javax.faces.application.FacesMessage;

import org.hibernate.LazyInitializationException;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.springframework.webflow.execution.RequestContext;

import ar.com.larreta.commons.controllers.impl.StandardControllerImpl;
import ar.com.larreta.commons.exceptions.AppException;
import ar.com.larreta.commons.exceptions.NotServiceAssignedException;
import ar.com.larreta.smarttrace.domain.Container;
import ar.com.larreta.smarttrace.domain.MaterialType;
import ar.com.larreta.smarttrace.views.ContainerDataView;

/**
 * @author ignacio.m.larreta
 *
 */
public class ContainerController extends StandardControllerImpl{
	
	@Override
	public void initCreate(RequestContext flowRequestContext) {
		super.initCreate(flowRequestContext);
		setContainer(new Container());
		init();
	}
	
	@Override
	public void initUpdate(RequestContext flowRequestContext) {
		super.initUpdate(flowRequestContext);
		List<String> properties = new ArrayList<String>();
		properties.add("materialType");
		properties.add("parentContainer");
		List<String> projectedCollection = new ArrayList<String>();
		projectedCollection.add("childrenContainers");
		setContainer(((Container)service.getEntity(getDataView().getSelected(), properties, projectedCollection)));
		init();
	}

	/** 
	 * Metodo Inicializador de Variables 
	 * @return void
	 * 
	 * */
	private void init() {
		getDataViewContainer().setContainerSelected(getContainer());
		loadRoot();
	}
	
	/**
	 * Carga el Nodo principal
	 * @return void
	 * 
	 */
	public void loadRoot(){
		Container fatherContainer;
		
		fatherContainer = getContainer();
		
		if(fatherContainer.getDescription() == null){
			fatherContainer.setDescription("New container");
			fatherContainer.setCount(1L);
		}
		TreeNode node = new DefaultTreeNode(fatherContainer, null);
		getDataViewContainer().setRoot(node);
		getDataViewContainer().getRoot().setExpanded(true);
	}
	
	/** 
	 * Metodo encargado de cargar los datos del nodo seleccionado
	 * en la vista.
	 *  
	 * @return void
	 * 
	 * */
	public void loadContainerSelectedInTree(){
		if(getDataViewContainer().getNodeSelected().getChildren().isEmpty()){
			try{
				checkChildrens();
			}catch(LazyInitializationException e){
				reloadContainerSelected();
				checkChildrens();
			}
		}
	}
	
	/** 
	 * Metodo encargado de revisar si el contenedor seleccionado tiene hijos
	 *   
	 * @return void
	 * 
	 * */
	private void checkChildrens() throws LazyInitializationException{
		if((getDataViewContainer().getContainerSelected().getChildrenContainers() != null)
				&& (!getDataViewContainer().getContainerSelected().getChildrenContainers().isEmpty())){
			loadChildrenContainers();
		}
	}
	
	/** 
	 * En caso de lazyInitialization recarga el contenedor con los datos de la base
	 *  
	 * @return void
	 * 
	 * */
	private void reloadContainerSelected(){
		List<String> properties = new ArrayList<String>();
		properties.add("materialType");
		properties.add("parentContainer");
		
		List<String> collections = new ArrayList<String>();
		collections.add("childrenContainers");
		
		getDataViewContainer().setContainerSelected((Container)service.getEntity(getDataViewContainer().getContainerSelected(), properties, collections));
	}
	
	/** 
	 * En caso de lazyInitialization recarga el tipo de material y los contenedores hijos del contenedor padre 
	 * del container seleccionado
	 *  
	 * @return void
	 * 
	 * */
	private void reloadParentContainerOfTheContainerSelected(){
		List<String> properties = new ArrayList<String>();
		properties.add("materialType");
		List<String> collections = new ArrayList<String>();
		collections.add("childrenContainers");
		getDataViewContainer().getContainerSelected().setParentContainer((Container)service.
				getEntity(getDataViewContainer().getContainerSelected().getParentContainer(), properties, collections));
	}
	
	/** 
	 * Metodo encargado de cargar en la vista los hijos del contenedor seleccionado 
	 * @return void
	 * 
	 * */
	private void loadChildrenContainers() {
		for(Container childContainer : getDataViewContainer().getContainerSelected().getChildrenContainers()){
			if(childContainer != null){	
				TreeNode childrenContainer = new DefaultTreeNode(childContainer, getDataViewContainer().getNodeSelected());
				childrenContainer.setExpanded(true);
			}
		}
	}
	
	/** 
	 * Metodo encargado de borrar el nodo seleccionado y su contenido 
	 * @return void
	 * 
	 * */
	public void deleteSelected(){
		if(getDataViewContainer().getContainerSelect()) deleteContainer();
	}
	
	/** 
	 * Metodo encargado de borrar el nodo seleccionado 
	 * @return void
	 * 
	 * */
	private void deleteNode() {
		getDataViewContainer().getLog().info("Borrando nodo...");
		getDataViewContainer().getNodeSelected().getParent().getChildren().remove(getDataViewContainer().getNodeSelected());
	}

	/** 
	 * Metodo encargado de borrar el contenedor seleccionado 
	 * @return void
	 * 
	 * */
	private void deleteContainer() {
		getDataViewContainer().getLog().info("Borrando contenedor...");
		try{
			getDataViewContainer().getContainerSelected().getParentContainer().
			getChildrenContainers().remove(getDataViewContainer().getContainerSelected());
			deleteNode();
		}catch(LazyInitializationException e){
			getDataViewContainer().getLog().info("Lazy Exception tratando de borrar el contenedor... recargando... y reintentando...");
			reloadParentContainerOfTheContainerSelected();
			getDataViewContainer().getContainerSelected().getParentContainer().
			getChildrenContainers().remove(getDataViewContainer().getContainerSelected());
			deleteNode();
		}
	}

	/** 
	 * Metodo  
	 * @return Boolean
	 * 
	 * */
	public Boolean isNodeDeleted(Object node){
		return ((Container)node).getParentContainer() != null;
	}

	/** 
	 * Metodo encargado de revisar si el nodo esta seleccionado
	 * @return Boolean
	 * 
	 * */
	public Boolean isEqualsNodes(Object node){
		Container container = (Container) node;
		if((getDataViewContainer().getContainerSelect()) && (container != null) && (container.equals(getDataViewContainer().getContainerSelected()))){
			return false;
		}
		return true;
	}
	
	/** 
	 * Metodo encargado de cargar lo que se selecciono de la vista
	 * @return void
	 * 
	 * */
	public void loadSelect(NodeSelectEvent event){
		getDataView().getLog().info("loadSelect");
		getDataViewContainer().setNodeSelected(event.getTreeNode());
		getDataViewContainer().setContainerSelected((Container)this.getDataViewContainer().getNodeSelected().getData());
		try{
			getDataViewContainer().getContainerSelected().getMaterialType();
		}catch(LazyInitializationException e){
			getDataView().getLog().debug("Ocurrio un lazyInitializationError al "
					+ "tratar cargar el tipo de material voy a cargarlo desde la base...");
			reloadContainerSelected();
		}
		loadContainerSelectedInTree();
	}
	
	/** 
	 * Metodo encargado de cargar un contenedor nuevo en el contenedor
	 * seleccionado y el arbol.
	 * 
	 * @return void
	 * 
	 * */
	public void addContainer(){
		if(getDataViewContainer().getContainerSelected().getChildrenContainers() == null){
			getDataViewContainer().getContainerSelected().setChildrenContainers(new ArrayList<Container>());
		}
		Container newContainer = new Container();
		newContainer.setDescription("New Container");
		newContainer.setCount(1L);
		newContainer.setParentContainer(getDataViewContainer().getContainerSelected());
		newContainer.getId();
		getDataViewContainer().getContainerSelected().getChildrenContainers().add(newContainer);
		addNewContainerNode(getDataViewContainer().getNodeSelected(), newContainer);
	}
	
	/** 
	 * Metodo encargado de cargar el nodo nuevo
	 * @return void
	 * 
	 * */
	private void addNewContainerNode(TreeNode root, Container container) {
		TreeNode tree = new DefaultTreeNode(container, root);
		tree.setExpanded(true);
		tree.setParent(root);
		root.getChildren().add(tree);
	}

	/**
	 * Recupera el container principal seleccionado
	 * 
	 * @return Container
	 */
	public Container getContainer(){
		return (Container) getDataViewContainer().getSelected();
	}
	
	/**
	 * SetContainer
	 * 
	 * @return void
	 * @param container
	 */
	public void setContainer(Container container){
		getDataViewContainer().setSelected(container);
	}
	
	/**
	 * Recupera el dataView Container
	 * 
	 * @return the dataView
	 */
	public ContainerDataView getDataViewContainer() {
		return (ContainerDataView) super.getDataView();
	}
	
	/**
	 * @return the containers
	 */
	public List<Container> getContainers() {
		try {
			return (List<Container>) super.getService().load(Container.class);
		} catch (NotServiceAssignedException e) {
			getLog().error(AppException.getStackTrace(e));
		}
		return new ArrayList<Container>();
	}
	
	/**
	 * 
	 * @return the materials
	 */
	public List<MaterialType> getMaterialsType(){
		try {
			List<String> lazyCollection = new ArrayList<String>();
			lazyCollection.add("containers");
			return (List<MaterialType>) super.getService().load(MaterialType.class);
		} catch (NotServiceAssignedException e) {
			getLog().error(AppException.getStackTrace(e));
		}
		return new ArrayList<MaterialType>();
	}
}
