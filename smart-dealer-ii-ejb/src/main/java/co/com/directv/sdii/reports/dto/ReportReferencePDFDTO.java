package co.com.directv.sdii.reports.dto;

import java.util.List;

public class ReportReferencePDFDTO implements java.io.Serializable  {

	private static final long serialVersionUID = 7217584631703469837L;

	private List<PreLoadGenericToPrintDTO> preLoad;
	private List<ReferencePDFDTO> refPDF;
	private List<ReferencePDFDTOnoSeralized> refPDFnoS;
	private List<ReferencePDFDTOModification> refPDFDTOModificationList;
	private List<ReferencePDFDTOInconsistency> refPDFDTOInconsistencyList;
	
	public List<PreLoadGenericToPrintDTO> getPreLoad() {
		return preLoad;
	}
	public void setPreLoad(List<PreLoadGenericToPrintDTO> preLoad) {
		this.preLoad = preLoad;
	}
	public List<ReferencePDFDTO> getRefPDF() {
		return refPDF;
	}
	public void setRefPDF(List<ReferencePDFDTO> refPDF) {
		this.refPDF = refPDF;
	}
	public List<ReferencePDFDTOnoSeralized> getRefPDFnoS() {
		return refPDFnoS;
	}
	public void setRefPDFnoS(List<ReferencePDFDTOnoSeralized> refPDFnoS) {
		this.refPDFnoS = refPDFnoS;
	}
	public List<ReferencePDFDTOModification> getRefPDFDTOModificationList() {
		return refPDFDTOModificationList;
	}
	public void setRefPDFDTOModificationList(
			List<ReferencePDFDTOModification> refPDFDTOModificationList) {
		this.refPDFDTOModificationList = refPDFDTOModificationList;
	}
	public List<ReferencePDFDTOInconsistency> getRefPDFDTOInconsistencyList() {
		return refPDFDTOInconsistencyList;
	}
	public void setRefPDFDTOInconsistencyList(
			List<ReferencePDFDTOInconsistency> refPDFDTOInconsistencyList) {
		this.refPDFDTOInconsistencyList = refPDFDTOInconsistencyList;
	}
	
}
