package net.richarddawkins.watchmaker.swing;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.logging.Logger;
import java.util.prefs.InvalidPreferencesFormatException;
import java.util.prefs.Preferences;

import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

public class Main {
	private static Logger logger = Logger.getLogger("net.richarddawkins.watchmaker.swing.Main");

	public static void setUIFont(javax.swing.plaf.FontUIResource f) {
		Enumeration<Object> keys = UIManager.getDefaults().keys();
		while (keys.hasMoreElements()) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if (value != null && value instanceof javax.swing.plaf.FontUIResource) {
				logger.info("UIManager key " + key);
				UIManager.put(key, f);
			}
		}
	}

	public static void main(String[] args) {
		try {
			
			InputStream prefsInputStream = Main.class.getResourceAsStream("/net/richarddawkins/watchmaker/prefs/preferences.xml");
			Preferences.importPreferences(prefsInputStream);
			
	        Preferences prefs = Preferences.userRoot().node("net/richarddawkins/watchmaker/ui");

	        String fontName = prefs.get("font", "");
			
	        logger.info("Font:" + fontName);
			
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			InputStream fontStream = Main.class.getResourceAsStream("/net/richarddawkins/watchmaker/font/" + fontName + ".ttf");
			
			Font font = Font.createFont(Font.TRUETYPE_FONT, fontStream);
			logger.info("Font name: " + font.getName());
			ge.registerFont(font);
			FontUIResource fontUIResource = new javax.swing.plaf.FontUIResource(fontName, Font.PLAIN, 12);
			logger.info("FontUIResource " + fontUIResource);
			setUIFont(fontUIResource);
		} catch (IOException e) {
			logger.warning(e.getMessage());
		} catch (FontFormatException e) {
			logger.warning(e.getMessage());
		} catch (InvalidPreferencesFormatException e) {
			logger.warning(e.getMessage());
		}

		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				SwingWatchmakerFrame.getInstance();
			}
		});
	}
}
