package ar.com.larreta.screens;

import java.util.Set;

public class Table extends ScreenElement {

	private String var = "actualItem";
	private String value = "#{dataView.paginator}";
	private Boolean paginator = Boolean.TRUE;
	private Boolean lazy = Boolean.TRUE;
	private String rows = "10";
	private String paginatorTemplate = "{RowsPerPageDropdown} {FirstPageLink} {PreviousPageLink} {CurrentPageReport} {NextPageLink} {LastPageLink}";
	private String rowsPerPageTemplate = "5,10,15";
	private String selectionMode = "single";
	private String selection = "#{dataView.selected}";
	private String emptyMessage = "#{msg['datatable.sindatos']}";
	private String currentPageReportTemplate = "{currentPage} #{msg['datatable.de']} {totalPages}";
	
	private Set<Column> columns;

	public String getVar() {
		return var;
	}

	public void setVar(String var) {
		this.var = var;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Boolean getPaginator() {
		return paginator;
	}

	public void setPaginator(Boolean paginator) {
		this.paginator = paginator;
	}

	public Boolean getLazy() {
		return lazy;
	}

	public void setLazy(Boolean lazy) {
		this.lazy = lazy;
	}

	public String getRows() {
		return rows;
	}

	public void setRows(String rows) {
		this.rows = rows;
	}

	public String getPaginatorTemplate() {
		return paginatorTemplate;
	}

	public void setPaginatorTemplate(String paginatorTemplate) {
		this.paginatorTemplate = paginatorTemplate;
	}

	public String getRowsPerPageTemplate() {
		return rowsPerPageTemplate;
	}

	public void setRowsPerPageTemplate(String rowsPerPageTemplate) {
		this.rowsPerPageTemplate = rowsPerPageTemplate;
	}

	public String getSelectionMode() {
		return selectionMode;
	}

	public void setSelectionMode(String selectionMode) {
		this.selectionMode = selectionMode;
	}

	public String getSelection() {
		return selection;
	}

	public void setSelection(String selection) {
		this.selection = selection;
	}

	public String getEmptyMessage() {
		return emptyMessage;
	}

	public void setEmptyMessage(String emptyMessage) {
		this.emptyMessage = emptyMessage;
	}

	public String getCurrentPageReportTemplate() {
		return currentPageReportTemplate;
	}

	public void setCurrentPageReportTemplate(String currentPageReportTemplate) {
		this.currentPageReportTemplate = currentPageReportTemplate;
	}

	public Set<Column> getColumns() {
		return columns;
	}

	public void setColumns(Set<Column> columns) {
		this.columns = columns;
	}
	
}
