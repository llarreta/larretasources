package ar.com.larreta.commons.reports;

import net.sf.jasperreports.engine.export.JRHtmlExporter;
import ar.com.larreta.commons.AppConfigData;

public class HTML extends Report {
	
	public static final String HTML_CONTENT_TYPE = "text/html";

	public HTML(AppConfigData appConfigData) {
		super(appConfigData);
	}
	
	protected void setSpecificExporterParams() {
		getLog().debug("setSpecificExporterParams:doNothing");
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
