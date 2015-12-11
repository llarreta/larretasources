package ar.com.larreta.prode.domain;

import ar.com.larreta.commons.domain.Entity;

public abstract class GameState extends Entity {
	
	public static final GameState LOCAL = new LocalGameState();
	public static final GameState VISITOR = new VisitorGameState();
	public static final GameState EQUALS = new EqualsGameState();

	public GameState(Long id){
		setId(id);
	}
	
}
