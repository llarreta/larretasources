package ar.com.larreta.rest.messages;

public class DeleteResponse extends Response {
	
	private Long targetId;

	public Long getTargetId() {
		return targetId;
	}

	public void setTargetId(Long targetId) {
		this.targetId = targetId;
	}
}
