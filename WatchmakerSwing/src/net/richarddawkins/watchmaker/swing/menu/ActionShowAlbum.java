package net.richarddawkins.watchmaker.swing.menu;

import java.awt.event.ActionEvent;

import javax.swing.Icon;

import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.morph.draw.BoxedMorphCollection;
import net.richarddawkins.watchmaker.swing.album.SwingAlbumMorphView;

public class ActionShowAlbum extends SwingWatchmakerAction {


	private static final long serialVersionUID = 4121419685469500509L;
	protected AppData appData;
	
	public ActionShowAlbum(AppData appData, String name, Icon icon) {
		super(appData, name, icon);
		this.appData = appData;
	}
	public ActionShowAlbum(AppData appData) {
		this(appData, "Show Album", null);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		for(BoxedMorphCollection album: appData.getMorphConfig().getAlbums()) {
			appData.getMorphViewsTabbedPane().addMorphView(new SwingAlbumMorphView(appData, album));
		}
	}

}