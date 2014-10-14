import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

public class Screen extends Applet implements KeyListener{
	Level mainLevel = new Level();
	int deltat;
	ArrayList<BlockDesign> blockList = new ArrayList<BlockDesign>();
	BlockDesign lBlock = new BlockDesign(new int[][]  {{0,1,0,0},{0,1,0,0},{0,1,1,0},{0,0,0,0}},Color.cyan);
	BlockDesign lpBlock = new BlockDesign(new int[][] {{0,1,0,0},{0,1,0,0},{1,1,0,0},{0,0,0,0}},Color.blue);
	BlockDesign sBlock = new BlockDesign(new int[][]  {{1,1,0,0},{0,1,1,0},{0,0,0,0},{0,0,0,0}},Color.red);
	BlockDesign spBlock = new BlockDesign(new int[][] {{0,1,1,0},{1,1,0,0},{0,0,0,0},{0,0,0,0}},Color.orange);
	BlockDesign kBlock = new BlockDesign(new int[][]  {{0,1,0,0},{1,1,1,0},{0,0,0,0},{0,0,0,0}},Color.magenta);
	BlockDesign qBlock = new BlockDesign(new int[][]  {{1,1,0,0},{1,1,0,0},{0,0,0,0},{0,0,0,0}},Color.green);
	BlockDesign iBlock = new BlockDesign(new int[][]  {{1,0,0,0},{1,0,0,0},{1,0,0,0},{1,0,0,0}},Color.yellow);
	int startX = 30;
	int startY = 30;
	BufferedImage gameOverScreen = null;
	
	
	public void initWindow()
	{
		this.setLocation(0,0);
		 this.setSize(380,700);
		 this.setVisible(true);
		 this.setBackground(Color.LIGHT_GRAY);
		 mainLevel.initblockMatrix();
	}
	public void drawBlock(Graphics g, Block b)
	{
		for(int i = 0; i < 4; i++)
		{
			for(int j = 0; j < 4; j++)
			{
				if(b.getBlock().getDesign()[i][j] == 1)
				{
					g.setColor(b.getBlock().getBlockColor());
					g.fillRect(
							30+(b.getxPos())*30+(i-2)*30
							,b.getyPos()*30+j*30
							,30
							,30);
					g.setColor(Color.white);
					g.drawRect(
							30+(b.getxPos())*30+(i-2)*30
							,b.getyPos()*30+j*30
							,30
							,30);
					g.drawRect(
							30+(b.getxPos())*30+(i-2)*30+1
							,b.getyPos()*30+j*30+1
							,30-1
							,30-1);
					
				}
				g.setColor(Color.black);
				//g.drawRect(30+(b.getxPos()-2)*30, 30+(b.getyPos()-1)*30, 30*4, 30*4);
			}
		}
	}
	
	
	public void drawLevel(Graphics g, Level l)
	{
		int blockSize = 30;
		g.setColor(Color.white);
		g.fillRect(30-1, 30-1, (l.getGridWidth()-4)*blockSize+2, (l.getGridHeight()-1)*blockSize+2);
			drawBlock(g,mainLevel.getCurBlock());
		drawMatrix(g,l,blockSize);
		g.setColor(Color.black);;
		g.drawString("SCORE: "+l.getGameScore(), 150, 20);
	}
	
	
	public void drawMatrix(Graphics g, Level l, int bs)
	{
		for(int i = 2; i<l.getGridWidth()-2; i++)
		{
			for(int j = 0; j<l.getGridHeight()-1; j++)
			{
				if(l.getBlockMatrix()[i][j] == 1)
				{
					g.setColor(l.getBlockMatrixColor()[i][j]);
					g.fillRect(30+(i-2)*bs,30+ (j)*bs, bs, bs);
					g.setColor(Color.white);
					g.drawRect(30+(i-2)*bs,30+ (j)*bs, bs, bs);
					g.drawRect(30+(i-2)*bs+1,30+ (j)*bs+1, bs-1, bs-1);
				}
			}
		}
	}
	
	
	public void initStandardBlocks()
	{
		
		blockList.add(lBlock);
		blockList.add(lpBlock);
		blockList.add(sBlock);
		blockList.add(spBlock);
		blockList.add(kBlock);
		blockList.add(qBlock);
		blockList.add(iBlock);
		
	}
	BlockDesign getNextBlockDesign()
	{
		return(blockList.get((int)(Math.random()*blockList.size())));
	}
	public void updateGame()
	{
		//check if game over
		for(int i = 2; i < mainLevel.getGridWidth()-2; i ++)
		{
			if(mainLevel.getBlockMatrix()[i][1] == 1)
			{
				mainLevel.setGameStatus(3);
			}
		}
		if (mainLevel.getGameStatus() == 0)
		{
			mainLevel.addBlock(new Block(getNextBlockDesign(),4,0));
			mainLevel.setGameStatus(1);
		}
		if (mainLevel.getGameStatus() == 1)
		{
			//check if CurBlock has reached bottom
			boolean blockMerged = false;
			for(int i = 0; i < 4; i++)
			{
				for(int j = 0; j < 4; j++)
				{
					if(mainLevel.getCurBlock().getBlock().getDesign()[i][j] == 1 && mainLevel.getBlockMatrix()[mainLevel.getCurBlock().getxPos()+i][mainLevel.getCurBlock().getyPos()+j] == 1 && blockMerged == false)
					{
						blockMerged = true;
						mainLevel.mergeBlock();
						mainLevel.setGameStatus(0);
						mainLevel.deleteLines();
						break;
					}
				}
			}
			if(mainLevel.getGameStatus() == 1)
			{
				mainLevel.moveBlock();
				
			}
			
		}
		if(mainLevel.getGameStatus() == 3)
		{
			
		}
	}
	public void sleep(int t)
	{
		try {
			TimeUnit.MILLISECONDS.sleep(t);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void init()
	{
		initStandardBlocks();
		initWindow();
		addKeyListener(this);
		deltat = 0;
	}
	public void stop()
	{
		this.setVisible(false);
	}
	public void paint(Graphics g)
	{
		if(deltat%400 == 0)
		{
			updateGame();
			deltat = 0;
		}
		deltat++;
		if(mainLevel.getGameStatus() !=3)
		{
			drawLevel(g,mainLevel);
		}
		else
		{
			try {
			    gameOverScreen = ImageIO.read(new File("go.jpg"));
			} catch (IOException e) {
			}
			g.setColor(Color.black);
			g.fillRect(0, 0, 380, 700);
			g.drawImage(gameOverScreen,-50, 125, this);
		}
		repaint();
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}
	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode())
		{
			case KeyEvent.VK_UP:
				mainLevel.rotateBlock();
				break;
			case KeyEvent.VK_RIGHT:
				mainLevel.moveBlockR();
				break;
			case KeyEvent.VK_LEFT:
				mainLevel.moveBlockL();
				break;
			case KeyEvent.VK_DOWN:
				mainLevel.manualMove();
				break;
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}


