package org.todo.gameOfLife;

public class Cell implements Cloneable
{
	private boolean alive;
	private int matrixPosition;
	
	public Cell()
	{
		alive = false;
		
	}
	
	public Cell(boolean alive)
	{
		this.alive = alive;
	}
	
	public Cell clone()
	{
		Cell copy = new Cell();
		copy.alive = this.alive;
		copy.matrixPosition = this.matrixPosition;
		
		return copy;
	}
	public void setState(boolean alive)
	{
		this.alive = alive;
	}
	public void setDead()
	{
		alive = false;
	}
	public void setAlive()
	{
		alive = true;
	}
	public boolean isAlive()
	{
		return alive;
	}
	
	public boolean isDead()
	{
		return (!alive);
	}
	
	public void setMatrixPosition(int position)
	{
		this.matrixPosition = position;
	}
	
	public int getMatrixPosition()
	{
		return this.matrixPosition;
	}
}
