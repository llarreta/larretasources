package ar.com.larreta.commons.reports;

import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import ar.com.larreta.commons.AppConfigData;

public class PDF extends Report{
	
	private static final String REPORT_PDF_AUTOPRINT_SCRIPT = "var pp = this.getPrintParams(); pp.interactive = pp.constants.interactionLevel.silent; this.print(pp);";
	
	public static final String PDF_CONTENT_TYPE = "application/pdf";

	public PDF(AppConfigData appConfigData) {
		super(appConfigData);
	}
	
	protected void setSpecificExporterParams() {
		if (appConfigData.getReportPDFAutoprint()){
			//Necesario para que se imprima automaticamente
		     exporter.setParameter(JRPdfExporterParameter.PDF_JAVASCRIPT, REPORT_PDF_AUTOPRINT_SCRIPT);
		}
		exporter.setParameter(JRPdfExporterParameter.FORCE_LINEBREAK_POLICY, Boolean.TRUE);
	}	

	@Override
	public String getContentType() {
		return PDF_CONTENT_TYPE;
	}

	@Override
	protected Class getExporterType() {
		return JRPdfExporter.class;
	}
	
}
