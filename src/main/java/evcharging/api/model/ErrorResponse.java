package evcharging.api.model;

public class ErrorResponse {
	private String message;
	private String title;

	public ErrorResponse(String message, String title) {

		this.message = message;
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public String getTitle() {
		return title;
	}
}
