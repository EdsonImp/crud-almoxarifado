package almoxarifadoFXMLmvc.controler;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import almoxarifadoFXMLmvc.model.dao.Dao;
import almoxarifadoFXMLmvc.model.entidades.Produto;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
	private List<Produto> listaPorNome;
	private List<Produto> listaTodos;
	private String listaPorId;
	
	private List<Produto> listaParaImprimir;
	
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
		
		
	} //tentativa de preencher lista geral
	public void listaTodosProdutos() {
		listaPorNome = new ArrayList<>();
		Dao<Produto> dao = new Dao<>(Produto.class);
		//dao.abrirT();
		listaTodos = dao.obterPorNome("");
		//for(Produto e: dao.obterTodos()) {
		//	getListaTodos().add(e);
		//}
		dao.fecharT();
		dao.fechar();
		columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		columnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		columnQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
		columnLocal.setCellValueFactory(new PropertyValueFactory<>("local"));
		columnEspecie.setCellValueFactory(new PropertyValueFactory<>("especie"));
	
		tableViewPesquisa.setItems(FXCollections.observableArrayList(listaTodos));
	
	}
	
	
	public void listaProdutoPorId() {
		String idConsulta = txtId.getText();
		int id = Integer.parseInt(idConsulta);
		Dao<Produto> dao = new Dao<>(Produto.class);
		dao.abrirT();
		Produto produto = dao.obterPorId(id);
		columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		columnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		columnQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
		columnLocal.setCellValueFactory(new PropertyValueFactory<>("local"));
		columnEspecie.setCellValueFactory(new PropertyValueFactory<>("especie"));
		
		tableViewPesquisa.setItems(FXCollections.observableArrayList(produto));
		
		
	}
	public void listaProdutoPorNome() {
		listaTodos = new ArrayList<Produto>();
		String nomeConsulta = txtNome.getText();
		Dao<Produto> daoNome = new Dao<>(Produto.class);
		listaPorNome = daoNome.obterPorNome(nomeConsulta);
		//for (Produto produto : listaPorNome) {
			columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
			columnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
			columnQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
			columnLocal.setCellValueFactory(new PropertyValueFactory<>("local"));
			columnEspecie.setCellValueFactory(new PropertyValueFactory<>("especie"));
			
			tableViewPesquisa.setItems(FXCollections.observableArrayList(listaPorNome));
			
					
		}
		
	
	
	
	public void imprimiTabelaAtual() {
		if (listaPorNome.get(0).getNome() != null) {
			listaParaImprimir = listaPorNome;}
		 if(listaTodos.get(0).getNome() != null) {
			listaParaImprimir = listaTodos;
		}
		else {System.out.println("nada a ser impresso");}
		try {
			JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile("C:\\icones\\relatorioAlmoxarifado.jasper");
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null,new JRBeanCollectionDataSource(listaParaImprimir));

			JasperViewer jasperView = new JasperViewer(jasperPrint,false);
			jasperView.setVisible(true);
		} catch (JRException e) {
			
			e.printStackTrace();
		}
	}
	
	
	public List<Produto> getListaTodos() {
		return listaTodos;
	}
	public void setListaTodos(List<Produto> listaTodosProdutos) {
		this.listaTodos = listaTodosProdutos;
	}
	public void setListaPorId(String listaPorId) {
		this.listaPorId = listaPorId;
}
	public String getListaPorId() {
		return listaPorId;
	}
	}
