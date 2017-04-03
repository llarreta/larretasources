package ar.com.larreta.prototypes;

public class ObjectJSONable extends JSONable {

	private Object target;

	public ObjectJSONable(Object target){
		this.target = target;
	}
	
	public Object getTarget() {
		return target;
	}

	public void setTarget(Object target) {
		this.target = target;
	}

}
