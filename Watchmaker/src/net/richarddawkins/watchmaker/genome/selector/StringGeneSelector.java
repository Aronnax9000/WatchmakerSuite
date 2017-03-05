package net.richarddawkins.watchmaker.genome.selector;

import java.util.Collection;
import java.util.Iterator;

import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.genome.StringGene;

public class StringGeneSelector implements GenomeSelector {

    @Override
    public Genome best(Genome target, Collection<Genome> genomes) {
        Iterator<Genome> iter = genomes.iterator();
        Genome bestSoFar = iter.next();
        while (iter.hasNext()) {
            Genome candidate = iter.next();
            if (betterThan(target, candidate, bestSoFar)) {
                bestSoFar = candidate;
            }
        }
        return bestSoFar;
    }

    protected int stringDifferences(String string1, String string2) {

        int differences = 0;

        if (string1.length() > string2.length()) {
            String string3 = string2;
            string2 = string1;
            string1 = string3;
        }
        // string2 is now the longer, if the two are not of the same length.
        differences += string2.length() - string1.length();

        for (int i = 0; i < string1.length(); i++) {
            if (string1.charAt(i) != string2.charAt(i)) {
                differences++;
            }
        }
        return differences;
    }

    protected boolean areDifferencesLess(StringGene targetGene,
            StringGene potentialBetterGene, StringGene candidateToBeatGene) {
        return stringDifferences(targetGene.getValue(),
                potentialBetterGene.getValue()) < stringDifferences(
                        candidateToBeatGene.getValue(),
                        candidateToBeatGene.getValue());

    }

    protected boolean betterThan(Genome target, Genome potentialBetter,
            Genome candidateToBeat) {
        StringGene targetGene = (StringGene) target.getGene(0);
        StringGene potentialBetterGene = (StringGene) potentialBetter
                .getGene(0);
        StringGene candidateToBeatGene = (StringGene) candidateToBeat
                .getGene(0);
        return areDifferencesLess(targetGene, potentialBetterGene,
                candidateToBeatGene);
    }



}
