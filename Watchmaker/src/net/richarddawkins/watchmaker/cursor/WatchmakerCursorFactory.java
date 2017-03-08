package net.richarddawkins.watchmaker.cursor;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.logging.Logger;

public abstract class WatchmakerCursorFactory {
    private static Logger logger = Logger.getLogger(
            "net.richarddawkins.watchmaker.cursor.WatchmakerCursorFactory");

    public abstract Object newCustomCursor(BufferedImage image);
    public abstract Object newCursor(WatchmakerCursor cursorType);
    protected Point hotspot;
    protected HashMap<WatchmakerCursor, Object> cursors;
    
    /**
     * Initialize any resources (such as GUI toolkits) 
     * needed by the factory before loadAllCursors is called by
     * the constructor. 
     */
    
    protected void init() {}
    
    /**
     * Load the standard Watchmaker cursors by calls to getCursor.
     */
    protected void loadStandardCursors() {
        for(WatchmakerCursor cursorType: WatchmakerCursor.values()) {
            // Initialize the cursors, except for the custom one.
            if(cursorType != WatchmakerCursor.custom) {
                this.getCursor(cursorType);
            }
        }
    }
    
    public WatchmakerCursorFactory() {
        this.cursors = new HashMap<WatchmakerCursor, Object>();
        this.init();
        this.loadStandardCursors();
    }
    
    public boolean isCursorType(WatchmakerCursor cursorType, Object object) {
        return cursors.get(cursorType) == object;
    }
    
    public void putCursor(WatchmakerCursor cursorType, Object cursor) {
        cursors.put(cursorType, cursor);
    }
    

    public Object getCursor(WatchmakerCursor cursorType) {
        Object cursor = cursors.get(cursorType);
        
        if (cursor == null) {
            cursor = newCursor(cursorType);
            cursors.put(cursorType, cursor);
        } else {
//            logger.fine("getCursor cache hit " + cursorType + " cursor: " + cursor);
        }
        return cursor;
    }
   
}
