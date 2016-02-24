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

	private void init() {
		getDataViewContainer().setContainContainer(true);
		getDataViewContainer().setContainerSelected(this.getContainer());
		getDataViewContainer().setContainerSelect(true);
		getDataViewContainer().setMaterialSelect(false);
		getDataViewContainer().setFatherContainerSelect(true);
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
			fatherContainer.setDescription("new container");
			fatherContainer.setCount(1L);
		}
		TreeNode node = new DefaultTreeNode(fatherContainer, null);
		getDataViewContainer().setRoot(node);
		getDataViewContainer().getRoot().setExpanded(true);
	}
	
	public void loadContainerSelectedInTree(){
		if(getDataViewContainer().getNodeSelected().getChildren().isEmpty()){
			try{
				checkChildrensAndMaterialType();
			}catch(LazyInitializationException e){
				reloadContainerSelected();
				checkChildrensAndMaterialType();
			}
		}
	}
	
	private void checkChildrensAndMaterialType() throws LazyInitializationException{
		if((getDataViewContainer().getContainerSelected().getChildrenContainers() != null)
				&& (!getDataViewContainer().getContainerSelected().getChildrenContainers().isEmpty())){
			loadChildrenContainers();
		}
		if(getDataViewContainer().getContainerSelected().getMaterialType() != null){
			loadMaterialContainer();
		}
	}
	
	private void reloadContainerSelected(){
		List<String> properties = new ArrayList<String>();
		properties.add("materialType");
		properties.add("parentContainer");
		
		List<String> collections = new ArrayList<String>();
		collections.add("childrenContainers");
		
		getDataViewContainer().setContainerSelected((Container)service.getEntity(getDataViewContainer().getContainerSelected(), properties, collections));
	}
	
	private void loadMaterialContainer() {
		TreeNode material = new DefaultTreeNode(getDataViewContainer().getContainerSelected().getMaterialType(), getDataViewContainer().getNodeSelected());
		material.setExpanded(true);	
	}

	private void loadChildrenContainers() {
		for(Container childContainer : getDataViewContainer().getContainerSelected().getChildrenContainers()){
			TreeNode childrenContainer = new DefaultTreeNode(childContainer, getDataViewContainer().getNodeSelected());
			childrenContainer.setExpanded(true);
		}
	}
	
	public void deleteSelected(){
		if(getDataViewContainer().getContainerSelected() != null){
			deleteContainer();
		}else{
			if(getDataViewContainer().getMaterialSelected() != null){
				deleteMaterial();
			}
		}
	}

	private void deleteMaterial() {
		getDataViewContainer().getLog().info("Borrando Material...");
		if(getDataViewContainer().getNodeSelected() != null){	
			getDataViewContainer().getLog().info("Tiene seleccionado un nodo");
			((Container) getDataViewContainer().getNodeSelected().getParent().getData()).setMaterialType(null);
			deleteNode();
		}else{
			addMessage("global-messages-error", getMessage("containersUpdate.materialIsNotSelectedErrorTitle"), 
					getMessage("containersUpdate.materialIsNotSelectedErrorSumary"), FacesMessage.SEVERITY_ERROR);
			getDataViewContainer().getLog().error("El usuario intento eliminar un material pero no lo selecciono primero.");
		}
	}

	private void deleteNode() {
		if((getDataViewContainer().getRoot().getChildren() != null)){	
			if(getDataViewContainer().getRoot().getChildren().contains(getDataViewContainer().getNodeSelected())){
				getDataViewContainer().getRoot().getChildren().remove(getDataViewContainer().getNodeSelected());
			}else{
				deleteNodeChildren(getDataViewContainer().getRoot().getChildren());
			}
		}
	}

	private void deleteNodeChildren(List<TreeNode> children) {
		for(TreeNode node : children){
			if((node.getChildren() != null) && (node.getChildren().contains(getDataViewContainer().getNodeSelected()))){
				node.getChildren().remove(getDataViewContainer().getNodeSelected());
			}else{
				deleteNodeChildren(node.getChildren());
			}
		}
	}

	private void deleteContainer() {
		getDataViewContainer().getLog().info("Borrando contenedor...");
		if((getContainer().getChildrenContainers()!=null) && (!getContainer().getChildrenContainers().isEmpty())){
			getDataViewContainer().getLog().info("Id de contenedor a borrar: " + getDataViewContainer().getContainerSelected().getId());
			if(getContainer().getChildrenContainers().contains(getDataViewContainer().getContainerSelected())){
				getContainer().getChildrenContainers().remove(getDataViewContainer().getContainerSelected());
				deleteNode();
			}else{
				deleteContainerInChildrenContainer(getContainer().getChildrenContainers());
			}
		}
	}

	private void deleteContainerInChildrenContainer(
			Collection<Container> childrenContainers) {
		getDataViewContainer().getLog().info("No se encontro el contenedor... seguimos buscando...");	
		for(Container children : childrenContainers){
			if((children.getChildrenContainers()!=null) && (!children.getChildrenContainers().isEmpty())){
				if(children.getChildrenContainers().contains(getDataViewContainer().getContainerSelected())){
					children.getChildrenContainers().remove(getDataViewContainer().getContainerSelected());
					deleteNode();
				}else{
					deleteContainerInChildrenContainer(children.getChildrenContainers());
				}
			}
		}
	}

	
	public Boolean isNodeDeleted(Object node){
		try{
			Container container = (Container) node;
			getDataViewContainer().getLog().info("IsNodeDeleted");
			if((container != null) && (container.getParentContainer() != null)){
				getDataViewContainer().getLog().info("Container " + container.getDescription());
				getDataViewContainer().getLog().info("Parent Container " + container.getParentContainer());
				return true;
			}
			return false;
		}catch(ClassCastException e){
		  	return	tryCastMaterial(node);
		}
	}
	
	public Boolean tryCastMaterial(Object node){
		try{
			MaterialType material = (MaterialType) node;
			return true;
		}catch(ClassCastException e){
			return false;
		}
	}
	
	public Boolean tryCastContainer(Object node){
		try{
			Container container = (Container) node;
			return true;
		}catch(ClassCastException e){
			return false;
		}
	}
	
	public Boolean isNodeContainer(Object node){
		try{
			Container container = (Container) node;
			return true;
		}catch(ClassCastException e){
			return false;
		}
	}
	
	public Boolean isEqualsNodes(Object node){
		if(tryCastMaterial(node)){
			MaterialType materialType = (MaterialType) node;
			if((getDataViewContainer().getMaterialSelected() != null) && (materialType != null) && (materialType.equals(getDataViewContainer().getMaterialSelected()))){
				return false;
			}
		}
		if(tryCastContainer(node)){
			Container container = (Container) node;
			if((getDataViewContainer().getContainerSelected() != null) && (container != null) && (container.equals(getDataViewContainer().getContainerSelected()))){
				return false;
			}
		}
		return true;
	}
	
	public void loadSelect(NodeSelectEvent event){
		try{
			getDataViewContainer().setNodeSelected(event.getTreeNode());
			getDataViewContainer().setContainerSelected((Container)this.getDataViewContainer().getNodeSelected().getData());
			getDataViewContainer().setMaterialSelected(null);
			getDataViewContainer().setMaterialSelect(false);
			getDataViewContainer().setContainerSelect(true);
			getDataViewContainer().setFatherContainerSelect(getDataViewContainer().getNodeSelected().getParent() == null);
			loadContainerSelectedInTree();
		}catch(ClassCastException castError){
			castMaterial(event);
		}
	}

	private void castMaterial(NodeSelectEvent event) {
		getDataViewContainer().setNodeSelected(event.getTreeNode());
		getDataViewContainer().setMaterialSelected((MaterialType)this.getDataViewContainer().getNodeSelected().getData());
		getDataViewContainer().setContainerSelected(null);
		getDataViewContainer().setContainerSelect(false);
		getDataViewContainer().setMaterialSelect(true);
		getDataViewContainer().setFatherContainerSelect(false);
	}
	
	public void addContainer(){
		getDataViewContainer().setContainContainer(true);
		addNewContainerOrMaterial();
	}
	
	public void addMaterial(){
		getDataViewContainer().setContainContainer(false);
		addNewContainerOrMaterial();
	}
	
	public void addNewContainerOrMaterial(){
		if(getDataViewContainer().getContainContainer()){
			if(getDataViewContainer().getContainerSelected().getChildrenContainers() != null){
				Container newContainer = new Container();
				newContainer.setDescription("New Container");
				newContainer.setCount(1L);
				newContainer.setParentContainer(getDataViewContainer().getContainerSelected());
				newContainer.getId();
				getDataViewContainer().getContainerSelected().getChildrenContainers().add(newContainer);
				addNewContainerNode(newContainer);
			}else{
				Container newContainer = new Container();
				newContainer.setDescription("New Container");
				newContainer.setCount(1L);
				newContainer.setParentContainer(getDataViewContainer().getContainerSelected());
				newContainer.getId();
				getDataViewContainer().getContainerSelected().setChildrenContainers(new ArrayList<Container>());
				getDataViewContainer().getContainerSelected().getChildrenContainers().add(newContainer);
				addNewContainerNode(newContainer);
			}
		}else{
			if(getDataViewContainer().getContainerSelected().getMaterialType() == null){
				MaterialType newMaterialType = new MaterialType();
				newMaterialType.setDescription("New Material");
				newMaterialType.setCount(1L);
				newMaterialType.setContainers(new HashSet<Container>());
				newMaterialType.getId();
				newMaterialType.getContainers().add(getDataViewContainer().getContainerSelected());
				getDataViewContainer().getContainerSelected().setMaterialType(newMaterialType);
				addNewMaterialNode();
			}
		}
		
	}
	
	
	private void addNewMaterialNode() {
		TreeNode tree = new DefaultTreeNode(getDataViewContainer().getContainerSelected().getMaterialType(), getDataViewContainer().getNodeSelected());
		tree.setExpanded(true);
		tree.setParent(getDataViewContainer().getNodeSelected());
		getDataViewContainer().getNodeSelected().getChildren().add(tree);
	}

	private void addNewContainerNode(Container newContainer) {
		checkChildrenNode(getDataViewContainer().getNodeSelected(), newContainer);
	}
	
	private void checkChildrenNode(TreeNode root, Container container) {
		TreeNode tree = new DefaultTreeNode(container, root);
		tree.setExpanded(true);
		tree.setParent(root);
		root.getChildren().add(tree);
	}

	/**
	 * Recupera el container padre seleccionado
	 * 
	 * @return Container
	 */
	public Container getContainer(){
		return (Container) getDataViewContainer().getSelected();
	}
	
	public void setContainer(Container container){
		getDataViewContainer().setSelected(container);
	}
	
	/**
	 * @return the dataView
	 */
	public ContainerDataView getDataViewContainer() {
		return (ContainerDataView) super.getDataView();
	}
	
	public void changeMaterialOfContainer(){
		if(getDataViewContainer().getMaterialSelected() != null){	
			((Container)getDataViewContainer().getNodeSelected().getParent().getData()).setMaterialType(getDataViewContainer().getMaterialSelected());
			for(int i = 0; i < getDataViewContainer().getNodeSelected().getParent().getChildren().size(); i++){
				if(getDataViewContainer().getNodeSelected().getParent().getChildren().get(i).equals(getDataViewContainer().getNodeSelected())){
					TreeNode tree = new DefaultTreeNode(getDataViewContainer().getMaterialSelected(), getDataViewContainer().getNodeSelected().getParent());
					getDataViewContainer().getNodeSelected().getParent().getChildren().set(i, tree);
					getDataViewContainer().setNodeSelected(getDataViewContainer().getNodeSelected().getParent().getChildren().get(i)); 
				}
			}
		}
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
	
	public List<MaterialType> getMaterialsType(){
		try {
			return (List<MaterialType>) super.getService().load(MaterialType.class);
		} catch (NotServiceAssignedException e) {
			getLog().error(AppException.getStackTrace(e));
		}
		return new ArrayList<MaterialType>();
	}
}
