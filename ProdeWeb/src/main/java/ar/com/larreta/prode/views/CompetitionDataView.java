/**
 * 
 */
package ar.com.larreta.prode.views;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ar.com.larreta.commons.domain.Entity;
import ar.com.larreta.commons.exceptions.PaginatorNotFoundException;
import ar.com.larreta.commons.views.DataView;
import ar.com.larreta.prode.controllers.CompetitionPaginator;
import ar.com.larreta.prode.domain.Competition;
import ar.com.larreta.prode.domain.Round;

public class CompetitionDataView extends DataView implements Serializable {
	
	private List<Round> rounds = new ArrayList<Round>();
	private Round selectedRound;
	private Date registrationStart;
	private Date registrationEnd;
	private String name;
	
	public Date getRegistrationStart() {
		return registrationStart;
	}

	public void setRegistrationStart(Date registrationStart) {
		this.registrationStart = registrationStart;
	}

	public Date getRegistrationEnd() {
		return registrationEnd;
	}

	public void setRegistrationEnd(Date registrationEnd) {
		this.registrationEnd = registrationEnd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Round> getRounds() {
		return rounds;
	}

	public void setRounds(List<Round> rounds) {
		this.rounds = rounds;
	}
	
	public Round getSelectedRound() {
		return selectedRound;
	}

	public void setSelectedRound(Round selectedRound) {
		this.selectedRound = selectedRound;
	}

	@Override
	public Entity newSelected() {
		return new Competition();
	}

	
	public CompetitionPaginator getPaginator() throws PaginatorNotFoundException {
		return (CompetitionPaginator) super.getPaginator();
	}

	@Autowired
	public void setPaginator(CompetitionPaginator paginator) {
		super.setPaginator(paginator);
	}
}
