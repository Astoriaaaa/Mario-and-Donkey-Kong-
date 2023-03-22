import javax.swing.ImageIcon;

public class DonkeyKong {

	private int xPos, yPos;
	private double width, height;
	private ImageIcon imgKong;
	private int dir;
	
	public DonkeyKong() {
		
		xPos = 10;
		yPos = 15;
		dir = 1;
		
		imgKong = new ImageIcon("images/DonkeyKongRB.png");
		
		width = imgKong.getIconWidth();
		height = imgKong.getIconHeight();
	}
	
	public int getDir()
	{
		if(xPos == 0)
			dir = 1;
		else if(xPos == width + 500)
			dir = -1;
		
		return dir;
	}
	public void setX(int x) {
		xPos = x;

	}
	public void setY(int y) {
		yPos = y;
	}
	public int getX() {
		return xPos;
	}
	public int getY() {
		return yPos;
	}
	public double getWidth() {
		return width;
	}
	public double getHeight() {
		return height;
	}
	public ImageIcon getImage() {
		return imgKong;
	}
	public void moveLeft() {
		xPos -= 10;
	}
	public void moveRight() {
		xPos += 10;
	}
	
}
