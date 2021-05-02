package hr.fer.oprpp1.hw08.jnotepadpp.localization;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.text.DefaultEditorKit;

public abstract class LocalizableAction extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String key;
	ILocalizationListener listener;
	ILocalizationProvider provider;
	
	
	public LocalizableAction(String key, ILocalizationProvider lp) {
		this.key = key;
		this.provider = lp;
		String translate = provider.getString(key);
		this.putValue(NAME, translate);
		this.listener = new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				String translate = provider.getString(LocalizableAction.this.key);
				LocalizableAction.this.putValue(NAME, translate);
			}
		};
		provider.addLocalizationListener(listener);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		
	}


	
	



}
