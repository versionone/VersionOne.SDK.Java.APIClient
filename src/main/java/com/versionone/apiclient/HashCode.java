package com.versionone.apiclient;


/**
 * Generate good hashcode from integer data.
 * 
 * Adapted from Paul Hsieh's SuperFastHash.
 * http://www.azillionmonkeys.com/qed/hash.html
 */
public class HashCode {
	// Borrowed from FNV hash algorithm's 32-bit prime multiplier
	private static final int magic = 16777619;
	private int _hash = magic;

	public static int Hash(int data1) {
		int hash = magic;
		hash = MixHash(hash, data1);
		return (int) PostHash(hash);
	}

	public static int Hash(Object data1) {
		return Hash(data1.hashCode());
	}

	public static int Hash(int data1, int data2) {
		int hash = magic;
		hash = MixHash(hash, data1);
		hash = MixHash(hash, data2);
		return (int) PostHash(hash);
	}

	public static int Hash(Object data1, Object data2) {
		return Hash(data1.hashCode(), data2.hashCode());
	}

	public static int Hash(int data1, int data2, int data3) {
		int hash = magic;
		hash = MixHash(hash, data1);
		hash = MixHash(hash, data2);
		hash = MixHash(hash, data3);
		return (int) PostHash(hash);
	}

	public static int Hash(Object data1, Object data2, Object data3) {
		return Hash(data1.hashCode(), data2.hashCode(), data3.hashCode());
	}

	public static int Hash(int[] data) {
		int hash = magic;
		for (int datum : data)
			hash = MixHash(hash, datum);
		return (int) PostHash(hash);
	}

	public static int Hash(byte[] data) {
		int hash = magic;
		for (byte datum : data)
			hash = MixHash(hash, datum);
		return (int) PostHash(hash);
	}

	public static int Hash(Object[] data) {
		int hash = magic;
		for (Object datum : data)
			hash = MixHash(hash, datum.hashCode());
		return (int) PostHash(hash);
	}

	public static int Hash(String data) {
		int hash = magic;
		for (char datum : data.toCharArray())
			hash = MixHash(hash, datum);
		return (int) PostHash(hash);
	}

	private static int MixHash(int hash, int data) {
		int lo16 = data & 0xFFFF;
		int hi16 = data >> 16;
		hash += lo16;
		hash ^= hash << 16;
		hash ^= hi16 << 11;
		hash += hash >> 11;
		return hash;
	}

	private static int PostHash(int hash) {
		hash ^= hash << 3;
		hash += hash >> 5;
		hash ^= hash << 2;
		hash += hash >> 15;
		hash ^= hash << 10;
		return hash;
	}

	public void Mix(int data) {
		_hash = MixHash(_hash, data);
	}

	public void Mix(Object data) {
		Mix(data.hashCode());
	}

	public int getValue() {
		return (int) PostHash(_hash);
	}
}