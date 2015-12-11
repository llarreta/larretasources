package ar.com.larreta.prode.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "competitionScore")
@Where(clause="deleted IS NULL")
public class RealCompetitionScore extends CompetitionScore {

	@OneToMany (mappedBy="competitionScore", fetch=FetchType.LAZY, cascade=CascadeType.ALL, targetEntity=RealPredictionScore.class)
	public Set<PredictionScore> getScores() {
		return super.getScores();
	}
	
	@Formula("(SELECT SUM(S.value) FROM predictionScore P, score S WHERE S.idPredictionScore = P.id AND P.idCompetitionScore = id)")
	public Integer getValue() {
		return super.getValue();
	}
}
