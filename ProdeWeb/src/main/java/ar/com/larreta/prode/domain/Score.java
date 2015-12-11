package ar.com.larreta.prode.domain;

import javax.persistence.Basic;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.Transient;


@MappedSuperclass
/**
 * Representa el score con una determinada apuesta 
 */
public abstract class Score extends ar.com.larreta.commons.domain.Entity {
	
	private Bet bet;
	private Integer value = 0;
	private PredictionScore predictionScore;
	
	@Transient
	public PredictionScore getPredictionScore() {
		return predictionScore;
	}
	public void setPredictionScore(PredictionScore predictionScore) {
		this.predictionScore = predictionScore;
	}
	
	@OneToOne (targetEntity=Bet.class)
	@JoinColumn (name="idBet")
	public Bet getBet() {
		return bet;
	}
	public void setBet(Bet bet) {
		this.bet = bet;
	}
	
	@Basic
	public Integer getValue() {
		return value;
	}
	public void setValue(Integer value) {
		this.value = value;
	}
	
	
}
