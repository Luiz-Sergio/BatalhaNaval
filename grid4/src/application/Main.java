package application;
	
import java.io.File;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader();
			AnchorPane root = (AnchorPane)loader.load(getClass().getResource("/br/ufrn/imd/visao/Grid.fxml").openStream());

			primaryStage.setScene(new Scene(root));
			primaryStage.setResizable(false);
			Image icon = new Image("icon.png");
			primaryStage.getIcons().add(icon);
			primaryStage.setTitle("BATTLE SHIP - MASTER GAME ULTRA VERSION");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
