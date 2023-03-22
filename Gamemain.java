import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
/*
 * NAME: Phil Terence, Aayushma Sapkota
 * DATE: Wednesday, December 14, 2022
 * COURSE CODE: ICS 3U1
 * PROGRAM DESCIRPTION: this program will display one panel for 3 seconds, another panel for another 3 seconds,
 * and then go to the game screen. Donkey Kong will move and throw barrels towards mario. Within 20 seconds, the
 * user must use the left and right keys to move mario and dodge the barrels. if they fail to dodge the barrel, 
 * thier lives will be decreased. they have a total of 3 lives and if they run out of lives a message will indicate 
 * that they lost and how many more seconds they needed to win. if they don't run out of lives for 20 seconds, a 
 * message will indicate that they won.
 */

public class Gamemain extends JPanel implements ActionListener, KeyListener{
	
	// fields or global variables 
	private int xM, lives;
	private Timer roomadd, dk, barrel, movebarrel, clock, framechange;
	private DonkeyKong dong;
	private Rectangle2D maskMario, maskBarrel;
	private ImageIcon loseM, winM;
	private int barrellsec, framechangesec, gamesec;
	private ImageIcon firstback, secondback, thirdback, img, barrelimg;
	public JFrame frame = new JFrame();
	private mario mario;
	private boolean rightM, leftM;
	private int barellx, barelly;
	private Font f;

	public static void main(String[] args) 
	{
		// calls constructor from this class 
		new Gamemain();
	}
	
	// constructor to initialize global variables and set properties of the frame/JPanel
	public Gamemain() 
	{
		// initialize images 
		firstback = new ImageIcon("images/Title Page.png");
		secondback = new ImageIcon("images/Title 2.jpg");
		thirdback = new ImageIcon("images/backGround.png");
		barrelimg = new ImageIcon("images/Barrel.png");
		loseM = new ImageIcon("images/IconMsgKong.png");
		winM =  new ImageIcon("images/IconMsgMario.png");
		img = firstback;
		
		// create object for mario class and DonkeyKong class
		mario = new mario();
		dong = new DonkeyKong();
		
		rightM = false;
		leftM= false;
		
		// x and y coordinates of barrel
		barellx = dong.getX();
		barelly = dong.getY();
		
		// masks for mario and barrel
		maskMario = new Rectangle2D.Double(mario.getX(), mario.getY(), mario.getwidth(), mario.getHeight() + 20);
		maskBarrel = new Rectangle2D.Double(barellx, barelly, barrelimg.getIconWidth(), barrelimg.getIconHeight() - 20);
		
		// import new font 
		try
		{
			f = Font.createFont(Font.TRUETYPE_FONT, new File ("images/emulogic.ttf")).deriveFont(30f);
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, "There is an Error with the font!", "ERROR",
			JOptionPane.ERROR_MESSAGE);
		}
		
		// set properties of JPanel
		setLayout(null);
		setBackground(Color.WHITE);
		addKeyListener(this);
		setFocusable(true);
		requestFocus();
		// set properties of frame 
		frame.setSize(400, 535);
		frame.setContentPane(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		
		// initialize timers
		roomadd = new Timer(50, this); 
		dk = new Timer(50, this); 
		barrel = new Timer (1000, this); 
		movebarrel = new Timer(50, this); 
		clock = new Timer(1000, this); 
		framechange = new Timer(1000, this); 

		// initialize counters 
		barrellsec = 3;
		framechangesec = 3;
		gamesec = 20;
		lives = 3;
		
		// start timer to change the frame 
		framechange.start();	
	}

	public void keyTyped(KeyEvent e) {
		}

	// check which key is pushed 
	public void keyPressed(KeyEvent e) 
	{
		if (e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			// set boolean to true so program knows what direction to go and what image to use 
			leftM = true;
			// move mario
			xM = -1;
			// update mask on mario
			maskMario = new Rectangle2D.Double(mario.getX(), mario.getY(), mario.getwidth(), mario.getHeight() + 20);
		}
		else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			// set boolean to true so program knows what direction to go and what image to use
			rightM = true;
			// move mario
			xM = 1;
			// update mask on mario
			maskMario = new Rectangle2D.Double(mario.getX(), mario.getY(), mario.getwidth(), mario.getHeight() + 20);
		}
	}
	
	// check which key is not being pushed 
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			// set boolean to false so program does not continue to move mario 
			leftM = false;
		}
		else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			// set boolean to false so program does not continue to move mario
			rightM = false;
		}
	}

	public void actionPerformed(ActionEvent e) 
	{
		// use timer to change frame 
		if(e.getSource() == framechange)
		{
			framechangesec -= 1;
			// when timer is 0 change frame to second background 
			if(framechangesec == 0)
			{
				frame.setSize(secondback.getIconWidth(), secondback.getIconHeight() + 20);
				img = secondback;
				
			}
			// when timer is -3 change frame to game screen
			if(framechangesec == -3)
			{
				frame.setSize(thirdback.getIconWidth(), thirdback.getIconHeight());
				img = thirdback;
				 
				framechange.stop(); // stop timer to change frame
				roomadd.start(); // start timer to move mario
				barrel.start(); // start timer to countdown seconds before dropping barrel
				movebarrel.start(); // start timer to move barrel 
				clock.start(); // start timer to countdown seconds in the game 
				dk.start(); // start timer to move Donkey Kong
			}
		}
		// use timer to move mario
		if(e.getSource() == roomadd)
		{
			if(rightM == true)
			{
				mario.moveright();
			}
			if(leftM == true)
			{
				mario.moveleft();
			}
		}
		// use timer to move Donkey Kong 
		if(e.getSource() == dk)
		{
			if(dong.getDir() == 1)
			{
				dong.moveRight();
				
			}
			else
			{
				dong.moveLeft();
			}
		}
		// use timer to countdown seconds before dropping barrel
		if(e.getSource() == barrel)
		{
			barrellsec -=1;
			if(barrellsec == 0)
			{
				barellx = dong.getX();
				barelly = dong.getY();
				barrellsec = 3;
			}
		}
		// use timer to move barrel
		if(e.getSource() == movebarrel)
		{
			barelly += 20;
			// if the barrel and the mario intersect timer is stopped, lives is decreased to 1 and the barrel is moved off the frame
			maskBarrel = new Rectangle2D.Double(barellx, barelly, barrelimg.getIconWidth(), barrelimg.getIconHeight() - 20);
			if(maskBarrel.intersects(maskMario))
			{
				barrel.stop();
				lives -= 1;
				barellx = -1000;
				barelly = -1000;
				// barrel timer restarts 
				barrel.start();
			}
		}
		
		// use timer to countdown seconds in the game 
		if(e.getSource() == clock)
		{
			gamesec -= 1;
		}
		
		// if user runs out of lives stop timers and show game over message 
		if(lives==0)
		{
			roomadd.stop();
			barrel.stop();
			movebarrel.stop();
			clock.stop();
			dk.stop();
			
			JOptionPane.showMessageDialog(null, "Game Over! You loose!\nYou had " + gamesec + " seconds left to survive", "Gmse Over!", JOptionPane.PLAIN_MESSAGE, loseM);
		}
		// if time runs out before lives, stop timers and show winner message 
		else if(lives> 0 && gamesec == 0)
		{
			roomadd.stop();
			barrel.stop();
			movebarrel.stop();
			clock.stop();
			dk.stop();
			JOptionPane.showMessageDialog(null, "CONGRATULATIONS!\n You Win!", "Winner!", JOptionPane.PLAIN_MESSAGE, winM);
		}
		repaint();
	}
	public void paint(Graphics g)
	{
		// set background for JFrame 
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(img.getImage(), 0, 0, this);
		
		// make mario, Donkey Kong, barrel, gamesec, and timer print in game screen 
		if(framechangesec == -3)
		{
			g2.drawImage(mario.getimgmario(xM).getImage(), mario.getX(), mario.getY(), this);
			g2.drawImage(dong.getImage().getImage(), dong.getX(), dong.getY(),this );
			g2.drawImage(barrelimg.getImage(), barellx, barelly, this);
			Color col = new Color(0, 0, 0);
			g2.setFont(f);
			g2.setColor(col);
			g2.drawString("TIMER " + Integer.toString(gamesec), 445, 30);
			g2.drawString("LIVES " + Integer.toString(lives), 0, 30);
		}	
	}
}

