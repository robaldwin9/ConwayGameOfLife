package org.todo.gameOfLife;

public class CellMatrix implements Cloneable
{
	private Cell[][] matrix;
	private int size;
	
	public CellMatrix(int size)
	{
		this.size = size;
		matrix = new Cell[size][size];
		
		for(int i = 0; i < size; i ++)
		{
			for(int j = 0; j < size; j++)
			{
				matrix[i][j] = new Cell();
				matrix[i][j].liveNeighbors = 0;
			}
		}
	}
	
	public CellMatrix clone()
	{
		CellMatrix copy = new CellMatrix(this.getSize());
		
		for(int i = 0; i < this.getSize(); i++)
		{
			for(int j = 0; j < this.getSize(); j++)
			{
				copy.matrix[i][j] = this.matrix[i][j].clone();
			}
		}
		
		return copy;
	}
	
	//randomly set alive or dead
	public void randomize(int max)
	{
		for(int i = 0; i < size; i ++)
		{
			for(int j = 0; j < size; j++)
			{
				int randomNum = (int) (Math.random() * max + 1);
				if(randomNum == 1)
					matrix[i][j].setAlive();
				else
					matrix[i][j].setDead();
			}
		}
	}
	
	//Update cell based on their neighbors
	public void nextGeneration()
	{
		updateCellNeighborCount();
		
		for(int i = 0; i < size; i++)
		{
			for(int j = 0; j < size; j++)
			{
				if(this.get(i,j).liveNeighbors < 2 && this.get(i, j).isAlive())
					this.get(i,j).setDead();
				else if(this.get(i,j).liveNeighbors > 3 && this.get(i, j).isAlive())
					this.get(i, j).setDead();
				else if (this.get(i, j).isDead() && this.get(i,j).liveNeighbors == 3) //error?
					this.get(i, j).setAlive();
				else if(this.get(i, j).isAlive() && this.get(i,j).liveNeighbors == 3 || this.get(i,j).liveNeighbors == 2)
					this.get(i, j).setState(this.get(i, j).isAlive());
				}
			}
		}
		
	//Adjusts cells neighbor count
	private void updateCellNeighborCount()
		{
			for(int i = 0; i < size; i++)
			{
				for( int j = 0; j < size; j++) // top left corner
				{
					this.get(i,j).liveNeighbors = 0;
					 
					if(j == 0 && i == 0)
					 {
						 checkBottom(i,j);
						 BRDiagnal(i,j);
						 checkRight(i,j);
					 }
					
					else if(j == size - 1 && i == size - 1) //bottom right corner
					{
						checkTop(i,j);
						TLDiagnal(i,j);
						checkLeft(i,j);
					}
					
					else if(i == 0 && j == size -1) // top right corner
					 {
						
						 checkBottom(i,j);
						 BLDiagnal(i,j);
						 checkLeft(i,j);
					 }
					
					else if (i == size -1 && j == 0) // bottom left corner
					 {
						checkTop(i,j);
						TRDiagnal(i,j);
						checkRight(i,j);
					 }
					 
					else if(j == 0) //first of row
					{
						checkRight(i,j);
						checkBottom(i,j);
						checkTop(i,j);
						BRDiagnal(i,j);
						TRDiagnal(i,j);
					}
					
					else if( j == size -1) //last of row
					{
						checkLeft(i,j);
						checkBottom(i,j);
						checkTop(i,j);
						BLDiagnal(i,j);
						TLDiagnal(i,j);
					}
					
					else if(i == 0) //first row
					{
						checkBottom(i,j);
						BRDiagnal(i,j);
						BLDiagnal(i,j);
						checkRight(i,j);
						checkLeft(i,j);
					}
					
					else if(i == size -1 ) //last row
					{
						checkTop(i,j);
						 TRDiagnal(i,j);
						TLDiagnal(i,j);
						checkRight(i,j);
						 checkLeft(i,j);
					}
					
					else
					{
						 checkTop(i,j);
						 checkBottom(i,j);
						
						TRDiagnal(i,j);
						TLDiagnal(i,j);
						
						 BRDiagnal(i,j);
						 BLDiagnal(i,j);
						
						checkRight(i,j);
						 checkLeft(i,j);
					}
				}
			}
		}
		
	//Methods for checking neighbors relative to current cells position
	private void checkTop( int i, int j)
	{
		if(this.get(i - 1, j).isAlive())
			this.get(i, j).liveNeighbors++;
	}
			
	private void checkBottom( int i, int j)
	{
		if(this.get(i + 1, j).isAlive())
			this.get(i, j).liveNeighbors++;
	}
			
	private void checkRight( int i, int j)
	{
		if(this.get(i, j+1).isAlive())
			this.get(i, j).liveNeighbors++;
	}
			
	private void checkLeft(int i, int j)
	{
		if(this.get(i, j-1).isAlive())
			this.get(i, j).liveNeighbors++;
	}
			
	private void TRDiagnal( int i, int j)
	{
		if(this.get(i-1,j+1).isAlive())
			this.get(i, j).liveNeighbors++;
	}
			
	private void TLDiagnal( int i, int j)
	{
		if(this.get(i - 1, j - 1).isAlive())
			this.get(i, j).liveNeighbors++;
	}
			
	private void BRDiagnal(int i, int j)
	{
		if(this.get(i+1, j+ 1).isAlive())
			this.get(i, j).liveNeighbors++;
	}
			
	private void BLDiagnal( int i, int j)
	{
		if(this.get(i + 1, j - 1).isAlive())
			this.get(i, j).liveNeighbors++;
	}
	
	public int getSize()
	{
		return size;
	}
	
	public  Cell get(int i, int j)
	{
		return this.matrix[i][j];
	}
	
	protected class Cell implements Cloneable
	{
		private boolean alive;
		private int matrixPosition;
		private int liveNeighbors;
		
		public Cell()
		{
			alive = false;
			liveNeighbors = 0;
			matrixPosition = 0;
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
	}
}