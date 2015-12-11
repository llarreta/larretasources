package ar.com.larreta.commons.controllers;

import org.hibernate.criterion.Order;
import org.primefaces.model.SortOrder;

public class AccessPaginator extends Paginator {

	private static final String USER_ACCESS_DATE = "userAccessDate";

	@Override
	public Order getOrder(SortOrder sortOrder, String field) {
		if (field==null){
			return Order.desc(USER_ACCESS_DATE);
		}
		return super.getOrder(sortOrder, field);
	}

}
