package application;

import java.util.ArrayList;

import javafx.scene.text.Text;

public class Logs {
	private ArrayList<Text> logs;
	
	
	public Logs() {
		logs = new ArrayList<Text>();
	}
	public void generateMessage(boolean hit,boolean player, Ship ship) {
		Text message;
		if(player) message = new Text("VOCÊ:");
		else message = new Text("INIMIGO:");		
        if(hit) message = new Text(message.getText()+" Acertou o navio "+ship.getName());
        else message = new Text(message.getText()+" Acertou o oceano");				
		logs.add(message);
	}
	public Text getMessage() {
		return logs.get(logs.size()-1);
	}
}
