package ust.jzhuaq.drumPC.listener;

public interface StateChangeListener {
	public void stateChangeEvent(StateChangeEvent event);
	public void cursorMovementEvent(CursorMovementEvent event);
}
