package evcharging.api.validation;

import javax.validation.ValidationException;

public class AbstractBaseValidator {

	protected boolean isNullOrEmpty(final String s) {
		if (s == null || s.equalsIgnoreCase("")) {
			return true;
		}
		return false;
	}

	protected void validateObjectIsNull(Object id, String messages) {
		if (id == null) {
			throw new ValidationException(String.format(messages));
		}
	}

	public boolean isNumber(int number) {
		return number > 0;

	}

}
