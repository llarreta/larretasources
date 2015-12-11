package ar.com.larreta.commons.reports.xls;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;

public class XLSPage implements Serializable {

	private HSSFSheet sheet;
	private Map<Integer, XLSRow> rows = new HashMap<Integer, XLSRow>();
	private Integer actualRow = -1;
	
	public XLSPage(HSSFSheet sheet, Integer rowsCount, Integer columnCounts){
		this.sheet = sheet;
		createRows(rowsCount, columnCounts);
	}

	private void createRows(Integer rowsCount, Integer columnCounts) {
		//Creamos las filas para la pagina actual
		Integer index = 0;
		while (index<rowsCount){
			rows.put(index, new XLSRow(sheet.createRow(index), columnCounts));
			index++;
		}
	}
	
	public XLSRow getRow(Integer rowNumber){
		return rows.get(rowNumber);
	}
	
	public XLSCell getCell(Integer rowNumber, Integer colNumber){
		return getRow(rowNumber).getCell(colNumber);
	}
	
	public XLSRow next(){
		actualRow++;
		return getRow(actualRow);
	}
	
	public Boolean hastNext(){
		return (actualRow+1<rows.size());
	}

	public XLSCell nextCell(){
		XLSRow row = getRow(actualRow);
		if (row.hastNext()){
			return row.next();
		}
		actualRow++;
		return nextCell();
	}
	
	public Boolean hastNextCell(){
		XLSRow row = getRow(actualRow);
		if ((row!=null) && (row.hastNext())){
			return Boolean.TRUE;
		}
		if (hastNext()){
			return getRow(actualRow+1).hastNext();
		}
		return Boolean.FALSE;
	}

	public void autoSize(){
		Integer index = 0;
		while (index<rows.size()){
			sheet.autoSizeColumn(index);
			index++;
		}
	}
	
	public void mergeActualRow(){
		sheet.addMergedRegion(new CellRangeAddress(actualRow, actualRow, 0, getRow(actualRow).getCellCount() - 1));
	}
}
