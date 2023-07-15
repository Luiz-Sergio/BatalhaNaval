package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Fragata extends Ship {
	
	private String nome;
	private static Image verticalImage;
	private static Image horizontalImage;
	
	static {
		verticalImage = new Image(Corveta.class.getResourceAsStream("ShipBattleshipHull.png"));
		horizontalImage = new Image(Corveta.class.getResourceAsStream("ShipBattleshipHull2.png"));
	}
	public Fragata(int tamanho, ImageView imageView, boolean vertical,boolean isEnemey) {

		super(tamanho,  imageView,  verticalImage,  horizontalImage,vertical,isEnemey);
		this.nome = "Fragata";
		
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getNome() {
		return nome;
	}
}
