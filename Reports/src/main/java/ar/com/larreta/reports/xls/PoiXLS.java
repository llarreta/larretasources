package ar.com.larreta.reports.xls;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;

import ar.com.larreta.annotations.Log;
import ar.com.larreta.reports.Report;
import ar.com.larreta.reports.XLS;

public class PoiXLS extends Report {
	
	private static @Log Logger LOG;
	
	private HSSFWorkbook workbook = new HSSFWorkbook();
	private Map<Integer, XLSPage> pages = new HashMap<Integer, XLSPage>();
	private HSSFPalette palette = workbook.getCustomPalette();
	
	public PoiXLS (Integer sheetCount, Integer rowsCount, Integer columnCounts) {
		super();
		createSheets(sheetCount, rowsCount, columnCounts);
	}

	private void createSheets(Integer pagesCount, Integer rowsCount, Integer columnCounts) {
		//Creamos las hojas que va a tener el excel
		Integer index = 0;
		while (index<pagesCount){
			pages.put(index, new XLSPage(workbook.createSheet(), rowsCount, columnCounts));
			index++;
		}
	}
	
	public CellStyle createStyle(){
		return workbook.createCellStyle();
	}
	
	public HSSFFont createFont(){
		return workbook.createFont();
	}

	@Override
	public String getContentType() {
		return XLS.XLS_CONTENT_TYPE;
	}

	@Override
	public Class getExporterType() {
		return null;
	}

	@Override
	protected void setSpecificExporterParams() {
		LOG.debug("setSpecificExporterParams:doNothing");
	}

	public HSSFWorkbook getWorkbook() {
		return workbook;
	}

	public XLSPage getPage(Integer pageNumber){
		return pages.get(pageNumber);
	}
	
	public XLSRow getRow(Integer pageNumber, Integer rowNumber){
		return getPage(pageNumber).getRow(rowNumber);
	}
	
	public XLSCell getCell(Integer pageNumber, Integer rowNumber, Integer colNumber){
		return getRow(pageNumber, rowNumber).getCell(colNumber);
	}
	
	public HSSFColor getColor(Integer red, Integer green, Integer blue){
		return palette.findSimilarColor(red, green, blue);
	}
	
	public Short getColorNumber(Integer red, Integer green, Integer blue){
		return getColor(red, green, blue).getIndex();
	}
	
	
	
	
}
