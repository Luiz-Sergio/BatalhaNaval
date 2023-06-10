package application;

import java.io.File;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class GridController {
	//@FXML
	ImageView imageView2;
	ImageView imageView3;
	@FXML
	Pane mainPane;
	@FXML
	Pane enemyPane;
	@FXML
	Pane playerPane;
	@FXML
	AnchorPane logsPane;
	private int size = 400;
	private int spots = 10;
	private int dimension = size/spots;
	
	private Rectangle[][] playerBoard;
	private boolean[][] playerOccupiedPositions, enemyOccupiedPositions;
	private ArrayList<Ship> playerShips,enemyShips;
	private Logs logs;
	
	
	Image image = new Image(getClass().getResourceAsStream("ShipRescue.png"));
	Image image2 = new Image(getClass().getResourceAsStream("ShipRescue2.png"));

    @FXML
	public void initialize() {
    	
    	playerShips = new ArrayList<>();
    	playerBoard = new Rectangle[spots][spots];
    	playerOccupiedPositions = new boolean[spots][spots];
    	
    	enemyShips = new ArrayList<>();
    	enemyOccupiedPositions = new boolean[spots][spots];
    	
    	logs = new Logs();
    	
    	//Setting all positions as not occupied
    	for(int i = 0; i < spots;i++) {
    		for(int j = 0; j < spots; j++) {
    			playerOccupiedPositions[i][j] = false;
    		}
    	}
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
				playerBoard[i / dimension][j/dimension] = rec2;
				
				//add the squares to each pane
				playerPane.getChildren().add(rec2);
				enemyPane.getChildren().add(rec);
				
				rec.setFill(Color.WHITE);
			}
		}
		
		//loop to create each ship
		instantiateShips();
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
    	if(event.isSecondaryButtonDown()) {
    		System.out.println("SHIP GET Y "+ship.getY()/dimension);
    		if(ship.getVertical() && ((ship.getX()/dimension) + ship.getSize() <= 10)) {
    			ship.rotate();
    		}else if(!ship.getVertical() && ((ship.getY()/dimension) + ship.getSize() <= 10)) {
    			ship.rotate();
    		}
    		

    	}else {
    		ship.setLastValidX(ship.getX());
    		ship.setLastValidY(ship.getY());
    		System.out.println("LAST VALID X AND Y"+ship.getLastValidX()+" "+ship.getLastValidY());
    	}
    		
    }
    public void draged(MouseEvent event, Ship ship) {
    	if(event.isPrimaryButtonDown()) {
    		if(ship.getX() + event.getX()<=400 &&ship.getX() + event.getX()>0 && ship.getY()+ event.getY()<=400 &&ship.getY()+event.getY()>0){
    			ship.setX(ship.getX() + event.getX());
            	ship.setY(ship.getY() + event.getY());
            	ship.draw();
            	System.out.println("Draged X AND Y"+ship.getX()+" "+ship.getY());	
    		}else {
    			
    	    		ship.setX(ship.getLastValidX());
    	    		ship.setY(ship.getLastValidY());
    	    	
    		}
    	}
    	
    }
    public void released(MouseEvent event, Ship ship) {
    	int posX,posY;
    	if(event.getButton() == MouseButton.PRIMARY) {
    		posX = (int)ship.getX()/dimension;//45 becomes 1.something then becomes 1
        	posY = (int)ship.getY()/dimension;
        	System.out.println("RELEASED X: "+posX+" RELEASED Y"+posY);
        	if(ship.isValid(posX, posY, ship, playerOccupiedPositions)){
        		ship.setX(ship.getLastValidX());//1 * 40(square size) its position is assigned as 40 when dropped at 45
            	ship.setY(ship.getLastValidY());
        	}else {
        		ship.setX(posX*dimension);//1 * 40(square size) its position is assigned as 40 when dropped at 45
            	ship.setY(posY*dimension);
        	
        	}
        		
        	
        	
        		
        	//board[posX/dimension][posY/dimension].setFill(Color.RED);
        	
    	}
    	ship.draw();
    }
    
    
   
    
    public void givePosition(Ship ship) {
    	int x,y;
    	//give random coordinates
    	
    	do {
    		x = dimension * (int)(Math.random() * spots);
	    	y = dimension * (int)(Math.random() * spots);
	    System.out.println(x/dimension+"<<<"+y/dimension);
    	}while(ship.isValid(x/dimension,y/dimension,ship,playerOccupiedPositions));
    	
    	ship.setX(x);
    	ship.setY(y);
    }
    
    public void instantiateShips() {
    
			imageView2 = new ImageView();
		
	    	
	    	Ship ship = new Ship(5,imageView2,image,image2,false);
	    	//create ship and add to the array
	    	
	    	givePosition(ship);
	    	
	    	imageView3 = new ImageView();
	    	Ship ship1 = new Ship(3,imageView3,image,image2,true);
	    	//create ship and add to the array
	    	
	    	givePosition(ship1);
	    	
	    	
	    	playerShips.add(ship);
	    	playerShips.add(ship1);
	    	attack(0,0,true);
	    	attack(0,0,false);
	    	
	    	//creating mouse events functionality to the ships
	    	imageView2.setOnMousePressed(event -> pressed(event, ship));
	    	imageView2.setOnMouseDragged(event -> draged(event, ship));
	    	imageView2.setOnMouseReleased(event -> released(event, ship));
	    	imageView3.setOnMousePressed(event -> pressed(event, ship1));
	    	imageView3.setOnMouseDragged(event -> draged(event, ship1));
	    	imageView3.setOnMouseReleased(event -> released(event, ship1));
	    	
	    	//add the image view to the player pane
	    	playerPane.getChildren().add(imageView2);
	    	playerPane.getChildren().add(imageView3);
	    	//draw the ship in the player board
	    	ship.draw();
	    	ship1.draw();
		//}
    }
    public void attack(int x, int y, boolean player) {
    	Ship ship = new Ship(0,null,null,null,false);
    	for(Ship s : enemyShips) if(s.isHit(x,y)) ship = s;    	   
    	logs.generateMessage(enemyOccupiedPositions[x][y],player, ship);
    	Text message = logs.getMessage();    	
        for(Node n : logsPane.getChildren()) {
        	n.setLayoutY(n.getLayoutY()+18);
        }
        logsPane.getChildren().add(message);
        logsPane.getChildren().get(logsPane.getChildren().size()-1).setLayoutX(14);
    	logsPane.getChildren().get(logsPane.getChildren().size()-1).setLayoutY(19);
    }
}
