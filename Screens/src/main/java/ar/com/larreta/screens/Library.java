package ar.com.larreta.screens;

public abstract class Library extends ScreenElement {
	private String name;
	private String library;

	public Library(String library, String name){
		setName(name);
		setLibrary(library);
	}
	
	public String getLibrary() {
		return library;
	}

	public void setLibrary(String library) {
		this.library = library;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
