package net.richarddawkins.watchmaker.morph.selector;

import net.richarddawkins.watchmaker.genome.StringGene;
import net.richarddawkins.watchmaker.morph.Morph;

public class StringMorphSelector extends SimpleMorphSelector implements MorphSelector {



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

    protected boolean favor(Morph target, Morph potentialBetter,
            Morph candidateToBeat) {
        StringGene targetGene = (StringGene) target.getGenome().getGene(0);
        StringGene potentialBetterGene = (StringGene) potentialBetter.getGenome()
                .getGene(0);
        StringGene candidateToBeatGene = (StringGene) candidateToBeat.getGenome()
                .getGene(0);
        return areDifferencesLess(targetGene, potentialBetterGene,
                candidateToBeatGene);
    }



}
