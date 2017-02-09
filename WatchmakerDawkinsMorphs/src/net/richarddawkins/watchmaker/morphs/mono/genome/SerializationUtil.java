package net.richarddawkins.watchmaker.morphs.mono.genome;

public class SerializationUtil {
	
	public static final int kSwellTypeSwell = 0;
	public static final int kSwellTypeSame = 1;
	public static final int kSwellTypeShrink = 2;
	public static final int kCompletenessTypeSingle = 0;
	public static final int kCompletenessTypeDouble = 1;
	public static final int kSpokesTypeNorthOnly = 0;
	public static final int kSpokesTypeNSouth = 1;
	public static final int kSpokesTypeRadial = 2;
	
	
	byte[] arrayQuad = new byte[4];
	byte[] arrayDuo = new byte[2]; 
	
	public byte[] mangleArrayQuad(byte[] victim) {
		
		// 00112233 becomes
		// 33221100
	
		byte tempByte0 = victim[0];
		byte tempByte1 = victim[1];
		victim[0] = victim[3];
		victim[1] = victim[2];
		victim[2] = tempByte1;
		victim[3] = tempByte0;
		return victim;
		
	}
	public byte[] mangleArrayDuo(byte[] victim) {
		byte tempByte = victim[0];
		victim[0] = victim[1];
		victim[1] = tempByte;
		return victim;
	}
	/**
	 * <pre>
	 * 	type

	chromosomeArrayDuo = ARRAY[1..9] OF arrayDuo;
	PersonSerializer = record
			gene: chromosomeArrayDuo;
			dgene: ARRAY[1..10] of byte;
			SegNoGene: arrayDuo;
			SegDistGene: arrayDuo;
			CompletenessGene: byte;
			SpokesGene: byte;
			tricklegene: arrayDuo; 
			mutsizegene: arrayDuo;
			mutprobgene: arrayDuo;	
		end;
	 * </pre>
	 * @author sven
	 *
	 */
	class MonochromeGenomeSerializer {
		public byte[][] gene = new byte[9][2];
		public byte[] dgene = new byte[10];
		public byte[] segNoGene = new byte[2];
		public byte[] segDistGene = new byte[2];
		public byte completenessGene;
		public byte spokesGene;
		public byte[] tricklegene = new byte[2]; 
		public byte[] mutsizegene = new byte[2];
		public byte[] mutprobgene = new byte[2];	
	}


}
