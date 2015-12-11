package ar.com.larreta.prode.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

@MappedSuperclass
/**
 * Representa el score obtenido en una prediccion
 * Suma de todos los scores de cada apuesta echa para esta prediccion 
 */
public abstract class PredictionScore extends ar.com.larreta.commons.domain.Entity {
	
	private Prediction prediction;
	private Set<Score> scores = new HashSet<Score>();
	private Integer value = 0;
	private Integer position;
	private CompetitionScore competitionScore;
	
	private PredictionScore next;
	private PredictionScore previous;

	private Boolean bestInCompetition;
	private Boolean bestInRound;

	private Integer accumulated;
	
	@Transient
	public PredictionScore getPrevious() {
		return previous;
	}

	public void setPrevious(PredictionScore previous) {
		this.previous = previous;
	}
	
	@Transient
	public Boolean getIsAscendentPosition(){
		return getPositionVariance() > 0;
	}
	
	@Transient
	public Boolean getIsDescendentPosition(){
		return getPositionVariance() < 0;
	}
	
	@Transient
	public Boolean getIsEqualPosition(){
		return getPositionVariance() == 0;
	}
	
	@Transient
	public Integer getPositionVariance() {
		if ((getPosition()==null) || (previous==null) || (previous.getPosition()==null)){
			return 0;
		}
		return getPosition()-previous.getPosition();
	}

	@Basic
	public Integer getAccumulated() {
		return accumulated;
	}

	public void setAccumulated(Integer accumulated) {
		this.accumulated = accumulated;
	}

	@Transient
	public Boolean getBestInRound() {
		return bestInRound;
	}

	public void setBestInRound(Boolean bestInRound) {
		this.bestInRound = bestInRound;
	}

	@Transient
	public Boolean getBestInCompetition() {
		return bestInCompetition;
	}

	public void setBestInCompetition(Boolean bestInCompetition) {
		this.bestInCompetition = bestInCompetition;
	}

	
	public void initializeValue(){
		if ((value!=null)&&(value>0)){
			if (competitionScore.getValue()>0){
				competitionScore.setValue(competitionScore.getValue()-value);
			}
			value=0;
		}
	}
	
	@Transient
	public PredictionScore getNext() {
		return next;
	}
	public void setNext(PredictionScore next) {
		this.next = next;
	}

	@Transient
	public CompetitionScore getCompetitionScore() {
		return competitionScore;
	}
	
	public void setCompetitionScore(CompetitionScore competitionScore) {
		this.competitionScore = competitionScore;
	}

	@Basic
	public Integer getPosition() {
		return position;
	}
	public void setPosition(Integer position) {
		this.position = position;
	}
	
	@Transient
	public Integer getValue() {
		return value;
	}
	
	public void setValue(Integer value) {
		this.value = value;
	}

	@OneToOne (fetch=FetchType.LAZY, targetEntity=Prediction.class)
	@JoinColumn (name="idPrediction")
	public Prediction getPrediction() {
		return prediction;
	}
	public void setPrediction(Prediction prediction) {
		this.prediction = prediction;
	}
	
	@Transient
	public Set<Score> getScores() {
		return scores;
	}

	public void setScores(Set<Score> scores) {
		this.scores = scores;
	}
	
	@Transient
	public void addScore(Score score){
		scores.add(score);
	}
	
}
