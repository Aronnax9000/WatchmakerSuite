package net.richarddawkins.watchmaker.swing.cursor;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.logging.Logger;

import net.richarddawkins.watchmaker.cursor.WatchmakerCursor;
import net.richarddawkins.watchmaker.cursor.WatchmakerCursorFactory;
import net.richarddawkins.watchmaker.swing.images.ClassicImageLoader;

public class SwingWatchmakerCursorFactory extends WatchmakerCursorFactory {
    private static Logger logger = Logger.getLogger(
            "net.richarddawkins.watchmaker.swing.cursor.SwingWatchmakerCursorFactory");

    protected Toolkit toolkit;

    @Override
    protected void init() {
        this.toolkit = Toolkit.getDefaultToolkit();
        this.hotspot = new Point(4, 4);
    }


    
    @Override
    public Object newCustomCursor(BufferedImage image) {
        WatchmakerCursor cursorType = WatchmakerCursor.custom;
        Cursor cursor = toolkit.createCustomCursor(
                image.getSubimage(0, 0, 16, 16), hotspot, cursorType.label());
        return cursor;
    }

    @Override
    public Object newCursor(WatchmakerCursor cursorType) {
        logger.info("New Cursor " + cursorType );
        Object cursor = toolkit.createCustomCursor(
                ClassicImageLoader.getPicture(cursorType.imageName())
                .getImage().getSubimage(0, 0, 16, 16),
        hotspot, cursorType.label());
        return cursor;
    }




}
