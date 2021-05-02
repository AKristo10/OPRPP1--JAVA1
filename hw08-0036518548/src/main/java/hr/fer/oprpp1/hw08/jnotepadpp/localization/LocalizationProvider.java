package hr.fer.oprpp1.hw08.jnotepadpp.localization;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocalizationProvider extends AbstractLocalizationProvider {

	private String language;
	private ResourceBundle bundle;
	static LocalizationProvider instance;

	public LocalizationProvider() {
		setLanguage("en");
		Locale locale = Locale.forLanguageTag(language);
		this.bundle = ResourceBundle.getBundle("hr.fer.oprpp1.hw08.jnotepadpp.localization.prijevodi", locale);
	}

	public static LocalizationProvider getInstance() {
		if (instance != null) {
			return instance;
		} else {
			return instance = new LocalizationProvider();
		}
	}

	@Override
	public String getString(String key) {
		return bundle.getString(key);
	}

	public void setLanguage(String str) {
		this.language = str;
		Locale locale = Locale.forLanguageTag(language);
		this.bundle = ResourceBundle.getBundle("hr.fer.oprpp1.hw08.jnotepadpp.localization.prijevodi", locale);
		fire();

	}
	public String getLanguage() {
		return language;
	}

}
