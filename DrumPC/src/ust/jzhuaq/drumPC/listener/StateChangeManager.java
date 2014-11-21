package ust.jzhuaq.drumPC.listener;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import ust.jzhuaq.drumPC.Cursor;


public class StateChangeManager {
	private Collection<StateChangeListener> listeners;
	
	public void addListener(StateChangeListener listener) {
		if (listeners == null) {
			listeners = new HashSet<StateChangeListener>();
		}
		listeners.add(listener);
	}

	public void removeListener(StateChangeListener listener) {
		if (listeners == null)
			return;
		listeners.remove(listener);
	}

	public void unConnect() {
		if (listeners == null)
			return;
		StateChangeEvent event = new StateChangeEvent(this, "unconnected");
		notifyListeners(event);
	}

	public void isConnect() {
		if (listeners == null)
			return;
		StateChangeEvent event = new StateChangeEvent(this, "connected");
		notifyListeners(event);
	}
	
	public void cursorMove(Cursor cursor){
		if (listeners == null)
			return;
		CursorMovementEvent event = new CursorMovementEvent(this, cursor);
		notifyListeners(event);
	}

	private void notifyListeners(StateChangeEvent event) {
		Iterator<StateChangeListener> iter = listeners.iterator();
		while (iter.hasNext()) {
			StateChangeListener listener = iter.next();
			listener.stateChangeEvent(event);
		}
	}
	
	private void notifyListeners(CursorMovementEvent event){
		Iterator<StateChangeListener> iter = listeners.iterator();
		while (iter.hasNext()) {
			StateChangeListener listener = iter.next();
			listener.cursorMovementEvent(event);
		}
	}
}
