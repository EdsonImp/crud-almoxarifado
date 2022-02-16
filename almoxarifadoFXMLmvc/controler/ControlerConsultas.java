package almoxarifadoFXMLmvc.controler;

import java.net.URL; 
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import almoxarifadoFXMLmvc.model.dao.Dao;
import almoxarifadoFXMLmvc.model.entidades.Produto;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;


public class ControlerConsultas implements Initializable{
	private List<Produto> listaPorNome = new ArrayList<>();
	private List<Produto> listaPorEspecie = new ArrayList<>();
	private List<Produto> listaPorLocal = new ArrayList<>();
	private List<Produto> listaTodos = new ArrayList<>();
	private List<Produto> listaPorId = new ArrayList<>();
	private List<Produto> listaParaImprimir = new ArrayList<>();
	private String nomeCampoTemp= "";
	
	@FXML
	private TextField txtLocal;
	@FXML
	private TextField txtEspecie;
	@FXML
	private TextField txtId;
	@FXML
	private TextField txtNome;
	@FXML
	private TableView<Produto> tableViewPesquisa;
	@FXML
	private TableColumn columnId;
	@FXML
	private TableColumn columnNome;
	@FXML
	private TableColumn columnEspecie;
	@FXML
	private TableColumn columnLocal;
	@FXML
	private TableColumn columnQuantidade;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
	} 
	public void listaTodosProdutos() {
		zeraTodasListas();
		Dao<Produto> dao = new Dao<>(Produto.class);
		dao.abrirT();
		for(Produto e: dao.obterTodos()) {
		 getListaTodos().add(e);
		}
		dao.fecharT();
		dao.fechar();
		
	    popularTabelas();
		tableViewPesquisa.setItems(FXCollections.observableArrayList(getListaTodos()));
	
	}
	
	public void listaProdutoPorId() {
		zeraTodasListas();
		
		String nomeConsulta;
		if(txtId.getText().isEmpty() && !getNomeCampoTemp().isEmpty()) {nomeConsulta = getNomeCampoTemp();}
		else {
		nomeConsulta = txtId.getText();
		nomeCampoTemp = nomeConsulta;
		//int id = Integer.parseInt(nomeConsulta);
		
		if(nomeConsulta.isEmpty()) {mensagemErro();}
		else {
		int id = Integer.parseInt(nomeConsulta);
		Dao<Produto> dao = new Dao<>(Produto.class);
		dao.abrirT();
		listaPorId.add(dao.obterPorId(id));
		popularTabelas();	
				
		tableViewPesquisa.setItems(FXCollections.observableArrayList(listaPorId));
		}
		if(columnId.getCellData(0) ==  null && !nomeConsulta.isEmpty()) {mensagemNaoEncontrado();
		}
		txtId.setText("");
		}}
	
	
	public void listaProdutoPorNome() {
		zeraTodasListas();
		String nomeConsulta;
		if(txtNome.getText().isEmpty() && !getNomeCampoTemp().isEmpty()) {nomeConsulta = getNomeCampoTemp();}
		else {
		nomeConsulta = txtNome.getText();
		nomeCampoTemp = nomeConsulta;}
		if(nomeConsulta.isEmpty()) {mensagemErro();}
		else {
		Dao<Produto> daoNome = new Dao<>(Produto.class);
		listaPorNome = daoNome.obterPorNome(nomeConsulta);
		
		popularTabelas();
			tableViewPesquisa.setItems(FXCollections.observableArrayList(listaPorNome));
		 } 	
			if(columnNome.getCellData(0) ==  null && !nomeConsulta.isEmpty()) {mensagemNaoEncontrado();
			}	
			txtNome.setText("");
	}
		public void listaProdutosPorEspecie() {
		zeraTodasListas();
		String nomeConsulta;
		if(txtEspecie.getText().isEmpty() && !getNomeCampoTemp().isEmpty()) {nomeConsulta = getNomeCampoTemp();}
		else {
			nomeConsulta = txtEspecie.getText();
			nomeCampoTemp = nomeConsulta;}
		if(nomeConsulta.isEmpty()) {mensagemErro();}
		else {
		Dao<Produto> daoEspecie = new Dao<>(Produto.class);
		listaPorEspecie = daoEspecie.obterPorEspecie(nomeConsulta);
					
			popularTabelas();
			tableViewPesquisa.setItems(FXCollections.observableArrayList(listaPorEspecie));
		 } 	
			if(columnEspecie.getCellData(0) ==  null && !nomeConsulta.isEmpty()) {mensagemNaoEncontrado();
			}	
			txtEspecie.setText("");
	}
		public void listaProdutosPorLocal() {
			zeraTodasListas();
			String nomeConsulta;
			if(txtLocal.getText().isEmpty() && !getNomeCampoTemp().isEmpty()) {nomeConsulta = getNomeCampoTemp();}
			else {
				nomeConsulta = txtLocal.getText();
				nomeCampoTemp = nomeConsulta;}
			if(nomeConsulta.isEmpty()) {mensagemErro();}
			else {
			Dao<Produto> daoLocal = new Dao<>(Produto.class);
				listaPorLocal = daoLocal.obterPorLocal(nomeConsulta);
				popularTabelas();
				tableViewPesquisa.setItems(FXCollections.observableArrayList(listaPorLocal));
				if(columnLocal.getCellData(0) ==  null && !nomeConsulta.isEmpty()) {mensagemNaoEncontrado();
				}
				txtLocal.setText("");
			}
					
		}
		
	public void removerLinhaMarcada() {
		if (tableViewPesquisa.getSelectionModel().getSelectedItem() !=null) {
		Produto prod = tableViewPesquisa.getSelectionModel().getSelectedItem();
		int id = prod.getId();
		Dao<Produto> daoDelete = new Dao<>(Produto.class);
		daoDelete.removerProduto(id);
		mensagemProduoRemovido();
		}
		if (!listaPorNome.isEmpty()) {listaProdutoPorNome(); nomeCampoTemp = "";}
		if (!listaPorEspecie.isEmpty()) {listaProdutosPorEspecie(); nomeCampoTemp = "";}
		if (!listaPorId.isEmpty()) {listaProdutoPorId(); nomeCampoTemp = "";}
		if (!listaTodos.isEmpty()) {listaTodosProdutos(); nomeCampoTemp = "";}
		if (!listaPorLocal.isEmpty()) {listaProdutosPorLocal(); nomeCampoTemp = "";}
	}
	
	//button imprimir 
	public void imprimiTabelaAtual() {
		if(! listaPorId.isEmpty()) {
		listaParaImprimir = listaPorId;
		}
		if(! listaTodos.isEmpty()) {
		listaParaImprimir = listaTodos;
		}
		if(! listaPorLocal.isEmpty()) {
		listaParaImprimir = listaPorLocal;
		}
		else if (!listaPorNome.isEmpty()) {
		listaParaImprimir = listaPorNome;	
		}
		
		
		try {
			JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile("C:\\icones\\relatorioAlmoxarifado.jasper");
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null,new JRBeanCollectionDataSource(listaParaImprimir));

			JasperViewer jasperView = new JasperViewer(jasperPrint,false);
			jasperView.setVisible(true);
		} catch (JRException e) {
			
			e.printStackTrace();
		}
	}
	private void popularTabelas() {
		columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		columnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		columnQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
		columnLocal.setCellValueFactory(new PropertyValueFactory<>("local"));
		columnEspecie.setCellValueFactory(new PropertyValueFactory<>("especie"));
	}
	private void zeraTodasListas() {
		listaPorNome = new ArrayList<Produto>();
		listaTodos = new ArrayList<Produto>();
		listaPorId = new ArrayList<Produto>();
		listaPorLocal = new ArrayList<Produto>();
	}
	
	
	public List<Produto> getListaTodos() {
		return listaTodos;
	}
	public void setListaTodos(List<Produto> listaTodosProdutos) {
		this.listaTodos = listaTodosProdutos;
	}
	public String getNomeCampoTemp() {
		return nomeCampoTemp;
	}
	public void setNomeCampoTemp(String nomeCampoTemp) {
		this.nomeCampoTemp = nomeCampoTemp;
	}
	
	public void mensagemErro() {
		  Alert dialogoInfo = new Alert(Alert.AlertType.INFORMATION);
          dialogoInfo.setTitle("Mensagem do Sistema");
          dialogoInfo.setHeaderText("Mensagem de Verificação...");
          dialogoInfo.setContentText("Verifique se Preencheu o Campo Corretamente!");
          dialogoInfo.showAndWait();
	}
	public void mensagemNaoEncontrado() {
		  Alert dialogoInfo = new Alert(Alert.AlertType.WARNING);
        dialogoInfo.setTitle("Mensagem do Sistema");
        dialogoInfo.setHeaderText("Informação não Encontrada");
        dialogoInfo.setContentText("Verifique se digitou corretamente ou se o que procura foi cadastrado");
        dialogoInfo.showAndWait();
	}
	public void mensagemProduoRemovido() {
		  Alert dialogoInfo = new Alert(Alert.AlertType.CONFIRMATION);
      dialogoInfo.setTitle("Mensagem do Sistema");
      dialogoInfo.setHeaderText("Sucesso");
      dialogoInfo.setContentText("Produto Removido");
      dialogoInfo.showAndWait();
	}
	
	
	}
