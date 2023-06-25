package br.ufrn.imd.controle;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import br.ufrn.imd.modelo.Corveta;
import br.ufrn.imd.modelo.Destroyer;
import br.ufrn.imd.modelo.Fragata;
import br.ufrn.imd.modelo.Logs;
import br.ufrn.imd.modelo.Ship;
import br.ufrn.imd.modelo.Submarino;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
/**
 * Class to control all the flow of the game
 * 
 * @author Luiz Sergio
 *
 */
public class GameController{

	
	@FXML
	Pane mainPane;
	/**
	 * Pane to serve as enemyBoard
	 */
	@FXML
	Pane enemyPane;
	/**
	 * Pane to serve as playerBoard
	 */
	@FXML
	Pane playerPane;
	/**
	 *  AnchorPane to see all the Attacks results
	 */
	@FXML
	AnchorPane logsPane;	
	/**
	 * Button to start game
	 */
	@FXML
	Button playButton;
	/**
	 * Cheat button to show enemies
	 */
	@FXML
	Button showEnemys;
	/**
	 * Text to show victories counter in screen
	 */
	@FXML
	Text winsCounter;
	/**
	 * Text to show loses counter in screen
	 */
	@FXML
	Text defeatsCounter;	
	/**
	 * int counter of victories to convert to string and show in screen
	 */
	private int winsCounterInt=0;
	/**
	 * int counter of loses to convert to string and show in screen
	 */
	private int defeatsCounterInt=0;
	
	/**
	 * size of the board
	 */
	private int size = 400;
	/**
	 * number of cells[squares]
	 */
	private int spots = 10;
	/**
	 * size of the cell[square]
	 */
	private int dimension = size/spots;
	/**
	 * matrix to store the squares of the board of the player
	 */
	private Rectangle[][] playerBoard;
	/**
	 * matrix to store the squares of the board of the enemy
	 */
	private Rectangle[][] enemyBoard;
	
	/**
	 * matrix of integers to signal player unoccupied positions, position of the ships and position of the shots
	 */
	private int[][] occupiedPositions;
	/**
	 * matrix of integers to signal enemy unoccupied positions, position of the ships and position of the shots
	 */
	private int[][] occupiedPositionsEnemy;//occupied positions of the enemy
	/**
	 * Array to hold the player ships
	 */
	private ArrayList<Ship> playerShips;
	/**
	 * Array to store the enemy ships
	 */
	private ArrayList<Ship> enemyShips;
	/**
	 * boolean to determine if the enemy ship is visible
	 */
	private boolean enemyVisible = false;
	/**
	 * boolean to determine if the game ended
	 */
	private boolean gameOver = false;
	/**
	 * boolean to determine if is player turn
	 */
	private boolean playerTurn = false;
	/**
	 * boolean to determine if the game started
	 */
	private boolean gameStart = false;
	/**
	 * List of input attacks in game instance
	 */
	private Logs logs;
	/**
	 * dummy Ship to be used in certain functions
	 */
	private Ship dummyShip = new Corveta(2,null,false, false);

	private boolean mousePressed = false;
	/**
	 * initialize game scenario, such as creating and painting board and instantiating ships
	 */
    @FXML
	public void initialize() {
    	System.out.println("HYUUUUUUUUUUUUUUUUUUUUUUUUUUUUUU"+playButton.getText()); 
    	gameStart = false;
    	gameOver = false;
    	
    	playerShips = new ArrayList<>();
    	enemyShips = new ArrayList<>();
    	playerBoard = new Rectangle[spots][spots];
    	enemyBoard = new Rectangle[spots][spots];
    	occupiedPositions = new int[spots][spots];
    	occupiedPositionsEnemy = new int[spots][spots];
    	logs = new Logs();
    	
    	
    	//Setting all positions as not occupied
    	for(int i = 0; i < spots;i++) {
    		for(int j = 0; j < spots; j++) {
    			occupiedPositions[i][j] = 0;
    		}
    	}
    	//loop to create each board
		for(int i = 0; i < size; i+=dimension) {
			for(int j = 0; j < size; j+=dimension) {
				//enemy's board squares
				Rectangle rec = new Rectangle(i,j,40,40);
				rec.setFill(Color.GREEN);
				rec.setStroke(Color.BLACK);
				rec.setOnMouseClicked(event -> {
					if(playerTurn && !gameOver && gameStart) {
						playerTurn((int)(event.getY()/dimension),(int)(event.getX()/dimension));
					}
				});
				enemyBoard[i / dimension][j/dimension] = rec;
				
				//player's board squares
				Rectangle rec2 = new Rectangle(i,j,40,40);
				rec2.setFill(Color.DEEPSKYBLUE);
				rec2.setStroke(Color.BLACK);
				playerBoard[i / dimension][j/dimension] = rec2;
				
				//add the squares to each pane
				enemyPane.getChildren().add(rec);
				playerPane.getChildren().add(rec2);				
			}
		}
		

		instantiateShips(playerPane, occupiedPositions,false, playerShips);
		instantiateShips(enemyPane,occupiedPositionsEnemy,true,enemyShips);
		
		

	}
    
    /**
     * alternates the visibility of the ships based on the previous value of enemyVisible
     * @param ships the ships to set the visibility to enemyVisible
     */
    public void setShipsVisibility(ArrayList<Ship> ships) {
    	enemyVisible = !enemyVisible;
    	for(Ship ship : ships) {
    		ship.draw(enemyVisible);
    	}
    }
    
    /**
     * fuction called to change the visibility of the enemies
     */
    public void showEnemies() {
    	setShipsVisibility(enemyShips);
    }
    
    /**
     * save last valid position of the ship or rotate the ship
     * @param event the mouse event
     * @param ship the ship being pressed
     */
    public void pressed(MouseEvent event, Ship ship) {
    	//used to get the pane parent
    	Node node = (Node) event.getSource();
    	Parent parent = node.getParent();
    	
    	//used to determine which matrix of occupied position to use based on the parent's name
    	int[][] ocPositions = new int[10][10];    	
    	if( parent.getId().equals("enemyPane")) {
    		System.out.println(":::::::::PANE NAME:::::::: " + parent.getId());
    		ocPositions = occupiedPositionsEnemy;
    	}else if(parent.getId().equals("playerPane")) {
    		ocPositions = occupiedPositions;
    		System.out.println(":::::::::PANE NAME:::::::: " + parent.getId());
    	}
    	
    	//if the right button of the mouse is clicked verify is a rotation can be made
    	if(!mousePressed) {
    		if(event.isSecondaryButtonDown() ) {    			
        		System.out.println("SHIP GET Y "+ship.getY()/dimension);
        		if(ship.getVertical() && ((ship.getX()/dimension) + ship.getTamanho() <= 10)) {
        			if(ship.verifyOccupiedPositions((int)ship.getX()/dimension,(int)ship.getY()/dimension, ocPositions, true)){
        				occupyPositions((int)ship.getX()/dimension, (int)ship.getY()/dimension, ship.getVertical(), ship.getTamanho(), ocPositions,0);
            			ship.rotate();
            			occupyPositions((int)ship.getX()/dimension, (int)ship.getY()/dimension, ship.getVertical(), ship.getTamanho(), ocPositions,ship.getTamanho());
        			}
        			else {
            			logs.generateError(0);
            			this.addMessage(logs.getMessage());
            		}
        			
        			
        		}else if(!ship.getVertical() && ((ship.getY()/dimension) + ship.getTamanho() <= 10)) {
        			if(ship.verifyOccupiedPositions((int)ship.getX()/dimension,(int)ship.getY()/dimension, ocPositions, true)){
        			occupyPositions((int)ship.getX()/dimension, (int)ship.getY()/dimension, ship.getVertical(), ship.getTamanho(), ocPositions,0);
        			ship.rotate();
        			occupyPositions((int)ship.getX()/dimension, (int)ship.getY()/dimension, ship.getVertical(), ship.getTamanho(), ocPositions,ship.getTamanho());
        			}
        			else {
            			logs.generateError(0);
            			this.addMessage(logs.getMessage());
            		}
        		}
        		else {
        			logs.generateError(0);
        			this.addMessage(logs.getMessage());
        		}
        		
        	//if the left button is clicked then save it as the lastValidX and lastValidY
        	}else {
        		mousePressed = !mousePressed;
        		ship.setLastValidX(ship.getX());
        		ship.setLastValidY(ship.getY());
        	System.out.println("LAST VALID X AND Y"+ship.getLastValidX()+" "+ship.getLastValidY());
        	}
        	
    	}
    }
    
    /**
     * moves and draws the ship in the current position if its within borders otherwise print in the last valid position
     * @param event the mouse event
     * @param ship the ship being pressed
     */
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
    	    		logs.generateError(1);
        			this.addMessage(logs.getMessage());
    	    	
    		}
    	}
    	
    }
    
    /**
     * 
     * @param event the mouse event
     * @param ship the ship being pressed
     */
    public void released(MouseEvent event, Ship ship) {
    	int posX,posY;
    	
    	//used to get the pane parent
    	Node node = (Node) event.getSource();
    	Parent parent = node.getParent();
    	
    	//used to determine which matrix of occupied position to use based on the parent's name
    	int[][] ocPositions = new int[10][10];
    	if( parent.getId().equals("enemyPane")) {
    		System.out.println(":::::::::PANE NAME:::::::: " + parent.getId());
    		ocPositions = occupiedPositionsEnemy;
    	}else if(parent.getId().equals("playerPane")) {
    		ocPositions = occupiedPositions;
    		System.out.println(":::::::::PANE NAME:::::::: " + parent.getId());
    	}
    	
    	//if the position where it was released is invalid draw it in the last valid position, if valid fill the previous occupied position with zeros and fill the new occupied position with the ship id and draw it
    	if(event.getButton() == MouseButton.PRIMARY) {
    		mousePressed = !mousePressed;
    		posX = (int)ship.getX()/dimension;//45 becomes 1.something then becomes 1
        	posY = (int)ship.getY()/dimension;
        	System.out.println("RELEASED X: "+posX+" RELEASED Y"+posY);
        	if(ship.isInvalid(posX, posY, ocPositions)){
        		
        		ship.setX(ship.getLastValidX());//1 * 40(square size) its position is assigned as 40 when dropped at 45
            	ship.setY(ship.getLastValidY());
        	}else {
        		occupyPositions((int)ship.getLastValidX()/dimension, (int)ship.getLastValidY()/dimension, ship.getVertical(), ship.getTamanho(), ocPositions,0);
        		System.out.println();
        		occupyPositions(posX, posY, ship.getVertical(), ship.getTamanho(), ocPositions,ship.getTamanho());
        		ship.setX(posX*dimension);//1 * 40(square size) its position is assigned as 40 when dropped at 45
            	ship.setY(posY*dimension);
        	
        	}	
    	}
    	ship.draw();
    }
    
  /**
   * fill matrix of occupied position based on the height and vetical value
   * @param x the x coordinate of the ship
   * @param y the y coordinate of the ship
   * @param vertical boolean to determine if is vertical or horizontal
   * @param tamanho the height of the ship, used to determine how many cell it will fill
   * @param occupiedPositions matrix of integers to signal unoccupied positions, position of the ships and position of the shots
   * @param value the value that will be filled in each cell
   */
   public void occupyPositions(int x, int y, boolean vertical,int tamanho, int occupiedPositions[][], int value) {
	   if(vertical) {
		   for(int i = 0; i < tamanho; i++) {
			   occupiedPositions[y+i][x] = value;
		   }
	   }else {
		   for(int i = 0; i < tamanho; i++) {
			   occupiedPositions[y][x+i] = value;
		   }
	   }
	   for(int i = 0; i < 10; i++) {
		   for(int j = 0; j < 10; j++) {
			   System.out.print(occupiedPositions[i][j]+ " ");
		   }
		   System.out.println();
	   }
   }
    
   /**
    * give the x and y anchor coordinate to the ship
    * @param ship the ship that will receive the position
    * @param ocPositions matrix of integers to signal unoccupied positions, position of the ships and position of the shots
    */
    public void givePosition(Ship ship, int[][] ocPositions) {
    	int x,y;
    	//give random coordinates
    	
    	do {
    		x = dimension * (int)(Math.random() * spots);
	    	y = dimension * (int)(Math.random() * spots);
	    System.out.println(x/dimension+"<<<"+y/dimension);
    	}while(ship.isInvalid(x/dimension,y/dimension,ocPositions));
    	
    	occupyPositions(x/dimension,y/dimension,ship.getVertical(), ship.getTamanho(), ocPositions,ship.getTamanho());
    	
    	ship.setX(x);
    	ship.setY(y);
    }
    
    /**
     * Intantiate the ships, store it in an array and add it to the respective pane
     * 
     * @param pane the pane to whom the ship will pertain
     * @param ocPositions matrix of integers to signal unoccupied positions, position of the ships and position of the shots
     * @param isEnemy boolean to determine if the ship is a player's ship or a enemy's ship
     * @param ships Array to store the ships
     */
    public void instantiateShips(Pane pane, int[][] ocPositions,boolean isEnemy, ArrayList<Ship> ships) {
	    	ImageView imageView2;
	    	ImageView imageView3;
	    	ImageView imageView4;
	    	ImageView imageView5;
	    	
	    	
			imageView2 = new ImageView();
	    	Ship ship = new Corveta(2,imageView2,false, isEnemy);
	    	givePosition(ship, ocPositions);
	    	
	    	imageView3 = new ImageView();
	    	Ship ship1 = new Submarino(3,imageView3,true, isEnemy);
	    	givePosition(ship1, ocPositions);
	    	
	    	imageView4 = new ImageView();
	    	Ship ship2 = new Fragata(4,imageView4,false, isEnemy);
	    	givePosition(ship2, ocPositions);
	    	
	    	imageView5 = new ImageView();
	    	Ship ship3 = new Destroyer(5,imageView5,true, isEnemy);
	    	givePosition(ship3,ocPositions);

	    	
	    	ships.add(ship);
	    	ships.add(ship1);
	    	ships.add(ship2);
	    	ships.add(ship3);
	    	
	    	//creating mouse events functionality to the ships
	    	imageView2.setOnMousePressed(event -> pressed(event, ship));
	    	imageView2.setOnMouseDragged(event -> draged(event, ship));
	    	imageView2.setOnMouseReleased(event -> released(event, ship));
	    	imageView3.setOnMousePressed(event -> pressed(event, ship1));
	    	imageView3.setOnMouseDragged(event -> draged(event, ship1));
	    	imageView3.setOnMouseReleased(event -> released(event, ship1));
	    	imageView4.setOnMousePressed(event -> pressed(event, ship2));
	    	imageView4.setOnMouseDragged(event -> draged(event, ship2));
	    	imageView4.setOnMouseReleased(event -> released(event, ship2));
	    	imageView5.setOnMousePressed(event -> pressed(event, ship3));
	    	imageView5.setOnMouseDragged(event -> draged(event, ship3));
	    	imageView5.setOnMouseReleased(event -> released(event, ship3));
	    	//add the image view to the player pane
	    	pane.getChildren().add(imageView2);
	    	pane.getChildren().add(imageView3);
	    	pane.getChildren().add(imageView4);
	    	pane.getChildren().add(imageView5);
	    	//draw the ship in the player board
	    	System.out.println("IDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD"+pane.getId());
	    	ship.draw();
	    	ship1.draw();
	    	ship2.draw();
	    	ship3.draw();
	    	
		//}
    }
    
    
    /**
     * Possible actions  in the player turn
     * @param y y coordinate of the mouse in the enemy board
     * @param x x coordinate of the mouse in the enemy board
     */
	public void playerTurn(int y, int x) {		
		//verify is the position wasn't shot[!=10]
		if(occupiedPositionsEnemy[y][x]!=10) {
			//verify if is a ship[!=0]
			if(occupiedPositionsEnemy[y][x]!=0 ) {
				
				System.out.println("Acertou navio "+occupiedPositionsEnemy[y][x]);
				
				for(Ship ship: enemyShips) {
					System.out.println("LIFE BEFORE: "+ship.getLife());
				}
				
				enemyShips.get(occupiedPositionsEnemy[y][x]-2).attacked();				
				enemyBoard[x][y].setFill(Color.RED);
				logs.generateMessage(true,true, enemyShips.get(occupiedPositionsEnemy[y][x]-2));
				
				for(Ship ship: enemyShips) {
					System.out.println("LIFE AFTER: "+ship.getLife());
				}
			//if the two verification above fails it mean the ocean was hit	
			}else {
				enemyBoard[x][y].setFill(Color.YELLOW);
				logs.generateMessage(false,true, new Corveta(2,null,false, false));
				System.out.println("Acertou o oceano ");
			}
			
			    	    	
	        this.addMessage(logs.getMessage());
	        
			//if the position wasn't shot, mark it as shot and alternate turn
			occupiedPositionsEnemy[y][x]=10;
			playerTurn = !playerTurn;
		}
		else {
			logs.generateError(2);
			this.addMessage(logs.getMessage());
		}
		
		//after a valid shot verify if the game is over
		isGameOver(enemyShips);
		
		//if the game is over increment the counter of wins and print it in the screen
		if(gameOver) {
			logs.generateEndMessage(true);
			this.addMessage(logs.getMessage());
			System.out.println("YOU WON!!!");
			winsCounterInt++;
			winsCounter.setText(Integer.toString(winsCounterInt));
		}
	}
	
	/**
	 * Possible actions in the computer turn
	 */
	public void computerTurn() {		
		int x ;
		int y ;
		
		//will try random numbers till it find one that hasnt been shot
		do {
			x = (int)(Math.random() * spots);
			y = (int)(Math.random() * spots);
			System.out.println("COMPUTADOR ESCOLHEU "+x+" "+y);
		}while(occupiedPositions[y][x]==10);
		
		playerTurn = !playerTurn;
		
		//just for debuging to delete latter
		for(Ship ship: playerShips) {
			System.out.println("LIFE BEFORE: "+ship.getLife());
		}
		
		if(occupiedPositions[y][x]!=0) {
			playerBoard[x][y].setFill(Color.RED);
			playerShips.get(occupiedPositions[y][x]-2).attacked();	
			logs.generateMessage(true,false, playerShips.get(occupiedPositions[y][x]-2));
		}
		else {
			playerBoard[x][y].setFill(Color.YELLOW);
			logs.generateMessage(false,false, dummyShip);
		}
		
		//just for debugging to delete latter
		for(Ship ship: playerShips) {
			System.out.println("LIFE AFTER: "+ship.getLife());
		}
		    	    	
        this.addMessage(logs.getMessage());
		//set the unshot position as 10 to sign that it was shot there
		occupiedPositions[y][x]=10;
		
		
		
		//just for debuging to delete latter
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j<10;j++) {
				System.out.print(occupiedPositions[i][j]+" ");
			}
			System.out.println();
		}			
		
		//after the shot verify it the game is over
		isGameOver(playerShips);
		
		//if the game is over increment loss counter and print it on the screen
		if(gameOver) {
			logs.generateEndMessage(false);
			this.addMessage(logs.getMessage());
			System.out.println("COMPUTER WON!!!");
			defeatsCounterInt++;
			defeatsCounter.setText(Integer.toString(defeatsCounterInt));
		}
		//
	}
	
    
    /**
     * if all the ships is with zero life it set gameOver as true otherwise set as false
     * @param ships array of ship to iterate over and verify if every ship is with zero life
     */
    private void isGameOver(ArrayList<Ship> ships) {
    	gameOver = true;
    	for(Ship ship:ships) {
			if(ship.getLife()>0) {
				gameOver = false;
			}else {
				ship.draw(true);
			}
		}
    	
    }
    
    /**
     * 
     */
    @FXML
    public void startGame() {
    	if(!gameStart)logsPane.getChildren().clear();    
    	//when the game start block the ships movement
    	for(Ship ship: playerShips) {
    		ship.blockShip();
    	}
    	//the player start the game
    	playerTurn = true;
    	//determine that the game started
    	gameStart = true;
    	
    	new Thread(() -> {
    	while(!gameOver) {
    		Platform.runLater(() -> {
    		

    		if(playerTurn) {
    		}else {    			
    			computerTurn();
    		}
    		});

    		 try {
                 Thread.sleep(100);
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }
    	}
    		System.out.println("JOGO ACABOU");    	
    	 }).start();
    }    
    public void addMessage(Text message) {
    	for(Node n : logsPane.getChildren()) {
        	n.setLayoutY(n.getLayoutY()+18);
        }
        logsPane.getChildren().add(message);       
        logsPane.getChildren().get(logsPane.getChildren().size()-1).setLayoutX(14);
    	logsPane.getChildren().get(logsPane.getChildren().size()-1).setLayoutY(19);
    }
}
