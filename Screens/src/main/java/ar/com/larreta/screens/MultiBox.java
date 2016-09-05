package ar.com.larreta.screens;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.primefaces.model.DualListModel;

import ar.com.larreta.commons.AppManager;

@Entity
@Table(name = "multiBox")
@DiscriminatorValue(value = "multiBox")
@PrimaryKeyJoinColumn(name=ar.com.larreta.commons.domain.Entity.ID)
public class MultiBox extends ListSelector {

	private String sourceCaption;
	private String targetCaption;
	private String propertyItemLabel;
	private String effect="bounce";
	
	private Boolean showSourceControls 	= Boolean.FALSE;
	private Boolean showTargetControls 	= Boolean.FALSE;
	private Boolean showCheckbox 		= Boolean.FALSE;
	private Boolean showSourceFilter 	= Boolean.FALSE;
	private Boolean showTargetFilter	= Boolean.FALSE;
	
	private String filterMatchMode = "contains";
	
	@Basic
	public Boolean getShowSourceControls() {
		return showSourceControls;
	}

	public void setShowSourceControls(Boolean showSourceControls) {
		this.showSourceControls = showSourceControls;
	}

	@Basic
	public Boolean getShowTargetControls() {
		return showTargetControls;
	}

	public void setShowTargetControls(Boolean showTargetControls) {
		this.showTargetControls = showTargetControls;
	}

	@Basic
	public Boolean getShowCheckbox() {
		return showCheckbox;
	}

	public void setShowCheckbox(Boolean showCheckbox) {
		this.showCheckbox = showCheckbox;
	}

	@Basic
	public Boolean getShowSourceFilter() {
		return showSourceFilter;
	}

	public void setShowSourceFilter(Boolean showSourceFilter) {
		this.showSourceFilter = showSourceFilter;
	}

	@Basic
	public Boolean getShowTargetFilter() {
		return showTargetFilter;
	}

	public void setShowTargetFilter(Boolean showTargetFilter) {
		this.showTargetFilter = showTargetFilter;
	}

	@Basic
	public String getFilterMatchMode() {
		return filterMatchMode;
	}

	public void setFilterMatchMode(String filterMatchMode) {
		this.filterMatchMode = filterMatchMode;
	}

	@Basic
	public String getEffect() {
		return effect;
	}

	public void setEffect(String effect) {
		this.effect = effect;
	}

	@Basic
	public String getPropertyItemLabel() {
		return propertyItemLabel;
	}

	public void setPropertyItemLabel(String propertyItemLabel) {
		this.propertyItemLabel = propertyItemLabel;
	}

	@Basic
	public String getSourceCaption() {
		return sourceCaption;
	}
	
	@Transient
	public String getSourceCaptionEvaluated() {
		return (String) ScreenUtils.evaluate(getSourceCaption());
	}

	public void setSourceCaption(String sourceCaption) {
		this.sourceCaption = sourceCaption;
	}

	@Basic
	public String getTargetCaption() {
		return targetCaption;
	}

	@Transient
	public String getTargetCaptionEvaluated() {
		return (String) ScreenUtils.evaluate(getTargetCaption());
	}
	
	public void setTargetCaption(String targetCaption) {
		this.targetCaption = targetCaption;
	}


	@Transient
	public DualListModel getDualListValue(){
		return getDualListModel(AppManager.getInstance().getStandardService().load(ScreenUtils.getClass(getEntityType()), null, null, null, null, getLazyPropertiesSplitted()), 
				(Collection) getBindingPropertyValue());
	}
	
	public void setDualListValue(DualListModel dualListModel){
		setBindingPropertyValue(new HashSet(dualListModel.getTarget()));
	}
	
	/**
	 * Retorna un DualListModel a partir del target y source pasado por parametro
	 * Quitando del source lo que ya se encuentra en el target
	 * @param setTarget
	 * @param setSource
	 * @return
	 */
	private DualListModel getDualListModel(Collection setSource, Collection setTarget) {
		List target = new ArrayList(getNotNullSet(setTarget));
		List source = new ArrayList(getNotNullSet(setSource));
		if (target!=null){
			source.removeAll(target);
		}
		DualListModel dualListModel = new DualListModel(source, target);
		return dualListModel;
	}
	
	/**
	 * En caso del que la collection sea nulo lo inicializa
	 * @param source
	 * @return
	 */
	private Collection getNotNullSet(Collection source) {
		Collection target = source;
		if (target==null){
			target = new ArrayList();
		}
		return target;
	}
	
}
