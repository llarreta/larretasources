package ar.com.larreta.prototypes;

import java.io.Serializable;

public interface Person extends Entity {
	public String getName();
	public void setName(String name);
	public String getSurname();
	public void setSurname(String surname);
	public Serializable getDocumentType();
	public void setDocumentType(Long documentType);
	public String getDocumentNumber();
	public void setDocumentNumber(String documentNumber);
}
