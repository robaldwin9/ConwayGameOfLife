package com.rjservers.gameoflife;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
/*
 * Handles a 2d representation of conways game of life
 */
public class ConwayCanvas extends Canvas implements Runnable 
{
	private GraphicsContext context = getGraphicsContext2D();
	private double height; 			//Starting height of canvas
	private double width; 			//Starting width of canvas
	private CellMatrix grid; 		//A matrix of binary cell values
	private int density; 		
	
	//Determines the density of starting matrix
	private int size;				//The size of the matrix
	private double drawDelay; 		//Time inbetween draw commands
	private Color cellColor;		//Color of cells drawing on canvas
	private Color backgroundColor;	//Color of non Living cells on canvas
	private long drawCount;
	boolean auto;
	
	/*
	 * 
	 */
	public ConwayCanvas( double height, double width, boolean auto)
	{
		drawCount = 0;
		size = 200;
		drawDelay = 0.1; //translates to 100 miliseconds
		density = 10;
		cellColor = Color.WHITE;
		backgroundColor = Color.BLACK;
		this.auto = auto;
		
		this.grid = new CellMatrix(size);
		grid.randomize(density);
		setHeight(height);
		setWidth(width);
		
		//Resize with window
		widthProperty().addListener(e -> draw());
		heightProperty().addListener(e -> draw());
	}
	
	public void animate()
	{
		draw();
		update();
	}
	public void draw()
	{
		
		//Get Current height and width of canvas for determining size of cells
		height = getHeight();
		width = getWidth();
		
		//Clear canvas
		context.setFill(backgroundColor);
		context.fillRect(0,0, width, height);
	
		//Draw a white square for each cell that is alive in the matrix
		context.setFill(cellColor);
		for(int i = 0; i < grid.getSize(); i++)
		{
			for(int j = 0; j < grid.getSize(); j ++)
			{
				//The size of a cell is dependent on size of canvas and the number of elements in the cellMatrix
				if(grid.get(i, j).isAlive())
					context.fillRect(width/grid.getSize()*i , height/size*j   ,width/grid.getSize() , height/size  );
			}
		}
		context.setFill(Color.WHITE);
		drawCount++;
		
		if(drawCount == 1000 && auto == true)
		{
			reset();
			
		}
		
	}
	
	//Used each time CellMatrix is initialized
	public void setDensity(int density)
	{
		this.density = density;
	}
	
	//Re initialize the CellMatrix after changing size or density
	public void reset()
	{
		//stop();
		grid = new CellMatrix(size);
		grid.randomize(density);
		drawCount = 0;
		
	}
	//Updates the cell matrix that is used for drawing to the canvas
	//Gets next period
	private void update() 
	{
		grid.nextGeneration();
	}

	@Override
	public void run()
	{
		//continually update and draw canvas
		while( true)
		{
			//Must be used to push canvas drawing to javaFX thread
			//Canvas crashes after unspecified time if Platform.runLater is not used
			Platform.runLater( new Runnable()
			{
				@Override
				public void run() 
				{
					animate(); 
				}});
		//Thread is paused to make canvas changes more visible
		pause();
		}
	}
	
	//Handle pausing of thread
	private void pause()
	{
		try {
			Thread.sleep((long) (drawDelay * 1000));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Change pause time of thread
	public void setDrawDelay( double sleepTime)
	{
		drawDelay = sleepTime;
	}
	
	public double getDrawDelay()
	{
		return drawDelay;
	}
	
	//Change size used to initialize CellMatrix
	public void setSize(int size)
	{
		this.size = size;
	}
	public int getSize()
	{
		return this.size;
	}
	
	//change colors of canvas elements
	public void setCellColor (Color color)
	{
		cellColor = color;
	}
	public void setBackgroundColor(Color color)
	{
		backgroundColor = color;
	}
	
	public Color getBgColor()
	{
		return backgroundColor;
	}
}
