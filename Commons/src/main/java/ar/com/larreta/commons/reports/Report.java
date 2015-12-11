package ar.com.larreta.commons.reports;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRAbstractExporter;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.fill.JRFileVirtualizer;
import net.sf.jasperreports.repo.RepositoryUtil;
import ar.com.larreta.commons.AppConfigData;
import ar.com.larreta.commons.AppObjectImpl;
import ar.com.larreta.commons.exceptions.AppException;

public abstract class Report extends AppObjectImpl{
	
	public static final String BINARY_CONTENT_TYPE = "application/octet-stream";

	protected Map<String, Object> parameters = new HashMap<String, Object>();
	protected JRAbstractExporter exporter;
	
	protected AppConfigData appConfigData;
	
	protected abstract Class getExporterType();
	
	public Report(AppConfigData appConfigData){
		this.appConfigData = appConfigData;
		try {
			Class exporterType = getExporterType();
			if (exporterType!=null){
				exporter = (JRAbstractExporter) exporterType.newInstance();
			}
		} catch (Exception e){
			getLog().error(AppException.getStackTrace(e));
		}
	}
	
	public JasperReport getJasperReport(String reportTemplatePath) throws FileNotFoundException, JRException {
		return RepositoryUtil.getReport(reportTemplatePath);
	}
	
	/**
	 * Agrega un parametro que sera tenido en cuenta a la hora de construir el reporte
	 * @param key
	 * @param value
	 */
	public void addParameter(String key, Object value){
		parameters.put(key, value);
	}
	
	/**
	 * Se obtiene el objeto print que permite imprimir el reporte en diferentes formatos
	 * @param dataSource
	 * @return
	 * @throws IOException 
	 */
	protected JasperPrint getPrint(String reportTemplatePath, JRDataSource dataSource) throws IOException {
		JasperPrint print = null;
		try {
			JasperReport reporte = getJasperReport(reportTemplatePath);
			
			// creating the virtualizer
			JRFileVirtualizer virtualizer = new JRFileVirtualizer(appConfigData.getReportVirtualizerSize(), appConfigData.getReportVirtualizerDirectory()); 

			// Pass virtualizer object throw parameter map
			parameters.put(JRParameter.REPORT_VIRTUALIZER, virtualizer); 
			
		    print = JasperFillManager.fillReport(reporte,  parameters, dataSource);
		} catch (JRException e) {
			getLog().error(AppException.getStackTrace(e));
		} 
		return print;
	}
	
	/**
	 * Obtiene un stream de salida con formato PDF
	 * @param dataSource
	 * @return
	 * @throws IOException 
	 */
	public ByteArrayOutputStream getOutputStream(String reportTemplatePath, JRDataSource dataSource) throws IOException{
		JasperPrint print = getPrint(reportTemplatePath, dataSource);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			
			setStandardExporterParams(print, baos);
			setSpecificExporterParams();
             
             exporter.exportReport();
		} catch (JRException e) {
			getLog().error(AppException.getStackTrace(e));
		}
		
		return baos;
	}
	
	protected void setStandardExporterParams(JasperPrint print,
			ByteArrayOutputStream baos) {
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
	}
	
	public ByteArrayOutputStream getOutputStream(String reportTemplatePath, Collection dataSource)throws IOException{
		return getOutputStream(reportTemplatePath, new JRBeanCollectionDataSource(dataSource));
	}

	public ByteArrayOutputStream getOutputStream(String reportTemplatePath)throws IOException{
		Collection empty = new ArrayList();
		empty.add(1);
		return getOutputStream(reportTemplatePath, empty);
	}
	
	public ByteArrayOutputStream getOutputStream()throws IOException{
		throw new IOException("Not implemented method.");
	}
	
	public abstract String getContentType();
	protected abstract void setSpecificExporterParams();
}
