package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Destroyer extends Ship {
	
	private String nome;
	private static Image verticalImage;
	private static Image horizontalImage;
	
	static {
		verticalImage = new Image(Corveta.class.getResourceAsStream("ShipRescue.png"));
		horizontalImage = new Image(Corveta.class.getResourceAsStream("ShipRescue2.png"));
	}
	public Destroyer(int tamanho, ImageView imageView, boolean vertical,boolean isEnemey) {

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
