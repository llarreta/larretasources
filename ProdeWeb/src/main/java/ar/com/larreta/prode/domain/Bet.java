package ar.com.larreta.prode.domain;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "bet")
public class Bet extends ar.com.larreta.commons.domain.Entity implements Comparable<Bet> {

	private Game game;
	private Integer localGoals;
	private Integer visitorGoals;
	private Prediction prediction;
	private Boolean hardBet;
	private Score score;
	
	@Transient
	public Collection getResult(){
		Collection result = new ArrayList();
		result.add(game);
		return result;
	}
	
	@Transient
	public Boolean getHasScore(){
		return score!=null;
	}

	@OneToOne (mappedBy="bet", targetEntity=RealScore.class)
	public Score getScore() {
		return score;
	}

	public void setScore(Score score) {
		this.score = score;
	}

	@Basic
	public Boolean getHardBet() {
		return (hardBet!=null)&&(hardBet);
	}

	public void setHardBet(Boolean hardBet) {
		this.hardBet = hardBet;
		setUpdated();
	}

	public void setUpdated() {
		if (prediction!=null){
			prediction.setUpdated(Boolean.TRUE);
		}
	}

	@Transient
	public Boolean getNotHardBet() {
		return (hardBet==null) || !hardBet;
	}
	
	/**
	 * Retorna la diferencia de gol
	 * @return
	 */
	@Transient
	public Integer getGoalDiff(){
		return localGoals - visitorGoals;
	}
	
	/**
	 * Retorna el estado del encuentro, local empate o visitante
	 * @return
	 */
	@Transient
	public GameState getGameState(){
		if (getGoalDiff()==0){
			return GameState.EQUALS;
		}
		if (getGoalDiff()>0){
			return GameState.LOCAL;
		}
		return GameState.VISITOR;
	}
	
	/**
	 * Retorna verdadero si se encuentra disponible el resultado
	 * @return
	 */
	@Transient
	public Boolean avaiableResult(){
		return (localGoals!=null) && (visitorGoals!=null);
	}
	
	@ManyToOne (fetch=FetchType.LAZY, targetEntity=Prediction.class)
	@JoinColumn (name="idPrediction")
	public Prediction getPrediction() {
		return prediction;
	}
	public void setPrediction(Prediction prediction) {
		this.prediction = prediction;
	}
	@ManyToOne (fetch=FetchType.LAZY, targetEntity=Game.class)
	@JoinColumn (name="idGame")	
	public Game getGame() {
		return game;
	}
	/**
	 * @param match the match to set
	 */
	public void setGame(Game match) {
		this.game = match;
	}

	@Basic
	public Integer getLocalGoals() {
		return localGoals;
	}
	/**
	 * @param localGoals the localGoals to set
	 */
	public void setLocalGoals(Integer localGoals) {
		this.localGoals = localGoals;
		setUpdated();
	}

	@Basic
	public Integer getVisitorGoals() {
		return visitorGoals;
	}
	/**
	 * @param visitorGoals the visitorGoals to set
	 */
	public void setVisitorGoals(Integer visitorGoals) {
		this.visitorGoals = visitorGoals;
		setUpdated();
	}

	@Override
	public int compareTo(Bet bet) {
		return this.getGame().compareTo(bet.getGame());
	}
	
	
}
