package net.richarddawkins.watchmaker.gui;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.util.Enumeration;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.UIManager;

import net.richarddawkins.watchmaker.morph.common.MorphConfig;
import net.richarddawkins.watchmaker.morph.common.MorphConfigFactory;
import net.richarddawkins.watchmaker.morph.common.MorphType;
import net.richarddawkins.watchmaker.morph.common.MorphTypeNotSupportedException;

public class WatchmakerFrame extends JFrame {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1800967943270519085L;

	private void initFonts() {
        try {
          Font font = Font.createFont(Font.TRUETYPE_FONT, WatchmakerFrame.class
              .getResourceAsStream("/net/richarddawkins/watchmaker/font/ChicagoFLF.ttf"));
          GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
          ge.registerFont(font);
          font = Font.createFont(Font.TRUETYPE_FONT, WatchmakerFrame.class
              .getResourceAsStream("/net/richarddawkins/watchmaker/font/virtue.ttf"));
          ge.registerFont(font);

        } catch (FontFormatException | IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        setUIFont(new javax.swing.plaf.FontUIResource(new Font("ChicagoFLF", Font.PLAIN, 12)));

      }

      private static void setUIFont(javax.swing.plaf.FontUIResource f) {
        Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
          Object key = keys.nextElement();
          Object value = UIManager.get(key);
          if (value instanceof javax.swing.plaf.FontUIResource) {
            UIManager.put(key, f);
          }
        }
      }
      
      public WatchmakerFrame() {
          initFonts();
    	  setTitle("Watchmaker Suite by Richard Dawkins");
          //Create and set up the window.
          setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          setJMenuBar(new JMenuBar());

      }
      WatchmakerTabbedPane tabbedPane = new WatchmakerTabbedPane();
   
      private void initComponents() {

	    this.add(tabbedPane);

	    for (MorphType morphType : MorphType.values()) {
	    	MorphConfigFactory factory = MorphConfigFactory.getInstance(morphType); 
	    	if(factory != null) {
	    		try {
		 	    	MorphConfig config = factory.createConfig();
		 	    	config.setFrame(this);
				    tabbedPane.addMorphConfig(config);
	    		}
	    		catch(MorphTypeNotSupportedException e) {
	    			// MorphTypeNotSupported
	    		}
		    }

	   
    	}
      }
}
