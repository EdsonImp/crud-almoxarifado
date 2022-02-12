package almoxarifadoFXMLmvc.controler;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;



public class ControlerTelaInicial implements Initializable{
	   
	  	   
	    @Override
	    public void initialize(URL location, ResourceBundle resources) {
	    }
	
	    //chama tela de cadastro
	    @FXML
	public void cadastrar() throws IOException {
	  	
	    Stage estagio = new Stage();
		TabPane a = (TabPane) FXMLLoader.load(getClass().getResource("/almoxarifadoFXMLmvc/view/telaCadastro.fxml"));
		
		Scene cena = new Scene(a);
		estagio.setScene(cena);
		estagio.show();
	}
	    @FXML
	    public void consultar() throws IOException {
	    	Stage estagio = new Stage();
			ScrollPane a = (ScrollPane) FXMLLoader.load(getClass().getResource("/almoxarifadoFXMLmvc/view/telaConsultas.fxml"));
			
			Scene cena = new Scene(a);
			estagio.setScene(cena);
			estagio.show();
	    	
	    }

		
	}


