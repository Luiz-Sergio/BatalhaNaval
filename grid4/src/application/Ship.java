package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Ship {
	private double x;
	private double y;
	private double lastValidX;
	private double lastValidY;
	private int tamanho;
	private int life;
	private boolean vertical = true;
	private ImageView imageView;
	private Image verticalImage;
	private Image horizontalImage;
	private boolean isEnemy;
	
	
	public Ship() {
		
	}
	
	public Ship(int tamanho, ImageView imageView, Image verticalImage, Image horizontalImage, boolean vertical,boolean isEnemy) {
		
		this.tamanho = tamanho;
		this.life = tamanho;
		this.verticalImage = verticalImage;
		this.horizontalImage = horizontalImage;
		this.vertical = vertical;
		this.imageView = imageView;
		this.isEnemy = isEnemy;
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
		return tamanho;
	}
	
	public boolean getVertical() {
		return vertical;
	}
	
	public int getLife() {
		return this.life;
	}
	
	public void atacked() {
		if(this.life>0)
			this.life--;
	}
	
	public void draw() {
		imageView.setTranslateX(x);
		imageView.setTranslateY(y);
		if(vertical) {
			imageView.setImage(verticalImage);
			imageView.setFitHeight(tamanho * 40);
	    	imageView.setFitWidth( 40);
			
		}else {
			imageView.setImage(horizontalImage);
			imageView.setFitHeight(40);
	    	imageView.setFitWidth(tamanho * 40);
			
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
	
	public void draw(boolean visible) {
	
		imageView.setVisible(visible);

		System.out.println("eu estava aqui!");
	}
	
	public void blockShip() {
		imageView.setOnMouseDragged(null);
		imageView.setOnMousePressed(null);
		imageView.setOnMouseReleased(null);
	}
	
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
	//if encounter position occupied return true
	public boolean verifyOccupiedPositions(int x, int y, int ocupiedPositions[][],boolean checkRotate) {
		boolean v = vertical;
		if(checkRotate) {
			v = !v;
		}
		if(v) {
			for(int i = 0; i< tamanho;i++) {
				System.out.println("Sou horizontal mas estou olhando verticaallllllllllllllllllllllllll");
				System.out.println("YYYY " + y + "XXXXX " +x);
				System.out.println("position: "+(y+i)+" : "+ocupiedPositions[y+i][x]);
				if(ocupiedPositions[y+i][x]!=tamanho && ocupiedPositions[y+i][x]!=0) {
					return false;
				}
			}
		}else {
			for(int i = 0; i< tamanho;i++) {
				System.out.println("Sou vertical mas estou olhando horizontallllllllllllllllllllllllll");
				System.out.println("YYYY " + y + "XXXXX " +x);
				System.out.println("POSITION: "+(x+i)+" : "+ocupiedPositions[y][x+i]);
				if(ocupiedPositions[y][x+i]!=tamanho && ocupiedPositions[y][x+i]!=0) {
					return false;
				}
			}
		}
		return true;//no position occupied found return true
	}
	
	 public boolean isInvalid(int x, int y,Ship ship,int ocupiedPositions[][]) {
	    	System.out.println(ship.getTamanho() + "[[[[[["+x+"[[[[["+y);
	    	
	    		if((ship.getVertical() &&  ship.getTamanho() + y > 10 ) ) {
	    			
		    		return true;
		    	}else if((!ship.getVertical() &&  ship.getTamanho() + x > 10) ) {
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

}
