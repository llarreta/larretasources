package ar.com.larreta.tools;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UniqueIDGenerator {

	@Autowired
	private LockApp lockApp;
	
	private Long lastID;
	
	public UniqueIDGenerator(){}
	
	@PostConstruct
	public void initialize() throws Exception {
		lastID = lockApp.getIdentifier();
	}
	
	public synchronized Long nextId(){
		lastID++;
		lockApp.setIdentifier(lastID);
		lockApp.write();
		// Actualizar archivo de propiedades de LockApp
		return lastID;
	}
	
}
