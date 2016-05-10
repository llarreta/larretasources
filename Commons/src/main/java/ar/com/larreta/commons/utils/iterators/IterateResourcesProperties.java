package ar.com.larreta.commons.utils.iterators;

import java.net.URL;

public class IterateResourcesProperties extends IterateResources {

	public IterateResourcesProperties(String name, final PropertyAction action) {
		super(name, new UrlAction() {
			
			@Override
			public void process(URL url) {
				IterableProperties iterableProperties = new IterableProperties(url, action);
				iterableProperties.start();
			}
		});
	}

}
