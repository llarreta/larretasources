package ar.com.larreta.screens;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import ar.com.larreta.commons.AppObjectImpl;
import ar.com.larreta.screens.exceptions.NotUniqueObjectException;

/**
 * se encarga de clasificar los componentes de un formulario
 * para luego poder realizar busquedas facilmente 
 */
public class SearchMap extends AppObjectImpl {

	private Map<Long, ScreenElement> elementsById = new HashMap<Long, ScreenElement>();
	private Map<Class, Map<Long, ScreenElement>> elementsByClasses = new HashMap<Class, Map<Long,ScreenElement>>();
	private Collection<Container> containers = new ArrayList<Container>();
	

	public void add(Collection<ContainedElement> elements) throws NotUniqueObjectException{
		if (elements!=null){
			Iterator<ContainedElement> it = elements.iterator();
			while (it.hasNext()) {
				ContainedElement containedElement = (ContainedElement) it.next();
				add(containedElement.getElement());
			}
		}
	}
	
	public void add(ScreenElement element) throws NotUniqueObjectException{
		ScreenElement old = recursiveFind(element.getId());
		if (old!=null && old!=element){
			getLog().error("Se encontro objeto identico con id:" + old.getIdValue());
			throw new NotUniqueObjectException();
		}
		
		elementsById.put(element.getId(), element);

		Map<Long, ScreenElement> elementsByClass = elementsByClasses.get(element.getClass());
		if (elementsByClass==null){
			elementsByClass = new HashMap<Long, ScreenElement>();
		}
		elementsByClass.put(element.getId(), element);
		elementsByClasses.put(element.getClass(), elementsByClass);
		
		if (element instanceof Container) {
			containers.add((Container) element);
		}
	}

	/**
	 * Busca un ScreenElement en el Container actual
	 * mediante el identificador pasado por parametro
	 * @param id
	 * @return
	 */
	public ScreenElement find(Long id){
		return elementsById.get(id);
	}
	
	/**
	 * Busca todos los ScreenElement en el Container actual
	 * mediante el tipo o clase de los elementos buscados
	 * @param type
	 * @return
	 */
	public Collection<ScreenElement> find(Class type){
		Map<Long, ScreenElement> elements = elementsByClasses.get(type);
		if (elements!=null){
			return elements.values();
		}
		return null;
	}
	
	/**
	 *  Busca un ScreenElement en el Container actual
	 *  mediante el identificador pasado por parametro
	 *  de manera recursiva en todos los contenedores
	 *  que estan incluidos en este
	 * @param id
	 * @return
	 */
	public ScreenElement recursiveFind(Long id){
		ScreenElement element = find(id);
		Iterator<Container> it = containers.iterator();
		while ((element==null) && (it.hasNext())) {
			Container container = (Container) it.next();
			element = container.getSearchMap().recursiveFind(id);
		}
		return element;
	}

	/**
	 * Busca todos los ScreenElement en el Container actual
	 * mediante el tipo o clase de los elementos buscados
	 * de manera recursiva en todos los contenedores
	 * que estan incluidos en este
	 * @param type
	 * @return
	 */
	public Collection<ScreenElement> recursiveFind(Class type){
		Collection<ScreenElement> elements = new ArrayList<ScreenElement>();
		Collection<ScreenElement> finded = find(type);
		if (finded!=null){
			elements.addAll(finded);
		}
		Iterator<Container> it = containers.iterator();
		while (it.hasNext()) {
			Container container = (Container) it.next();
			finded = container.getSearchMap().recursiveFind(type);
			if (finded!=null){
				elements.addAll(finded);
			}
		}
		return elements;
	}
	
	
	
}
