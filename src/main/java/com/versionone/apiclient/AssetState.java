package com.versionone.apiclient;

/**
 * VersionOne built in states for an Asset
 * @author jerry
 *
 */
public enum AssetState {
	/**
	 * Asset has not been activated
	 */
	Future  (0),
	/**
	 * Asset is active
	 */
	Active  (64),
	/**
	 * Asset was closed
	 */
	Closed  (128),
	/**
	 * Asset is dead
	 */
	Dead    (192),
	/**
	 * Asset was deleted
	 */
	Deleted (255);
	
	private int _value;
	
	private AssetState(int value) {_value = value;}
	
	/**
	 * Get the integer value of this state
	 * @return
	 */
	public int value() {return _value;}
	
	/**
	 * Select AssetState based on an integer value
	 * @param intValue
	 * @return
	 */
	public static AssetState valueOf(int intValue) {		
		AssetState[] temp = AssetState.values();
		for(AssetState oneState : temp) {
			if(oneState._value == intValue)
				return oneState;
		}
		throw new IllegalArgumentException(intValue + " is not a valid AssetType value");
	}
	
	/**
	 * is an int a valid AssetState
	 * @param intValue
	 * @return
	 */
	public static boolean isDefined(int intValue) {
		AssetState[] temp = AssetState.values();
		for(AssetState oneState : temp) {
			if(oneState._value == intValue)
				return true;
		}
		return false;
	}	
}