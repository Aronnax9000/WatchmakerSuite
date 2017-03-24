package net.richarddawkins.watchmaker.swing.morphview;

import java.awt.Cursor;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.component.WatchComponent;
import net.richarddawkins.watchmaker.component.WatchPanel;
import net.richarddawkins.watchmaker.cursor.WatchmakerCursor;
import net.richarddawkins.watchmaker.image.ClassicImageLoader;
import net.richarddawkins.watchmaker.image.ClassicImageLoaderService;
import net.richarddawkins.watchmaker.morphview.MorphView;
import net.richarddawkins.watchmaker.morphview.MorphViewsTabbedPanel;
import net.richarddawkins.watchmaker.swing.images.AWTClassicImage;

public class SwingMorphViewsTabbedPanel extends SwingWatchTabbedPane
        implements MorphViewsTabbedPanel {
    private static Logger logger = Logger.getLogger(
            "net.richarddawkins.watchmaker.swing.morphview.SwingMorphViewsTabbedPanel");

    protected AppData appData;

    @Override
    public AppData getAppData() {
        return appData;
    }

    @Override
    public void setAppData(AppData appData) {
        this.appData = appData;
    }

    private static final long serialVersionUID = -9105080336982806166L;

    public SwingMorphViewsTabbedPanel(AppData appData) {
        this.appData = appData;
        addChangeListener(new TabChangeListener());
    }

    protected Vector<MorphView> morphViews = new Vector<MorphView>();

    public String uniquify(String name) {
        String newName = name;
        boolean unique = false;
        int counter = 0;
        while (!unique) {
            boolean found = false;
            for (MorphView morphView : morphViews) {
                String morphViewName = morphView.getName();
                if (newName.equals(morphViewName)) {
                    found = true;
                    break;
                }
            }
            if (found)
                newName = name + " " + ++counter;
            else
                unique = true;
        }
        return newName;
    }

    public void addMorphView(MorphView view) {
        logger.log(Level.INFO, "addMorphView " + view.getName());
        view.setName(uniquify(view.getName()));
        morphViews.add(view);

        ClassicImageLoader loader = ClassicImageLoaderService.getInstance()
                .getClassicImageLoader();
        AWTClassicImage classicImage = (AWTClassicImage) loader
                .getPicture(view.getIcon());
        addTab(view.getName(), new ImageIcon(classicImage.getImage()),
                (WatchComponent) view.getPanel(), view.getToolTip());

        SwingMorphViewTabComponent tabComponent = new SwingMorphViewTabComponent();
        tabComponent.setSwingMorphViewsTabbedPanel(this);
        tabComponent.setIcon(view.getIcon());
        tabComponent.setName(view.getName());
        int tabCount = this.getTabCount() - 1;
        setTabComponentAt(tabCount, tabComponent);
        setSelectedIndex(tabCount);
        appData.setSelectedMorphView(view);
    }

    class TabChangeListener implements ChangeListener {
        public void stateChanged(ChangeEvent e) {
            int selectedIndex = getSelectedIndex();
            if (selectedIndex != -1) {
                MorphView morphView = morphViews.get(getSelectedIndex());
                morphView.gainFocus();
                morphView.getSelectedPanel().gainFocus();
                WatchPanel morphViewPanel = (WatchPanel) morphView.getPanel();
                Cursor cursor = ((JComponent) morphViewPanel.getComponent())
                        .getCursor();
                appData.setHighlighting(appData.getWatchmakerCursorFactory()
                        .isCursorType(WatchmakerCursor.highlight, cursor));

            }
        }

    }

    // @Override
    // public MorphView getSelectedMorphView() {
    // MorphView selectedMorphView = (MorphView) this.getSelectedComponent();
    // return selectedMorphView;
    // }
    @Override
    public Vector<MorphView> getMorphViews() {
        return morphViews;

    }

}
