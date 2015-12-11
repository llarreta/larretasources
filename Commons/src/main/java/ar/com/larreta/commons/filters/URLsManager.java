package ar.com.larreta.commons.filters;

import java.util.HashMap;
import java.util.Map;

import ar.com.larreta.commons.domain.User;
import ar.com.larreta.commons.utils.SessionUtils;

public class URLsManager {
	
	public static final String EQUAL = "=";

	public static final String PREFIX = "?";

	public static final String ACTUAL_INDEX = "ActualIndex";

	private static URLsManager instance;
	
	private Map<User, Integer> actualIndex;
	private Map<User, Map<Integer, String>> navigationHistory;
	
	private URLsManager(){
		actualIndex = new HashMap<User, Integer>();
		navigationHistory = new HashMap<User, Map<Integer,String>>();
	}
	
	public static URLsManager getInstance(){
		if (instance==null){
			instance = new URLsManager();
		}
		return instance;
	}

	public Integer getNewIndex(){
		Integer index = getActualIndex();
		return ++index;
	}

	public Integer getActualIndex() {
		Integer index = actualIndex.get(SessionUtils.getActualUser());
		if (index==null){
			index = -1;
			actualIndex.put(SessionUtils.getActualUser(), index);
		}
		return index;
	}

	public String getForwardURL(){
		return getURL(getActualIndex()+1);
	}
	
	public String getActualURL(){
		return getURL(getActualIndex());
	}

	public String getPreviousURL(){
		return getURL(getActualIndex()-1);
	}
	
	private String getURL(Integer index) {
		StringBuilder url = new StringBuilder();
		String startURL = getStartURL(index);
		if (startURL!=null){
			url.append(startURL);
			url.append(PREFIX);
			url.append(ACTUAL_INDEX);
			url.append(EQUAL);
			url.append(index);
		}
		return url.toString();
	}

	private String getStartURL(Integer index) {
		String startURL = getNavigationHistory().get(index);
		return startURL;
	}
	
	public void setActualIndex(Integer index){
		actualIndex.put(SessionUtils.getActualUser(), index);
	}
	
	public void putActualURL(String url){
		if ((url!=null) && (!url.equals(getStartURL(getActualIndex())))){
			Integer index = getNewIndex();
			setActualIndex(index);
			Map<Integer, String> history = getNavigationHistory();
			history.put(index, url);
		}
	}

	private Map<Integer, String> getNavigationHistory() {
		Map<Integer, String> history = navigationHistory.get(SessionUtils.getActualUser());
		if (history==null){
			history = new HashMap<Integer, String>();
			navigationHistory.put(SessionUtils.getActualUser(), history);
		}
		return history;
	}
	
	public void cleanUser(){
		actualIndex.remove(SessionUtils.getActualUser());
		navigationHistory.remove(SessionUtils.getActualUser());
	}
	
}
