package net.richarddawkins.watchmaker.morphview;

import java.util.Vector;

import javax.swing.Icon;

public enum MorphViewType {
    
    
    
    album ("Album", null, null), 
    breeding ("Breeding", null, null), 
    engineering ("Engineering", null, null), 
    fossil ("Fossil Record", null, null), 
    pedigree ("Pedigree", null, null), 
    triangle ("Triangle", null, null),
    drift ("Drift", null, null),
    drift_sweep ("Drift Sweep", null, null),
    array("Array", null, null)
    ;
    MorphViewType(String name, String iconFilename, String toolTip) {
        this.name= name;
        this.toolTip = toolTip;
        this.iconFilename = iconFilename;
        this.icon = null;
    }

    public static MorphViewType getByName(String name) {
        for(MorphViewType type: MorphViewType.values()) {
            if(name.equals(type.getName())) {
                return type;
            }
                
        }
        return null;
    }
    
    static Vector<String> getNames() {
        Vector<String> names = new Vector<String>();
        for(MorphViewType type: MorphViewType.values()) {
            names.add(type.getName());
        }
        return names;
    }

    private final String name;
    private final Icon icon;
    private final String toolTip;
    private final String iconFilename;

    
    public String getName() {
        return name;
    }
    public Icon getIcon() {
        return icon;
    }
    public String getToolTip() {
        return toolTip;
    }

    public String getIconFilename() {
        return iconFilename;
    }
    
}
