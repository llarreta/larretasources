package ar.com.larreta.reports;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

import ar.com.larreta.annotations.Log;
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

public abstract class Report {
	
	public static final String BINARY_CONTENT_TYPE = "application/octet-stream";

	private static @Log Logger LOG;
	
	//FIXME: crear el archivo donde se externalizan estos valores
	@Value("report.virtualizer.size")
	private String virtualizerSize;
	@Value("report.virtualizer.directory")
	private String virtualizerDirectory;
	
	
	protected Map<String, Object> parameters = new HashMap<String, Object>();
	protected JRAbstractExporter exporter;
	
	protected abstract Class getExporterType();
	
	public Report(){
		try {
			Class exporterType = getExporterType();
			if (exporterType!=null){
				exporter = (JRAbstractExporter) exporterType.newInstance();
			}
		} catch (Exception e){
			LOG.error("Ocurrio un error generando reporte", e);
			//FIXME: Lanzar excepcion
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
			JRFileVirtualizer virtualizer = new JRFileVirtualizer(new Integer(virtualizerSize), virtualizerDirectory); 

			// Pass virtualizer object throw parameter map
			parameters.put(JRParameter.REPORT_VIRTUALIZER, virtualizer); 
			
		    print = JasperFillManager.fillReport(reporte,  parameters, dataSource);
		} catch (JRException e) {
			LOG.error("Ocurrio un error generando reporte", e);
			//FIXME: Lanzar excepcion
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
			LOG.error("Ocurrio un error generando reporte", e);
			//FIXME: Lanzar excepcion
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
