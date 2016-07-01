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

import org.apache.log4j.Logger;
import org.hibernate.annotations.Where;

@MappedSuperclass
public abstract class StandardContainer extends ScreenElement implements Container {

	private static final Logger LOGGER = Logger.getLogger(StandardContainer.class);
	
	protected Set<ContainedElement> containedElements = new HashSet<ContainedElement>();
	private SearchMap searchMap = new SearchMap();

	@Transient
	public SearchMap getSearchMap() {
		return searchMap;
	}

	@OneToMany (mappedBy="container", fetch=FetchType.EAGER, cascade=CascadeType.ALL, targetEntity=ContainedElement.class)
	@Where(clause="deleted IS NULL")
	public Set<ContainedElement> getContainedElements() {
		return containedElements;
	}

	public void setContainedElements(Set<ContainedElement> containedElements) {
		try {
			this.containedElements = containedElements;
			searchMap.add(containedElements);
		} catch (Exception e){
			LOGGER.error("Ocurrio un error", e);
		}
	}

	@Transient
	public Collection<ScreenElement> getOrdererElements(){
		List<ContainedElement> ordererElements = new ArrayList<ContainedElement>(getContainedElements());
		Collections.sort(ordererElements, new Comparator<ContainedElement>() {
			public int compare(ContainedElement elementA, ContainedElement elementB) {
				return elementA.getOrder().compareTo(elementB.getOrder());
			}
		});
		List<ScreenElement> elements = new ArrayList<ScreenElement>();
		java.util.Iterator<ContainedElement> it = ordererElements.iterator();
		while (it.hasNext()) {
			ContainedElement containedElement = (ContainedElement) it.next();
			ScreenElement screenElement = containedElement.getElement();
			elements.add(screenElement);
		}
		return elements;
	}

	public void add(Integer orderIndex, ScreenElement element){
		try {
			containedElements.add(new ContainedElement(orderIndex, this, element));
			searchMap.add(element);
		} catch (Exception e){
			LOGGER.error("Ocurrio un error", e);
		}
	}
	
	public void add(ScreenElement element){
			add(0, element);
	}
	
}
