package hr.fer.oprpp1.hw08.jnotepadpp.localization;

import java.util.ArrayList;

public abstract class AbstractLocalizationProvider implements ILocalizationProvider {

	ArrayList<ILocalizationListener> list = new ArrayList<ILocalizationListener>();
	

	@Override
	public void addLocalizationListener(ILocalizationListener listener) {
		list.add(listener);
	}

	@Override
	public void removeLocalizationListener(ILocalizationListener listener) {
		list.remove(listener);
	}
	
	public void fire() {
		list.stream().forEach(l -> l.localizationChanged());
	}
	

}
