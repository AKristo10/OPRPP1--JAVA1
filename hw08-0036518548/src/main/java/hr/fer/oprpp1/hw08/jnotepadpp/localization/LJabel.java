package hr.fer.oprpp1.hw08.jnotepadpp.localization;
import javax.swing.JLabel;

public class LJabel extends JLabel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String key;
	ILocalizationListener listener;
	ILocalizationProvider provider;
	
	
	
	
	 public LJabel(ILocalizationProvider lp, String key) {
		 this.key = key;
			this.provider = lp;
			this.listener = new ILocalizationListener() {
				
				@Override
				public void localizationChanged() {
					updateLabel();
				}
			};
			updateLabel();
			provider.addLocalizationListener(listener);
	}
	 
	 private void updateLabel() {
			this.setText(provider.getString(key));
	 }
}
