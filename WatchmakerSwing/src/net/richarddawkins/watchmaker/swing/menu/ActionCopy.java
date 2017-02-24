package net.richarddawkins.watchmaker.swing.menu;

import java.awt.Event;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.KeyStroke;

import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.morph.Morph;

public class ActionCopy extends SwingWatchmakerAction {


	private static final long serialVersionUID = 4121419685469500509L;
	
	public ActionCopy(AppData appData) {
		super(appData, "Copy", null, KeyStroke.getKeyStroke(KeyEvent.VK_C, Event.ALT_MASK));
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
	    Morph morph = appData.getMorphOfTheHour();
	    Image image = (Image) morph.getImage();
	    ImageSelection imgSel = new ImageSelection(image);
	    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(imgSel, null);
	}
	 class ImageSelection implements Transferable
	 {
	   private Image image;

	   public ImageSelection(Image image)
	   {
	     this.image = image;
	   }

	   // Returns supported flavors
	   public DataFlavor[] getTransferDataFlavors()
	   {
	     return new DataFlavor[] { DataFlavor.imageFlavor };
	   }

	   // Returns true if flavor is supported
	   public boolean isDataFlavorSupported(DataFlavor flavor)
	   {
	     return DataFlavor.imageFlavor.equals(flavor);
	   }

	   // Returns image
	   public Object getTransferData(DataFlavor flavor)
	       throws UnsupportedFlavorException, IOException
	   {
	     if (!DataFlavor.imageFlavor.equals(flavor))
	     {
	       throw new UnsupportedFlavorException(flavor);
	     }
	     return image;
	   }
	 }
}