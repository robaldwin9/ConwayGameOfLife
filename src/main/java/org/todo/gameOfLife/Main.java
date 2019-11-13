package org.todo.gameOfLife;
	

import org.todo.gameOfLife.ConwayCanvas;
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
import javafx.scene.input.KeyCode;
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
			inputDensity.setText("3");
			
			
			TextArea matrixSizeInput = new TextArea();
			matrixSizeInput.setPrefSize(40, 10);
			matrixSizeInput.setFont(Font.font("Verdana",10));
			matrixSizeInput.setCenterShape(true);
			matrixSizeInput.setText("500");
			
			
			//Input descriptions
			Label lblDensity = new Label("Population Density 1/");
			lblDensity.setCenterShape(true);
			lblDensity.setFont(Font.font("Verdana",15));
			
			
			Label lblSize = new Label("Matrix size: ");
			lblSize.setCenterShape(true);
			lblSize.setFont(Font.font("Verdana",15));
		
		
			
			final  ConwayCanvas canvas = new ConwayCanvas(300,300, true);
			canvas.toBack();
		
			final Thread thrd = new Thread(canvas);
			
			thrd.setDaemon(true);
			
			//color of cells in canvas
			ColorPicker cellColorPicker = new ColorPicker(Color.WHITE);
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
			
		
			
			//Holds Nodes for user input
			HBox hBox = new HBox();
			hBox.getChildren().addAll(lblDensity,inputDensity, lblSize, matrixSizeInput, btnReset,cellColorPicker,bgColorPicker);
			hBox.setSpacing(8);
			hBox.setPrefHeight(10);
			hBox.setVisible(false);
			hBox.setManaged(false);
			

		
		
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
			
			
			primaryStage.setScene(scene);
			primaryStage.centerOnScreen();
			primaryStage.getIcons().add(new Image("http://rjservers.com/img/life.png"));
			scene.setOnKeyPressed(e ->
			{
		
			
					// TODO Auto-generated method stub
					KeyCode key = e.getCode();
					//handle full screen
					if(key == KeyCode.F11)
					{
						if(primaryStage.isFullScreen())
							primaryStage.setFullScreen(false);
						else
							primaryStage.setFullScreen(true);
					}
					
					//handle input visibility
					else if(key == KeyCode.ENTER)
					{
						if(hBox.isManaged())
						{
							hBox.setManaged(false);
							hBox.setVisible(false);
						}
							
						else
						{
							hBox.setManaged(true);
							hBox.setVisible(true);
						}
					}
				});
			
			primaryStage.setFullScreen(true);
			primaryStage.show();
			//primaryStage.setFocused(true);
			thrd.start();
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
