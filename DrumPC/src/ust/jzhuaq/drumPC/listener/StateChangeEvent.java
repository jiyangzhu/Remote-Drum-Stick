package ust.jzhuaq.drumPC.listener;

import java.util.EventObject;

public class StateChangeEvent extends EventObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String State = "";

	public StateChangeEvent(Object source, String State) {
		super(source);
		this.State = State;
	}

	public void setState(String State) {
		this.State = State;
	}

	public String getState() {
		return this.State;
	}
}