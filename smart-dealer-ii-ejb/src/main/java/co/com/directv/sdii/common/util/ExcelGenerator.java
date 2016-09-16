package co.com.directv.sdii.common.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.ejb.Stateless;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

import org.apache.commons.io.output.FileWriterWithEncoding;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import co.com.directv.sdii.common.enumerations.ApplicationTextEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.exceptions.ExcelGenerationException;
import co.com.directv.sdii.exceptions.PDFException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.reports.VisitsReportItem;
import co.com.directv.sdii.reports.VisitsReportItemExcel;

/**
 * Session Bean implementation class ExcelGenerator
 */
@Stateless(mappedName = "ejb/ExcelGenerator")
public class ExcelGenerator implements ExcelGeneratorLocal {
	
	private final static Logger log = UtilsBusiness.getLog4J(ExcelGenerator.class);
	private final static String REPORT_EXTENSION = ".jasper";
	private final static String TEMPLATE_FOLDER = "templates" + File.separator;

    /**
     * Default constructor. 
     */
    public ExcelGenerator() {
    }

    public void populateExcelFile(List<String> dataField,List<String> dataList,String originalWorkBookName, int sheetNumber)
		throws ExcelGenerationException {
		log.debug("== Inicia createExcelFile/ExcelGenerator ==");
		try {
			// Crear el libro de trabajo
	        FileWriterWithEncoding fichero = null;
	        PrintWriter pw = null;
	        fichero = new FileWriterWithEncoding(getReportsPathTemp()+originalWorkBookName, Constantes.ISO_LATIN_ENCODING, true);
	        pw = new PrintWriter(fichero);
			if(sheetNumber==0){
				for(String df: dataField){
					pw.print("=\""+df+"\""+";");
				}
				pw.println();
			}
			for(String dl: dataList){
				List<String> campos = getDataValues(dl);
				for(String campo: campos){
					pw.print("=\""+campo+"\";");
					
				}
				pw.println();
			}
			pw.close();
			fichero.close();
		} catch (Exception e){
			log.debug("== Error generando el archivo de excel == ", e);
			throw new ExcelGenerationException("== Error generando el archivo de excel ==", e);
		} finally {
		    log.debug("== Termina createExcelFile/ExcelGenerator ==");
		}
    }
    
	@Override
	public Workbook createExcelFile(List<String> dataList, List<String> fieldList, String sheetName)
			throws ExcelGenerationException {
		
		log.debug("== Inicia createExcelFile/ExcelGenerator ==");
		Workbook wb = new HSSFWorkbook();
		try {
			// Crear el libro de trabajo
			Sheet sheet = null;
			int i = 0;

			// Crear la hoja de trabajo con el nombre
			if (sheetName != null && !sheetName.isEmpty()){
				sheet = wb.createSheet(sheetName);
			} else
				throw new ExcelGenerationException("== Error: Se requiere un nombre para la hoja de cálculo ==");

			// Crear las filas en la hoja de trabajo
			if (sheet != null && dataList != null && !dataList.isEmpty()){
				int rowCount = dataList.size();
				for(i = 0; i < rowCount; i++)
					sheet.createRow(i);
				// Si hay fila con nombres de campos se crea una nueva fila
				if (fieldList != null && !fieldList.isEmpty()){
					sheet.createRow(i);
				}
			} else
				throw new ExcelGenerationException("== Error: No hay datos para exportar ==");
			
			// Poblar la hoja de cálculo con los valores
			populateSheet(sheet, dataList, fieldList);
		} catch (Exception e){
			log.debug("== Error generando el archivo de excel == ", e);
			throw new ExcelGenerationException("== Error generando el archivo de excel ==", e);
		} finally {
		    log.debug("== Termina createExcelFile/ExcelGenerator ==");
		}
		return wb;
	}
	
	/**
	 * Método que se encarga de poblar la hoja de cálculo.
	 * 
	 * @param sheet
	 * @param dataList
	 * @param fieldList
	 */
	private void populateSheet(Sheet sheet, List<String> dataList, List<String> fieldList){
		Row theRow;
		int rowNum = 0;
		
		// Crea la primera fila con los nombres de las columnas
		if (fieldList != null && !fieldList.isEmpty()){
			theRow = sheet.getRow(rowNum++);
			populateHeader(theRow, fieldList);
		}
		
		// Crea las filas en la hoja de cálculo de excel
		for (String rowData : dataList){
			theRow = sheet.getRow(rowNum++);
			populateRow(theRow, rowData);
		}
	}

	
	/**
	 * Método que se encarga de poblar el encabezado de la hoja de cálculo.
	 * 
	 * @param theRow
	 * @param fieldList
	 */
	private void populateHeader(Row theRow, List<String> fieldList){
		int i = 0;
		if (theRow != null && fieldList != null && !fieldList.isEmpty()){
			for(String colName: fieldList){
				theRow.createCell(i++).setCellValue(colName);
			}
		}
	}
	
	/**
	 * Método que se encarga de poblar una fila en la hoja de cálculo.
	 * 
	 * @param theRow
	 * @param rowData
	 */
	private void populateRow(Row theRow, String rowData){
		List<String> dataValues;
		int i = 0;

		if (theRow != null && rowData != null && !rowData.isEmpty()){
			dataValues = getDataValues(rowData);
			for (String data : dataValues){
				theRow.createCell(i++).setCellValue(data);
			}
		}
	}

	/**
	 * Método que se encarga de obtener los valores de las celdas 
	 * con los cuales se poblará la una fila en la hoja de cálculo.
	 * Los datos se encuentran separados por el caracter (|).
	 * 
	 * @param dataRow
	 * @return
	 */
	private List<String> getDataValues(String dataRow){
		List<String> dataValues = new ArrayList<String>();
		if (dataRow != null && !dataRow.isEmpty()){
			StringTokenizer tokenizer = new StringTokenizer(dataRow, "|");
			for (;tokenizer.hasMoreTokens();){
				dataValues.add(tokenizer.nextToken());
			}
		}
		return dataValues;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ByteArrayOutputStream createExcelStreamWithJasper(List dataList,List<String> fieldList, String[] sheetName,String command)throws ExcelGenerationException {
		log.debug("== Inicia createExcelStreamWithJasper/ExcelGenerator ==");
		try{
			if (dataList == null || dataList.isEmpty()){
				dataList = new ArrayList<Object>();
				Object object = dataList.getClass().getComponentType();
				dataList.add(object);
			}
			
			String reportsPath = getReportsPath();
			String reportConfFile = reportsPath + TEMPLATE_FOLDER + command + REPORT_EXTENSION;			

			//JasperPrint print = JasperFillManager.fillReport(reportConfFile,new HashMap(), new JRBeanCollectionDataSource(dataList));
			JasperPrint print = JasperFillManager.fillReport(reportConfFile,UtilsBusiness.getReportParams(), new JRBeanCollectionDataSource(dataList));
						
			ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();

			JRXlsExporter exporterXLS = new JRXlsExporter();
			exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, print);
			exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM,arrayOutputStream);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,Boolean.TRUE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND,Boolean.FALSE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_COLLAPSE_ROW_SPAN,Boolean.TRUE);

			exporterXLS.exportReport();
			return arrayOutputStream;
		} catch (Exception e){
			log.error("== Error generando el archivo de excel == ", e);
			throw new ExcelGenerationException("== Error createExcelStreamWithJasper/ExcelGenerator ==", e);
		} finally {
		    log.debug("== Termina createExcelStreamWithJasper/ExcelGenerator ==");
		}
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public ByteArrayOutputStream createExcelMultipleSheetStreamWithJasper(List<VisitsReportItemExcel> visitsReportItemExcelList, List<String> fieldList, String[] sheetNames, String command)throws ExcelGenerationException {
		log.debug("== Inicia createExcelMultipleSheetStreamWithJasper/ExcelGenerator ==");
		try{
			if (visitsReportItemExcelList == null || visitsReportItemExcelList.isEmpty()){
				visitsReportItemExcelList = new ArrayList<VisitsReportItemExcel>();
				Object object = visitsReportItemExcelList.getClass().getComponentType();
				visitsReportItemExcelList.add((VisitsReportItemExcel) object);
			}
			Date now = new Date();
			String reportsPath = getReportsPath();
			String reportConfFile = reportsPath + TEMPLATE_FOLDER + command + REPORT_EXTENSION;			
			//List<String> sheetNames = new ArrayList<String>();			
			ArrayList<JasperPrint> list = new  ArrayList<JasperPrint>();
			
			for( VisitsReportItemExcel visitsReportItemExcel: visitsReportItemExcelList){
				
				Map<String, String> pars = UtilsBusiness.getReportParams();
			    pars.put("PV_TITLE", "PLANTILLA DE VISITAS");
			    pars.put("PV_RESPONSIBLE", visitsReportItemExcel.getEmployeeResponsibleCrew().getFirstName()+ " " + visitsReportItemExcel.getEmployeeResponsibleCrew().getLastName());
			    pars.put("PV_CODE", visitsReportItemExcel.getEmployeeResponsibleCrew().getDocumentNumber());
			    pars.put("PV_CI", ""+visitsReportItemExcel.getEmployeeResponsibleCrew().getId());
			    pars.put("PV_DATA", UtilsBusiness.formatDate(now));				
				JasperPrint print = JasperFillManager.fillReport(reportConfFile ,pars, new JRBeanCollectionDataSource(visitsReportItemExcel.getVisitsReportItems()));
				list.add(print);
				
			}
			
			ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();

			JRXlsExporter exporterXLS = new JRXlsExporter();
			exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT_LIST,list );
			exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM,arrayOutputStream);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,Boolean.TRUE);
		    exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND,Boolean.FALSE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_COLLAPSE_ROW_SPAN,Boolean.TRUE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);
			exporterXLS.setParameter(JRXlsExporterParameter.SHEET_NAMES, sheetNames);
			
			exporterXLS.exportReport();
			return arrayOutputStream;
		} catch (Exception e){
			log.error("== Error generando el archivo de excel == ", e);
			throw new ExcelGenerationException("== Error createExcelMultipleSheetStreamWithJasper/ExcelGenerator ==", e);
		} finally {
		    log.debug("== Termina createExcelMultipleSheetStreamWithJasper/ExcelGenerator ==");
		}
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public String createExcelFileWithJasper(List dataList,List<String> fieldList, String[] sheetName,String command, String... nameAndPath)throws ExcelGenerationException {
		log.debug("== Inicia createExcelFileWithJasper/ExcelGenerator ==");
		try{
			String reportsPath = getReportsPath();

			String reportConfFile = reportsPath + TEMPLATE_FOLDER + command + REPORT_EXTENSION;
			Date now = new Date();

			//JasperPrint print = JasperFillManager.fillReport(reportConfFile,new HashMap(), new JRBeanCollectionDataSource(dataList));
			JasperPrint print = JasperFillManager.fillReport(reportConfFile,UtilsBusiness.getReportParams(), new JRBeanCollectionDataSource(dataList));
			
			if(nameAndPath!=null && nameAndPath.length>1){
				reportsPath+=nameAndPath[1];
			}
			
			String reportName = ApplicationTextEnum.EXCEL_REPORT.getApplicationTextValue() + command + "_"+ UtilsBusiness.formatYYYYMMDD(now) + "-" + now.getTime();
			String report = reportsPath+reportName;
			
			if(nameAndPath!=null && nameAndPath.length>0){
				report+=nameAndPath[0];
			}
			
			report += ".xls";
			
			JRXlsExporter exporterXLS = new JRXlsExporter();
			exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, print);
			exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME,report);			
			exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,Boolean.TRUE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND,Boolean.FALSE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_COLLAPSE_ROW_SPAN,Boolean.TRUE);
			exporterXLS.exportReport();
			return report;
		} catch (Exception e){
			log.debug("== Error generando el archivo de excel == ", e);
			throw new ExcelGenerationException("== Error createExcelFileWithJasper/ExcelGenerator ==", e);
		} finally {
		    log.debug("== Termina createExcelFileWithJasper/ExcelGenerator ==");
		}
	}
	
	public static String getReportsPath() throws PDFException{
		try {
			return PropertiesReader.getInstance().getAppKey(Constantes.LABEL_RUTA_REPORTS);
		} catch (PropertiesException e) {
			throw new PDFException(ErrorBusinessMessages.REPORT_PATH_INVALID.getCode(),"No se pudo localizar la ruta de exportación de los reportes", e);
		}
		
	}
	
	public static String getTempPath() throws PDFException{
		try {
			return PropertiesReader.getInstance().getAppKey(Constantes.LABEL_RUTA_REPORTS_RELATIVE_TEMP);
		} catch (PropertiesException e) {
			throw new PDFException(ErrorBusinessMessages.REPORT_PATH_INVALID.getCode(),"No se pudo localizar la ruta de exportación de los reportes", e);
		}
		
	}
	
	public static String getReportsPathTemp() throws PDFException{
		try {
			return PropertiesReader.getInstance().getAppKey(Constantes.LABEL_RUTA_REPORTS_TEMP);
		} catch (PropertiesException e) {
			throw new PDFException(ErrorBusinessMessages.REPORT_PATH_INVALID.getCode(),"No se pudo localizar la ruta de exportación de los reportes temporales", e);
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ByteArrayOutputStream createPdfStreamWithJasper(List dataList, String command)throws ExcelGenerationException {
		log.debug("== Inicia createPdfStreamWithJasper/PdfGenerator ==");
		try{
			String reportsPath = getReportsPath();
			String reportConfFile = reportsPath + TEMPLATE_FOLDER + command + REPORT_EXTENSION;
			
			Date now = new Date();
			String reportName = ApplicationTextEnum.PDF_REPORT.getApplicationTextValue()+ UtilsBusiness.formatYYYYMMDD(now) + "-" + now.getTime();
			String report = reportsPath+reportName+".pdf";

			//JasperPrint print = JasperFillManager.fillReport(reportConfFile,new HashMap(), new JRBeanCollectionDataSource(dataList));
			JasperPrint print = JasperFillManager.fillReport(reportConfFile,UtilsBusiness.getReportParams(), new JRBeanCollectionDataSource(dataList));
				
			ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();

			JasperExportManager.exportReportToPdfStream(print,arrayOutputStream);
			return arrayOutputStream;
		} catch (Exception e){
			log.debug("== Error generando el archivo de excel == ", e);
			throw new ExcelGenerationException("== Error createPdfStreamWithJasper/PdfGenerator ==", e);
		} finally {
		    log.debug("== Termina createPdfStreamWithJasper/PdfGenerator ==");
		}
	}

	@Override
	public ByteArrayOutputStream createPdfStream(List<String> dataList) throws ExcelGenerationException {
		try{
			String reportsPath = getReportsPath();
			Date now = new Date();
			String reportName = ApplicationTextEnum.PDF_REPORT.getApplicationTextValue()+ UtilsBusiness.formatYYYYMMDD(now) + "-" + now.getTime();
			String report = reportsPath+reportName+".pdf";
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			File file = null;
			boolean deleteFile = false;
			if( dataList.size() > 1 ){
				PdfUtils.concatPDFs(dataList, report, false);
				for( String fileName : dataList ){
					File tempFile = new File(fileName);
					tempFile.delete();
				}
				file = new File(report);
				deleteFile = true;
			} else{
				file = new File(dataList.get(0));
				deleteFile = true;
			}			
			if( file != null && file.exists() ){
				FileInputStream fis = new FileInputStream(file);
			    byte[] buf = new byte[1024];
			    int readNum;
			    while ((readNum = fis.read(buf)) != -1) {
			    	bos.write(buf, 0, readNum);
			    }
			    if( fis != null ){
			    	fis.close();
			    }
			    if( deleteFile )
			    	file.delete();
			}			
		    return bos;
		}catch (Exception e) {
			throw new ExcelGenerationException("== Error createPdfStream/ExcelGenerator ==", e);
		}
	}

	
}
