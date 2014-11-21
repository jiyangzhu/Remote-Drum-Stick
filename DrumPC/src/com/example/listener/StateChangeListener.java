package com.example.listener;

public interface StateChangeListener {
	public void stateChangeEvent(StateChangeEvent event);
	public void cursorMovementEvent(CursorMovementEvent event);
}
