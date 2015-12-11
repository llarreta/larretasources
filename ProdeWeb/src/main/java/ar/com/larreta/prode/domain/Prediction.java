package ar.com.larreta.prode.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Entity
@Table(name = "prediction")
public class Prediction extends ar.com.larreta.commons.domain.Entity implements Comparable<Prediction>{

	private Round round;
	private Set<Bet> bets;
	private Player player;
	
	private Boolean updated;
	
	private PredictionScore score;
	
	/**
	 * Inidca si fue generado automaticamente o por el usuario
	 */
	private Boolean auto = Boolean.TRUE;

	@Transient
	public Boolean getPredictable(){
		Date date = new Date();
		return ((round.getStart()==null) || (date.before(round.getStart())));
	}
	
	@Basic
	public Boolean getAuto() {
		return auto;
	}

	public void setAuto(Boolean auto) {
		this.auto = auto;
	}

	@OneToOne (mappedBy="prediction", fetch=FetchType.LAZY, targetEntity=RealPredictionScore.class)
	public PredictionScore getScore() {
		return score;
	}

	public void setScore(PredictionScore score) {
		this.score = score;
	}

	@Transient
	public Boolean getUpdated() {
		return updated;
	}

	public void setUpdated(Boolean updated) {
		this.updated = updated;
	}

	@Transient
	//FIXME: Este total deberia salir de PredictionScore
	public Integer getTotalScore(){
		Integer total = 0;
		if (score!=null){
			total=score.getValue();			
		}
		return total;
	}
	
	@ManyToOne (fetch=FetchType.LAZY, targetEntity=Round.class)
	@JoinColumn (name="idRound")
	public Round getRound() {
		return round;
	}

	/**
	 * @param round the dateOfCompetition to set
	 */
	public void setRound(Round round) {
		this.round = round;
	}

	@OneToMany (mappedBy="prediction", fetch=FetchType.LAZY, cascade=CascadeType.ALL, targetEntity=Bet.class)
	public Set<Bet> getBets() {
		return bets;
	}

	@Transient
	public Collection<Bet> getOrderBets(){
		List orderBets = new ArrayList<Bet>(bets);
		Collections.sort(orderBets);
		return orderBets;
	}

	/**
	 * @param bets the bets to set
	 */
	public void setBets(Set<Bet> bets) {
		this.bets = bets;
	}
	
	public void addBet(Bet bet){
		if(bets==null){
			bets = new HashSet<Bet>();
		}
		bets.add(bet);
	}

	@ManyToOne (fetch=FetchType.LAZY, targetEntity=Player.class)
	@JoinColumn (name="idPlayer")
	public Player getPlayer() {
		return player;
	}

	/**
	 * @param player the player to set
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}

	@Override
	public int compareTo(Prediction prediction) {
		return this.getRound().compareTo(prediction.getRound());
	}
	
	@Transient
	public JRBeanCollectionDataSource getBetsJRDS(){
		return getJasperReportDataSource(bets);
	}
	
}
