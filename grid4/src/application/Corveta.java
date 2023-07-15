package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Corveta extends Ship {
	
	private String nome;
	private static Image verticalImage;
	private static Image horizontalImage;
	
	static {
		verticalImage = new Image(Corveta.class.getResourceAsStream("ShipPatrolHull.png"));
		horizontalImage = new Image(Corveta.class.getResourceAsStream("ShipPatrolHull2.png"));
	}
	public Corveta(int tamanho, ImageView imageView, boolean vertical,boolean isEnemy) {

		super(tamanho,  imageView,  verticalImage,  horizontalImage,vertical,isEnemy);
		this.nome = "Fragata";
		
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getNome() {
		return nome;
	}
}
