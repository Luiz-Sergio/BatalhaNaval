package application;

import java.io.File;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GridController {
	//@FXML
	ImageView imageView2;
	@FXML
	Pane mainPane;
	@FXML
	Pane enemyPane;
	@FXML
	Pane playerPane;
	private int size = 400;
	private int spots = 10;
	private int dimension = size/spots;
	
	private Rectangle[][] board;
	private ArrayList<Ship> ships;
	
	
	Image image = new Image(getClass().getResourceAsStream("ShipRescue.png"));

    @FXML
	public void initialize() {
    	
    	ships = new ArrayList<>();
    	board = new Rectangle[spots][spots];
    	//loop to create each board
		for(int i = 0; i < size; i+=dimension) {
			for(int j = 0; j < size; j+=dimension) {
				//player's board squares
				Rectangle rec = new Rectangle(i,j,40,40);
				rec.setFill(Color.DEEPSKYBLUE);
				rec.setStroke(Color.BLACK);
				
				//enemy's board squares
				Rectangle rec2 = new Rectangle(i,j,40,40);
				rec2.setFill(Color.DEEPSKYBLUE);
				rec2.setStroke(Color.BLACK);
				board[i / dimension][j/dimension] = rec2;
				
				//add the squares to each pane
				playerPane.getChildren().add(rec2);
				enemyPane.getChildren().add(rec);
				
				rec.setFill(Color.WHITE);
			}
		}
		
		//loop to create each ship
		for(int navios = 0; navios < 4; navios++) {
			//create the image of the ship, to be send as parameter to the ship constructor
			imageView2 = new ImageView();
			imageView2.setFitHeight(80);
	    	imageView2.setFitWidth(40);
	    	imageView2.setImage(image);
	    	
	    	//give random coordinates
	    	int x = dimension * (int)(Math.random() * spots);
	    	int y = dimension * (int)(Math.random() * spots);
	    
	    	//create ship and add to the array
	    	Ship ship = new Ship(x,y,imageView2);
	    	ships.add(ship);
	    	
	    	//creating mouse events functionality to the ships
	    	imageView2.setOnMousePressed(event -> pressed(event, ship));
	    	imageView2.setOnMouseDragged(event -> draged(event, ship));
	    	imageView2.setOnMouseReleased(event -> released(event, ship));
	    	
	    	//add the image view to the player pane
	    	playerPane.getChildren().add(imageView2);
	    	
	    	//draw the ship in the player board
	    	ship.draw();
		}
		/*
		imageView2 = new ImageView();
    	imageView2.setFitHeight(80);
    	imageView2.setFitWidth(40);
    	imageView2.setImage(image);
    	imageView2.setRotate(imageView2.getRotate() + 90); 
    	Ship ship = new Ship(40,40,imageView2);
    	playerPane.getChildren().add(imageView2);
    	ship.draw();
    	*/
    	
	}
    
    
    public void pressed(MouseEvent event, Ship ship) {
    	if(event.isSecondaryButtonDown())
    		ship.rotate();
    }
    public void draged(MouseEvent event, Ship ship) {
    	ship.setX(ship.getX() + event.getX());
    	ship.setY(ship.getY() + event.getY());
    	ship.draw();
    }
    public void released(MouseEvent event, Ship ship) {
    	int posX = ((int)ship.getX() / dimension) * dimension;
    	int posY = ((int)ship.getY() / dimension) * dimension;
    	ship.setX(posX);
    	ship.setY(posY);
    	board[posX/dimension][posY/dimension].setFill(Color.RED);
    	ship.draw();
    }
}
