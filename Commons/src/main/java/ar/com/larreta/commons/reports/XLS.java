package ar.com.larreta.commons.reports;

import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import ar.com.larreta.commons.AppConfigData;

public class XLS extends Report {
	
	public static final String XLS_CONTENT_TYPE = "application/vnd.ms-excel";

	public XLS(AppConfigData appConfigData) {
		super(appConfigData);
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
