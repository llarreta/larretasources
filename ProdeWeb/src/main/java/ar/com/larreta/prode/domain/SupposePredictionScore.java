package ar.com.larreta.prode.domain;

import java.util.Iterator;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;

import ar.com.larreta.commons.utils.UniqueKeys;

@Entity
@Table(name = "supposePredictionScore")
public class SupposePredictionScore extends PredictionScore {

	public SupposePredictionScore(){}
	
	public SupposePredictionScore(PredictionScore predictionScore){
		setId(UniqueKeys.getInstance().next(getClass()));
		setPrediction(predictionScore.getPrediction());
		setValue(predictionScore.getValue());
		setPosition(predictionScore.getPosition());
		setBestInCompetition(predictionScore.getBestInCompetition());
		setBestInRound(predictionScore.getBestInRound());
		setAccumulated(predictionScore.getAccumulated());
		if (predictionScore.getScores()!=null){
			Iterator<Score> it = predictionScore.getScores().iterator();
			while (it.hasNext()) {
				Score score = (Score) it.next();
				SupposeScore supposeScore = new SupposeScore(score);
				supposeScore.setPredictionScore(this);
				addScore(supposeScore);
			}
		}
	}
	
	@OneToMany (mappedBy="predictionScore", fetch=FetchType.LAZY, cascade=CascadeType.ALL, targetEntity=SupposeScore.class)
	public Set<Score> getScores() {
		return super.getScores();
	}
	
	@Formula("(SELECT 1 " +
			"FROM supposePredictionScore P, prediction T " + 
			"WHERE P.id = id AND P.idPrediction = T.id " +
			"HAVING (SELECT SUM(S.value) FROM supposeScore S WHERE S.idPredictionScore = P.id) >= ALL " +
									"(SELECT (SELECT SUM(S2.value) FROM supposeScore S2 WHERE S2.idPredictionScore = P2.id) " + 
									"FROM supposePredictionScore P2, prediction T2 WHERE T.idRound = T2.idRound AND P2.idPrediction = T2.id))")
	public Boolean getBestInRound() {
		return super.getBestInRound();
	}
	
	@Formula("(SELECT 1 " +
			"FROM supposePredictionScore P, prediction T, Round R " +
			"WHERE P.id = id AND P.idPrediction = T.id AND T.idRound = R.id " +
					"HAVING (SELECT SUM(S.value) FROM supposeScore S WHERE S.idPredictionScore = P.id) >= ALL " + 
						"(SELECT (SELECT SUM(S2.value) FROM supposeScore S2 WHERE S2.idPredictionScore = P2.id) " + 
						"FROM supposePredictionScore P2, prediction T2, Round R2 WHERE R.idCompetition = R2.idCompetition AND P2.idPrediction = T2.id AND T2.idRound = R2.id ))")
	public Boolean getBestInCompetition() {
		return super.getBestInCompetition();
	}

	@Formula("(SELECT SUM(S.value) FROM supposeScore S WHERE S.idPredictionScore = id)")
	public Integer getValue() {
		return super.getValue();
	}
	
	@ManyToOne (fetch=FetchType.LAZY, targetEntity=SupposeCompetitionScore.class)
	@JoinColumn (name="idCompetitionScore")
	public CompetitionScore getCompetitionScore() {
		return super.getCompetitionScore();
	}


}
