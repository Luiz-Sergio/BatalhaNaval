package br.ufrn.imd.modelo;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
/**
 * Ship is the main class, which will contain the main object methods we will be using in the game
 * 
 * It will validate positions, contain graphics, rotate self, draw self and block self movement.
 * 
 * @author Luiz Sergio
 *
 */
public class Ship implements Piece{
	/**
	 * the x coordinate of the ship
	 */
	private double x;
	/**
	 * the y coordinate of the ship
	 */
	private double y;
	/**
	 * the lastValidX coordinate of the ship, it refers to the last known valid x coordinate of the ship, used to get back to in case it is dropped in invalid position
	 */
	private double lastValidX;
	/**
	 * the lastValidY coordinate of the ship, it refers to the last known valid y coordinate of the ship, used to get back to in case it is dropped in invalid position
	 */
	private double lastValidY;
	/**
	 * The number of cells occupied by the ship
	 */
	private int height;
	/**
	 * The life of the ship start as the number of the cells occupied by the ship and decremented by one every time one cell of his is hit on the grid
	 */
	private int life;
	/**
	 * Determines if the ship is set vertically or horizontally
	 */
	private boolean vertical = true;
	/**
	 * Picture frame of the ship
	 */
	private ImageView imageView;
	/**
	 * The image used when he is vertically
	 */
	private Image verticalImage;
	/**
	 * the image used when he is horizontal
	 */
	private Image horizontalImage;
	/**
	 * boolean to determine if the ship is enemy or player
	 */
	private boolean isEnemy;
	/**
	 * the name of the ship type
	 */
	private String name;
	
	
	public Ship() {
		this.name = "Navio";
	}
	
	public Ship(int height, ImageView imageView, Image verticalImage, Image horizontalImage, boolean vertical,boolean isEnemy) {
		
		this.height = height;
		this.life = height;
		this.verticalImage = verticalImage;
		this.horizontalImage = horizontalImage;
		this.vertical = vertical;
		this.imageView = imageView;
		this.isEnemy = isEnemy;
		this.name = "Navio";
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public void setY(double y) {
		this.y = y;
	}

	public void setLastValidX(double x) {
		this.lastValidX = x;
	}
	
	public void setLastValidY(double y) {
		this.lastValidY = y;
	}
	
	public double getLastValidX() {
		return this.lastValidX;
	}
	
	public double getLastValidY() {
		return this.lastValidY;
	}
	
	public double getX() {
		return this.x;
	}
	
	public double getY() {
		return this.y;
	}
	
	public int getTamanho() {
		return height;
	}
	
	public boolean getVertical() {
		return vertical;
	}
	/**
	 * called in gameController to verify if all ships from each player is alive to determine end game
	 * @return the life of the ship
	 */
	public int getLife() {
		return this.life;
	}
	/**
	 * called in game controller when its detected that one of his positions was hit
	 */
	public void attacked() {
		if(this.life>0)
			this.life--;
	}
	/**
	 * Draws the ship in the grid, drawing it differently depending if its vertical or horizontal and if is enemy or player ship
	 * 
	 */
	public void draw() {
		imageView.setTranslateX(x);
		imageView.setTranslateY(y);
		if(vertical) {
			imageView.setImage(verticalImage);
			imageView.setFitHeight(height * 40);
	    	imageView.setFitWidth( 40);
			
		}else {
			imageView.setImage(horizontalImage);
			imageView.setFitHeight(40);
	    	imageView.setFitWidth(height * 40);
			
		}
		if(isEnemy) {
			imageView.setVisible(false);
			imageView.setOnMouseDragged(null);
			imageView.setOnMousePressed(null);
			imageView.setOnMouseReleased(null);
			//imageView.setMouseTransparent(true);
		}
		System.out.println("eu estava aqui!");
	}
	/**
	 * overloaded the draw function to be able to toggle the visibility of the ships
	 * @param visible determines if the ship is visible or not, true if visible false otherwise
	 */
	public void draw(boolean visible) {
	
		imageView.setVisible(visible);

		System.out.println("eu estava aqui!");
	}
	/**
	 * used to block the movements of the ship
	 * used in the enemyShips because they should be able to be moved and in the players ship when the game started
	 */
	public void blockShip() {
		imageView.setOnMouseDragged(null);
		imageView.setOnMousePressed(null);
		imageView.setOnMouseReleased(null);
	}
	/**
	 * used to rotate the ships when right mouse is clicked
	 * it changes the image, the width and the height, but keeps the anchor x and y coordinates
	 */
	public void rotate() {
		//imageView.setRotate(imageView.getRotate() + 90);
		if(vertical) {
			Image image = new Image(getClass().getResourceAsStream("ShipRescue2.png"));
			imageView.setFitHeight(40);
	    	imageView.setFitWidth(80);
			imageView.setImage(image);
			draw();
	    	vertical = false;
		}else {
			Image image = new Image(getClass().getResourceAsStream("ShipRescue.png"));
			imageView.setFitHeight(80);
	    	imageView.setFitWidth(40);
			imageView.setImage(image);
			draw();
	    	vertical = true;
		}
		
	}
	/**
	 * 
	 * @param x anchor x coordinate of the ship used as base to verify if the other coordinates are occupied or not
	 * @param y anchor y coordinate of the ship used as base to verify if the other coordinates are occupied or not
	 * @param ocupiedPositions matrix of int numbers used to signal what ship is in which positions and if there is even a ship
	 * @param checkRotate boolean to verify if he wants to check for a possible rotation or verify the current direction
	 * @return true in case its not occupied and false if is occupied
	 */
	public boolean verifyOccupiedPositions(int x, int y, int ocupiedPositions[][],boolean checkRotate) {
		boolean v = vertical;
		//if a possible rotation is being check change locally the direction of the ship
		if(checkRotate) {
			v = !v;
		}
		//the first for checks its cells vertically, the second horizontally
		if(v) {
			for(int i = 0; i< height;i++) {
				System.out.println("Sou horizontal mas estou olhando verticaallllllllllllllllllllllllll");
				System.out.println("YYYY " + y + "XXXXX " +x);
				System.out.println("position: "+(y+i)+" : "+ocupiedPositions[y+i][x]);
				if(ocupiedPositions[y+i][x]!=height && ocupiedPositions[y+i][x]!=0) {
					return false;
				}
			}
		}else {
			for(int i = 0; i< height;i++) {
				System.out.println("Sou vertical mas estou olhando horizontallllllllllllllllllllllllll");
				System.out.println("YYYY " + y + "XXXXX " +x);
				System.out.println("POSITION: "+(x+i)+" : "+ocupiedPositions[y][x+i]);
				if(ocupiedPositions[y][x+i]!=height && ocupiedPositions[y][x+i]!=0) {
					return false;
				}
			}
		}
		return true;//no position occupied found return true
	}
	/**
	 * 
	 * @param x anchor x coordinate of the ship used as base to verify if the other coordinates are valid or not
	 * @param y anchor y coordinate of the ship used as base to verify if the other coordinates are valid or not
	 * @param ocupiedPositions matrix of int numbers used to signal what ship is in which positions and if there is even a ship
	 * @return true if position is invalid and false if is valid
	 */
	 public boolean isInvalid(int x, int y,int ocupiedPositions[][]) {

	    		if((vertical &&  height + y > 10 ) ) {	
		    		return true;
		    	}else if((!vertical &&  height + x > 10) ) {
		    		return true;
		    	}else {
		    		//ocupiedPositions[x][y] = true;
		    		if(verifyOccupiedPositions(x,y,ocupiedPositions,false)) {
		    			return false;//its a valid position
		    		}else {
		    			return true;//found an occupied position return its invalid
		    		}		
		    	}	   	
	    }
	 
	 public void setName(String name) {
			this.name = name;
		}
		public String getName() {
			return name;
		}
	 
}
