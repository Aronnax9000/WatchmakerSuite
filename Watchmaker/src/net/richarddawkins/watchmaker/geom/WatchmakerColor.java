package net.richarddawkins.watchmaker.geom;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.logging.Logger;

public class WatchmakerColor {
    private static Logger logger = Logger
            .getLogger("net.richarddawkins.watchmaker.geom.WatchmakerColor");

    public final static int BlackColor = getColorIndexByRGB666Coordinates(0, 0,
            0);
    public final static int WhiteColor = getColorIndexByRGB666Coordinates(5, 5,
            5);
    public final static int RedColor = getColorIndexByRGB666Coordinates(5, 0,
            0);
    public final static int GreenColor = getColorIndexByRGB666Coordinates(0, 5,
            0);
    public final static int BlueColor = getColorIndexByRGB666Coordinates(0, 0,
            5);
    public final static int CyanColor = getColorIndexByRGB666Coordinates(0, 5,
            5);
    public final static int MagentaColor = getColorIndexByRGB666Coordinates(5,
            0, 5);
    public final static int YellowColor = getColorIndexByRGB666Coordinates(5, 5,
            0);
    public static final String DEFAULT_PALETTE = "Classic Mac";

    private static WatchmakerColor singleton = null;

    public static int getColorIndexByRGB666Coordinates(int r, int g, int b) {
        return r * 36 + g * 6 + b;
    }

    public static final int[] allowedLevels = new int[] { 0, 51, 102, 153, 204,
            255 };

    public static synchronized WatchmakerColor getInstance() {
        if (singleton == null) {
            singleton = new WatchmakerColor();
        }
        return singleton;
    }

    public static Palette generateMacPalette() {
        RGBTriple[] colors = new RGBTriple[256];
        for (int i = 0; i < 256; i++) {
            if (i < 216) {
                int r = i / 36;
                int g = (i - (36 * r)) / 6;
                int b = i % 6;
                colors[i] = new RGBTriple(allowedLevels[r], allowedLevels[g],
                        allowedLevels[b]);
            } else {
                int indexMinus216 = i - 216;
                int singleColorIntensity = 255 - (indexMinus216 % 10) * 25;
                if (i >= 216 && i < 216 + 10) {
                    colors[i] = new RGBTriple(singleColorIntensity, 0, 0);
                } else if (i >= 226 && i < 226 + 10) {
                    colors[i] = new RGBTriple(0, singleColorIntensity, 0);
                } else if (i >= 236 && i < 236 + 10) {
                    colors[i] = new RGBTriple(0, 0, singleColorIntensity);
                } else {
                    colors[i] = new RGBTriple(singleColorIntensity,
                            singleColorIntensity, singleColorIntensity);
                }
            }
        }
        return new Palette("Classic Mac", colors);
    }

    protected WatchmakerColor() {
    }

    protected Palette palette = null;

    private Vector<File> getAllFiles(File curDir) {

        Vector<File> files = new Vector<File>();
        File[] filesList = curDir.listFiles();
        if (filesList != null) {
            for (File f : filesList) {
                if (f.isDirectory())
                    files.addAll(getAllFiles(f));
                if (f.isFile()) {
                    if (f.getName().endsWith(".gpl"))
                        ;
                    files.add(f);
                }
            }
        } else {
            try {
                logger.warning(
                        "WatchmakerColor.getAllFiles could not list files from "
                                + curDir.getCanonicalPath());
            } catch (IOException e) {
                logger.warning(
                        "WatchmakerColor.getAllFiles could not get canonical path from curDir "
                                + curDir);
            }
        }
        return files;

    }

    public void loadPalettes() {
        Vector<String> searchExpressions = new Vector<String>();
        searchExpressions.add(".gimp-2.8" + File.separator + "palettes");
        searchExpressions.add("Library" + File.separator + "Application Support"
                + File.separator + "GIMP" + File.separator + "2.8"
                + File.separator + "palettes");
        for (String dirToSearch : searchExpressions) {
            logger.info("dirToSearch:" + dirToSearch);
            File[] files = new File(System.getProperty("user.home")
                    + File.separator + dirToSearch).listFiles();
            if(files != null) {
                for(File file: files) {
                    logger.info(dirToSearch + ": " + file.getName());
                }
            }
            loadPalettesSearch(dirToSearch);
        }
    }

    private void loadPalettesSearch(String searchString) {
        Vector<String> potentialPaletteDirectories = new Vector<String>();
        potentialPaletteDirectories.add(System.getProperty("user.home")
                + File.separator + searchString);
        for (String potentialPaletteDirectory : potentialPaletteDirectories) {
            StringTokenizer st = new StringTokenizer(File.separator);
            
            loadPalettes(potentialPaletteDirectory);
        }
    }

    public void loadPalettes(String path) {
        try {
            logger.info("WatchmakerColor.loadPalette " + path);
            File dir = new File(path);
            Vector<File> files = getAllFiles(dir);
            if (files != null) {
                for (File file : files) {
                    loadPalette(file);
                }
            } else {
                logger.warning("No files found in " + dir.getCanonicalPath());

            }

        } catch (IOException e) {
            logger.warning(
                    "WatchmakerColor.loadPalette couldn't obtain current working directory");
        }
    }

    private void loadPalette(File file) {
        String fileName = file.getName();
        fileName = fileName.substring(0, fileName.lastIndexOf(".gpl"));
        logger.info("WatchmakerColor.loadPalette " + fileName);
        Palette palette = new Palette(fileName);
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(new FileInputStream(file)));
            palette.loadFromGimpPaletteBufferedReader(reader);
            palettes.put(fileName, palette);
        } catch (FileNotFoundException e) {
            logger.warning(
                    "WatchmakerColor.loadPalette file not found exception for fileName: "
                            + fileName + ": " + e.getMessage());
        }
    }

    public void switchToPalette(String name) {
        palette = palettes.get(name);
        if (palette != null) {
            logger.info("Switched to palette " + name + " with "
                    + palette.getColors().length + " colors ");
        }
    }

    private static Map<String, Palette> palettes = null;

    public Palette getPalette() {
        if (palette == null) {
            palette = getPalettes().get(DEFAULT_PALETTE);
        }
        return palette;
    }

    public synchronized Map<String, Palette> getPalettes() {
        if (palettes == null) {
            palettes = new HashMap<String, Palette>();
            Palette defaultPalette = generateMacPalette();
            palettes.put(defaultPalette.getName(), defaultPalette);
            loadPalettes();
        }
        return palettes;
    }

    public synchronized RGBTriple getRGBTripleForIndex(int index) {
        return getPalette().getColors()[index];
    }

}
