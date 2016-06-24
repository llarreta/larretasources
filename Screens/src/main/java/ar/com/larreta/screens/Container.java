package ar.com.larreta.screens;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

public interface Container extends Serializable {
	Set<ContainedElement> getContainedElements();
	void setContainedElements(Set<ContainedElement> containedElements);
	Collection<ScreenElement> getOrdererElements();
	void add(Integer orderIndex, ScreenElement element);
	void add(ScreenElement element);
	SearchMap getSearchMap();
}
