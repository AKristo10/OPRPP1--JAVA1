package hr.fer.oprpp1.hw08.jnotepadpp.localization;

public interface ILocalizationProvider {

	String getString(String key);
	
	public void addLocalizationListener(ILocalizationListener listener);
	
	public void removeLocalizationListener(ILocalizationListener listener);
	
}
