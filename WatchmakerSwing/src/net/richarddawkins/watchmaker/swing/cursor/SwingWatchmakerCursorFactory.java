package net.richarddawkins.watchmaker.swing.cursor;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.logging.Logger;

import net.richarddawkins.watchmaker.cursor.WatchmakerCursor;
import net.richarddawkins.watchmaker.cursor.WatchmakerCursorFactory;
import net.richarddawkins.watchmaker.image.ClassicImageLoader;
import net.richarddawkins.watchmaker.image.ClassicImageLoaderService;
import net.richarddawkins.watchmaker.swing.images.AWTClassicImage;

public class SwingWatchmakerCursorFactory extends WatchmakerCursorFactory {
    private static Logger logger = Logger.getLogger(
            "net.richarddawkins.watchmaker.swing.cursor.SwingWatchmakerCursorFactory");

    protected Toolkit toolkit;

    protected ClassicImageLoader classicImageLoader;

    @Override
    protected void init() {
        this.toolkit = Toolkit.getDefaultToolkit();
        this.hotspot = new Point(0, 0);
        this.classicImageLoader = ClassicImageLoaderService.getInstance()
                .getClassicImageLoader();
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
        logger.info("New Cursor " + cursorType);
        AWTClassicImage classicImage = (AWTClassicImage) classicImageLoader
                .getPicture(cursorType.imageName());
        Object cursor = toolkit.createCustomCursor(
                (classicImage.getImage()).getSubimage(0, 0, 16, 16), hotspot,
                cursorType.label());
        return cursor;
    }

}
