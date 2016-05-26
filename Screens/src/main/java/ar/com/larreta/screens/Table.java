package ar.com.larreta.screens;

import javax.faces.context.FacesContext;
import javax.persistence.Basic;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Transient;

@Entity
@javax.persistence.Table(name = "tabletag")
@DiscriminatorValue(value = "table")
@PrimaryKeyJoinColumn(name=ar.com.larreta.commons.domain.Entity.ID)
public class Table extends Container {

	private String var = "actualItem";
	private String value = "dataView.paginator";	
	//private String value = "#{dataView.paginator}";
	private Boolean paginator = Boolean.TRUE;
	private Boolean lazy = Boolean.TRUE;
	private String rows = "10";
	private String paginatorTemplate = "{RowsPerPageDropdown} {FirstPageLink} {PreviousPageLink} {CurrentPageReport} {NextPageLink} {LastPageLink}";
	private String rowsPerPageTemplate = "5,10,15";
	private String selectionMode = "single";
	private String selection = "dataView.selected";
	//private String selection = "#{dataView.selected}";
	private String emptyMessage = "datatable.sindatos";
	//private String emptyMessage = "#{msg['datatable.sindatos']}";
	private String currentPageReportTemplate = "{currentPage} 'datatable.de' {totalPages}";
	//private String currentPageReportTemplate = "{currentPage} #{msg['datatable.de']} {totalPages}";

	
	@Override
	/**
	 * Este metodo se sobrescribe para que no tenga funcionalidad
	 * utilizar addColumn
	 */
	public void add(Integer orderIndex, ScreenElement element) {
	}

	@Override
	/**
	 * Este metodo se sobrescribe para que no tenga funcionalidad
	 * utilizar addColumn
	 */
	public void add(ScreenElement element) {
	}
	
	public void addColumn(Integer orderIndex, Column element) {
		super.add(orderIndex, element);
	}

	public void addColumn(Column element) {
		super.add(element);
	}

	@Basic
	public String getVar() {
		return var;
	}

	public void setVar(String var) {
		this.var = var;
	}

	@Transient
	public Object getValueEvaluated(){
		return ScreenUtils.evaluate(getValue());
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

	@Basic
	public String getCurrentPageReportTemplate() {
		return currentPageReportTemplate;
	}

	public void setCurrentPageReportTemplate(String currentPageReportTemplate) {
		this.currentPageReportTemplate = currentPageReportTemplate;
	}

}
