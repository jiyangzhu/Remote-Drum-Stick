package ust.jzhuaq.drumPC.Util;

import java.awt.AWTException;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.util.List;

import ust.jzhuaq.drumPC.Cursor;
import ust.jzhuaq.drumPC.MainFrame;

public class Mouse {
	private Robot robot;

	private float xLeftover = 0;
	private float yLeftover = 0;

	private GraphicsDevice[] gDevices;
	private Rectangle[] gBounds;

	private static final float sensitivity = 4f;

	int currX;
	int currY;

	private Cursor cursorMove;

	public Mouse() throws AWTException {
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
			System.out.println("x");
			x = 0;
		}
		try {
			y = Integer.parseInt(args.get(4).toString());
		} catch (Exception e) {
			System.out.println("y");
			y = 0;
		}
		try {
			z = Integer.parseInt(args.get(5).toString());
		} catch (Exception e) {
			System.out.println("z");
			z = 0;
		}
		/*if ((args.get(3) == null) || args.get(3).toString().isEmpty())
			x = 0;
		else
			x = Integer.parseInt(args.get(3).toString());

		if ((args.get(4) == null) || args.get(4).toString().isEmpty())
			y = 0;
		else
			y = Integer.parseInt(args.get(4).toString());

		if ((args.get(5) == null) || args.get(5).toString().isEmpty())
			z = 0;
		else
			z = Integer.parseInt(args.get(5).toString());
*/
		switch (type) {
		case Constants.EVENT_CLICK:
			mouseClick(0);
			break;
		case Constants.EVENT_CURSOR:
			move(x, y, z);
		default:
			break;
		}
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
			System.out.println("Click left");
			break;

		case 1:
			this.robot.mousePress(InputEvent.BUTTON3_MASK);
			this.robot.mouseRelease(InputEvent.BUTTON3_MASK);
			System.out.println("Click right");
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

	public void mouseMove(float xOffset, float yOffset) {
		PointerInfo info = MouseInfo.getPointerInfo();
		if (info != null) {
			java.awt.Point p = info.getLocation();
			float ox = (xOffset * sensitivity) + xLeftover;
			float oy = (yOffset * sensitivity) + yLeftover;
			int ix = Math.round(ox);
			int iy = Math.round(oy);
			xLeftover = ox - ix;
			yLeftover = oy - iy;
			//
			p.x += ix;
			p.y += iy;
			int l = this.gBounds.length;
			for (int i = 0; i < l; ++i) {
				if (this.gBounds[i].contains(p)) {
					this.robot.mouseMove(p.x, p.y);
					break;
				}
			}

			try {
				this.robot.mouseMove(p.x, p.y);// for systems with quick bounds
												// checking, allow mouse to move
												// smoothly along to and left
												// edges
			} catch (Exception e) {
			}

		}
	}

}
