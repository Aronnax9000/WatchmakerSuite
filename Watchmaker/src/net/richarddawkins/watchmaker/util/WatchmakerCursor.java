package net.richarddawkins.watchmaker.util;

public enum WatchmakerCursor {

    leftArrow("CursorLeftArrow_CURS_00135_48x16", "Left arrow"),
    magnify("CursorMagnifyingGlass_CURS_00150_48x16", "Magnify"), 
    kill("CursorGun_CURS_00149_48x16", "Kill"), 
    move("CursorPointyHand_CURS_00146_48x16", "Move"), 
    detach("CursorScissors_CURS_00148_48x16", "Detach"),
    rightArrow("CursorRightArrow_CURS_00136_48x16","Right arrow"), 
    upArrow("CursorUpArrow_CURS_00137_48x16", "Up arrow"), 
    downArrow("CursorDownArrow_CURS_00138_48x16", "Down arrow"), 
    equalsSign("CursorUpperEquals_CURS_00139_48x16", "Equals"), 
    watchCursor("CursorWatch_CURS_-15776_48x16", "Watch"), 
    hypodermic("CursorHypodermic_CURS_00140_48x16", "Hypodermic"), 
    breed("CursorBreed_CURS_00145_48x16", "Breed"), 
    random("CursorCentreDie_CURS_00144_48x16", "Random"), 
    highlight("CursorBlackMonolith_CURS_00142_48x16", "Highlight"), 
    pedigree("CursorPedigreeMaybe_CURS_00147_48x16", "Pedigree"),
    custom(null, "Custom");
    private final String imageName;
    private final String label;

    public String imageName() { return imageName; }
    public String label() { return label; }
    WatchmakerCursor(String imageName, String label) {
        this.imageName = imageName;
        this.label = label;
    }
}
