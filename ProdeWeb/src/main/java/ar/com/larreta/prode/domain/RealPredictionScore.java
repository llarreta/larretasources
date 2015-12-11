package ar.com.larreta.prode.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;

@Entity
@Table(name = "predictionScore")
public class RealPredictionScore extends PredictionScore {
	
	@Formula("(SELECT 1 " +
			"FROM predictionScore P, prediction T " + 
			"WHERE P.id = id AND P.idPrediction = T.id " +
			"HAVING (SELECT SUM(S.value) FROM score S WHERE S.idPredictionScore = P.id) >= ALL " +
									"(SELECT (SELECT SUM(S2.value) FROM score S2 WHERE S2.idPredictionScore = P2.id) " + 
									"FROM predictionScore P2, prediction T2 WHERE T.idRound = T2.idRound AND P2.idPrediction = T2.id))")
	public Boolean getBestInRound() {
		return super.getBestInRound();
	}
	
	@Formula("(SELECT 1 " +
			"FROM predictionScore P, prediction T, Round R " +
			"WHERE P.id = id AND P.idPrediction = T.id AND T.idRound = R.id " +
					"HAVING (SELECT SUM(S.value) FROM score S WHERE S.idPredictionScore = P.id) >= ALL " + 
						"(SELECT (SELECT SUM(S2.value) FROM score S2 WHERE S2.idPredictionScore = P2.id) " + 
						"FROM predictionScore P2, prediction T2, Round R2 WHERE R.idCompetition = R2.idCompetition AND P2.idPrediction = T2.id AND T2.idRound = R2.id ))")
	public Boolean getBestInCompetition() {
		return super.getBestInCompetition();
	}
	
	@Formula("(SELECT SUM(S.value) FROM score S WHERE S.idPredictionScore = id)")
	public Integer getValue() {
		return super.getValue();
	}
	
	@ManyToOne (fetch=FetchType.LAZY, targetEntity=RealCompetitionScore.class)
	@JoinColumn (name="idCompetitionScore")
	public CompetitionScore getCompetitionScore() {
		return super.getCompetitionScore();
	}

	@OneToMany (mappedBy="predictionScore", fetch=FetchType.LAZY, cascade=CascadeType.ALL, targetEntity=RealScore.class)
	public Set<Score> getScores() {
		return super.getScores();
	}
	

}
