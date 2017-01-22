package net.richarddawkins.watchmaker.genome.mutation;

public abstract class SimpleMutagen implements Mutagen {
	protected AllowedMutations allowedMutations;
	public SimpleMutagen(AllowedMutations allowedMutations) {
		this.allowedMutations = allowedMutations;
	}
	public AllowedMutations getAllowedMutations() {
		return allowedMutations;
	}
}
