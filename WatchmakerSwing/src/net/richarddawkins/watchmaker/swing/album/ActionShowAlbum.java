package net.richarddawkins.watchmaker.swing.album;

import java.awt.event.ActionEvent;
import java.util.Vector;

import javax.swing.Icon;

import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.morph.MorphConfig;
import net.richarddawkins.watchmaker.morph.draw.BoxedMorphCollection;
import net.richarddawkins.watchmaker.swing.menu.SwingWatchmakerAction;

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
		MorphConfig config = appData.getMorphConfig();
		Vector<BoxedMorphCollection> albums = config.getAlbums();
		if(albums != null) {
			for(BoxedMorphCollection album: albums) {
				appData.getMorphViewsTabbedPane().addMorphView(new SwingAlbumMorphView(appData, album));
			}
		}
	}

}