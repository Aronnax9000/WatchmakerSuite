package net.richarddawkins.watchmaker.swing.components;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.logging.Logger;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.richarddawkins.watchmaker.geom.BoxManager;
import net.richarddawkins.watchmaker.morph.draw.BoxedMorphCollection;
import net.richarddawkins.watchmaker.morphview.MorphView;
import net.richarddawkins.watchmaker.morphview.MorphViewPanel;
import net.richarddawkins.watchmaker.morphview.MorphViewWidget;
import net.richarddawkins.watchmaker.util.Globals;

public class SwingScaleSlider
        implements MorphViewWidget, ChangeListener, PropertyChangeListener {
    private static Logger logger = Logger.getLogger(
            "net.richarddawkins.watchmaker.swing.components.SwingScaleSlider");

    static final int SCALE_INIT = 0; // initial frames per second
    static final int SCALE_MAX = +8;
    static final int SCALE_MIN = -SCALE_MAX;
    private static final long serialVersionUID = 1L;
    protected BoxManager boxManager;
    protected JPanel panel;
    protected PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    protected int scale = SCALE_INIT;
    protected JSlider slider;
    JLabel label = new JLabel();

    public void updateLabel(double scale) {
        label.setText("Scale " + Math.pow(Globals.zoomBase, scale) * 100 + "% (" + Globals.zoomBase + "^" + scale + ")");
    }
    
    public SwingScaleSlider(MorphView morphView) {
        morphView.addPropertyChangeListener("selectedPanel", this);
        this.panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(label, constraints);
        constraints.gridy = 1;

        slider = new JSlider(JSlider.HORIZONTAL, SCALE_MIN, SCALE_MAX,
                SCALE_INIT);
        slider.addChangeListener(this);
        // Turn on labels at major tick marks.
        slider.setMajorTickSpacing(8);
        slider.setPaintTicks(false);
        slider.setPaintLabels(false);
        panel.add(slider, constraints);
    }
    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(propertyName, listener);
    }

    public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(propertyName, listener);
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }
    

    @Override
    public void setPanel(Object newValue) {
        panel = (JPanel) newValue;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("scale")) {
            this.scale = (Integer) evt.getNewValue();
            slider.setValue(scale);
            updateLabel(scale);
        } else if (evt.getPropertyName().equals("selectedPanel")) {
            MorphView morphView = (MorphView)evt.getSource();
            MorphViewPanel morphViewPanel = morphView.getSelectedPanel();
            BoxedMorphCollection boxedMorphCollection = morphViewPanel.getBoxedMorphCollection();
            if(boxedMorphCollection == null) {
                logger.warning("SwingScaleSlider.propertyChange(): BoxedMorphCollection is null for morphViewPanel " + morphViewPanel + ". Scale slider not setting box manager.");
            } else {
                BoxManager selectedPanelBoxManager = boxedMorphCollection.getBoxManager();
                this.setBoxManager(selectedPanelBoxManager);
            }
        }
    }

    public void setBoxManager(BoxManager newValue) {
        BoxManager oldValue = boxManager;
        if(oldValue != null) {
            oldValue.removePropertyChangeListener("scale", this);
            pcs.removePropertyChangeListener("scale", oldValue);
        }
        if(newValue != null) {
            newValue.addPropertyChangeListener("scale", this);
            pcs.addPropertyChangeListener("scale", newValue);
        }
        boxManager = newValue;
        this.scale = newValue.getScale();
        slider.setValue(scale);
    }


    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider slider = (JSlider) e.getSource();
        int newValue = slider.getValue();
        int oldValue = scale;
        if(newValue != oldValue) {
            this.scale = newValue;
            pcs.firePropertyChange("scale", oldValue, newValue);
        }
    }

}
