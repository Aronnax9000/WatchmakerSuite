package net.richarddawkins.watchmaker.morphs.colour.genome.mutation;

import net.richarddawkins.watchmaker.morphs.mono.genome.mutation.BiomorphAllowedMutations;

public class ColourAllowedMutations extends BiomorphAllowedMutations {

	public static final int MutTypeNo = 13;

	public ColourAllowedMutations() {
		mut = new boolean[MutTypeNo];
		for (int i = 0; i < MutTypeNo; i++)
			mut[i] = true;
	}
}
