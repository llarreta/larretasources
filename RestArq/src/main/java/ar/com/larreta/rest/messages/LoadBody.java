package ar.com.larreta.rest.messages;

public class LoadBody<E extends JSONable> extends Body {

	private Integer firstResult;
	private Integer maxResults;
	private JSONableCollectionBody<E> result;
	
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
	public JSONableCollectionBody<E> getResult() {
		return result;
	}
	public void setResult(JSONableCollectionBody<E> result) {
		this.result = result;
	}
	
}
