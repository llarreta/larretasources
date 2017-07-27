package ar.com.larreta.stepper.messages;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ar.com.larreta.tools.Const;

@Component @Scope(Const.PROTOTYPE)
public class LoadBody<E extends JSONable> extends Body {

	private Integer firstResult;
	private Integer maxResults;
	private JSONableCollection<E> result;
	
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
	public JSONableCollection<E> getResult() {
		return result;
	}
	public void setResult(JSONableCollection<E> result) {
		this.result = result;
	}
	
}
