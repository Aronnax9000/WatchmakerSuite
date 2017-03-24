package net.richarddawkins.watchmaker.morphs.swing;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import net.richarddawkins.watchmaker.image.ClassicImageLoader;
import net.richarddawkins.watchmaker.image.ClassicImageLoaderService;
import net.richarddawkins.watchmaker.swing.images.AWTClassicImage;

public enum MorphType {
    MONOCHROME_BIOMORPH("Monochrome", "BWSpiderLogoMono_ICNO_23096_32x32",
            "Blind Watchmaker (Monochrome)"), COLOUR_BIOMORPH("Colour",
                    "BWSpiderLogoPurple_icl8_23096_32x32",
                    "Blind Watchmaker (Colour)"), SNAIL("Snails",
                            "SnailLogo_ICNO_17669_32x32",
                            "Blind Snailmaker"), ARTHROMORPH("Arthromorphs",
                                    "Arthromorph_ALAN_00010_32x32",
                                    "Arthromorphs");
    private final String name;
    private final Icon icon;
    private final String toolTip;
    private final String iconFilename;

    MorphType(String name, String icon, String toolTip) {
        this.name = name;
        ClassicImageLoader loader = ClassicImageLoaderService
                .getInstance().getClassicImageLoader();
        AWTClassicImage classicImage = (AWTClassicImage)loader.getPicture(icon);
        this.icon = new ImageIcon(classicImage.getImage());
        this.toolTip = toolTip;
        this.iconFilename = icon;
    }

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