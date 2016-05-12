package ar.com.larreta.screens;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.Where;

@MappedSuperclass
public abstract class Container extends ScreenElement {
	
	protected Set<ScreenElement> elements = new HashSet<ScreenElement>();

	@OneToMany (mappedBy="parent", fetch=FetchType.EAGER, cascade=CascadeType.ALL, targetEntity=ScreenElement.class)
	@Where(clause="deleted IS NULL")
	public Set<ScreenElement> getElements() {
		return elements;
	}
	
	@Transient
	public Collection<ScreenElement> getOrdererElements(){
		List<ScreenElement> ordererElements = new ArrayList<ScreenElement>(getElements());
		Collections.sort(ordererElements, new Comparator<ScreenElement>() {
			public int compare(ScreenElement elementA, ScreenElement elementB) {
				return elementA.getOrder().compareTo(elementB.getOrder());
			}
		});
		return ordererElements;
	}

	public void setElements(Set<ScreenElement> elements) {
		this.elements = elements;
	}
	
	public void add(ScreenElement element){
		elements.add(element);
	}
}
