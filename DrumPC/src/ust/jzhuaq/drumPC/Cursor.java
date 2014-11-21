package ust.jzhuaq.drumPC;

public class Cursor {

	private int x;
	private int y;
	
	private String signedX;	//with "+" or "-" prefix
	private String signedY;	//with "+" or "-" prefix
	
	public Cursor() {
		this.x = 0;
		this.y = 0;
		signedX = addPrefix(this.x);
		signedY = addPrefix(this.y);
	}
	
	public Cursor(int x, int y){
		this.x = x;
		this.y = y;
		signedX = addPrefix(this.x);
		signedY = addPrefix(this.y);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public String getSignedX() {
		return signedX;
	}

	public void setSignedX(String signedX) {
		this.signedX = signedX;
	}

	public String getSignedY() {
		return signedY;
	}

	public void setSignedY(String signedY) {
		this.signedY = signedY;
	}

	private String addPrefix(int num){
		String signedNum = null;
		if(num == 0){
			signedNum = "" + num;
		} else if (num > 0) {
			signedNum = "+" + num;
		} else {
			signedNum = "" + num;
		}
		return signedNum;
	}

}
