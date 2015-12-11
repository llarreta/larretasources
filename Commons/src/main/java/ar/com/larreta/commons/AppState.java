package ar.com.larreta.commons;


public class AppState extends AppObjectImpl {
	
	private static AppState INSTANCE;
	private Integer stateLevel=0;
	
	public static AppState getInstance(){
		if (INSTANCE==null){
			INSTANCE = new AppState();
		}
		return INSTANCE;
	}
	
	public void advanceLevel(){
		stateLevel++;
	}
	
	public Boolean isInitializing(){
		return stateLevel==0;
	}

	
	
}
