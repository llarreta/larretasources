package ar.com.larreta.prode.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "score")
public class RealScore extends Score {

	@ManyToOne (fetch=FetchType.LAZY, targetEntity=RealPredictionScore.class)
	@JoinColumn (name="idPredictionScore")
	public PredictionScore getPredictionScore() {
		return super.getPredictionScore();
	}
}
