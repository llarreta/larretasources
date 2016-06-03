package ar.com.larreta.commons.services.args;

import java.io.Serializable;
import java.util.Collection;

import ar.com.larreta.commons.persistence.dao.args.LoadArguments;

public class ServiceInfo implements Serializable {

	private Collection data;
	private LoadArguments arguments;
	
	public ServiceInfo(LoadArguments arguments, Collection data){
		setArguments(arguments);
		setData(data);
	}
	
	public Collection getData() {
		return data;
	}
	public void setData(Collection data) {
		this.data = data;
	}
	public LoadArguments getArguments() {
		return arguments;
	}
	public void setArguments(LoadArguments arguments) {
		this.arguments = arguments;
	}
	
	
	
}
