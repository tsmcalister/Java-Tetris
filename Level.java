import java.awt.Color;
import java.util.ArrayList;


public class Level {
	private int gridWidth = 14;
	private int gridHeight = 22;
	private Block curBlock;
	private int[][] blockMatrix = new int[gridWidth+1][gridHeight+1];
	private Color[][] blockMatrixColor = new Color[gridWidth][gridHeight+1];
	//game Status 0 = Generate new Block
	//game Status 1 = Block Falling 
	private int gameStatus = 0;
	private int gameScore = 0;
	
	Level()
	{
		for(int i = 0; i < gridWidth; i++)
		{
			blockMatrix[i][gridHeight] = 1;
		}
	}
	public void initblockMatrix()
	{
		for(int i = 0; i < gridWidth; i ++)
		{
			this.blockMatrix[i][gridHeight-1] = 1;
		}
		for(int i = 0; i < gridHeight; i ++)
		{
			this.blockMatrix[0][i] = 1;
			this.blockMatrix[1][i] = 1;
			this.blockMatrix[gridWidth][i] = 1;
			this.blockMatrix[gridWidth-1][i] = 1;
			this.blockMatrix[gridWidth-2][i] = 1;
		}
	}
	public Color[][] getBlockMatrixColor()
	{
		return this.blockMatrixColor;
	}
	public int getGridHeight() {
		return gridHeight;
	}

	public int getGridWidth() {
		return gridWidth;
	}

	public int getGameStatus() {
		return gameStatus;
	}

	public void setGameStatus(int gameStatus) {
		this.gameStatus = gameStatus;
	}
	public void addBlock(Block a)
	{
		setCurBlock(a);
	}
	public void manualMove()
	{
		boolean move = true;
		for(int i = 0; i < 4; i++)
		{
			for(int j = 0; j < 4; j++)
			{
				if(this.curBlock.getBlock().getDesign()[i][j] ==1)
				{
					if (this.getBlockMatrix()[this.curBlock.getxPos() +i][this.curBlock.getyPos()+j+1] == 1)
					{
						move = false;
					}
				}
			}
		}
		if (move)
		{
			this.curBlock.setyPos(this.curBlock.getyPos()+1);
		}
	}
	public void moveBlock()
	{
		this.curBlock.setyPos(this.curBlock.getyPos()+1);
	}
	public void moveBlockR()
	{
		boolean move = true;
		for(int i = 0; i < 4; i++)
		{
			for(int j = 0; j < 4; j++)
			{
				if(this.curBlock.getBlock().getDesign()[i][j] ==1)
				{
					if (this.getBlockMatrix()[this.curBlock.getxPos() +i+1][this.curBlock.getyPos()+j] == 1)
					{
						move = false;
					}
				}
			}
		}
		if (move)
		{
			this.curBlock.setxPos(this.curBlock.getxPos()+1);
		}

	}
	public void moveBlockL()
	{
		boolean move = true;
		for(int i = 0; i < 4; i++)
		{
			for(int j = 0; j < 4; j++)
			{
				if(this.curBlock.getBlock().getDesign()[i][j] ==1)
				{
					if (this.getBlockMatrix()[this.curBlock.getxPos() +i-1][this.curBlock.getyPos()+j] == 1)
					{
						move = false;
					}
				}
			}
		}
		if (move)
		{
			this.curBlock.setxPos(this.curBlock.getxPos()-1);
		}
	}
	public void rotateBlock()
	{
		this.curBlock.getBlock().rotate();
		boolean hit = false;
		for(int i = 0; i < 4; i++)
		{
			for(int j = 0; j < 4; j++)
			{
				if(this.curBlock.getBlock().getDesign()[i][j] ==1)
				{
					if (this.getBlockMatrix()[this.curBlock.getxPos() +i][this.curBlock.getyPos()+j] == 1)
					{
						hit=true;
					}
				}
			}
		}
		if(hit)
		{
			this.curBlock.getBlock().rotate();
			this.curBlock.getBlock().rotate();
			this.curBlock.getBlock().rotate();
		}
	}
	public void debugBlock()
	{
		for(int i=0; i < gridHeight; i ++)
		{
			for(int j=0; j < gridWidth; j ++)
			{
				if(this.blockMatrix[j][i] == 1)
				{
					System.out.print("#");
				}
				else
				{
					System.out.print(" ");
				}
			}
			System.out.println();
		}
	}
	public int checkLines()
	{
		boolean fullLine = true;
		for(int i = gridHeight-2; i > 0; i --)
		{
			fullLine = true;
			for(int j = 2; j < gridWidth-2; j ++)
			{
				if (this.blockMatrix[j][i] == 0)
				{
					fullLine = false;
				}
			}
			if(fullLine)
			{
				System.out.print(i);
				return(i);
			}
		}
		return(0);
	}
	public void deleteLines()
	{	
		while(checkLines() != 0)
		{
			int tempArr[][] = new int[gridWidth][gridHeight];
			Color tempCol[][] = new Color[gridWidth][gridHeight];
			int line = checkLines();
			if(line != 0)
			{
				this.gameScore += 100;
				for(int i = 2; i < gridWidth-2; i++)
				{
					this.blockMatrix[i][line] = 0;

				}
				//load Array in temp array
				for(int i = 2; i < gridWidth-2; i ++)
				{
					for(int j = 0; j < line; j ++)
					{
						tempArr[i][j] = this.blockMatrix[i][j];
						tempCol[i][j] = this.blockMatrixColor[i][j];
						
						this.blockMatrix[i][j] = 0;
						this.blockMatrixColor[i][j] = Color.gray;
 					}
				}
				debugBlock();
				boolean hitTester = false;
				int iter = 0;
				while (!hitTester)
				{
					iter++;
					for(int i = 2; i < gridWidth-2; i ++)
					{
						for(int j = 0; j < line+1; j ++)
						{
							if(tempArr[i][j] == 1 && this.blockMatrix[i][j+1] == 1)
							{
								hitTester = true;
							}
						}
					}
					if(!hitTester)
					{
	
						for(int i = 2; i < gridWidth-2; i ++)
						{
							for(int j = gridHeight-1; j >0; j --)
							{
								tempArr[i][j] = tempArr[i][j-1];
								tempCol[i][j] = tempCol[i][j-1];
							}
						}
					}
					if(iter > 9)
					{
						hitTester= true;
					}
				}
				for(int i = 2; i < gridWidth-2; i ++)
				{
					for(int j = 0; j < gridHeight-1; j ++)
					{
						if(this.blockMatrix[i][j] == 0)
						{
							this.blockMatrix[i][j] = tempArr[i][j];
							this.blockMatrixColor[i][j] = tempCol[i][j];
						}
					}
				}/*
				//load temp Array into real array
				for(int i = 2; i < gridWidth-2; i ++)
				{
					for(int j = 0; j < gridHeight-1; j ++)
					{
						if(this.blockMatrix[i][j] == 0)
						{
							this.blockMatrix[i][j] = tempArr[i][j];
						}
					}
				}*/
				debugBlock();
			}
			
		}

		

	}
	
	public void mergeBlock()
	{
		for(int i =0; i < 4; i++)
		{
			for(int j =0; j < 4; j++)
			{
				if(this.curBlock.getBlock().getDesign()[i][j] == 1)
				{
					this.blockMatrix[i+this.curBlock.getxPos()][j+this.curBlock.getyPos()-1] = 1;
					this.blockMatrixColor[i+this.curBlock.getxPos()][j+this.curBlock.getyPos()-1] = this.curBlock.getBlock().getBlockColor();
				}
			}
		}
	}
	
	public int[][] getBlockMatrix() {
		return blockMatrix;
	}

	public void setBlockMatrix(int[][] blockMatrix) {
		this.blockMatrix = blockMatrix;
	}
	public Block getCurBlock() {
		return curBlock;
	}
	public void setCurBlock(Block curBlock) {
		this.curBlock = curBlock;
	}
	public int getGameScore() {
		return gameScore;
	}
	public void setGameScore(int gameScore) {
		this.gameScore = gameScore;
	}
	
}
