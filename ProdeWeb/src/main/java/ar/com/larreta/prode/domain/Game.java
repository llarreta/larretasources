package ar.com.larreta.prode.domain;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import ar.com.larreta.commons.utils.DateUtils;

@Entity
@Table(name = "game")
@Where(clause="deleted IS NULL")
@SQLDelete (sql="UPDATE Game SET deleted=CURRENT_TIMESTAMP WHERE id=?")
public class Game extends ar.com.larreta.commons.domain.Entity implements Comparable<Game>{

	private Team local;
	private Team visitor;
	private Integer localGoals;
	private Integer visitorGoals;
	private Date startDate;
	private Round round;

	@Transient
	public Boolean withResult(){
		return ((localGoals!=null) && (visitorGoals!=null));
	}
	
	/**
	 * Retorna la diferencia de gol
	 * @return
	 */
	@Transient
	public Integer getGoalDiff(){
		return localGoals - visitorGoals;
	}
	
	/**
	 * Retorna el estado del encuentro, local empate o visitante
	 * @return
	 */
	@Transient
	public GameState getGameState(){
		if (getGoalDiff()==0){
			return GameState.EQUALS;
		}
		if (getGoalDiff()>0){
			return GameState.LOCAL;
		}
		return GameState.VISITOR;
	}
	
	/**
	 * Retorna verdadero si se encuentra disponible el resultado
	 * @return
	 */
	@Transient
	public Boolean avaiableResult(){
		return (localGoals!=null) && (visitorGoals!=null);
	}
	
	@Transient
	public Boolean getHaveLocalLogo(){
		return ((local!=null) && (local.getHaveLogo()));
	}

	@Transient
	public Boolean getHaveVisitorLogo(){
		return ((visitor!=null) && (visitor.getHaveLogo()));
	}
	
	@ManyToOne (fetch=FetchType.LAZY, targetEntity=Team.class)
	@JoinColumn (name="idLocal")
	public Team getLocal() {
		return local;
	}

	/**
	 * @param local the local to set
	 */
	public void setLocal(Team local) {
		this.local = local;
	}

	@ManyToOne (fetch=FetchType.LAZY, targetEntity=Team.class)
	@JoinColumn (name="idVisitor")
	public Team getVisitor() {
		return visitor;
	}

	/**
	 * @param visitor the visitor to set
	 */
	public void setVisitor(Team visitor) {
		this.visitor = visitor;
	}


	@Basic
	public Integer getLocalGoals() {
		return localGoals;
	}

	/**
	 * @param localGoals the localGoals to set
	 */
	public void setLocalGoals(Integer localGoals) {
		this.localGoals = localGoals;
	}


	@Basic
	public Integer getVisitorGoals() {
		return visitorGoals;
	}

	/**
	 * @param visitorGoals the visitorGoals to set
	 */
	public void setVisitorGoals(Integer visitorGoals) {
		this.visitorGoals = visitorGoals;
	}

	@Transient
	public Boolean getNonPredictable(){
		return !getPredictable();
	}
	
	@Transient
	public Boolean getPredictable(){
		Date date = new Date();
		return ((round.getStart()==null) || (date.before(round.getStart())));
	}

	@Basic
	public Date getStartDate() {
		return startDate;
	}
	
	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}


	@ManyToOne (fetch=FetchType.LAZY, targetEntity=Round.class)
	@JoinColumn (name="idRound")
	public Round getRound() {
		return round;
	}

	/**
	 * @param round the dateOfCompetition to set
	 */
	public void setRound(Round round) {
		this.round = round;
	}
	
	@Override
	public int compareTo(Game game) {
		if (DateUtils.isEqual(this.getStartDate(), game.getStartDate())){
			return getLocal().getName().compareTo(game.getLocal().getName());
		}
		if (DateUtils.isMajor(this.getStartDate(), game.getStartDate())){
			return 1;
		}
		return -1;
	}
	
}
