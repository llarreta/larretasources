package ar.com.larreta.screens;

import java.util.Collection;
import java.util.Iterator;

import javax.persistence.Basic;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

import ar.com.larreta.commons.controllers.Paginator;

@Entity
@javax.persistence.Table(name = "tabletag")
@DiscriminatorValue(value = "table")
@PrimaryKeyJoinColumn(name=ar.com.larreta.commons.domain.Entity.ID)
public class Table extends StandardContainer {

	private String value = ScreenUtils.generateExpression("dataView.paginator");
	private Boolean paginator = Boolean.TRUE;
	private Boolean lazy = Boolean.TRUE;
	private String rows = "10";
	private String paginatorTemplate = "{RowsPerPageDropdown} {FirstPageLink} {PreviousPageLink} {CurrentPageReport} {NextPageLink} {LastPageLink}";
	private String rowsPerPageTemplate = "5,10,15";
	private String selectionMode = "single";
	private String selection = ScreenUtils.generateExpression("dataView.selected");
	private String emptyMessage = ScreenUtils.generateExpression("msg['datatable.sindatos']");
	private String currentPageReportTemplate = "{currentPage} #{msg['datatable.de']} {totalPages}";
	private String lazyProperties;

	public ScreenElement findInColumn(Integer column, Long id){
		Integer index = 0;
		Collection<ScreenElement> elements = getOrdererElements();
		if (elements!=null){
			Iterator<ScreenElement> it = elements.iterator();
			while (it.hasNext()) {
				ScreenElement screenElement = (ScreenElement) it.next();
				if (screenElement instanceof Column) {
					Column columnFinded = (Column) screenElement;
					if (index==column){
						return columnFinded.getSearchMap().recursiveFind(id);	
					}
					index++;
				}
				
			}
		}
		return null;
	}
	
	public Collection<ScreenElement> findInColumn(Integer column, Class type){
		Integer index = 0;
		Collection<ScreenElement> elements = getOrdererElements();
		if (elements!=null){
			Iterator<ScreenElement> it = elements.iterator();
			while (it.hasNext()) {
				ScreenElement screenElement = (ScreenElement) it.next();
				if (screenElement instanceof Column) {
					Column columnFinded = (Column) screenElement;
					if (index==column){
						return columnFinded.getSearchMap().recursiveFind(type);	
					}
					index++;
				}
				
			}
		}
		return null;
	}
	
	@Basic
	public String getLazyProperties() {
		return lazyProperties;
	}

	public void setLazyProperties(String lazyProperties) {
		this.lazyProperties = lazyProperties;
	}

	@Override
	/**
	 * Este metodo se sobrescribe para que no tenga funcionalidad
	 * utilizar addColumn
	 */
	public void add(Integer orderIndex, ScreenElement element) {
		super.add(orderIndex, (Column)element);
	}

	@Override
	/**
	 * Este metodo se sobrescribe para que no tenga funcionalidad
	 * utilizar addColumn
	 */
	public void add(ScreenElement element) {
		super.add((Column)element);
	}
	
	public void addColumn(Integer orderIndex, Column element) {
		super.add(orderIndex, element);
	}

	public void addColumn(Column element) {
		super.add(element);
	}

	@Transient
	public Object getValueEvaluated(){
		Object valueEvaluated = ScreenUtils.evaluate(getValue());
		if (!StringUtils.isEmpty(lazyProperties) && (valueEvaluated instanceof Paginator)){
			((Paginator)valueEvaluated).setLazyProperties(ScreenUtils.split(lazyProperties));
		}
		return valueEvaluated;
	}
	
	@Basic
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Basic
	public Boolean getPaginator() {
		return paginator;
	}

	public void setPaginator(Boolean paginator) {
		this.paginator = paginator;
	}

	@Basic
	public Boolean getLazy() {
		return lazy;
	}

	public void setLazy(Boolean lazy) {
		this.lazy = lazy;
	}

	@Basic
	public String getRows() {
		return rows;
	}

	public void setRows(String rows) {
		this.rows = rows;
	}

	@Basic
	public String getPaginatorTemplate() {
		return paginatorTemplate;
	}

	public void setPaginatorTemplate(String paginatorTemplate) {
		this.paginatorTemplate = paginatorTemplate;
	}

	@Basic
	public String getRowsPerPageTemplate() {
		return rowsPerPageTemplate;
	}

	public void setRowsPerPageTemplate(String rowsPerPageTemplate) {
		this.rowsPerPageTemplate = rowsPerPageTemplate;
	}

	@Basic
	public String getSelectionMode() {
		return selectionMode;
	}

	public void setSelectionMode(String selectionMode) {
		this.selectionMode = selectionMode;
	}

	@Transient
	public Object getSelectionEvaluated(){
		return ScreenUtils.evaluate(getSelection());
	}
	
	@Basic
	public String getSelection() {
		return selection;
	}

	public void setSelection(String selection) {
		this.selection = selection;
	}

	@Basic
	public String getEmptyMessage() {
		return emptyMessage;
	}

	public void setEmptyMessage(String emptyMessage) {
		this.emptyMessage = emptyMessage;
	}

	@Transient
	public String getCurrentPageReportTemplateEvaluated() {
		return (String) ScreenUtils.evaluate(getCurrentPageReportTemplate());
	}
	
	@Basic
	public String getCurrentPageReportTemplate() {
		return currentPageReportTemplate;
	}

	public void setCurrentPageReportTemplate(String currentPageReportTemplate) {
		this.currentPageReportTemplate = currentPageReportTemplate;
	}

	
}
