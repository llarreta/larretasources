package ar.com.larreta.reports;

import org.slf4j.Logger;

import ar.com.larreta.annotations.Log;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

public class XLS extends Report {
	
	public static final String XLS_CONTENT_TYPE = "application/vnd.ms-excel";

	private static @Log Logger LOG;
	
	public XLS() {
		super();
	}
	
	protected void setSpecificExporterParams() {
		exporter.setParameter (JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
	}

	@Override
	public String getContentType() {
		return XLS_CONTENT_TYPE;
	}

	@Override
	public Class getExporterType() {
		return JRXlsExporter.class;
	}

}
