package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Ship {
	private double x;
	private double y;
	private boolean vertical = true;
	private ImageView imageView;
	
	public Ship(double x, double y, ImageView imageView) {
		this.x = x;
		this.y = y;
		this.imageView = imageView;
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public double getX() {
		return this.x;
	}
	
	public double getY() {
		return this.y;
	}
	
	public void draw() {
		imageView.setTranslateX(x);
		imageView.setTranslateY(y);
		System.out.println("eu estava aqui!");
	}
	
	public void rotate() {
		//imageView.setRotate(imageView.getRotate() + 90);
		if(vertical) {
			Image image = new Image(getClass().getResourceAsStream("ShipRescue2.png"));
			imageView.setImage(image);
			imageView.setFitHeight(40);
	    	imageView.setFitWidth(80);
	    	vertical = false;
		}else {
			Image image = new Image(getClass().getResourceAsStream("ShipRescue.png"));
			imageView.setImage(image);
			imageView.setFitHeight(80);
	    	imageView.setFitWidth(40);
	    	vertical = true;
		}
		
	}

}
