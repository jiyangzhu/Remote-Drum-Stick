package ust.jzhuaq.drumPC.listener;

import javax.swing.JLabel;

import ust.jzhuaq.drumPC.MainFrame;


public class StateChangeHandle implements StateChangeListener {
	private JLabel jl;
	
	private JLabel xLabel;
	private JLabel yLabel;
	
	public StateChangeHandle (JLabel j){
		jl=j;
	}
	/*public StateChangeHandle(JLabel x, JLabel y){
		this.xLabel = x;
		this.yLabel = y;
	}*/
	
	public void stateChangeEvent(StateChangeEvent event) {
		if (event.getState() != null && event.getState().equals("connected")) {
			jl.setText(MainFrame.onConnection);
		} else {
			jl.setText(MainFrame.offConnection);
		}
	}
	@Override
	public void cursorMovementEvent(CursorMovementEvent event) {
	}
	
	
}
