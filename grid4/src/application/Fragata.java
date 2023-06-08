package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Fragata extends Ship {
	
	private String nome;
	
	public Fragata(int tamanho, ImageView imageView, Image verticalImage, Image horizontalImage, boolean vertical, String nome) {
		super(tamanho,  imageView,  verticalImage,  horizontalImage,vertical);
		this.nome = nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getNome() {
		return nome;
	}
}
