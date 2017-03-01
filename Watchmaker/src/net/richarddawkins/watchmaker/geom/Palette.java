package net.richarddawkins.watchmaker.geom;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.logging.Logger;

public class Palette {
    private static Logger logger = Logger
            .getLogger("net.richarddawkins.watchmaker.geom.Palette");

    protected String name;
    protected RGBTriple[] colors;

    public Palette(String name, RGBTriple[] colors) {
        this.name = name;
        this.colors = colors;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RGBTriple[] getColors() {
        return colors;
    }

    public void setColors(RGBTriple[] colors) {
        this.colors = colors;
    }

    public Palette(String name) {
        this.name = name;
    }

    public Palette(RGBTriple[] colors) {
        this.colors = colors;
    }

    private void readAndDiscardUpToPaletteData(BufferedReader reader) {
        try {
            while (reader.ready()) {
                try {
                    String line = reader.readLine();
                    if (line.startsWith("#")) {
                        break;
                    }
                } catch (IOException e) {
                    logger.warning(
                            "Exception while reading line from palette file: "
                                    + e.getMessage());
                }
            }
        } catch (IOException e) {
            logger.warning(
                    "Exception while determining readiness of palette file reader: "
                            + e.getMessage());
        }
    }

    public void loadFromGimpPaletteBufferedReader(BufferedReader reader) {
        readAndDiscardUpToPaletteData(reader);
        Vector<RGBTriple> rgbTriples = new Vector<RGBTriple>();
        try {
            while (reader.ready()) {
                String line = reader.readLine();
                StringTokenizer st = new StringTokenizer(line);
                try {
                    RGBTriple rgbTriple = new RGBTriple(
                            Integer.parseInt(st.nextToken()),
                            Integer.parseInt(st.nextToken()),
                            Integer.parseInt(st.nextToken()), st.nextToken());
                    rgbTriples.add(rgbTriple);
                    logger.info("Loaded color " + rgbTriple);
                } catch (NoSuchElementException e) {
                    logger.warning(
                            "NoSuchElementException while parsing color palette tokens from "
                                    + line);
                }
            }
        } catch (IOException e) {
            logger.warning(
                    "Exception while reading color line from palette file: "
                            + e.getMessage());
        }
        this.colors = rgbTriples.toArray(new RGBTriple[0]);
    }
}
