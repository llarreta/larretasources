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
@Table(name = "supposeCompetitionScore")
public class SupposeCompetitionScore extends CompetitionScore {

	private Player playerSuppose;
	
	public SupposeCompetitionScore(){}
	
	public SupposeCompetitionScore(CompetitionScore competitionScore, Round round){
		setId(UniqueKeys.getInstance().next(getClass()));
		setPlayer(competitionScore.getPlayer());
		setCompetition(competitionScore.getCompetition());
		setValue(competitionScore.getValue());
		setPosition(competitionScore.getPosition());
		if (competitionScore.getScores()!=null){
			Iterator<PredictionScore> it = competitionScore.getScores().iterator();
			while (it.hasNext()) {
				PredictionScore predictionScore = (PredictionScore) it.next();
				if (!round.equals(predictionScore.getPrediction().getRound())){
					SupposePredictionScore supposePredictionScore = new SupposePredictionScore(predictionScore);
					supposePredictionScore.setCompetitionScore(this);
					addScore(supposePredictionScore);
				}
			}
		}
		refresh();
	}

	@ManyToOne (fetch=FetchType.LAZY, targetEntity=Player.class)
	@JoinColumn (name="idPlayerSuppose")
	public Player getPlayerSuppose() {
		return playerSuppose;
	}

	public void setPlayerSuppose(Player playerSuppose) {
		this.playerSuppose = playerSuppose;
	}
	
	@OneToMany (mappedBy="competitionScore", fetch=FetchType.LAZY, cascade=CascadeType.ALL, targetEntity=SupposePredictionScore.class)
	public Set<PredictionScore> getScores() {
		return super.getScores();
	}

	@Formula("(SELECT SUM(S.value) FROM supposePredictionScore P, supposeScore S WHERE S.idPredictionScore = P.id AND P.idCompetitionScore = id)")
	public Integer getValue() {
		return super.getValue();
	}

}
