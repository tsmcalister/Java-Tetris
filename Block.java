import java.awt.Color;


public class Block 
{
	private BlockDesign block;
	private Color blockColor;
	private int rotation;
	private int xPos;
	private int yPos;
	
	Block(BlockDesign a, int xp, int yp)
	{
		this.setBlock(a);
		this.setxPos(xp);
		this.setyPos(yp);
	}

	public int getxPos() {
		return xPos;
	}

	public void setxPos(int xPos) {
		this.xPos = xPos;
	}

	public int getyPos() {
		return yPos;
	}

	public void setyPos(int yPos) {
		this.yPos = yPos;
	}

	public BlockDesign getBlock() {
		return block;
	}

	public void setBlock(BlockDesign block) {
		this.block = block;
	}
}
