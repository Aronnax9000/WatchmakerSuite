package net.richarddawkins.watchmaker.genome.mutation;

public class Random {
	public static final int randInt(int max) {
		return (int) (1 + (Math.random() * max));
	}
	public static final boolean nextBoolean() {
		java.util.Random random = new java.util.Random();
		return random.nextBoolean();
	}
}
