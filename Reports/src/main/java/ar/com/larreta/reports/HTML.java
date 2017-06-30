package ar.com.larreta.reports;

import org.apache.log4j.Logger;

import ar.com.larreta.annotations.Log;
import net.sf.jasperreports.engine.export.JRHtmlExporter;

public class HTML extends Report {
	
	private static @Log Logger LOG;
	
	public static final String HTML_CONTENT_TYPE = "text/html";

	public HTML() {
		super();
	}
	
	protected void setSpecificExporterParams() {
		LOG.debug("setSpecificExporterParams:doNothing");
	}

	@Override
	public String getContentType() {
		return HTML_CONTENT_TYPE;
	}

	@Override
	public Class getExporterType() {
		return JRHtmlExporter.class;
	}

}
