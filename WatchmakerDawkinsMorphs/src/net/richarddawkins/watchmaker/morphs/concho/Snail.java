package net.richarddawkins.watchmaker.morphs.concho;

import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.morph.MorphConfig;
import net.richarddawkins.watchmaker.morph.SimpleMorph;
import net.richarddawkins.watchmaker.morphs.concho.genome.SnailGenome;
import net.richarddawkins.watchmaker.morphs.concho.geom.SnailPic;

public class Snail extends SimpleMorph {

    protected SnailConfig config;

    @Override
    public MorphConfig getMorphConfig() {
        return config;
    }

    @Override
    public void setMorphConfig(MorphConfig config) {
        this.config = (SnailConfig) config;
    }

    @Override
    public Genome getGenome() {
        return genome;
    }

    @Override
    public void setGenome(Genome genome) {
        this.genome = (SnailGenome) genome;
    }

    public Snail(MorphConfig config) {
        super(config);
        Genome genome = new SnailGenome();
        setGenome(genome);
        pic = new SnailPic();
    }

    public Snail(MorphConfig config, int basicType) {
        this(config);
        genome.setBasicType(basicType);
    }

    

}
