package ust.jzhuaq.drumPC.listener;

import java.util.EventObject;

import ust.jzhuaq.drumPC.Cursor;


public class CursorMovementEvent extends EventObject{

	private Cursor cursor;
	public CursorMovementEvent(Object source, Cursor c) {
		super(source);
		this.cursor = c;
	}
	public Cursor getCursor() {
		return cursor;
	}
	public void setCursor(Cursor cursor) {
		this.cursor = cursor;
	}
	


}
