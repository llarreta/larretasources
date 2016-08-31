/**
 * 
 */
package co.com.directv.sdii.reports.commands.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.stock.ReferenceFacadeBeanLocal;
import co.com.directv.sdii.model.pojo.collection.ReferenceResponse;
import co.com.directv.sdii.model.vo.ReferenceVO;
import co.com.directv.sdii.reports.commands.BaseCommand;
import co.com.directv.sdii.reports.commands.CMDPreloadRefByWhSourceLocal;
import co.com.directv.sdii.reports.commands.ICommand;
import co.com.directv.sdii.reports.dto.PreloadReferenceDTO;

/**
 * @author jvelezmu
 * 
 */
@Stateless(name = "CMDPreloadRefByWhSource", mappedName = "ejb/CMDPreloadRefByWhSource")
public class CMDPreloadRefByWhSource extends BaseCommand implements
		ICommand,CMDPreloadRefByWhSourceLocal {

	private List<String> fieldList = new ArrayList<String>();

	@EJB
	private ReferenceFacadeBeanLocal referenceFacadeBean;

	public CMDPreloadRefByWhSource() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.com.directv.sdii.excelgen.ICommand#execute(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> execute(String args) throws BusinessException {
		try {
			ReferenceResponse r;
			HashMap<String, String> map = getParams(args);
			Long idWhSource = null;
			String strIdWhSource = map.get("idWhSource");
			if (strIdWhSource != null && !strIdWhSource.isEmpty())
				idWhSource = Long.parseLong(strIdWhSource);
			r = referenceFacadeBean.getPreloadedReferences(
					idWhSource, null);
			List<PreloadReferenceDTO> response = new ArrayList<PreloadReferenceDTO>();
			if (r.getReferencesVO() != null) {
				for (ReferenceVO vo : r.getReferencesVO()) {
					PreloadReferenceDTO dto = new PreloadReferenceDTO();
					dto.setReferenceId(vo.getId().toString());
					dto.setWhSourceName(vo.getWhSource());
					dto.setWhTargetName(vo.getWhTarget());
					dto.setIsPrepaidRef(vo.getIsPrepaidRef());
					response.add(dto);
				}
			}
			return (List<T>) response;
		} catch (Throwable e) {
			throw this.manageException(e);
		}
	}

	@Override
	public List<String> getFieldList() {
		return fieldList;
	}

	@Override
	public void setFieldList(List<String> fieldList) {
		this.fieldList = fieldList;
	}
}
