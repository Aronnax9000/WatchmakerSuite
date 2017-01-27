package net.richarddawkins.watchmaker.swing.triangle;

import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.draw.BoxedMorphVector;
import net.richarddawkins.watchmaker.swing.components.SwingGeneBoxyMorphView;

public class SwingTriangleMorphView extends SwingGeneBoxyMorphView {

	
	private static final long serialVersionUID = -5445629768562940527L;
    public SwingTriangleMorphView(AppData appData) {
       	super(appData, 
       			null, 
       			"Triangle",
       			false);
       	SwingTriangleMorphViewPanel trianglePanel = new SwingTriangleMorphViewPanel(appData);
       	setCentrePanel(trianglePanel);
    }
    @Override
    public Morph getMorphOfTheHour() {
    	SwingTriangleMorphViewPanel trianglePanel =  (SwingTriangleMorphViewPanel) centrePanel;
    	BoxedMorphVector boxedMorphVector = trianglePanel.getBoxedMorphVector();
    	return boxedMorphVector
    			.getBoxedMorph(boxedMorphVector.getBoxedMorphs().size() - 1)
    			.getMorph();
    }
}
