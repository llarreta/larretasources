package ar.com.larreta.prode.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.WordUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import ar.com.larreta.commons.domain.User;

@Entity
@Table(name = "player")
@PrimaryKeyJoinColumn(name="id")
//Solamente trae los que no fueron borrados logicamente
@Where(clause="deleted IS NULL")
//Borrado logico (No puede eliminarse el usuario root)
@SQLDelete (sql="UPDATE User SET deleted=CURRENT_TIMESTAMP WHERE id=? and nick!='root'")
public class Player extends User {

	private String name;
	private String surname;
	private Set<CompetitionScore> competitionScores;
	private Set<Prediction> predictions;
	private Player friend;
	
	private byte[] image;
	
	@Transient
	public String getFriendPlayerName(){
		if (friend!=null){
			return friend.getAppPlayerName();
		}
		return StringUtils.EMPTY;
	}
	
	@Transient
	public String getAppPlayerName(){
		return getNick() + " (" + name + " " + surname + ")";
	}
	
	@ManyToOne (fetch=FetchType.LAZY, targetEntity=Player.class)
	@JoinColumn (name="idFriend")
	public Player getFriend() {
		return friend;
	}

	public void setFriend(Player friend) {
		this.friend = friend;
	}
	
	@Column
	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	@Basic
	public String getSurname() {
		return WordUtils.capitalizeFully(surname);
	}

	public void setSurname(String surname) {
		this.surname = WordUtils.capitalizeFully(surname);
	}
	
	@Basic
	public String getName() {
		return WordUtils.capitalizeFully(name);
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = WordUtils.capitalizeFully(name);
	}


	@OneToMany (mappedBy="player", fetch=FetchType.LAZY, cascade=CascadeType.ALL, targetEntity=RealCompetitionScore.class)
	public Set<CompetitionScore> getCompetitionScores() {
		return competitionScores;
	}

	/**
	 * @param competitionScore the participateOf to set
	 */
	public void setCompetitionScores(Set<CompetitionScore> competitionScore) {
		this.competitionScores = competitionScore;
	}
	
	public void addParticipateOf(CompetitionScore competition){
		if (competitionScores==null){
			competitionScores=new HashSet<CompetitionScore>();
		}
		competitionScores.add(competition);
	}


	@OneToMany (mappedBy="player", fetch=FetchType.LAZY, cascade=CascadeType.ALL, targetEntity=Prediction.class)
	public Set<Prediction> getPredictions() {
		return predictions;
	}

	/**
	 * @param predictions the predictions to set
	 */
	public void setPredictions(Set<Prediction> predictions) {
		this.predictions = predictions;
	}

	public void addPredictions(Prediction prediction){
		if (predictions==null){
			predictions=new HashSet<Prediction>();
		}
		predictions.add(prediction);
	}
	
}
