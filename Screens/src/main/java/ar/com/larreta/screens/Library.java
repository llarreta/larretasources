package ar.com.larreta.screens;

import javax.persistence.Basic;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Library extends ScreenElement {
	private String name;
	private String library;

	public Library(String library, String name){
		setName(name);
		setLibrary(library);
	}
	
	@Basic
	public String getLibrary() {
		return library;
	}

	public void setLibrary(String library) {
		this.library = library;
	}

	@Basic
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
