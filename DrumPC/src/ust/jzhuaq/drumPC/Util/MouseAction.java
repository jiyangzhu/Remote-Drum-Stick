package ust.jzhuaq.drumPC.Util;

import java.awt.AWTException;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.List;

import ust.jzhuaq.drumPC.Cursor;
import ust.jzhuaq.drumPC.MainFrame;

public class MouseAction {
	private Robot robot;

	private GraphicsDevice[] gDevices;
	private Rectangle[] gBounds;

	int currX;
	int currY;

	private Cursor cursorMove;

	public MouseAction() throws AWTException {
		robot = new Robot();
		//
		this.gDevices = GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getScreenDevices();
		int l = this.gDevices.length;
		this.gBounds = new Rectangle[l];
		for (int i = 0; i < l; ++i) {
			this.gBounds[i] = this.gDevices[i].getDefaultConfiguration()
					.getBounds();
		}
	}

	public void getCommands(List<Object> args) {
		int type = -1;
		try {
			type = Integer.parseInt(args.get(2).toString());

		} catch (NumberFormatException e) {
			return;
		}
		int x, y, z;
		try {
			x = Integer.parseInt(args.get(3).toString());
		} catch (Exception e) {
			x = 0;
		}
		try {
			y = Integer.parseInt(args.get(4).toString());
		} catch (Exception e) {
			y = 0;
		}
		try {
			z = Integer.parseInt(args.get(5).toString());
		} catch (Exception e) {
			z = 0;
		}
		switch (type) {
		case Constants.EVENT_CLICK:
			mouseClick(0);
			break;
		case Constants.EVENT_CURSOR:
			move(x, y, z);
			break;
		default:
			break;
		}
	}
	
	public void getKeyCommands(List<Object> args) {
		int key = -1;
		try {
			key = Integer.parseInt(args.get(0).toString());
		} catch (NumberFormatException e) {
			return;
		}
		
		keyboard(key);
	}
	

	/**
	 * 0: left; 1: right
	 * 
	 * @param p
	 */
	private void mouseClick(int p) {
		switch (p) {
		case 0: // Left Click
			this.robot.mousePress(InputEvent.BUTTON1_MASK);
			this.robot.waitForIdle();
			this.robot.mouseRelease(InputEvent.BUTTON1_MASK);
			this.robot.waitForIdle();
			break;

		case 1:
			this.robot.mousePress(InputEvent.BUTTON3_MASK);
			this.robot.mouseRelease(InputEvent.BUTTON3_MASK);
			break;
		default:
			break;
		}
	}

	private void move(int x, int y, int z) {
		Point point = MouseInfo.getPointerInfo().getLocation();
		currX = point.x;
		currY = point.y;
		currX -= z;
		currY -= x;
		this.robot.mouseMove(currX, currY);
		cursorMove = new Cursor(z, x);
		MainFrame.stateChangeManager.cursorMove(cursorMove);
	}

	private void keyboard(int key){
		System.out.println("keybardaction  " + key);
		switch (key) {
		case Constants.KEY_UP:
			this.robot.keyPress(KeyEvent.VK_UP);
			this.robot.keyRelease(KeyEvent.VK_UP);
			break;
		case Constants.KEY_DOWN:
			this.robot.keyPress(KeyEvent.VK_DOWN);
			this.robot.keyRelease(KeyEvent.VK_DOWN);
			break;
		case Constants.KEY_LEFT:
			this.robot.keyPress(KeyEvent.VK_LEFT);
			this.robot.keyRelease(KeyEvent.VK_LEFT);
			break;
		case Constants.KEY_RIGHT:
			this.robot.keyPress(KeyEvent.VK_RIGHT);
			this.robot.keyRelease(KeyEvent.VK_RIGHT);
			break;
		case Constants.KEY_PGUP:
			this.robot.keyPress(KeyEvent.VK_PAGE_UP);
			this.robot.keyRelease(KeyEvent.VK_PAGE_UP);
			break;
		case Constants.KEY_PGDN:
			this.robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			this.robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
			break;
		case Constants.KEY_ESC:
			this.robot.keyPress(KeyEvent.VK_ESCAPE);
			this.robot.keyRelease(KeyEvent.VK_ESCAPE);
			break;
		case Constants.KEY_TASKSWITCH:
			System.out.println("TashSw");
			this.robot.keyPress(KeyEvent.VK_ALT);
			this.robot.keyPress(KeyEvent.VK_TAB);
			this.robot.keyRelease(KeyEvent.VK_TAB);	
			this.robot.keyRelease(KeyEvent.VK_ALT);
			break;
		default:
			break;
		}
	}
}
