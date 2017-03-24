package net.richarddawkins.watchmaker.menu;

public interface WatchmakerMenuBuilder extends MenuBuilder {
    abstract void createViewBoundingBoxes();
    abstract void createSpinBabyMorphs();
    abstract void createHighlightCheckbox();
}
