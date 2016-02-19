package ar.com.larreta.smarttrace.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import javax.faces.application.FacesMessage;

import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.springframework.webflow.execution.RequestContext;

import ar.com.larreta.commons.controllers.impl.StandardControllerImpl;
import ar.com.larreta.commons.exceptions.AppException;
import ar.com.larreta.commons.exceptions.NotServiceAssignedException;
import ar.com.larreta.smarttrace.domain.Classification;
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
		getDataViewContainer().setContainContainer(true);
		getDataViewContainer().setContainerSelected(this.getContainer());
		getDataViewContainer().setContainerSelect(true);
		getDataViewContainer().setMaterialSelect(false);
		getDataViewContainer().setContainers(new ArrayList<Container>());
		getDataViewContainer().setFatherContainerSelect(true);
		loadContainers();
		loadRoot();
	}
	
	@Override
	public void initUpdate(RequestContext flowRequestContext) {
		super.initUpdate(flowRequestContext);
		List<String> properties = new ArrayList<String>();
		properties.add("materialType");
		properties.add("parentContainer");
		setContainer(((Container)service.getEntity(getDataView().getSelected(), properties)));
	}
	
	private void loadContainers() {
		try {
			getDataViewContainer().getContainers().addAll((Collection<Container>)super.getService().load(Container.class));
		} catch (Exception e) {
			getLog().error(AppException.getStackTrace(e));
		}
	}
	
	public void loadRoot(){
		
		Container fatherContainer;
		
		fatherContainer = getContainer();
		
		if(fatherContainer.getDescription() == null){
			fatherContainer.setDescription("new container");
			fatherContainer.setCount(1L);
		}
		getDataViewContainer().setRoot(new DefaultTreeNode(fatherContainer, null));
		getDataViewContainer().getRoot().setExpanded(true);
		if((fatherContainer.getChildrenContainers() != null) && (!fatherContainer.getChildrenContainers().isEmpty())){
			loadChildrenContainers(getDataViewContainer().getRoot(), fatherContainer);
		}
		if(fatherContainer.getMaterialType() != null){
			loadMaterialContainer(getDataViewContainer().getRoot(), fatherContainer);
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
		this.loadRoot();
	}

	private void deleteMaterial() {
		if(getDataViewContainer().getNodeSelected() != null){	
			if(getContainer().getMaterialType()!=null){
				if(getContainer().getMaterialType().equals(getDataViewContainer().getMaterialSelected())){
					getContainer().setMaterialType(null);
					getDataViewContainer().getLog().info("El usuario elimino el elemento del contenedor padre.");
				}else{
					deleteMaterialInChildrenContainer(getContainer().getChildrenContainers());
				}
			}else{
				deleteMaterialInChildrenContainer(getContainer().getChildrenContainers());
			}
		}else{
			addMessage("global-messages-error", getMessage("containersUpdate.materialIsNotSelectedErrorTitle"), 
					getMessage("containersUpdate.materialIsNotSelectedErrorSumary"), FacesMessage.SEVERITY_ERROR);
			getDataViewContainer().getLog().error("El usuario intento eliminar un material pero no lo selecciono primero.");
		}
	}

	/**
	 * @param collection 
	 * 
	 */
	private void deleteMaterialInChildrenContainer(Collection<Container> containers) {
		for(Container container : containers){
			if(container.equals((Container)getDataViewContainer().getNodeSelected().getParent().getData())){
				if((container.getMaterialType() != null) && (container.getMaterialType().equals(getDataViewContainer().getMaterialSelected()))){
					container.setMaterialType(null);
					getDataViewContainer().getLog().info("El usuario elimino el elemento de un container hijo seleccionado correctamente.");
				}
			}
			if((container.getChildrenContainers() != null) && (!container.getChildrenContainers().isEmpty())){
				deleteMaterialInChildrenContainer(container.getChildrenContainers());
			}
		}
	}

	private void deleteContainer() {
		getDataViewContainer().getLog().info("Borrando contenedor...");
		if((getContainer().getChildrenContainers()!=null) && (!getContainer().getChildrenContainers().isEmpty())){
			getDataViewContainer().getLog().info("Id de contenedor a borrar: " + getDataViewContainer().getContainerSelected().getId());
			if(getContainer().getChildrenContainers().contains(getDataViewContainer().getContainerSelected())){
				getContainer().getChildrenContainers().remove(getDataViewContainer().getContainerSelected());
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
				}else{
					deleteContainerInChildrenContainer(children.getChildrenContainers());
				}
			}
		}
	}

	private void loadMaterialContainer(TreeNode root, Container fatherContainer) {
		TreeNode material = new DefaultTreeNode(fatherContainer.getMaterialType(), root);
		material.setExpanded(true);
		root.getChildren().add(material);
	}

	private void loadChildrenContainers(TreeNode fatherTreeNode, Container fatherContainer) {
		for(Container childContainer : fatherContainer.getChildrenContainers()){
			TreeNode childrenContainer = new DefaultTreeNode(childContainer, fatherTreeNode);
			childrenContainer.setExpanded(true);
			if((childContainer.getChildrenContainers() != null) && (!childContainer.getChildrenContainers().isEmpty())){
				loadChildrenContainers(childrenContainer, childContainer);
			}
			if(childContainer.getMaterialType() != null){
				this.loadMaterialContainer(childrenContainer, childContainer);
			}
		}
		
	}
	
	public Boolean isNodeDeleted(Object node){
		try{
			Container container = (Container) node;
			if(container.getParentContainer() != null)return true;
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
		}catch(ClassCastException castError){
			castMaterial();
		}
	}

	private void castMaterial() {
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
			}else{
				Container newContainer = new Container();
				newContainer.setDescription("New Container");
				newContainer.setCount(1L);
				newContainer.setParentContainer(getDataViewContainer().getContainerSelected());
				newContainer.getId();
				getDataViewContainer().getContainerSelected().setChildrenContainers(new ArrayList<Container>());
				getDataViewContainer().getContainerSelected().getChildrenContainers().add(newContainer);
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
			}
		}
		loadRoot();
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
			loadRoot();
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
