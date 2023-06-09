package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Ship {
	private double x;
	private double y;
	private double lastValidX;
	private double lastValidY;
	private int size;
	private boolean vertical = true;
	private ImageView imageView;
	private Image verticalImage;
	private Image horizontalImage;
	private String name; 
	
	public Ship(int size, ImageView imageView, Image verticalImage, Image horizontalImage, boolean vertical) {
		
		this.size = size;
		this.verticalImage = verticalImage;
		this.horizontalImage = horizontalImage;
		this.vertical = vertical;
		this.imageView = imageView;
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
	
	public int getSize() {
		return size;
	}
	
	public boolean getVertical() {
		return vertical;
	}
	
	public void draw() {
		imageView.setTranslateX(x);
		imageView.setTranslateY(y);
		if(vertical) {
			imageView.setImage(verticalImage);
			imageView.setFitHeight(size * 40);
	    	imageView.setFitWidth( 40);
			
		}else {
			imageView.setImage(horizontalImage);
			imageView.setFitHeight(40);
	    	imageView.setFitWidth(size * 40);
			
		}
		System.out.println("eu estava aqui!");
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
	
	 public boolean isValid(int x, int y,Ship ship,boolean ocupiedPositions[][]) {
	    	System.out.println(ship.getSize() + "[[[[[["+x+"[[[[["+y);
	    	
	    		if((ship.getVertical() &&  ship.getSize() + y > 10 )|| ocupiedPositions[x][y]) {
		    		return true;
		    	}else if((!ship.getVertical() &&  ship.getSize() + x > 10) || ocupiedPositions[x][y]) {
		    		return true;
		    	}else {
		    		//ocupiedPositions[x][y] = true;
		    		return false;
		    	}	
	    	
	    
	    	
	    }
	public void setName(String name) {
		this.name = name;
	}
	 
	public String getName() { 
		return this.name;
	}
	
	public boolean isHit(int x, int y) {
//		if(this.vertical) {
//			if(x == this.x) {
//				for()
//				return true;
//			}
//		}
//		else if((x == this.x || x == (this.x+1)) && y == this.y) return true;
		return false;
	}
	
}
