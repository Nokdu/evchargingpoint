package evcharging.api.exception;

import javax.servlet.http.HttpSession;
import javax.validation.ValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import evcharging.api.model.ErrorResponse;

@ControllerAdvice
public class DefaultExceptionHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultExceptionHandler.class);

	@Autowired
	GeneralException generalException;

	@ExceptionHandler(GeneralException.class)
	@ResponseBody
	public ResponseEntity<ErrorResponse> handle(GeneralException ex, HttpSession session) {
		return handle(ex, ex.getTitle(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	@ResponseBody
	public ResponseEntity<ErrorResponse> handle(DataIntegrityViolationException ex, HttpSession session) {
		return handle(ex, "Error", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(ValidationException.class)
	@ResponseBody
	public ResponseEntity<ErrorResponse> handle(ValidationException ex, HttpSession session) {
		return handle(ex, "Error", HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Throwable.class)
	@ResponseBody
	public final ResponseEntity<ErrorResponse> handle(final Throwable ex, final HttpSession session) {

		return handle(generalException, session);

	}

	private ResponseEntity<ErrorResponse> handle(Exception exception, String title, HttpStatus httpStatus) {

		ErrorResponse response = new ErrorResponse(exception.getMessage(), title);

		LOGGER.error("{}: {}", exception.getClass().getName(), exception.getMessage());
		LOGGER.error("Error is: ", exception);

		return new ResponseEntity<ErrorResponse>(response, httpStatus);
	}
}
