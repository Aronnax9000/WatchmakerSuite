package net.richarddawkins.watchmaker.swing.genebox;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.TimerTask;
import java.util.logging.Logger;

import net.richarddawkins.watchmaker.cursor.WatchmakerCursor;
import net.richarddawkins.watchmaker.cursor.WatchmakerCursorFactory;
import net.richarddawkins.watchmaker.genome.GeneManipulationAdapter;
import net.richarddawkins.watchmaker.genome.GeneManipulationEvent;
import net.richarddawkins.watchmaker.genome.GeneManipulationListener;
import net.richarddawkins.watchmaker.genome.GeneManipulationSupport;
import net.richarddawkins.watchmaker.genome.GooseDirection;
import net.richarddawkins.watchmaker.swing.images.WatchmakerCursors;

public class GeneBoxMouseAdapter extends MouseAdapter
        implements GeneManipulationSupport {
    private static Logger logger = Logger.getLogger(
            "net.richarddawkins.watchmaker.swing.genebox.GeneBoxMouseMotionAdapter");

    enum HorizPos {
        LeftThird, MidThird, RightThird, LeftHalf, RightHalf
    }

    enum VertPos {
        TopRung, MidRung, BottomRung
    }
    
    protected WatchmakerCursorFactory cursors;

    public GeneBoxMouseAdapter(GeneBoxType geneBoxType, WatchmakerCursorFactory cursors) {
        this.geneBoxType = geneBoxType;
        this.cursors = cursors;
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        if (toolkit != null) {
            Object multiClickInterval = toolkit
                    .getDesktopProperty("awt.multiClickInterval");
            if (multiClickInterval != null) {
                doubleClickInterval = (Integer) multiClickInterval;
                logger.fine("DoubleClickInterval: " + multiClickInterval);
            }
        }

    }

    protected GeneManipulationAdapter geneManipulationAdapter = new GeneManipulationAdapter();
    private java.util.Timer longPressTimer;

    long doubleClickInterval = 500;
    protected GeneBoxType geneBoxType;

    
    @Override
    public void mouseMoved(MouseEvent e) {

        Component component = e.getComponent();
        int x = e.getX();
        int y = e.getY();

        HorizPos mouseHoriz;

        VertPos mouseVert = null;
        if (geneBoxType == GeneBoxType.clickForPicker) {
            component.setCursor((Cursor)cursors.getCursor(WatchmakerCursor.magnify));
        } else {
            if (geneBoxType == GeneBoxType.leftRightOnly) {
                int halfWidth = component.getWidth() / 2;
                if (x < halfWidth)
                    mouseHoriz = HorizPos.LeftHalf;
                else
                    mouseHoriz = HorizPos.RightHalf;
            } else {
                int thirdWidth = component.getWidth() / 3;
                if (x < thirdWidth)
                    mouseHoriz = HorizPos.LeftThird;
                else if (x < 2 * thirdWidth)
                    mouseHoriz = HorizPos.MidThird;
                else
                    mouseHoriz = HorizPos.RightThird;

                if (geneBoxType == GeneBoxType.leftRightUpDownEquals) {
                    int height = component.getHeight();
                    int thirdHeight = height / 3;
                    int twoThirdsHeight = 2 * height / 3;
                    logger.fine("GeneBox Height: " + height + " 1/3: "
                            + thirdHeight + " 2/3:" + twoThirdsHeight);
                    if (y < thirdHeight)
                        mouseVert = VertPos.TopRung;
                    else if (y < twoThirdsHeight)
                        mouseVert = VertPos.MidRung;
                    else
                        mouseVert = VertPos.BottomRung;
                }
            }

            switch (mouseHoriz) {
            case LeftThird:
            case LeftHalf:
                component.setCursor((Cursor)cursors.getCursor(WatchmakerCursor.leftArrow));
                break;
            case RightThird:
            case RightHalf:
                component.setCursor((Cursor)cursors.getCursor(WatchmakerCursor.rightArrow));
                break;
            case MidThird:
                if (mouseVert != null) {
                    switch (mouseVert) {
                    case TopRung:
                        component.setCursor((Cursor)cursors.getCursor(WatchmakerCursor.upArrow));
                        break;
                    case BottomRung:
                        component.setCursor((Cursor)cursors.getCursor(WatchmakerCursor.downArrow));
                        break;
                    case MidRung:
                    default:
                        component.setCursor((Cursor)cursors.getCursor(WatchmakerCursor.equalsSign));
                    }
                } else {
                    component.setCursor((Cursor)cursors.getCursor(WatchmakerCursor.equalsSign));
                }
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        mouseMoved(e);
    }

    @Override
    public void mousePressed(final MouseEvent e) {
        if (longPressTimer == null) {
            longPressTimer = new java.util.Timer();
        }
        longPressTimer.schedule(new TimerTask() {
            public void run() {
                mouseClicked(e);
            }
        }, doubleClickInterval, 17);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (longPressTimer != null) {
            longPressTimer.cancel();
            longPressTimer = null;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        Cursor cursor = e.getComponent().getCursor();


        GooseDirection direction = null;
        if (cursors.isCursorType(WatchmakerCursor.magnify, cursor)) {
            direction = GooseDirection.launchPicker;
        } else if (cursors.isCursorType(WatchmakerCursor.leftArrow, cursor))
            direction = GooseDirection.leftArrow;
        else if (cursors.isCursorType(WatchmakerCursor.rightArrow, cursor))
            direction = GooseDirection.rightArrow;
        else if (cursors.isCursorType(WatchmakerCursor.upArrow, cursor))
            direction = GooseDirection.upArrow;
        else if (cursors.isCursorType(WatchmakerCursor.downArrow, cursor))
            direction = GooseDirection.downArrow;
        else if (cursors.isCursorType(WatchmakerCursor.equalsSign, cursor))
            direction = GooseDirection.equalsSign;
        if (direction != null)
            fireGeneManipulationEvent(new GeneManipulationEvent(direction));

    }

    @Override
    public void fireGeneManipulationEvent(GeneManipulationEvent event) {
        geneManipulationAdapter.fireGeneManipulationEvent(event);
    }

    @Override
    public void addGeneManipulationListener(GeneManipulationListener listener) {
        geneManipulationAdapter.addGeneManipulationListener(listener);

    }

    @Override
    public void removeAllGeneManipulationListeners() {
        geneManipulationAdapter.removeAllGeneManipulationListeners();

    }

    @Override
    public void removeGeneManipulationListener(
            GeneManipulationListener listener) {
        geneManipulationAdapter.removeGeneManipulationListener(listener);

    }

    @Override
    public GeneManipulationListener[] getGeneManipulationListeners() {
        return geneManipulationAdapter.getGeneManipulationListeners();
    }
}