/**
 * 
 */
package co.com.directv.sdii.reports.commands.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.stock.SpecialCommentFacadeBeanLocal;
import co.com.directv.sdii.model.pojo.collection.SpecialCommentResponse;
import co.com.directv.sdii.model.vo.SpecialCommentVO;
import co.com.directv.sdii.reports.commands.BaseCommand;
import co.com.directv.sdii.reports.commands.CMDSpecialCommentsByReferenceIdLocal;
import co.com.directv.sdii.reports.commands.ICommand;
import co.com.directv.sdii.reports.dto.SpecialCommentDTO;

/**
 * @author jvelezmu
 * 
 */
@Stateless(name = "CMDSpecialCommentsByReferenceId", mappedName = "ejb/CMDSpecialCommentsByReferenceId")
public class CMDSpecialCommentsByReferenceId extends BaseCommand implements
		ICommand,CMDSpecialCommentsByReferenceIdLocal {

	private List<String> fieldList = new ArrayList<String>();
	private static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	@EJB
	private SpecialCommentFacadeBeanLocal specialCommentFacadeBeanLocal;

	public CMDSpecialCommentsByReferenceId() {
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
			SpecialCommentResponse r;
			HashMap<String, String> map = getParams(args);
			Long refId = null;
			String strRefId = map.get("refId");
			if (strRefId != null && !strRefId.isEmpty())
				refId = Long.parseLong(strRefId);
			r = specialCommentFacadeBeanLocal.getSpecialCommentsByReferenceId(
					refId, null);
			List<SpecialCommentDTO> response = new ArrayList<SpecialCommentDTO>();
			if (r.getSpecialCommentVO() != null) {
				for (SpecialCommentVO vo : r.getSpecialCommentVO()) {
					SpecialCommentDTO dto = new SpecialCommentDTO();
					dto.setComment(vo.getCommentDescription());
					dto.setCommentDate(UtilsBusiness.dateToString(vo.getCommentDate(),DATE_FORMAT));
					dto.setUserName(vo.getUserName());
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
