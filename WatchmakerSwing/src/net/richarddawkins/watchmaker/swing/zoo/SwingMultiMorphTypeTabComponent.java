package net.richarddawkins.watchmaker.swing.zoo;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.app.MultiMorphTypeTabbedPanel;
import net.richarddawkins.watchmaker.component.WatchComponent;
import net.richarddawkins.watchmaker.image.ClassicImageLoader;
import net.richarddawkins.watchmaker.image.ClassicImageLoaderService;
import net.richarddawkins.watchmaker.menu.MenuBuilder;
import net.richarddawkins.watchmaker.morphview.MorphViewsTabbedPanel;
import net.richarddawkins.watchmaker.swing.components.SwingWatchPanel;
import net.richarddawkins.watchmaker.swing.images.AWTClassicImage;
import net.richarddawkins.watchmaker.swing.menu.SwingWatchmakerMenuBar;

public class SwingMultiMorphTypeTabComponent extends SwingWatchPanel
        implements WatchComponent {

    private static final long serialVersionUID = -5284721564837850902L;
    protected MultiMorphTypeTabbedPanel pane;
    protected JLabel lblTitle;

    public MultiMorphTypeTabbedPanel getPane() {
        return pane;
    }

    public void setPane(MultiMorphTypeTabbedPanel pane) {
        this.pane = pane;
    }

    public void setName(String name) {
        lblTitle.setText(name);
        btnClose.addActionListener(new MyCloseActionHandler(name));
    }

    protected void setIconFromFilename(String filename) {
        ClassicImageLoader classicImageLoader = ClassicImageLoaderService
        .getInstance().getClassicImageLoader();
        AWTClassicImage classicImage = (AWTClassicImage) classicImageLoader.getPicture(filename);    
        lblIcon.setIcon(new ImageIcon(classicImage.getImage()));
    }

    JButton btnClose = null;
    JLabel lblIcon = null;

    public SwingMultiMorphTypeTabComponent() {
        setLayout(new GridBagLayout());
        setOpaque(false);
        lblTitle = new JLabel();
        btnClose = new JButton("x");
        btnClose.setMargin(new Insets(0, 0, 0, 0));

        lblIcon = new JLabel();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4,4,4,4);
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.weightx = 1;
        add(lblIcon, gbc);

        gbc.gridx++;
        gbc.weightx = 2;
        add(lblTitle, gbc);
        gbc.insets = new Insets(0,0,0,0);
        gbc.gridx++;
        gbc.weightx = 0;
        gbc.weighty = 0;
        add(btnClose, gbc);

    }

    public class MyCloseActionHandler implements ActionListener {

        private String tabName;

        public MyCloseActionHandler(String tabName) {
            this.tabName = tabName;
        }

        public String getTabName() {
            return tabName;
        }

        public void actionPerformed(ActionEvent evt) {
            int index = pane.indexOfTab(getTabName());
            if (index >= 0) {
                pane.removeTabAt(index);
                ((JButton) evt.getSource()).removeActionListener(this);
                MorphViewsTabbedPanel morphViewsTabbedPanel = pane
                        .getSelectedMorphViewsTabbledPanel();
                AppData appData = morphViewsTabbedPanel.getAppData();
                MenuBuilder menuBuilder = appData.getMenuBuilder();
                menuBuilder.buildMenu(SwingWatchmakerMenuBar.getInstance());
            }

        }

    }

    public Icon getIcon() {
        return lblIcon.getIcon();
    }
}


