package ar.com.larreta.prode.domain;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import ar.com.larreta.commons.utils.DateUtils;

@Entity
@Table(name = "competition")
@Where(clause="deleted IS NULL")
@SQLDelete (sql="UPDATE Competition SET deleted=CURRENT_TIMESTAMP WHERE id=?")
public class Competition extends ar.com.larreta.commons.domain.Entity {
	
	private String name;
	private String picture;
	
	private Date registrationStart;
	private Date registrationEnd;

	private List<Round> rounds;
	
	private Set<CompetitionScore> competitionScores;
	
	private Date start;
	private Date finish;
	

	/**
	 * Comienzo de la competencia, calculada en funcion de todos los juegos
	 */
	@Formula("(SELECT MIN(G.startDate) FROM game G, round R WHERE G.idRound = R.id AND R.idCompetition = id)")
	public Date getStart(){
		return start;
	}
	
	public void setStart(Date start) {
		this.start = start;
	}	

	
	/**
	 * Fin de la competencia, calculada en funcion de todos los juegos
	 */
	@Formula("(SELECT MAX(G.startDate) FROM game G, round R WHERE G.idRound = R.id AND R.idCompetition = id)")
	public Date getFinish(){
		if (finish!=null){
			return DateUtils.add(finish, Calendar.DATE, 1);
		}
		return finish;
	}
	
	public void setFinish(Date finish) {
		this.finish = finish;
	}	
	
	@OneToMany (mappedBy="competition", fetch=FetchType.LAZY, cascade=CascadeType.ALL, targetEntity=RealCompetitionScore.class)
	@Where(clause="deleted IS NULL")
	public Set<CompetitionScore> getCompetitionScores() {
		return competitionScores;
	}

	public void setCompetitionScores(Set<CompetitionScore> competitionScores) {
		this.competitionScores = competitionScores;
	}

	@Basic
	public Date getRegistrationStart() {
		return registrationStart;
	}

	public void setRegistrationStart(Date registrationStart) {
		this.registrationStart = registrationStart;
	}

	@Basic
	public Date getRegistrationEnd() {
		return registrationEnd;
	}

	public void setRegistrationEnd(Date registrationEnd) {
		this.registrationEnd = registrationEnd;
	}
	

	@OneToMany (mappedBy="competition", fetch=FetchType.LAZY, cascade=CascadeType.ALL, targetEntity=Round.class)
	public List<Round> getRounds() {
		return rounds;
	}

	/**
	 * @param rounds the datesOfCompetition to set
	 */
	public void setRounds(List<Round> rounds) {
		this.rounds = rounds;
		if (rounds!=null){
			Iterator<Round> it = rounds.iterator();
			while (it.hasNext()) {
				Round round = (Round) it.next();
				round.setCompetition(this);
			}
		}
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
	 * @return the picture
	 */
	public String getPicture() {
		return picture;
	}

	/**
	 * @param picture the picture to set
	 */
	public void setPicture(String picture) {
		this.picture = picture;
	}
	
}
