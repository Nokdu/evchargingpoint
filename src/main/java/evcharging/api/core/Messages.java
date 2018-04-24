package evcharging.api.core;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class Messages {

	@Autowired
	private MessageSource messageSource;

	private Locale getLocale() {
		return LocaleContextHolder.getLocale();
	}

	public String get(final String key) {
		return get(key, null, getLocale());
	}

	public String get(final String key, final String defaultValue) {
		return get(key, defaultValue, getLocale());
	}

	public String get(final String key, final String defaultValue, final Locale locale) {
		return messageSource.getMessage(key, null, defaultValue, locale);
	}
}
