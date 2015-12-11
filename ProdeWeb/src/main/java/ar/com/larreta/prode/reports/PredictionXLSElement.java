package ar.com.larreta.prode.reports;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.CellStyle;

import ar.com.larreta.commons.reports.xls.XLSCell;
import ar.com.larreta.commons.reports.xls.XLSPage;
import ar.com.larreta.commons.reports.xls.XLSRow;
import ar.com.larreta.prode.domain.Bet;
import ar.com.larreta.prode.domain.Prediction;

public class PredictionXLSElement implements Serializable {
	
	private PredictionsXLS xls;
	private Prediction prediction;
	
	public PredictionXLSElement(PredictionsXLS xls, Prediction prediction){
		this.xls = xls;
		this.prediction = prediction;
		
	}
	
	public void buildArea(){

		XLSPage page = xls.getPage(0);
		page.next();
		if (page.hastNextCell()){
			XLSCell cell = page.nextCell();
			cell.getCell().setCellValue(prediction.getPlayer().getAppPlayerName());
			cell.getCell().setCellStyle(xls.getHeaderStyle());
			page.mergeActualRow();

			// Titulos
			XLSRow row = page.next();
			
			addColumnTitle(row, getScoreTitle());
			addColumnTitle(row, "Local.");
			addColumnTitle(row, "GL");
			addColumnTitle(row, "Doble");
			addColumnTitle(row, "GV");
			addColumnTitle(row, "Visitante");
			
			renderBets();
			page.next();
			

		}
	}

	private void addColumnTitle(XLSRow row, String text) {
		XLSCell titleCell = row.next();
		titleCell.getCell().setCellValue(text);
		titleCell.getCell().setCellStyle(xls.getColumnTitleStyle());
	}

	private String getScoreTitle() {
		StringBuffer scoreTitle = new StringBuffer();
		if (prediction.getScore()!=null){
			scoreTitle.append(prediction.getScore().getValue());
			scoreTitle.append(" ");
		}
		scoreTitle.append("Pts.");
		return scoreTitle.toString();
	}

	private void renderBets() {
		Collection<Bet> bets = prediction.getOrderBets();
		if (bets!=null){
			Iterator<Bet> itBets = bets.iterator();
			while (itBets.hasNext()) {
				BetXLSElement betXLSElement = new BetXLSElement(xls, (Bet) itBets.next());
				betXLSElement.createRow();
			}
		}
	}

}
