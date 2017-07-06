package ar.com.larreta.reports.xls;

import java.io.Serializable;

import org.apache.poi.hssf.usermodel.HSSFCell;

public class XLSCell implements Serializable {
	
	private HSSFCell cell;
	
	public XLSCell(HSSFCell cell){
		this.cell = cell;
	}

	public HSSFCell getCell() {
		return cell;
	}

	public void setCell(HSSFCell cell) {
		this.cell = cell;
	}
}
