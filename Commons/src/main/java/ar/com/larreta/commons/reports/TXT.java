package ar.com.larreta.commons.reports;

import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRTextExporterParameter;
import ar.com.larreta.commons.AppConfigData;

public class TXT extends Report {
	
    public static final String TXT_CONTENT_TYPE = "plain/text";
    
    public TXT(AppConfigData appConfigData) {
        super(appConfigData);
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
