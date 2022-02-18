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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;


public class ControleCadastro implements Initializable{
	@FXML
	private TableView<Especie> tabelaEspecie;
	@FXML
	private TableColumn colunaEspecieId;
	@FXML
	private TableColumn colunaEspecieNome;
	
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
	private TextField txtQuantidade;
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
     //Inicio método para persistir produtos
	public void persistirProduto() {
		String produto = txtProduto.getText();
	if(produto == null) {mensagemProdutoRepetido();}
		else {
		Especie espe = boxEspecie.getValue();
	if(espe == null) {mensagemProdutoRepetido();}
		else {
		String especie = espe.getNome();
		Local local = boxLocal.getValue();
	if(local == null) {mensagemProdutoRepetido();}
		else {
		String localizacao =local.getNome();
		String tipoQtdd = txtQuantidade.getText();
	if(tipoQtdd.isEmpty()) {mensagemProdutoRepetido();}
		else {
		Boolean valida = validaQuantidade(tipoQtdd);
	if (valida == false) {mensagemErro();}
		else {
		int quantdd = Integer.parseInt(txtQuantidade.getText());
		Produto prod = new Produto(produto, especie, quantdd, localizacao);
		Dao<Produto> daoproduto = new Dao<>();
		try {
			daoproduto.abrirT();
			daoproduto.incluir(prod);
			
		} catch (Exception e){
			mensagemProdutoRepetido();
		}
		daoproduto.fecharT();
		//mensagem de produto cadasatrado
		Alert produtoCadastrado = new Alert(Alert.AlertType.INFORMATION);
		produtoCadastrado.setTitle("Mensagem do Sistema");
		produtoCadastrado.setHeaderText("Cadastro de Produtos");
		produtoCadastrado.setContentText("Produto ("+produto+") Cadastrado");
		produtoCadastrado.showAndWait();
		
		
		txtProduto.setText(""); //limpar fild produto
		txtQuantidade.setText("");
	}}}
	}
	}
	} // Fim método persistir produto
	
	
	//Inicio método persistir Especie
	public void persistirEspecie() {
		
		String nome = txtCadEspecie.getText();
		if(nome.isEmpty()) {mensagemProdutoRepetido();}
		else {
		Especie especie = new Especie(nome);
		Dao<Especie> cadEspecie = new Dao<>(Especie.class);
		try {
		cadEspecie.abrirT();
		cadEspecie.incluir(especie);
		} catch (Exception e) {
			mensagemProdutoRepetido();
		}
		cadEspecie.fecharT();
		cadEspecie.fechar();
			}
		gerarTabelaDeEspecie();
		txtCadEspecie.setText("");
		} //Fim do método persistir espécie
	
	
	public void persistirLocal() {
		String nome = txtCadLocal.getText();
		if(nome.isEmpty()) {mensagemProdutoRepetido();}
		else {
		Local local = new Local(nome);
		Dao<Local> cadlocal = new Dao<>(Local.class);
		try {
		cadlocal.abrirT();
		cadlocal.incluir(local);
		
		} catch (Exception e) {
			mensagemProdutoRepetido();
		}
		cadlocal.fecharT();
		cadlocal.fechar();
		}
		geraTabelaDeLocal();
		txtCadLocal.setText("");
	}
	
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

	public  boolean validaQuantidade(String qtdd){
		boolean valido = true;
		if(!qtdd.matches("[0-9]*")){
			valido = false;
		} return valido;
	}
	
	//Inicio metodo para remover especie marcada
	public void removerLinhaMarcadaTabelaEspecie() {
		if (tabelaEspecie.getSelectionModel().getSelectedItem() !=null) {
		Especie especie = tabelaEspecie.getSelectionModel().getSelectedItem();
		int id = especie.getId();
		Dao<Especie> daoDelete = new Dao<>(Especie.class);
		daoDelete.removerEspecie(id);
		gerarTabelaDeEspecie();
		}
		
	}
	public void removerLinhaMarcadaTabelaLocal() {
		if (tabelaLocal.getSelectionModel().getSelectedItem() !=null) {
		Local local = tabelaLocal.getSelectionModel().getSelectedItem();
		int id = local.getId();
		Dao<Local> daoDelete = new Dao<>(Local.class);
		daoDelete.removerLocal(id);
		}
		geraTabelaDeLocal();
		
	}

	public void mensagemErro() {
	Alert dialogoInfo = new Alert(Alert.AlertType.INFORMATION);
    dialogoInfo.setTitle("Mensagem do Sistema");
    dialogoInfo.setHeaderText("Observe em Qantidade");
    dialogoInfo.setContentText("Por favor, digite um valor válido, ex 1, 2, 10, 20!");
    dialogoInfo.showAndWait();
}
	public void mensagemProdutoRepetido() {
		Alert dialogoInfo = new Alert(Alert.AlertType.INFORMATION);
	    dialogoInfo.setTitle("Mensagem do Sistema");
	    dialogoInfo.setHeaderText("Por favor preencha todos os campos");
	    dialogoInfo.setContentText("Produto não digitado ou ja Cadastrado");
	    dialogoInfo.showAndWait();
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
	return txtQuantidade;
}
public void setQuantidade(TextField quantidade) {
	this.txtQuantidade = quantidade;
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

