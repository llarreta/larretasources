package ar.com.larreta.prode.persistence;

import java.util.Collection;

import ar.com.larreta.commons.persistence.dao.StandardDAO;

public interface PlayerDAO extends StandardDAO {
	public Collection getActivePlayers();
}
