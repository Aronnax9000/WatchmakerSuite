package net.richarddawkins.watchmaker.swing.morphview;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import net.richarddawkins.watchmaker.morph.draw.BoxedMorphCollection;
import net.richarddawkins.watchmaker.morphview.MorphViewConfig;
import net.richarddawkins.watchmaker.morphview.MorphViewPanel;
import net.richarddawkins.watchmaker.morphview.SimpleMorphView;
import net.richarddawkins.watchmaker.morphview.SpeedSlider;
import net.richarddawkins.watchmaker.swing.components.SwingScaleSlider;
import net.richarddawkins.watchmaker.swing.components.SwingSpeedSlider;

public abstract class SwingMorphView extends SimpleMorphView {

    @SuppressWarnings("unused")
  private static Logger logger = Logger.getLogger(
            "net.richarddawkins.watchmaker.swing.morphview.SwingMorphView");

    protected JPanel panel;

    public SwingMorphView(MorphViewConfig config) {
        super(config);
    }



    @Override
    public void addGeneBoxStrip(boolean engineeringMode,
            boolean geneBoxToSide) {
        geneBoxStrip = appData.newGeneBoxStrip(engineeringMode);
        JPanel geneBoxStripPanel = (JPanel) geneBoxStrip.getPanel();
        if (geneBoxToSide) {
            // Nassty nassty JScrollPane will center our content otherwise
            JPanel dummy = new JPanel();
            dummy.add(geneBoxStripPanel);
            JScrollPane scrollPane = new JScrollPane(dummy);
            panel.add(scrollPane, BorderLayout.LINE_END);
        } else {
            panel.add(geneBoxStripPanel, BorderLayout.PAGE_START);
        }
    }

    @Override
    public void addPanel(MorphViewPanel morphViewPanel) {
        panels.add(morphViewPanel);
        Container container = (Container) this.panel;
        container.add((Component) morphViewPanel.getPanel());
        this.setSelectedPanel(morphViewPanel);
    }

    @Override
    public void addPanels() {

    }

    @Override
    public void addSliders() {
        JPanel sliders = new JPanel(new GridLayout(1, 0));
        scaleSlider = new SwingScaleSlider(this);
        sliders.add((JPanel) scaleSlider.getPanel());
        SpeedSlider speedSlider = new SwingSpeedSlider(appData);
        sliders.add((JPanel)speedSlider.getPanel());
        panel.add(sliders, BorderLayout.PAGE_END);
    }

    @Override
    public void createPanel() {
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.black));

    }


    @Override
    public Object getPanel() {
        return panel;
    }

    @Override
    public void removePanel(MorphViewPanel panel) {
        ((Container) panel).remove((Component) panel);
        if (selectedPanel == panel) {
            setSelectedPanel(null);
        }
        panels.remove(panel);
        panel.removePropertyChangeListener(geneBoxStrip);
    }

    @Override
    public void repaint() {
        panel.repaint();
    }
}
