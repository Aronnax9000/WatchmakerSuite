package net.richarddawkins.watchmaker.swing.menu;

import java.awt.Component;
import java.awt.LayoutManager;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.border.Border;

import net.richarddawkins.watchmaker.menu.WatchmakerMenu;
import net.richarddawkins.watchmaker.menu.WatchmakerMenuBar;

public class SwingWatchmakerMenuBar extends JMenuBar
        implements WatchmakerMenuBar {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Override
    public void add(WatchmakerMenu menu) {
        super.add((JMenu) menu);
    }

    private static SwingWatchmakerMenuBar instance;

    protected SwingWatchmakerMenuBar() {
    }

    public synchronized static WatchmakerMenuBar getInstance() {
        if (instance == null) {
            instance = new SwingWatchmakerMenuBar();
        }
        return instance;
    }

    @Override
    public void remove(String name) {
        Component[] menus = this.getComponents();
        for (Component menu : menus) {
            if (menu.getName() == name) {
                this.remove(menu);
                break;
            }
        }
    }

    @Override
    public void setLayout(Object layout) {
        super.setLayout((LayoutManager) layout);

    }

    @Override
    public void setBorder(Object border) {
        super.setBorder((Border) border);

    }

    @Override
    public void add(Object newComponent) {
        super.add((Component) newComponent);

    }

    @Override
    public void add(Object newComponent, Object constraints) {
        super.add((Component) newComponent, constraints);

    }

    @Override
    public WatchmakerMenu getMenu(String name) {
        int menuCount = this.getMenuCount();
        for(int i = 0; i < menuCount; i++) {
            SwingWatchmakerMenu menu = (SwingWatchmakerMenu)this.getMenu(i);
            if(menu.getText().equals(name)) {
                return menu;
            }
        }

        return null;
    }

}
