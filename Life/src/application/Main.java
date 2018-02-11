package application;
	
import com.rjservers.gameoflife.ConwayCanvas;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		try 
		{
			//User text input
			TextArea inputDensity = new TextArea();
			inputDensity.setPrefSize(40, 10);
			inputDensity.setFont(Font.font("Verdana",10));
			inputDensity.setCenterShape(true);
			inputDensity.setText("10");
			
			TextArea matrixSizeInput = new TextArea();
			matrixSizeInput.setPrefSize(40, 10);
			matrixSizeInput.setFont(Font.font("Verdana",10));
			matrixSizeInput.setCenterShape(true);
			matrixSizeInput.setText("200");
			
			//Input descriptions
			Label lblDensity = new Label("Population Density 1/");
			lblDensity.setCenterShape(true);
			lblDensity.setFont(Font.font("Verdana",15));
			
			Label lblSize = new Label("Matrix size: ");
			lblSize.setCenterShape(true);
			lblSize.setFont(Font.font("Verdana",15));
		
			
			final  ConwayCanvas canvas = new ConwayCanvas(300,300);
			canvas.toBack();
		
			final Thread thrd = new Thread(canvas);
			
			thrd.setDaemon(true);
			
			//color of cells in canvas
			ColorPicker cellColorPicker = new ColorPicker(Color.GREEN);
			cellColorPicker.setPrefSize(40, 40);
			cellColorPicker.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>()
					{
						public void handle(ActionEvent e)
						{
							canvas.setCellColor(cellColorPicker.getValue());
						}
					});
			
			//color of canvas background
			ColorPicker bgColorPicker = new ColorPicker(Color.BLACK);
			bgColorPicker.setPrefSize(40, 40);
			bgColorPicker.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>()
					{
						public void handle(ActionEvent e)
						{
							canvas.setBackgroundColor(bgColorPicker.getValue());
						}
					});
			
			
			
			Button btnReset = new Button("Reset");
			btnReset.setPrefHeight(40);
			btnReset.setFont(Font.font("Verdana",15));
			
			//reset the canvas and resume drawing using any user parameters
			btnReset.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>()
			{
				@Override	
				public void handle(ActionEvent e)
				{
					//Limit and check user input
					try
					{
						int density = Integer.parseInt(inputDensity.getText());
						if(density > 100 )
						{
							inputDensity.setText("100");
							density = 100;
						}
						
						else if(density < 1)
						{
							inputDensity.setText("1");
							density = 1;
						}
						
			
						canvas.setDensity(density);
						int size = Integer.parseInt(matrixSizeInput.getText());
					
						if(size > 1000)
						{
							matrixSizeInput.setText("1000");
							size = 1000;
						}
						else if(size < 10)
						{
							
							matrixSizeInput.setText("5");
							size = 5;
						}
						
						canvas.setSize(size);
					}
					catch(NumberFormatException ex)
					{
								
					}
				
					canvas.reset();
					canvas.draw();
				
				}
			});
			
			//Begins drawing on the canvas
			Button btnStart = new Button("Start");
			btnStart.setPrefHeight(40);
			btnStart.setFont(Font.font("Verdana",15));
			
			//start animation
			btnStart.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>()
			{
				@Override	
				public void handle(ActionEvent e)
				{
					//Thread is only started once
					if(!thrd.isAlive())
					{
						try 
						{
							int density = Integer.parseInt(inputDensity.getText());
								
							if(density > 100 )
							{
								inputDensity.setText("100");
								density = 100;
							}
							
							else if(density < 1)
							{
								inputDensity.setText("1");
								density = 1;
							}
							
						canvas.setDensity(density);
						int size = Integer.parseInt(matrixSizeInput.getText());
							
							if(size > 1000)
							{
								matrixSizeInput.setText("200");
								size = 1000;
							}
							
							else if(size < 10)
							{
								size = 5;
								matrixSizeInput.setText("5");
							}
						canvas.setSize(size);
						canvas.reset();
					}
							
					catch(NumberFormatException ex)
					{
							
					}
						
					thrd.start();
					
					}
							
				}
						
			});
			
			//Holds Nodes for user input
			HBox hBox = new HBox();
			hBox.getChildren().addAll(lblDensity,inputDensity, lblSize, matrixSizeInput, btnStart,btnReset,cellColorPicker,bgColorPicker);
			hBox.setSpacing(8);
			hBox.setPrefHeight(20);
		
			//Add nodes to pane and create scene
			BorderPane pane = new BorderPane();
			pane.setTop(hBox);
			pane.setCenter(canvas);
			pane.getTop().setOpacity(.9);
			
			//bind canvas size to the the pane
			canvas.widthProperty().bind(pane.widthProperty());
			canvas.heightProperty().bind(pane.heightProperty());
			canvas.draw();
			
			Scene scene = new Scene(pane, 700, 700);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			primaryStage.setScene(scene);
			primaryStage.centerOnScreen();
			primaryStage.getIcons().add(new Image("http://rjservers.com/img/life.png"));
			
			primaryStage.show();
		} 
		
		catch(Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) 
	{
		launch(args);
	}
}
