package ar.com.larreta.reports;

import org.slf4j.Logger;

import ar.com.larreta.annotations.Log;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRTextExporterParameter;

public class TXT extends Report {
	
    public static final String TXT_CONTENT_TYPE = "plain/text";
    
	private static @Log Logger LOG;
    
    public TXT() {
        super();
    }

	protected void setSpecificExporterParams() {
		exporter.setParameter(JRTextExporterParameter.CHARACTER_WIDTH,new Float(5.5));
		exporter.setParameter(JRTextExporterParameter.CHARACTER_HEIGHT,new Float(14));
	}

    @Override
    public String getContentType() {
        return TXT_CONTENT_TYPE;
    }

	@Override
	public Class getExporterType() {
		return JRTextExporter.class;
	}

}
