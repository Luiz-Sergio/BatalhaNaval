package application;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class GridController{

	
	@FXML
	Pane mainPane;
	@FXML
	Pane enemyPane;
	@FXML
	Pane playerPane;
	@FXML
	Button playButton;
	@FXML
	Button showEnemys;
	@FXML
	Text vitoriasCounter;
	@FXML
	Text derrotasCounter;
	
	private int vitoriasCounterInt=0;
	private int derrotasCounterInt=0;
	
	
	private int size = 400;
	private int spots = 10;
	private int dimension = size/spots;
	
	private Rectangle[][] playerBoard;
	private Rectangle[][] enemyBoard;
	
	private boolean[][] shotsEnemy;
	private boolean[][] shotsPlayer;
	
	private int[][] ocupiedPositions;//occupied positions of the player
	private int[][] ocupiedPositionsEnemy;//occupied positions of the enemy
	
	private ArrayList<Ship> playerShips;
	private ArrayList<Ship> enemyShips;
	
	private boolean enemyVisible = false;
	
	private boolean gameOver = false;
	
	private boolean playerTurn = false;
	
	private boolean gameStart = false;

	

    @FXML
	public void initialize() {
    	System.out.println("HYUUUUUUUUUUUUUUUUUUUUUUUUUUUUUU"+playButton.getText()); 
    	gameStart = false;
    	gameOver = false;
    	
    	playerShips = new ArrayList<>();
    	enemyShips = new ArrayList<>();
    	playerBoard = new Rectangle[spots][spots];
    	enemyBoard = new Rectangle[spots][spots];
    	ocupiedPositions = new int[spots][spots];
    	ocupiedPositionsEnemy = new int[spots][spots];
    	shotsEnemy = new boolean[spots][spots];
    	shotsPlayer = new boolean[spots][spots];
    	
    	//Setting all positions as not occupied
    	for(int i = 0; i < spots;i++) {
    		for(int j = 0; j < spots; j++) {
    			ocupiedPositions[i][j] = 0;
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
						rec.setFill(Color.YELLOW);
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
				
				//rec.setFill(Color.RED);
			}
		}
		

		instantiateShips(playerPane, ocupiedPositions,false, playerShips);
		instantiateShips(enemyPane,ocupiedPositionsEnemy,true,enemyShips);
		
		

	}
    
    public void setShipsVisibility(ArrayList<Ship> ships) {
    	enemyVisible = !enemyVisible;
    	for(Ship ship : ships) {
    		ship.draw(enemyVisible);
    	}
    }
    
    public void showEnemies() {
    	setShipsVisibility(enemyShips);
    }
    
    public void pressed(MouseEvent event, Ship ship) {
    	
    	Node node = (Node) event.getSource();
    	Parent parent = node.getParent();
    	int[][] ocPositions = new int[10][10];
    	if( parent.getId().equals("enemyPane")) {
    		System.out.println(":::::::::PANE NAME:::::::: " + parent.getId());
    		ocPositions = ocupiedPositionsEnemy;
    	}else if(parent.getId().equals("playerPane")) {
    		ocPositions = ocupiedPositions;
    		System.out.println(":::::::::PANE NAME:::::::: " + parent.getId());
    	}
    	
    	if(event.isSecondaryButtonDown()) {
    		System.out.println("SHIP GET Y "+ship.getY()/dimension);
    		if(ship.getVertical() && ((ship.getX()/dimension) + ship.getTamanho() <= 10)) {
    			if(ship.verifyOccupiedPositions((int)ship.getX()/dimension,(int)ship.getY()/dimension, ocPositions, true)){
    				occupyPositions((int)ship.getX()/dimension, (int)ship.getY()/dimension, ship.getVertical(), ship.getTamanho(), ocPositions,0);
        			ship.rotate();
        			occupyPositions((int)ship.getX()/dimension, (int)ship.getY()/dimension, ship.getVertical(), ship.getTamanho(), ocPositions,ship.getTamanho());
    			}
    			
    		}else if(!ship.getVertical() && ((ship.getY()/dimension) + ship.getTamanho() <= 10)) {
    			if(ship.verifyOccupiedPositions((int)ship.getX()/dimension,(int)ship.getY()/dimension, ocPositions, true)){
    			occupyPositions((int)ship.getX()/dimension, (int)ship.getY()/dimension, ship.getVertical(), ship.getTamanho(), ocPositions,0);
    			ship.rotate();
    			occupyPositions((int)ship.getX()/dimension, (int)ship.getY()/dimension, ship.getVertical(), ship.getTamanho(), ocPositions,ship.getTamanho());
    			}
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
    	
    	Node node = (Node) event.getSource();
    	Parent parent = node.getParent();
    	int[][] ocPositions = new int[10][10];
    	if( parent.getId().equals("enemyPane")) {
    		System.out.println(":::::::::PANE NAME:::::::: " + parent.getId());
    		ocPositions = ocupiedPositionsEnemy;
    	}else if(parent.getId().equals("playerPane")) {
    		ocPositions = ocupiedPositions;
    		System.out.println(":::::::::PANE NAME:::::::: " + parent.getId());
    	}
    	
    	if(event.getButton() == MouseButton.PRIMARY) {
    		posX = (int)ship.getX()/dimension;//45 becomes 1.something then becomes 1
        	posY = (int)ship.getY()/dimension;
        	System.out.println("RELEASED X: "+posX+" RELEASED Y"+posY);
        	if(ship.isInvalid(posX, posY, ship, ocPositions)){
        		
        		ship.setX(ship.getLastValidX());//1 * 40(square size) its position is assigned as 40 when dropped at 45
            	ship.setY(ship.getLastValidY());
        	}else {
        		occupyPositions((int)ship.getLastValidX()/dimension, (int)ship.getLastValidY()/dimension, ship.getVertical(), ship.getTamanho(), ocPositions,0);
        		System.out.println();
        		occupyPositions(posX, posY, ship.getVertical(), ship.getTamanho(), ocPositions,ship.getTamanho());
        		ship.setX(posX*dimension);//1 * 40(square size) its position is assigned as 40 when dropped at 45
            	ship.setY(posY*dimension);
        	
        	}	
        	//board[posX/dimension][posY/dimension].setFill(Color.RED);
    	}
    	ship.draw();
    }
    
  
   public void occupyPositions(int x, int y, boolean vertical,int tamanho, int ocupiedPositions[][], int value) {
	   if(vertical) {
		   for(int i = 0; i < tamanho; i++) {
			   ocupiedPositions[y+i][x] = value;
		   }
	   }else {
		   for(int i = 0; i < tamanho; i++) {
			   ocupiedPositions[y][x+i] = value;
		   }
	   }
	   for(int i = 0; i < 10; i++) {
		   for(int j = 0; j < 10; j++) {
			   System.out.print(ocupiedPositions[i][j]+ " ");
		   }
		   System.out.println();
	   }
   }
    
    public void givePosition(Ship ship, int[][] ocPositions) {
    	int x,y;
    	//give random coordinates
    	
    	do {
    		x = dimension * (int)(Math.random() * spots);
	    	y = dimension * (int)(Math.random() * spots);
	    System.out.println(x/dimension+"<<<"+y/dimension);
    	}while(ship.isInvalid(x/dimension,y/dimension,ship,ocPositions));
    	
    	occupyPositions(x/dimension,y/dimension,ship.getVertical(), ship.getTamanho(), ocPositions,ship.getTamanho());
    	
    	ship.setX(x);
    	ship.setY(y);
    }
    
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
    
    
    
	public void playerTurn(int y, int x) {
		//System.out.println(">>>>>>>>>>>>>>>>>>>>X "+(int)(event.getX()/dimension)+" >>>>>>>>>>>>>>>>>>>>>>>>Y "+(int)(event.getY()/dimension));
		if(ocupiedPositionsEnemy[y][x]!=10) {
			if(ocupiedPositionsEnemy[y][x]!=0 ) {
				System.out.println("Acertou navio "+ocupiedPositionsEnemy[y][x]);
				for(Ship ship: enemyShips) {
					System.out.println("LIFE BEFORE: "+ship.getLife());
				}
				enemyShips.get(ocupiedPositionsEnemy[y][x]-2).atacked();
				for(Ship ship: enemyShips) {
					System.out.println("LIFE AFTER: "+ship.getLife());
				}
				//diminua vida do navio inimigo acertado
				//atualize board de posiçoes ocupadas do navio inimigo
				//
			}else {
				System.out.println("Acertou o oceano ");
			}
			ocupiedPositionsEnemy[y][x]=10;
			playerTurn = !playerTurn;
		}
		
		isGameOver(enemyShips);
		if(gameOver) {
			System.out.println("YOU WON!!!");
			vitoriasCounterInt++;
			vitoriasCounter.setText(Integer.toString(vitoriasCounterInt));
		}
	}
	
	public void computerTurn() {
		//System.out.println(">>>>>>>>>>>>>>>>>>>>X "+(int)(event.getX()/dimension)+" >>>>>>>>>>>>>>>>>>>>>>>>Y "+(int)(event.getY()/dimension));
		int x ;
		int y ;
		//will try random numbers till it find one that hasnt been shot
		do {
			x = (int)(Math.random() * spots);
			y = (int)(Math.random() * spots);
			System.out.println("COMPUTADOR ESCOLHEU "+x+" "+y);
		}while(ocupiedPositions[y][x]==10);
		
		playerTurn = !playerTurn;
		
		for(Ship ship: playerShips) {
			System.out.println("LIFE BEFORE: "+ship.getLife());
		}
		if(ocupiedPositions[y][x]!=0) {
			playerShips.get(ocupiedPositions[y][x]-2).atacked();
		}
		for(Ship ship: playerShips) {
			System.out.println("LIFE AFTER: "+ship.getLife());
		}
		//set the unshot position as 10 to sign that it was shot there
		ocupiedPositions[y][x]=10;
		
		playerBoard[x][y].setFill(Color.RED);
		
		//just for debuging
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j<10;j++) {
				System.out.print(ocupiedPositions[i][j]+" ");
			}
			System.out.println();
		}
				/*
		if(ocupiedPositions[y][x]!=0) {
			System.out.println("Acertou navio "+ocupiedPositionsEnemy[y][x]);
			//diminua vida do navio inimigo acertado
			//atualize board de posiçoes ocupadas do navio inimigo
			//
		}
		*/
		
		isGameOver(playerShips);
		if(gameOver) {
			System.out.println("COMPUTER WON!!!");
			derrotasCounterInt++;
			derrotasCounter.setText(Integer.toString(derrotasCounterInt));
		}
		//
	}
	
    
    
    private void isGameOver(ArrayList<Ship> ships) {
    	gameOver = true;
    	for(Ship ship:ships) {
			if(ship.getLife()>0) {
				gameOver = false;
			}
		}
    	
    }
    
    @FXML
    public void startGame() {
    	//System.out.println("Starting GAMEE!!! Les goooooooooooooooooo");
    	//when the game start block the ships movement
    	for(Ship ship: playerShips) {
    		ship.blockShip();
    	}
    	playerTurn = true;
    	//gameOver = false;
    	gameStart = true;
    	new Thread(() -> {
    	while(!gameOver) {
    		Platform.runLater(() -> {
    		

    		if(playerTurn) {
    			//System.out.println("Player's turn");
    		}else {
    			//System.out.println("Computer's turn");
    			
    			computerTurn();
    			//playerTurn = !playerTurn;
    		}
    		});

    		 try {
                 Thread.sleep(100);
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }
    	}
    		System.out.println("JOGO ACABOU");    	
    	//gameOver = false;
    	 }).start();
    	//initialize();
    }
    
    public void endGame() {
    	
    	/*
    	for(Ship ship: playerShips) {
    		ship.unblockShip();
    	}
    	*/
    }
    
}
