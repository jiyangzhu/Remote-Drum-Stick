package ust.jzhuaq.drumPC.listener;

import javax.swing.JLabel;

import ust.jzhuaq.drumPC.MainFrame;


public class CursorMovementHandle implements StateChangeListener {
	private JLabel xLabel;
	private JLabel yLabel;

	public CursorMovementHandle(JLabel x, JLabel y) {
		this.xLabel = x;
		this.yLabel = y;
	}

	@Override
	public void stateChangeEvent(StateChangeEvent event) {

	}

	@Override
	public void cursorMovementEvent(CursorMovementEvent event) {
		if (event.getCursor() != null) {
			xLabel.setText(MainFrame.cursorX + event.getCursor().getSignedX());
			yLabel.setText(MainFrame.cursorY + event.getCursor().getSignedY());
		}

	}

}
