package net.richarddawkins.watchmaker.morphs.arthro.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import net.richarddawkins.watchmaker.morphs.arthro.ArthromorphConfig;
import net.richarddawkins.watchmaker.morphs.arthro.genome.type.Concentration;
import net.richarddawkins.watchmaker.morphs.arthro.genome.type.Pressure;
import net.richarddawkins.watchmaker.resourceloader.ClassicImageLoader;
import net.richarddawkins.watchmaker.resourceloader.Messages;
import net.richarddawkins.watchmaker.swing.SwingAppData;

public class ArthromorphsEngineer extends JFrame {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  protected SwingAppData swingAppData;
  protected ArthromorphConfig config;

  JCheckBox animalTrunkMut = new JCheckBox("Animal Trunk");
  JCheckBox animalLegsMut = new JCheckBox("Animal Legs");
  JCheckBox animalClawsMut = new JCheckBox("Animal Claws");
  JCheckBox sectionTrunkMut = new JCheckBox("Section Trunk");
  JCheckBox sectionLegsMut = new JCheckBox("Section Legs");
  JCheckBox sectionClawsMut = new JCheckBox("Section Claws");
  JCheckBox segmentTrunkMut = new JCheckBox("Segment Trunk");
  JCheckBox segmentLegsMut = new JCheckBox("Segment Legs");
  JCheckBox segmentClawsMut = new JCheckBox("Segment Claws");
  JCheckBox legsMut = new JCheckBox("Legs");
  JCheckBox clawsMut = new JCheckBox("Claws");

  JCheckBox lengthMut = new JCheckBox("Length");
  JCheckBox heightMut = new JCheckBox("Height");
  JCheckBox angleMut = new JCheckBox("Angle");
  JCheckBox duplicationMut = new JCheckBox("Duplication");
  JCheckBox deletionMut = new JCheckBox("Deletion");
  JRadioButton focusFirst = new JRadioButton("Focus on 1st seg");
  JRadioButton focusLast = new JRadioButton("Focus on last seg");
  JRadioButton focusNone = new JRadioButton("No focus");

  {
    ButtonGroup group = new ButtonGroup();
    group.add(focusFirst);
    group.add(focusLast);
    group.add(focusNone);
  }

  JRadioButton mutPressurePlus = new JRadioButton("+");
  JRadioButton mutPressureZero = new JRadioButton("0");
  JRadioButton mutPressureMinus = new JRadioButton("-");

  {
    ButtonGroup group = new ButtonGroup();
    group.add(mutPressurePlus);
    group.add(mutPressureZero);
    group.add(mutPressureMinus);
  }

  public ArthromorphsEngineer(SwingAppData swingAppData) {
    super("Allowed Mutations");
    this.swingAppData = swingAppData;
    config = (ArthromorphConfig) swingAppData.getMorphConfig();
    this.addComponentListener(new ComponentAdapter() {
      public void componentShown(ComponentEvent e) {
        animalTrunkMut.setSelected(config.isAnimalTrunkMut());
        animalLegsMut.setSelected(config.isAnimalLegsMut());
        animalClawsMut.setSelected(config.isAnimalClawsMut());
        sectionTrunkMut.setSelected(config.isSectionTrunkMut());
        sectionLegsMut.setSelected(config.isSectionLegsMut());
        sectionClawsMut.setSelected(config.isSectionClawsMut());
        segmentTrunkMut.setSelected(config.isSegmentTrunkMut());
        segmentLegsMut.setSelected(config.isSegmentLegsMut());
        segmentClawsMut.setSelected(config.isSegmentClawsMut());
        legsMut.setSelected(config.isLegsMut());
        clawsMut.setSelected(config.isClawsMut());
        focusFirst.setSelected(config.getFocusOfAttention() == Concentration.FirstSegmentOnly);
        focusLast.setSelected(config.getFocusOfAttention() == Concentration.LastSegmentOnly);
        focusNone.setSelected(config.getFocusOfAttention() == Concentration.AnySegment);
        lengthMut.setSelected(config.isWidthMut());
        heightMut.setSelected(config.isHeightMut());
        angleMut.setSelected(config.isAngleMut());
        duplicationMut.setSelected(config.isDuplicationMut());
        deletionMut.setSelected(config.isDeletionMut());
        mutPressurePlus.setSelected(config.getMutationPressure() == Pressure.Positive);
        mutPressureZero.setSelected(config.getMutationPressure() == Pressure.Zero);
        mutPressureMinus.setSelected(config.getMutationPressure() == Pressure.Negative);
      }
    });
    JPanel panel = new JPanel(new GridBagLayout());
    panel.setPreferredSize(new Dimension(342, 379));
    this.getContentPane().add(panel);
    GridBagConstraints constraints = new GridBagConstraints();

    // Right Side
    panel.add(createCentreLeftPanel(), constraints);
    constraints.gridx = 1;
    constraints.fill = GridBagConstraints.VERTICAL;
    panel.add(createCentreRightPanel(), constraints);
  }

  private JPanel createTagmaMutsCheckBoxPanel() {
    JPanel panel = new JPanel(new GridBagLayout());
    GridBagConstraints constraints = new GridBagConstraints();
    constraints.anchor = GridBagConstraints.WEST;
    // panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

    constraints.gridy = 0;
    panel.add(animalTrunkMut, constraints);

    constraints.gridy = 1;
    panel.add(animalLegsMut, constraints);

    constraints.gridy = 2;
    panel.add(animalClawsMut, constraints);

    constraints.gridy = 3;
    panel.add(sectionTrunkMut, constraints);

    constraints.gridy = 4;
    panel.add(sectionLegsMut, constraints);

    constraints.gridy = 5;
    panel.add(sectionClawsMut, constraints);

    constraints.gridy = 6;
    panel.add(segmentTrunkMut, constraints);

    constraints.gridy = 7;
    panel.add(segmentLegsMut, constraints);

    constraints.gridy = 8;
    panel.add(segmentClawsMut, constraints);

    constraints.gridy = 9;
    panel.add(legsMut, constraints);

    constraints.gridy = 10;
    panel.add(clawsMut, constraints);
    return panel;
  }

  private JPanel createTagmaMutsButtonPanel() {
    JPanel panel = new JPanel(new GridLayout(1, 2));

    // panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    panel.add(new JButton(new AbstractAction("All") {
      /**
       * 
       */
      private static final long serialVersionUID = 1L;

      @Override
      public void actionPerformed(ActionEvent e) {
        animalTrunkMut.setSelected(true);
        animalLegsMut.setSelected(true);
        animalClawsMut.setSelected(true);
        sectionTrunkMut.setSelected(true);
        sectionLegsMut.setSelected(true);
        ;
        sectionClawsMut.setSelected(true);
        segmentTrunkMut.setSelected(true);
        segmentLegsMut.setSelected(true);
        segmentClawsMut.setSelected(true);
        legsMut.setSelected(true);
        ;
        clawsMut.setSelected(true);
        ;
      }
    }));

    panel.add(new JButton(new AbstractAction("None") {
      /**
       * 
       */
      private static final long serialVersionUID = 1L;

      @Override
      public void actionPerformed(ActionEvent e) {
        animalTrunkMut.setSelected(false);
        animalLegsMut.setSelected(false);
        animalClawsMut.setSelected(false);
        sectionTrunkMut.setSelected(false);
        sectionLegsMut.setSelected(false);
        sectionClawsMut.setSelected(false);
        segmentTrunkMut.setSelected(false);
        segmentLegsMut.setSelected(false);
        segmentClawsMut.setSelected(false);
        legsMut.setSelected(false);
        clawsMut.setSelected(false);
      }
    }));
    return panel;
  }

  private JPanel createFocusPanel() {
    JPanel panel = new JPanel(new GridLayout(3, 1));
    panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    panel.add(focusFirst);
    panel.add(focusLast);
    panel.add(focusNone);
    return panel;
  }

  public JPanel createTagmaMutsPanel() {
    JPanel panel = new JPanel(new GridBagLayout());
    panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    GridBagConstraints constraints = new GridBagConstraints();
    constraints.gridx = 0;
    constraints.gridy = 0;
    constraints.weighty = 8;
    panel.add(createTagmaMutsCheckBoxPanel(), constraints);
    constraints.weighty = 1;
    constraints.gridy = 1;
    panel.add(createTagmaMutsButtonPanel(), constraints);
    return panel;

  }

  public JPanel createCentreLeftPanel() {
    JPanel panel = new JPanel(new GridBagLayout());
    // panel.setBorder(BorderFactory.createLineBorder(Color.RED));
    GridBagConstraints constraints = new GridBagConstraints();
    constraints.weighty = 5;
    constraints.gridy = 0;
    panel.add(createTagmaMutsPanel(), constraints);
    constraints.gridy = 1;
    panel.add(createFocusPanel(), constraints);
    return panel;
  }

  private JPanel createNonTagmaMutsCheckBoxPanel() {
    JPanel panel = new JPanel(new GridLayout(5, 1));
    // panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    panel.add(lengthMut);
    panel.add(heightMut);
    panel.add(angleMut);
    panel.add(duplicationMut);
    panel.add(deletionMut);
    return panel;
  }

  private JPanel createNonTagmaMutsButtonPanel() {
    JPanel panel = new JPanel(new GridBagLayout());
    // panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    GridBagConstraints constraints = new GridBagConstraints();
    constraints.fill = GridBagConstraints.VERTICAL;
    constraints.weightx = 1;
    constraints.weighty = 1;
    panel.add(new JButton(new AbstractAction("All") {
      /**
       * 
       */
      private static final long serialVersionUID = 1L;

      @Override
      public void actionPerformed(ActionEvent e) {
        lengthMut.setSelected(true);
        heightMut.setSelected(true);
        angleMut.setSelected(true);
        duplicationMut.setSelected(true);
        deletionMut.setSelected(true);
      }
    }), constraints);
    constraints.gridx = 1;
    panel.add(new JButton(new AbstractAction("None") {
      /**
       * 
       */
      private static final long serialVersionUID = 1L;

      @Override
      public void actionPerformed(ActionEvent e) {
        lengthMut.setSelected(false);
        heightMut.setSelected(false);
        angleMut.setSelected(false);
        duplicationMut.setSelected(false);
        deletionMut.setSelected(false);
      }
    }), constraints);
    return panel;
  }

  private JPanel createNonTagmaMutsPanel() {
    JPanel panel = new JPanel(new GridBagLayout());
    panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    GridBagConstraints constraints = new GridBagConstraints();
    constraints.weighty = 2;
    constraints.anchor = GridBagConstraints.SOUTH;
    panel.add(createNonTagmaMutsCheckBoxPanel(), constraints);
    constraints.weighty = 1;
    constraints.gridy = 1;
    constraints.anchor = GridBagConstraints.NORTH;
    panel.add(createNonTagmaMutsButtonPanel(), constraints);
    return panel;
  }

  private JPanel createCentreRightPanel() {
    JPanel panel = new JPanel(new GridBagLayout());
    // panel.setBorder(BorderFactory.createLineBorder(Color.RED));
    GridBagConstraints constraints = new GridBagConstraints();
    constraints.fill = GridBagConstraints.VERTICAL;
    constraints.gridy = 0;
    constraints.weighty = 2;
    panel.add(createNonTagmaMutsPanel(), constraints);
    constraints.weighty = 1;
    constraints.gridy = 1;
    panel.add(createPressurePanel(), constraints);
    constraints.weighty = 2;
    constraints.gridy = 2;
    panel.add(createActionButtonsPanel(), constraints);
    return panel;
  }

  private JPanel createPressurePanel() {
    JPanel panel = new JPanel(new GridBagLayout());
    panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    GridBagConstraints constraints = new GridBagConstraints();

    constraints.gridwidth = 3;
    panel.add(new JLabel("Mutation Pressure"), constraints);

    constraints.gridy = 1;
    constraints.gridwidth = 1;
    panel.add(mutPressurePlus, constraints);

    constraints.gridx = 1;
    panel.add(mutPressureZero, constraints);

    constraints.gridx = 2;
    panel.add(mutPressureMinus, constraints);

    return panel;
  }

  private JPanel createActionButtonsPanel() {
    JPanel panel = new JPanel(new GridBagLayout());
    // panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    GridBagConstraints constraints = new GridBagConstraints();
    constraints.weighty = 1;
    constraints.gridy = 0;
    panel.add(new JButton(new AbstractAction("Cancel") {
      /**
       * 
       */
      private static final long serialVersionUID = 1L;

      @Override
      public void actionPerformed(ActionEvent e) {
        handleCancelButton();

      }
    }), constraints);
    constraints.gridy = 1;
    panel.add(new JButton(new AbstractAction("OK") {

      /**
       * 
       */
      private static final long serialVersionUID = 1L;

      @Override
      public void actionPerformed(ActionEvent e) {
        handleOKButton();
      }

    }), constraints);
    return panel;
  }

  private void cautionAlert(String message) {
    JOptionPane.showMessageDialog(this, message, "Allowed Mutations Warning",
        JOptionPane.WARNING_MESSAGE, new ImageIcon(
            ClassicImageLoader.getPicture("AlertCautionAlert_cicn_00002_32x32").getImage()));

  }

  /**
   * See Arthromorphs/Engineering_Window.pas, PROCEDURE D_Engineering_Window, lines 442-643
   */
  private void handleOKButton() {
    boolean dearthOfAtomMuts = true;

    if (deletionMut.isSelected() || duplicationMut.isSelected())
      dearthOfAtomMuts = false;

    boolean dupDeleteOnly = true;

    if (angleMut.isSelected() || heightMut.isSelected() || lengthMut.isSelected()) {
      dearthOfAtomMuts = false;
      dupDeleteOnly = false;
    }

    boolean dearthOfBodyMuts = true;

    if (animalTrunkMut.isSelected() || animalLegsMut.isSelected() || animalClawsMut.isSelected()
        || sectionClawsMut.isSelected() || segmentClawsMut.isSelected() || clawsMut.isSelected())
      dearthOfBodyMuts = false;

    boolean animalOrClawsOnly = true;

    if (sectionTrunkMut.isSelected() || sectionLegsMut.isSelected() || segmentTrunkMut.isSelected()
        || segmentLegsMut.isSelected() || legsMut.isSelected()) {
      dearthOfBodyMuts = false;
      animalOrClawsOnly = false;
    }

    boolean agreeToExit = true;

    if (dearthOfAtomMuts) {
      agreeToExit = false;
      String errorMessage = Messages.getMessages().getString("Arth_DearthOfAtomMuts");
      cautionAlert(errorMessage);
    }
    if (dearthOfBodyMuts) {
      agreeToExit = false;
      String errorMessage = Messages.getMessages().getString("Arth_DearthOfBodyMuts");
      cautionAlert(errorMessage);

    }
    if (animalOrClawsOnly && dupDeleteOnly) {
      agreeToExit = false;
      String errorMessage = Messages.getMessages()
          .getString("Arth_AnimalOrClawsOnlyAndDupDeleteOnly");
      cautionAlert(errorMessage);
    }

    if (agreeToExit) {
      config.setAngleMut(angleMut.isSelected());
      config.setAnimalClawsMut(animalClawsMut.isSelected());
      config.setAnimalLegsMut(animalLegsMut.isSelected());
      config.setAnimalTrunkMut(animalTrunkMut.isSelected());
      config.setClawsMut(clawsMut.isSelected());
      config.setDeletionMut(deletionMut.isSelected());
      config.setDuplicationMut(duplicationMut.isSelected());
      config.setFocusOfAttention(focusFirst.isSelected() ? Concentration.FirstSegmentOnly
          : focusLast.isSelected() ? Concentration.LastSegmentOnly
              : focusNone.isSelected() ? Concentration.AnySegment : null);
      config.setHeightMut(heightMut.isSelected());
      config.setLegsMut(legsMut.isSelected());
      config.setMutationPressure(mutPressurePlus.isSelected() ? Pressure.Positive
          : mutPressureZero.isSelected() ? Pressure.Zero
              : mutPressureMinus.isSelected() ? Pressure.Negative : null);
      config.setSectionTrunkMut(sectionTrunkMut.isSelected());
      config.setSectionLegsMut(sectionLegsMut.isSelected());
      config.setSectionClawsMut(sectionClawsMut.isSelected());
      config.setSegmentTrunkMut(segmentTrunkMut.isSelected());
      config.setSegmentLegsMut(segmentLegsMut.isSelected());
      config.setSegmentClawsMut(segmentClawsMut.isSelected());
      config.setTrunkMut(animalTrunkMut.isSelected());
      config.setWidthMut(lengthMut.isSelected());
      this.dispose();
    }
  }

  public void handleCancelButton() {
    this.dispose();
  }

}
