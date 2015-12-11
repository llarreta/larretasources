package ar.com.larreta.prode.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

@MappedSuperclass
public abstract class CompetitionScore extends ar.com.larreta.commons.domain.Entity implements Comparable<CompetitionScore> {

	private Player player;
	private Competition competition;
	private Integer value = 0;
	private Integer position;
	private Set<PredictionScore> scores = new HashSet<PredictionScore>();
	
	private PredictionScore next;
	
	@Transient
	public PredictionScore getNextWithoutAvance(){
		if (next==null){
			refresh();
		}
		return next;
	}
	
	@Transient
	public PredictionScore getNext() {
		if (next==null){
			refresh();
		}
		PredictionScore actual = next;
		if (next!=null){
			next = next.getNext();
		}
		return actual;
	}
	
	public void setNext(PredictionScore next) {
		this.next = next;
	}
	
	@Transient
	public Set<PredictionScore> getScores() {
		return scores;
	}
	public void setScores(Set<PredictionScore> scores) {
		this.scores = scores;
	}
	
	@Transient
	public Integer getValue() {
		return value;
	}
	public void setValue(Integer value) {
		this.value = value;
	}

	@Basic
	public Integer getPosition() {
		return position;
	}
	public void setPosition(Integer position) {
		this.position = position;
	}

	@ManyToOne (fetch=FetchType.LAZY, targetEntity=Player.class)
	@JoinColumn (name="idPlayer")
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}

	@ManyToOne (fetch=FetchType.LAZY, targetEntity=Competition.class)
	@JoinColumn (name="idCompetition")
	public Competition getCompetition() {
		return competition;
	}
	public void setCompetition(Competition competition) {
		this.competition = competition;
	}
	@Override
	public int compareTo(CompetitionScore score) {
		return getValue(score).compareTo(getValue(this));
	}

	private Integer getValue(CompetitionScore score) {
		Integer scoreValue = 0;
		if ((score!=null) && (score.getValue()!=null)){
			scoreValue = score.getValue();
		}
		return scoreValue;
	}

	@Transient
	public Collection<PredictionScore> getOrdererPredictions(){
		List<PredictionScore> predictions = new ArrayList<PredictionScore>(scores);
		Collections.sort(predictions, new Comparator<PredictionScore>() {
			@Override
			public int compare(PredictionScore scoreA, PredictionScore scoreB) {
				return scoreA.getPrediction().getRound().compareTo(scoreB.getPrediction().getRound());
			}
		});
		return predictions;
	}
	
	@Transient
	public void addScore(PredictionScore score){
		scores.add(score);
	}
	
	public void refresh(){
		Collection<PredictionScore> scores = getOrdererPredictions();
		if (scores!=null){
			PredictionScore previous = null;
			PredictionScore actual = null;
			PredictionScore next = null;
			
			Iterator<PredictionScore> it = scores.iterator();
			Integer acumulated = 0;
			
			if (it.hasNext()){
				actual = it.next();
				next = advance(it);
				this.next = actual;
			}
			
			while (actual!=null) {
				actual.setPrevious(previous);
				actual.setNext(next);
				
				if (actual.getValue()!=null){
					acumulated+=actual.getValue();
				}
				actual.setAccumulated(acumulated);
				
				previous = actual;
				actual = next;
				next = advance(it);
			}
		}
	}

	private PredictionScore advance(Iterator<PredictionScore> it) {
		PredictionScore next;
		if (it.hasNext()){
			next = it.next();
		} else {
			next = null;
		}
		return next;
	}
	
}

