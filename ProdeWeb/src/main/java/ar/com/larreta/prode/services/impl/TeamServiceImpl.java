package ar.com.larreta.prode.services.impl;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.com.larreta.commons.services.impl.StandardServiceImpl;
import ar.com.larreta.prode.domain.Team;
import ar.com.larreta.prode.services.TeamService;

@Service
@Transactional
public class TeamServiceImpl extends StandardServiceImpl implements TeamService {
	public Collection load(){
		return load(Team.class);
	}
	
}
