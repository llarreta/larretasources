package ar.com.larreta.commons.reports.xls;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFRow;

public class XLSRow implements Serializable {
	
	private HSSFRow row;
	private Map<Integer, XLSCell> cells = new HashMap<Integer, XLSCell>();
	private Integer actualCell = -1;
	
	public XLSRow(HSSFRow row, Integer cellCount){
		this.row = row;
		createCells(cellCount);
	}

	private void createCells(Integer cellCount) {
		//Creamos las celdas
		Integer index=0;
		while (index<cellCount){
			cells.put(index, new XLSCell(row.createCell(index)));
			index++;
		}
	}
	
	public XLSCell getCell(Integer column){
		return cells.get(column);
	}
	
	public XLSCell next(){
		actualCell++;
		return getCell(actualCell);
	}
	
	public Boolean hastNext(){
		return (actualCell+1<cells.size());
	}
	
	public Integer getCellCount(){
		return cells.size();
	}
	
}
