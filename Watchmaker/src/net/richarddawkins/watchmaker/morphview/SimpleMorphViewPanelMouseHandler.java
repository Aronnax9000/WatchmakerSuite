package net.richarddawkins.watchmaker.morphview;

public abstract class SimpleMorphViewPanelMouseHandler implements MorphViewPanelMouseHandler{

    protected MorphView morphView;
    public MorphView getMorphView() {
        return morphView;
    }
    public void setMorphView(MorphView morphView) {
        this.morphView = morphView;
    }
    public SimpleMorphViewPanelMouseHandler(MorphView morphView) {
        this.morphView = morphView;
    }
    
}
