package ar.com.larreta.smarttrace.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.com.larreta.commons.persistence.dao.impl.LoadArguments;
import ar.com.larreta.commons.services.impl.StandardServiceImpl;
import ar.com.larreta.smarttrace.domain.Container;
import ar.com.larreta.smarttrace.services.ContainerService;

/**
 * @author ignacio.m.larreta
 *
 */
@Service(ContainerServiceImpl.CONTAINER_SERVICE)
@Transactional
public class ContainerServiceImpl extends StandardServiceImpl implements
		ContainerService {
	
	public static final String CONTAINER_SERVICE = "containerService";

	/* (non-Javadoc)
	 * @see ar.com.larreta.smarttrace.services.ContainerService#getContainerLoadingMaterialTypeAndChildContainers(ar.com.larreta.smarttrace.domain.Container)
	 */
	@Override
	public Container getContainerLoadingMaterialTypeAndChildContainers(
			Container container) {
		LoadArguments args = new LoadArguments(Container.class);
		args.addProjectedCollection("childrenContainers").addProjectedProperties("childrenContainers.materialType");
		args.addWhereEqual("id", container.getId());
		return (Container) dao.getEntity(args);
	}

}
