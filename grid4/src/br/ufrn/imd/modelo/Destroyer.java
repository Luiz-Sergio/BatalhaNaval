package br.ufrn.imd.modelo;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Destroyer extends Ship {
	
	private String name;
	private static Image verticalImage;
	private static Image horizontalImage;
	
	/**
	 * defines the images the ship will use
	 */
	static {
		verticalImage = new Image(Corveta.class.getResourceAsStream("ShipRescue.png"));
		horizontalImage = new Image(Corveta.class.getResourceAsStream("ShipRescue2.png"));
	}
	public Destroyer(int tamanho, ImageView imageView, boolean vertical,boolean isEnemey) {

		super(tamanho,  imageView,  verticalImage,  horizontalImage,vertical,isEnemey);
		this.name = "Destroyer";
		
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
}
