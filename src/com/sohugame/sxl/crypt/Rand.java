package com.sohugame.sxl.crypt;

/**
 * 线程不安全随机数.
 * @author XZ
 * 
 */
public class Rand {

	private final static long multiplier = 0x5DEECE66DL;
	private final static long addend = 0xBL;
	private final static long mask = (1L << 48) - 1;
	private long seed;

	public Rand(long seed) {
		this.seed = seed;
	}
	
	private int next(int bits) {
		long oldseed = seed, nextseed;
		nextseed = (oldseed * multiplier + addend) & mask;
		seed = nextseed;
		return (int) (nextseed >>> (48 - bits));
	}

	public void nextBytes(byte[] bytes) {
		for (int i = 0, len = bytes.length; i < len;)
			for (int rnd = nextInt(), n = Math.min(len - i, Integer.SIZE / Byte.SIZE); n-- > 0; rnd >>= Byte.SIZE)
				bytes[i++] = (byte) rnd;
	}

	public int nextInt() {
		return next(32);
	}
	
	public static void main(String[] args) {
		//long seed = System.currentTimeMillis();
		long seed = 1353557690200L;
		byte[] bytes = new byte[8];
		System.out.println(seed);
		
		
		Rand r = new Rand(seed);
		for (int i=0;i<100;i++) {
			r.nextBytes(bytes);
			for (int j=0;j<bytes.length;j++) {
				System.out.print(bytes[j] + " ");
			}
			System.out.println();
		}
	}
}
