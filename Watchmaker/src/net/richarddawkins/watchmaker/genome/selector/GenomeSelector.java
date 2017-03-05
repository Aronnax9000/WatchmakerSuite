package net.richarddawkins.watchmaker.genome.selector;

import java.util.Collection;

import net.richarddawkins.watchmaker.genome.Genome;

public interface GenomeSelector {
    Genome best(Genome target, Collection<Genome> genomes);
}
