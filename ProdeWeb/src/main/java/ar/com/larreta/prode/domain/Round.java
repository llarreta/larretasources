package ar.com.larreta.prode.domain;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import ar.com.larreta.commons.utils.DateUtils;

@Entity
@Table(name = "round")
@Where(clause="deleted IS NULL")
@SQLDelete (sql="UPDATE Round SET deleted=CURRENT_TIMESTAMP WHERE id=?")
public class Round extends ar.com.larreta.commons.domain.Entity implements Comparable<Round>{
	
	private String name;
	private Competition competition;
	private Set<Game> games;

	private Collection<Prediction> predictions;
	
	private Date start;
	private Date finish;
	
	@Transient
	/**
	 * Retorna verdadero si la fecha actual se encuentra en curso
	 * @return
	 */
	public Boolean onGoing(){
		Date actual = new Date();
		return DateUtils.isMajorOrEqual(actual, getStart()) && DateUtils.isMinorOrEqual(actual, getFinish());
	}

	@OneToMany(mappedBy="round", fetch=FetchType.LAZY, cascade=CascadeType.ALL, targetEntity=Prediction.class)
	public Collection<Prediction> getPredictions() {
		return predictions;
	}

	public void setPredictions(Collection<Prediction> predictions) {
		this.predictions = predictions;
	}

	/**
	 * Comienzo de la jornada, calculada en funcion de todos los juegos
	 */
	@Formula("(SELECT MIN(G.startDate) FROM game G WHERE G.idRound = id)")
	public Date getStart(){
		return start;
	}
	
	public void setStart(Date start) {
		this.start = start;
	}	

	
	/**
	 * Fin de la jornada, calculada en funcion de todos los juegos
	 */
	@Formula("(SELECT MAX(G.startDate) FROM game G WHERE G.idRound = id)")
	public Date getFinish(){
		if (finish!=null){
			return DateUtils.add(finish, Calendar.DATE, 1);
		}
		return finish;
	}
	
	public void setFinish(Date finish) {
		this.finish = finish;
	}	

	
	@Transient
	public List<Game> getSortedGames() {
		List<Game> sortedGames = new ArrayList<Game>(games);
		Collections.sort(sortedGames);
		return sortedGames;
	}

	@Transient
	public Date getNext(){
		List<Game> sortedGames = getSortedGames();
		Date actual = new Date();
		Iterator<Game> it = sortedGames.iterator();
		while (it.hasNext()) {
			Game game = (Game) it.next();
			if (DateUtils.isMinor(game.getStartDate(), actual)){
				return game.getStartDate();
			}
		}
		return null;
	}
	
	@OneToOne(fetch=FetchType.LAZY, targetEntity=Competition.class)
	@JoinColumn(name = "idCompetition")
	public Competition getCompetition() {
		return competition;
	}

	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}


	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * @param competition the competition to set
	 */
	public void setCompetition(Competition competition) {
		this.competition = competition;
	}

	@Transient
	public void addGame(Game game){
		if (games==null){
			games = new HashSet<Game>();
		}
		games.add(game);
	}
	

	@OneToMany (mappedBy="round", fetch=FetchType.LAZY, cascade=CascadeType.ALL, targetEntity=Game.class)
	public Set<Game> getGames() {
		return games;
	}

	/**
	 * @param games the matches to set
	 */
	public void setGames(Set<Game> games) {
		this.games = games;
	}

	@Override
	public int compareTo(Round round) {
		if (DateUtils.isEqual(this.getStart(), round.getStart())){
			return name.compareTo(round.getName());
		}
		if (DateUtils.isMajor(this.getStart(), round.getStart())){
			return 1;
		}
		return -1;
	}

	@Transient
	public Boolean getPredictable(){
		Date date = new Date();
		return ((getStart()==null) || (date.before(getStart())));
	}
	
}
