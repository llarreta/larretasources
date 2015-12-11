package ar.com.larreta.prode.reports;

import java.io.Serializable;

import org.apache.poi.ss.usermodel.CellStyle;

import ar.com.larreta.commons.reports.xls.XLSCell;
import ar.com.larreta.commons.reports.xls.XLSPage;
import ar.com.larreta.prode.domain.Bet;

public class BetXLSElement implements Serializable {
	
	private PredictionsXLS xls;
	private Bet bet;
	

	
	public BetXLSElement(PredictionsXLS xls, Bet bet){
		this.xls = xls;
		this.bet = bet;
	}
	
	public void createRow(){
		xls.getPage(0).next();
		addCellString(getScore(), xls.getValueStyle());
		addCellString(bet.getGame().getLocal().getName(), xls.getTeamLeftStyle());
		addCellString(getGoals(bet.getLocalGoals()), xls.getValueStyle());
		addCellString(getHardBet(), xls.getValueStyle());
		addCellString(getGoals(bet.getVisitorGoals()), xls.getValueStyle());
		addCellString(bet.getGame().getVisitor().getName(), xls.getTeamRightStyle());
	}
	
	public String getGoals(Integer value){
		if (value!=null){
			return value.toString();
		}
		return "";
	}

	private String getScore() {
		Integer score = null;
		if (bet.getScore()!=null){
			score = bet.getScore().getValue();
		}
		String textScore = "";
		if (score!=null){
			textScore = score.toString();
		}
		return textScore;
	}

	private String getHardBet() {
		String hardBet = "X";
		if (!bet.getHardBet()){
			hardBet = " ";
		}
		return hardBet;
	}

	private void addCellString(String value, CellStyle style) {
		XLSPage page = xls.getPage(0);
		if (page.hastNextCell()){
			XLSCell cell = xls.getPage(0).nextCell();
			cell.getCell().setCellValue(value);
			if (style!=null){
				cell.getCell().setCellStyle(style);
			}
		}
	}



}
