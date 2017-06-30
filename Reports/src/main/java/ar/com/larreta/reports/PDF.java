package ar.com.larreta.reports;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

import ar.com.larreta.annotations.Log;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;

public class PDF extends Report{
	
	private static final String REPORT_PDF_AUTOPRINT_SCRIPT = "var pp = this.getPrintParams(); pp.interactive = pp.constants.interactionLevel.silent; this.print(pp);";
	
	private static @Log Logger LOG;
	
	public static final String PDF_CONTENT_TYPE = "application/pdf";

	@Value("report.pdf.autoprint")
	private String pdfAutoprint;
	
	public PDF() {
		super();
	}
	
	protected void setSpecificExporterParams() {
		if (new Boolean(pdfAutoprint)){
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
