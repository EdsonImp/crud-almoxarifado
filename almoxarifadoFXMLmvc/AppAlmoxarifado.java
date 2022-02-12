package almoxarifadoFXMLmvc;

import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AppAlmoxarifado extends Application{

	private Stage estagio;
		
	public AppAlmoxarifado() {
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		estagio = primaryStage;
		URL urlfxmlTelaInicial = getClass().getResource("/almoxarifadoFXMLmvc/view/TelaInicial.fxml");
		VBox root = FXMLLoader.load(urlfxmlTelaInicial);
		Scene telaInicial = new Scene(root, 800,600);
		
		estagio.setScene(telaInicial);
		
		
		estagio.show();
		
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	public Stage getEstagio() {
		return estagio;
	}
	public void setEstagio(Stage estagio) {
		this.estagio = estagio;
	}
	
}
