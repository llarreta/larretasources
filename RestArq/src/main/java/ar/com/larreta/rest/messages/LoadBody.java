package ar.com.larreta.rest.messages;

public class LoadBody extends Body {
	
	private Integer firstResult;
	private Integer maxResults;
	
	public Integer getFirstResult() {
		return firstResult;
	}
	public void setFirstResult(Integer firstResult) {
		this.firstResult = firstResult;
	}
	public Integer getMaxResults() {
		return maxResults;
	}
	public void setMaxResults(Integer maxResults) {
		this.maxResults = maxResults;
	}
	
}
