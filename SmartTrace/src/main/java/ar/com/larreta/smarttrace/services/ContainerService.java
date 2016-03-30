package ar.com.larreta.smarttrace.services;

import ar.com.larreta.commons.services.StandardService;
import ar.com.larreta.smarttrace.domain.Container;

/**
 * @author ignacio.m.larreta
 *
 */
public interface ContainerService extends StandardService {
	
	public Container getContainerLoadingMaterialTypeAndChildContainers(Container container);
	
}
