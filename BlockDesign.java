import java.awt.Color;

public class BlockDesign {
	private int[][] blockDesign = new int[9][9];
	private Color blockColor;
	BlockDesign(int[][] bd, Color c)
	{
		this.blockDesign = bd;
		this.blockColor = c;
	}
	
	int[][] getDesign()
	{
		return this.blockDesign;
	}

	public Color getBlockColor() {
		return blockColor;
	}

	public void setBlockColor(Color blockColor) {
		this.blockColor = blockColor;
	}
	public void rotate()
	{
		int[][] temp = new int [4][4];
		for(int i = 0; i < 4; i++)
		{
			for(int j = 0; j < 4; j++)
			{
				temp[i][j] = this.blockDesign[j][3-i];
			}
		}
		for(int i = 0; i < 4; i++)
		{
			for(int j = 0; j < 4; j++)
			{
				this.blockDesign[i][j] = temp[i][j];
			}
		}
		boolean xshift = true;
		boolean yshift = true;
		while(xshift || yshift)
		{
			for(int i = 0; i < 4; i++)
			{
				if (this.blockDesign[i][0] == 1)
				{
					xshift = false;
				}
				if (this.blockDesign[0][i] == 1)
				{
					yshift = false;
				}
			}
			if(xshift)
			{
				for(int i = 0; i < 4; i++)
				{
					for(int j = 0; j < 3; j++)
					{
						this.blockDesign[i][j] = this.blockDesign[i][j+1];
					}
				}
				this.blockDesign[0][3] = 0;
				this.blockDesign[1][3] = 0;
				this.blockDesign[2][3] = 0;
				this.blockDesign[3][3] = 0;
			}
			if(yshift)
			{
				for(int i = 0; i < 4; i++)
				{
					for(int j = 0; j < 3; j++)
					{
						this.blockDesign[j][i] = this.blockDesign[j+1][i];
					}
				}
				this.blockDesign[3][0] = 0;
				this.blockDesign[3][1] = 0;
				this.blockDesign[3][2] = 0;
				this.blockDesign[3][3] = 0;
			}
		}
	}
}

