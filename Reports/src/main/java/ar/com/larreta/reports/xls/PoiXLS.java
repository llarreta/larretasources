package ar.com.larreta.commons.reports.xls;

import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;

import ar.com.larreta.commons.AppConfigData;
import ar.com.larreta.commons.reports.Report;
import ar.com.larreta.commons.reports.XLS;

public class PoiXLS extends Report {
	
	private HSSFWorkbook workbook = new HSSFWorkbook();
	private Map<Integer, XLSPage> pages = new HashMap<Integer, XLSPage>();
	private HSSFPalette palette = workbook.getCustomPalette();
	
	public PoiXLS(AppConfigData appConfigData, Integer sheetCount, Integer rowsCount, Integer columnCounts) {
		super(appConfigData);
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
		getLog().debug("setSpecificExporterParams:doNothing");
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
