import javax.swing.ImageIcon;

public class mario {
	
	private int xPos, yPos;
	private double width, height;
	private ImageIcon imgMarioRIGHT, imgMarioLEFT;
	
	public mario()
	{
		xPos = 10;
		yPos = 520;
		
		
		imgMarioRIGHT = new ImageIcon("images/MarioRIGHT.png");
		imgMarioLEFT = new ImageIcon("images/MarioLEFT.png");
		
		width = imgMarioRIGHT.getIconWidth();
		height = imgMarioRIGHT.getIconHeight();
	}
	
	public double getwidth()
	{
		return width;
	}
	
	public double getHeight()
	{
		return height;
	}
	public int getX()
	{
		return xPos;
	}
	public int getY()
	{
		return yPos;
	}
	

	public ImageIcon getimgmario(int x)
	{
		if (x == 1)
		{
			return imgMarioRIGHT;
		}
		if (x == -1)
			return imgMarioLEFT;
		else
			return imgMarioRIGHT;
	}
	public void moveleft()
	{
		if(xPos <= 10)
			xPos += 10;
		else
		xPos -=10;
	}
	public void moveright()
	{
		if(xPos >= 585 )
			xPos += 0;
		else
		xPos +=10;
	}
}
