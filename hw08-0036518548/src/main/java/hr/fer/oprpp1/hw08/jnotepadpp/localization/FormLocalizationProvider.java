package hr.fer.oprpp1.hw08.jnotepadpp.localization;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

public class FormLocalizationProvider extends LocalizationProviderBridge {

	public FormLocalizationProvider(ILocalizationProvider provider, JFrame jframe) {
		super(provider);
		WindowListener wlistener = new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				disconnect();
			}

			@Override
			public void windowOpened(WindowEvent e) {
				connect();
			}
		};

		jframe.addWindowListener(wlistener);
	}

}
