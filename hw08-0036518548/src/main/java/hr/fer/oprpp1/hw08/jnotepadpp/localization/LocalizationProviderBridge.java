package hr.fer.oprpp1.hw08.jnotepadpp.localization;

public class LocalizationProviderBridge extends AbstractLocalizationProvider {

	ILocalizationListener listener;
	ILocalizationProvider provider;

	boolean connected;

	public LocalizationProviderBridge(ILocalizationProvider p) {
		this.provider = p;

		listener = new ILocalizationListener() {

			@Override
			public void localizationChanged() {
				fire();
			}
		};
	}

	public void connect() {
		if (connected) {
			// baci neku izniimku ++++++++++++++
		}else {
			provider.addLocalizationListener(listener);
			this.connected = true;
			fire();
		}
		
	}

	public void disconnect() {
		provider.removeLocalizationListener(listener);
		fire();
		this.connected = false;
	}

	@Override
	public String getString(String key) {
		return provider.getString(key);
	}
}
