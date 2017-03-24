package net.richarddawkins.watchmaker.swing.fossil;

import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.geom.BoxManager;
import net.richarddawkins.watchmaker.geom.GridBoxManager;
import net.richarddawkins.watchmaker.menu.WatchmakerMenu;
import net.richarddawkins.watchmaker.menu.WatchmakerMenuBar;
import net.richarddawkins.watchmaker.morphview.MorphViewConfig;
import net.richarddawkins.watchmaker.morphview.fossil.FossilRecordMorphView;
import net.richarddawkins.watchmaker.swing.menu.SwingWatchmakerCheckBoxMenuItem;
import net.richarddawkins.watchmaker.swing.menu.SwingWatchmakerMenuItem;
import net.richarddawkins.watchmaker.swing.morphview.SwingMorphView;
import net.richarddawkins.watchmaker.swing.zoo.SwingMultiMorphTypeTabbedPanel;

public class SwingFossilRecordMorphView extends SwingMorphView implements FossilRecordMorphView {

    public SwingFossilRecordMorphView(MorphViewConfig config) {
        super(config);
        // TODO Auto-generated constructor stub
    }
    @Override
    public BoxManager newBoxManager() {
        return new GridBoxManager(1,1);
    }

    @Override
    public void seed() {
        // TODO Auto-generated method stub
        
    }
    
    SwingWatchmakerCheckBoxMenuItem recordingFossils;
    @Override
    public void buildMenu(WatchmakerMenuBar menuBar) {
       WatchmakerMenu menu = menuBar.getMenu("Operation");
        menu.add(new SwingWatchmakerMenuItem("Initialize Fossil Record"));
        menu.add(new SwingWatchmakerMenuItem("Play Back Fossils"));
        recordingFossils = new SwingWatchmakerCheckBoxMenuItem(
                "Recording Fossils") {
                    @Override
                    public void menuSelectionChanged(boolean isIncluded) {
                        boolean selected = isSelected();
                        AppData appData = SwingMultiMorphTypeTabbedPanel.getInstance().getSelectedAppData();
                        appData.setRecordingFossils(selected);
                    }

                    private static final long serialVersionUID = 1L;
        };
        
        menu.add(recordingFossils);

    }
    @Override
    public void cleanMenu(WatchmakerMenuBar menuBar) {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void updateMenu(WatchmakerMenuBar menuBar) {
        AppData appData = SwingMultiMorphTypeTabbedPanel.getInstance().getSelectedAppData();
        recordingFossils.setSelected(appData.isRecordingFossils());
        
    }

 }
