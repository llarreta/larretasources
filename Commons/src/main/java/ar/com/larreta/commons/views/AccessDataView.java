package ar.com.larreta.commons.views;

import ar.com.larreta.commons.controllers.AccessPaginator;
import ar.com.larreta.commons.controllers.Paginator;

public class AccessDataView extends DataView {

	@Override
	protected Paginator newPaginator() {
		return new AccessPaginator();
	}
}
