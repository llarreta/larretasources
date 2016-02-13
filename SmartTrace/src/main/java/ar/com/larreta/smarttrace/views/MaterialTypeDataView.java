package ar.com.larreta.smarttrace.views;

import java.util.List;

import ar.com.larreta.commons.controllers.Paginator;
import ar.com.larreta.commons.views.DataView;
import ar.com.larreta.smarttrace.controllers.MaterialTypePaginator;
import ar.com.larreta.smarttrace.domain.Classification;
import ar.com.larreta.smarttrace.domain.Provider;
import ar.com.larreta.smarttrace.domain.UnitType;

/**
 * @author ignacio.m.larreta
 *
 */
public class MaterialTypeDataView extends DataView{
	
	private List<UnitType> 			unitsTypes;
	private List<Provider> 			providers;
	private List<Classification> 	classifications;
	
	public MaterialTypeDataView() {
		paginator = newPaginator();
		paginator.setDataView(this);
	}
	
	@Override
	protected Paginator newPaginator() {
		return new MaterialTypePaginator();
	}
	
	/**
	 * @return the unitsTypes
	 */
	public List<UnitType> getUnitsTypes() {
		return unitsTypes;
	}
	/**
	 * @param unitsTypes the unitsTypes to set
	 */
	public void setUnitsTypes(List<UnitType> unitsTypes) {
		this.unitsTypes = unitsTypes;
	}
	/**
	 * @return the providers
	 */
	public List<Provider> getProviders() {
		return providers;
	}
	/**
	 * @param providers the providers to set
	 */
	public void setProviders(List<Provider> providers) {
		this.providers = providers;
	}
	/**
	 * @return the classifications
	 */
	public List<Classification> getClassifications() {
		return classifications;
	}
	/**
	 * @param classifications the classifications to set
	 */
	public void setClassifications(List<Classification> classifications) {
		this.classifications = classifications;
	}
}