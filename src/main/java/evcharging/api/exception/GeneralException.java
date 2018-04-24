package evcharging.api.exception;

import org.springframework.stereotype.Component;

@Component
public class GeneralException extends RuntimeException {

	private int code;
	private String title;

	public GeneralException() {
		// TODO Auto-generated constructor stub
	}

	GeneralException(int code, String message, String title, Throwable cause) {
		super(message, cause);
		this.code = code;
		this.title = title;
	}

	public int getCode() {
		return code;
	}

	public String getTitle() {
		return title;
	}
}
