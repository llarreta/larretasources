package co.com.directv.sdii.ejb.business.file.DTO;

import java.util.HashMap;
import java.util.Map;

/**
 * Contiene los datos de una fila de un archivo
 * @author gfandino, wjimenez
 *
 */
public class FileRecordDTO {
	
	private int row;
	private String[] rowData;
	
	private Map<String, Object> params = new HashMap<String, Object>();
	
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public String[] getRowData() {
		return rowData;
	}
	public void setRowData(String[] rowData) {
		this.rowData = rowData;
	}
	
	public void addParam(String key, Object value) {
		params.put(key, value);
	}
	public Object getParam(String key) {
		return params.get(key);
	}
	public void clearParams() {
		params.clear();
	}
	
}
