package net.richarddawkins.watchmaker.morph.util;

public class Random {
	public static final int randInt(int max) {
		return 1 + (int) (Math.random() * max);
	}
	public static final boolean nextBoolean() {
		java.util.Random random = new java.util.Random();
		return random.nextBoolean();
	}
}
