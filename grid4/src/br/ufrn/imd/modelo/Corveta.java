package br.ufrn.imd.modelo;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Corveta extends Ship {
	
	private static Image verticalImage;
	private static Image horizontalImage;
	
	/**
	 * defines the images the ship will use
	 */
	static {
		verticalImage = new Image(Corveta.class.getResourceAsStream("ShipPatrolHull.png"));
		horizontalImage = new Image(Corveta.class.getResourceAsStream("ShipPatrolHull2.png"));
	}
	public Corveta(int tamanho, ImageView imageView, boolean vertical,boolean isEnemy) {

		super(tamanho,  imageView,  verticalImage,  horizontalImage,vertical,isEnemy);
		this.setName("Corveta");
		
	}
}
