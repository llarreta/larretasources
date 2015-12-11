package ar.com.larreta.prode.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ar.com.larreta.commons.utils.UniqueKeys;

@Entity
@Table(name = "supposeScore")
public class SupposeScore extends Score{
	
	public SupposeScore(){}
	
	public SupposeScore(Score score){
		setId(UniqueKeys.getInstance().next(getClass()));
		setBet(score.getBet());
		setValue(score.getValue());
	}
	
	@ManyToOne (fetch=FetchType.LAZY, targetEntity=SupposePredictionScore.class)
	@JoinColumn (name="idPredictionScore")
	public PredictionScore getPredictionScore() {
		return super.getPredictionScore();
	}


}
