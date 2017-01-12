package net.richarddawkins.watchmaker.deities;

import net.richarddawkins.watchmaker.morph.Morph;

/**
 * <p>
 * Gods can be arranged as nodes in a double linked list, that is to say,
 * they can hold references to an ordered pair of other Gods, called
 * the 'next' God and 'previous' God.
 * </p>
 * <p>
 * Gods were first implemented by Richard Dawkins 
 * in the original Pascal source code for Blind Watchmaker.
 * His definition, in Pascal, is this (GodPtr and GodHandle,
 * which are "pointer types", necessary in Pascal to handle
 * varying numbers of objects at runtime, a number unknown
 * until the program actually runs.)
 * </p>
 * <pre>
 * GodPtr = ^God;
 * GodHandle = ^GodPtr;
 * God = RECORD
 *     Adam: FullHandle;
 *     PreviousGod, NextGod: GodHandle;
 * END; 
 * </pre>
 * @author alan
 *
 */
public class God {
  /** 
   * The next God to this one.
   */
  public God nextGod;
  /**
   * The previous God to this one.
   */
  public God prevGod;
  /**
   * The God's "Adam", the single morph to which it points.
   */
  public Morph adam;
  
  public void add(God nextGod) {
    this.nextGod = nextGod;
    nextGod.prevGod = this;
  }
  
  
  
}
