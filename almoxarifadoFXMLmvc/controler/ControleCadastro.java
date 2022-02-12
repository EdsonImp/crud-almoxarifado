package almoxarifadoFXMLmvc.controler;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import almoxarifadoFXMLmvc.model.dao.Dao;
import almoxarifadoFXMLmvc.model.entidades.Especie;
import almoxarifadoFXMLmvc.model.entidades.Local;
import almoxarifadoFXMLmvc.model.entidades.Produto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;


public class ControleCadastro implements Initializable{
	@FXML
	TableView<Especie> tabelaEspecie;
	@FXML
	TableColumn colunaEspecieId;
	@FXML
	TableColumn colunaEspecieNome;
	
	@FXML
	TableView<Local> tabelaLocal;
	@FXML
	TableColumn colunaId;
	@FXML
	TableColumn colunaNome;
	@FXML
	private TextField txtCadLocal;
	@FXML
	private TextField txtCadEspecie;
	@FXML
	private TextField quantidade;
	@FXML
	private TextField txtProduto;
	@FXML
	private ComboBox<Especie> boxEspecie;
	@FXML
	private ComboBox<Local> boxLocal;
	@FXML
	private Button botaoGravar;
			
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		gerarTabelaDeEspecie();
		geraTabelaDeLocal();
		preencherComboEspecie();	
		preencherBoxLocal();
	}
public ControleCadastro() {
	
}
	
	public void preencherComboEspecie() {
		    	
		List<Especie> listaBox = new ArrayList<>();
		Dao<Especie> daoEspecie = new Dao<>(Especie.class);
		ObservableList<Especie> obsList;
		daoEspecie.abrirT();
		for(Especie e: daoEspecie.obterTodos()) {
			listaBox.add(e);
		}
		daoEspecie.fecharT();
		daoEspecie.fechar();
		obsList = FXCollections.observableArrayList(listaBox);
		boxEspecie.setItems(obsList);
	
		
    }
	public void preencherBoxLocal() {
		List<Local> listaBoxLocal = new ArrayList<>();
		Dao<Local> daoLocal = new Dao<>(Local.class);
		ObservableList<Local> obsListLocal;
		daoLocal.abrirT();
		for(Local e: daoLocal.obterTodos()) {
			listaBoxLocal.add(e);
		}
		daoLocal.fecharT();
		daoLocal.fechar();
		obsListLocal = FXCollections.observableArrayList(listaBoxLocal);
		boxLocal.setItems(obsListLocal);
		
	}

	public void persistirProduto() {
		String produto = txtProduto.getText();
		Especie espe = boxEspecie.getValue();
		String especie = espe.getNome();
		int quantdd = Integer.parseInt(quantidade.getText());
		Local local = boxLocal.getValue();
		String localizacao =local.getNome();
		Produto prod = new Produto(produto, especie, quantdd, localizacao);
		Dao<Produto> daoproduto = new Dao<>();
		try {
			
			daoproduto.abrirT();
			daoproduto.incluir(prod);
			
		} catch (Exception e){
			JOptionPane.showMessageDialog(null,"Produto Repetido!");
			
		}
		daoproduto.fecharT();
	}
		
	public void persistirEspecie() {
		String nome = txtCadEspecie.getText();
		Especie especie = new Especie(nome);
		Dao<Especie> cadEspecie = new Dao<>(Especie.class);
		cadEspecie.abrirT();
		cadEspecie.incluir(especie);
		cadEspecie.fecharT();
		cadEspecie.fechar();
	}
	public void persistirLocal() {
		String nome = txtCadLocal.getText();
		Local local = new Local(nome);
		Dao<Local> cadlocal = new Dao<>(Local.class);
		cadlocal.abrirT();
		cadlocal.incluir(local);
		cadlocal.fecharT();
		cadlocal.fechar();
		
	}
	//tentativa de criar tabela local e mostrar
	public void geraTabelaDeLocal() {
		
		List<Local> locais = new ArrayList<>();
		Dao<Local> daoLocal = new Dao<>(Local.class);
		daoLocal.abrirT();
		for(Local e: daoLocal.obterTodos()) {
			locais.add(e);
		}
		daoLocal.fecharT();
		daoLocal.fechar();
		colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));// referencia da entidade
		colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		
		tabelaLocal.setItems(FXCollections.observableArrayList(locais));
		
	}
public void gerarTabelaDeEspecie() {
	List<Especie> especies = new ArrayList<Especie>();
	Dao<Especie> daoEspecie = new Dao<>(Especie.class);
	daoEspecie.abrirT();
	for(Especie e: daoEspecie.obterTodos()) {
		especies.add(e);
	}
	
	colunaEspecieId.setCellValueFactory(new PropertyValueFactory<>("id"));
	colunaEspecieNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
	tabelaEspecie.setItems(FXCollections.observableArrayList(especies));
}


public ComboBox<Especie> getBoxEspecie() {
	return boxEspecie;
}
public void setBoxEspecie(ComboBox<Especie> boxEspecie) {
	this.boxEspecie = boxEspecie;
}
public ComboBox<Local> getBoxLocal() {
	return boxLocal;
}
public void setBoxLocal(ComboBox<Local> boxLocal) {
	this.boxLocal = boxLocal;
}
public TextField getTxtProduto() {
	return txtProduto;
}
public void setTxtProduto(TextField txtProduto) {
	this.txtProduto = txtProduto;
}
public TextField getQuantidade() {
	return quantidade;
}
public void setQuantidade(TextField quantidade) {
	this.quantidade = quantidade;
}
public TextField getTxtCadLocal() {
	return txtCadLocal;
}
public void setTxtCadLocal(TextField txtCadLocal) {
	this.txtCadLocal = txtCadLocal;
}
public TextField getTxtCAdEspecie() {
	return txtCadEspecie;
}
public void setTxtCAdEspecie(TextField txtCAdEspecie) {
	this.txtCadEspecie = txtCAdEspecie;
}

}

