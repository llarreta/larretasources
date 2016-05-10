package ar.com.larreta.screens;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Where;

@MappedSuperclass
public abstract class Container extends ScreenElement {
	protected Collection<ScreenElement> elements = new ArrayList<ScreenElement>();

	@OneToMany (mappedBy="parent", fetch=FetchType.LAZY, cascade=CascadeType.ALL, targetEntity=ScreenElement.class)
	@Where(clause="deleted IS NULL")
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
