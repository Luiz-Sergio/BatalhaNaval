package br.ufrn.imd.modelo;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Submarino extends Ship {
	
	private static Image verticalImage;
	private static Image horizontalImage;
	
	/**
	 * defines the images the ship will use
	 */
	static {
		verticalImage = new Image(Corveta.class.getResourceAsStream("ShipSubMarineHull.png"));
		horizontalImage = new Image(Corveta.class.getResourceAsStream("ShipSubMarineHull2.png"));
	}
	public Submarino(int tamanho, ImageView imageView, boolean vertical,boolean isEnemey) {

		super(tamanho,  imageView,  verticalImage,  horizontalImage,vertical,isEnemey);
		this.setName("Submarino");
		
	}
}
