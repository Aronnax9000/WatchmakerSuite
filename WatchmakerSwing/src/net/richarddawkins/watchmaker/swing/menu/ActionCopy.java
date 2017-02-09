package net.richarddawkins.watchmaker.swing.menu;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.Icon;

import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.morph.Morph;

public class ActionCopy extends SwingWatchmakerAction {


	private static final long serialVersionUID = 4121419685469500509L;
	protected AppData appData;
	
	public ActionCopy(AppData appData, String name, Icon icon) {
		super(appData, name, icon);
		this.appData = appData;
	}
	public ActionCopy(AppData appData) {
		this(appData, "Copy", null);
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