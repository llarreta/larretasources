package ar.com.larreta.prode.reports;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.CellStyle;

import ar.com.larreta.commons.AppConfigData;
import ar.com.larreta.commons.reports.xls.PoiXLS;
import ar.com.larreta.prode.domain.Prediction;

public class PredictionsXLS extends PoiXLS {

	private CellStyle headerStyle;
	private CellStyle columnTitleStyle;
	private CellStyle teamLeftStyle;
	private CellStyle teamRightStyle;
	private CellStyle valueStyle;

	private Collection<Prediction> predictions;
	
	public CellStyle getHeaderStyle() {
		return headerStyle;
	}

	public void setHeaderStyle(CellStyle headerStyle) {
		this.headerStyle = headerStyle;
	}

	public CellStyle getColumnTitleStyle() {
		return columnTitleStyle;
	}

	public void setColumnTitleStyle(CellStyle columnTitleStyle) {
		this.columnTitleStyle = columnTitleStyle;
	}

	public CellStyle getTeamLeftStyle() {
		return teamLeftStyle;
	}

	public void setTeamLeftStyle(CellStyle teamLeftStyle) {
		this.teamLeftStyle = teamLeftStyle;
	}

	public CellStyle getTeamRightStyle() {
		return teamRightStyle;
	}

	public void setTeamRightStyle(CellStyle teamRightStyle) {
		this.teamRightStyle = teamRightStyle;
	}

	public CellStyle getValueStyle() {
		return valueStyle;
	}

	public void setValueStyle(CellStyle valueStyle) {
		this.valueStyle = valueStyle;
	}

	public PredictionsXLS(AppConfigData appConfigData, Collection<Prediction> predictions) {
		super(appConfigData, 1, predictions.size() * 20, 6);
		this.predictions = predictions;
		
		refreshStyles();
		
	}

	private void refreshStyles() {
		if (headerStyle==null){
			headerStyle = createStyle();
			headerStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
			headerStyle.setFillForegroundColor(getColorNumber(0, 0, 0));
			HSSFFont font = createFont();
			font.setColor(getColorNumber(255, 255, 255));
			headerStyle.setFont(font);
			headerStyle.setAlignment(CellStyle.ALIGN_LEFT);
		}
		
		if (columnTitleStyle==null){
			columnTitleStyle = createStyle();
			columnTitleStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
			columnTitleStyle.setFillForegroundColor(getColorNumber(0, 51, 102));
			HSSFFont font = createFont();
			font.setColor(getColorNumber(255, 255, 255));
			columnTitleStyle.setFont(font);
			columnTitleStyle.setAlignment(CellStyle.ALIGN_CENTER);
		}
		
		if (teamLeftStyle==null){
			teamLeftStyle = createStyle();
			teamLeftStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
			teamLeftStyle.setFillForegroundColor(getColorNumber(255, 255, 204));
			teamLeftStyle.setAlignment(CellStyle.ALIGN_LEFT);
		}
		
		if (teamRightStyle==null){
			teamRightStyle = createStyle();
			teamRightStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
			teamRightStyle.setFillForegroundColor(getColorNumber(255, 255, 204));
			teamRightStyle.setAlignment(CellStyle.ALIGN_RIGHT);
		}
		
		if (valueStyle==null){
			valueStyle = createStyle();
			valueStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
			valueStyle.setFillForegroundColor(getColorNumber(204, 255, 229));
			valueStyle.setAlignment(CellStyle.ALIGN_CENTER);
		}
	}

	@Override
	public ByteArrayOutputStream getOutputStream()	throws IOException {
		if (predictions!=null){
			Iterator<Prediction> it = predictions.iterator();
			while (it.hasNext()) {
				PredictionXLSElement element = new PredictionXLSElement(this, (Prediction) it.next());
				element.buildArea();
			}
		}
		
		getPage(0).autoSize();		
		
		ByteArrayOutputStream salida = new ByteArrayOutputStream();
		getWorkbook().write(salida);
		return salida;
	}

}
