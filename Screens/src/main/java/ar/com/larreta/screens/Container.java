package ar.com.larreta.screens;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

public class Container extends ScreenElement {
	protected Collection<ScreenElement> elements = new ArrayList<ScreenElement>();

	public Collection<ScreenElement> getElements() {
		return elements;
	}

	public void setElements(Set<ScreenElement> elements) {
		this.elements = elements;
	}
	
	public void add(ScreenElement element){
		elements.add(element);
	}
}
