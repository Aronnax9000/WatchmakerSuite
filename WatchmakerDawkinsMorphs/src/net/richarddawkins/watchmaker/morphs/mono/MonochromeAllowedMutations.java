package net.richarddawkins.watchmaker.morphs.mono;

import net.richarddawkins.watchmaker.genome.mutation.AllowedMutations;
import net.richarddawkins.watchmaker.morphs.colour.genome.BiomorphAllowedMutations;

public class MonochromeAllowedMutations extends BiomorphAllowedMutations implements AllowedMutations {
	
	public static final int MutTypeNo = 9;
	
	public MonochromeAllowedMutations() {
		mut = new boolean[MutTypeNo];
		for (int i = 0; i < MutTypeNo; i++)
			mut[i] = true;
	}

}
