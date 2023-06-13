module grid4 {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	exports application to javafx.graphics;
	opens br.ufrn.imd.controle to javafx.graphics, javafx.fxml;
	//opens br.ufrn.imd.controle to javafx.fxml;
}
