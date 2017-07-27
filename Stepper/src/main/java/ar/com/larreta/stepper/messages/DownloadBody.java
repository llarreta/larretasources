package ar.com.larreta.stepper.messages;

import org.springframework.stereotype.Component;

@Component
public class DownloadBody extends Body {

	private String fileContent;

	public String getFileContent() {
		return fileContent;
	}

	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}
	
}
