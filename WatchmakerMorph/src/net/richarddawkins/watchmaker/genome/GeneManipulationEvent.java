package net.richarddawkins.watchmaker.genome;

import java.util.EventObject;

public class GeneManipulationEvent extends EventObject  {

	public GeneManipulationEvent(Object source) {
		super(source);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -2258506006045246539L;
	
	public GooseDirection getGooseDirection() {
		return (GooseDirection) source;
	}

}
