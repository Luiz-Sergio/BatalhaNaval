package br.ufrn.imd.modelo;

import java.util.ArrayList;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * Logs is the class, which will contain the complete list of attacks that will be input in the game
 * 
 * Save all the attacks logs and generate new logs in the game.
 * 
 * @author Luís Eduardo
 *
 */
public class Logs {
	/**
	 * The list of all input logs inside the game	
	 */
	private ArrayList<Text> logs;
	
	
	public Logs() {
		logs = new ArrayList<Text>();
	}
	/**
	 * generates a Text component based on who attacked and if hit a ship or not
	 */
	public void generateMessage(boolean hit,boolean player, Ship ship) {
		Text message;
		if(player) message = new Text("VOCÊ:");
		else message = new Text("INIMIGO:");		
        if(hit) message = new Text(message.getText()+" Acertou o navio "+ship.getName());
        else message = new Text(message.getText()+" Acertou o oceano");				
		logs.add(message);
	}
	public void generateError(int param) {
		Text message = new Text("MOVIMENTO INVÁLIDO: ");
		if(param == 0) message = new Text(message.getText()+"Rotação Inválida");
		else if(param == 1) message = new Text(message.getText()+"Posição de Navio Inválida");
		else if(param == 2) message = new Text(message.getText()+"Posição de Disparo Inválida");
		message.setFill(Color.RED);
		logs.add(message);
	}
	public void generateEndMessage(boolean victory) {
		Text message;
		if(victory) {
			message = new Text("VOCÊ GANHOU!");
			message.setFill(Color.GREEN);
		}
		else {
			message = new Text("VOCÊ PERDEU!");
			message.setFill(Color.RED);
		}
		logs.add(message);
	}
	/**
	 * returns the latest added message to the ArrayList of logs
	 */
	public Text getMessage() {
		return logs.get(logs.size()-1);
	}
}
