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
	private List<Produto> listaTodos = new ArrayList<>();
	private List<Produto> listaPorId = new ArrayList<>();
	private List<Produto> listaParaImprimir = new ArrayList<>();
	
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
		listaPorNome = new ArrayList<Produto>();
		listaPorId = new ArrayList<Produto>();
		Dao<Produto> dao = new Dao<>(Produto.class);
		dao.abrirT();
		for(Produto e: dao.obterTodos()) {
		 getListaTodos().add(e);
		}
		dao.fecharT();
		dao.fechar();
		columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		columnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		columnQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
		columnLocal.setCellValueFactory(new PropertyValueFactory<>("local"));
		columnEspecie.setCellValueFactory(new PropertyValueFactory<>("especie"));
	
		tableViewPesquisa.setItems(FXCollections.observableArrayList(getListaTodos()));
	
	}
	
	//tentativa de corririr bug id nao digitado e id nao match
	public void listaProdutoPorId() {
		listaTodos = new ArrayList<Produto>();
		listaPorNome = new ArrayList<Produto>();
		listaPorId = new ArrayList<Produto>();
		String idConsulta = txtId.getText();
		
		if(idConsulta.isEmpty()) {
			JOptionPane.showMessageDialog(null,"Preencha o campo id!");
		}
		else {
		int id = Integer.parseInt(idConsulta);
		Dao<Produto> dao = new Dao<>(Produto.class);
		dao.abrirT();
		listaPorId.add(dao.obterPorId(id));
			
		columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		columnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		columnQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
		columnLocal.setCellValueFactory(new PropertyValueFactory<>("local"));
		columnEspecie.setCellValueFactory(new PropertyValueFactory<>("especie"));
		
		tableViewPesquisa.setItems(FXCollections.observableArrayList(listaPorId));
		
		}
		if(columnId.getCellData(0) ==  null && !idConsulta.isEmpty()) {JOptionPane.showMessageDialog(null,"Id Não Cadastrado!");}
		
		
}
			
	
	public void listaProdutoPorNome() {
		listaTodos = new ArrayList<Produto>();
		listaPorId = new ArrayList<Produto>();
		String nomeConsulta = txtNome.getText();
		if(nomeConsulta.isEmpty()) {JOptionPane.showMessageDialog(null,"Preencha o Campo Nome!");}
		else {
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
			if(columnNome.getCellData(0) ==  null && !nomeConsulta.isEmpty()) {JOptionPane.showMessageDialog(null,"Produto Não Cadastrado!");}	
	}
		
	
	
	//button imprimir 
	public void imprimiTabelaAtual() {
		if(!listaPorId.isEmpty()) {
		listaParaImprimir = listaPorId;
		}
		if(! listaTodos.isEmpty()) {
		listaParaImprimir = listaTodos;
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
	
	
	public List<Produto> getListaTodos() {
		return listaTodos;
	}
	public void setListaTodos(List<Produto> listaTodosProdutos) {
		this.listaTodos = listaTodosProdutos;
	}
	
	
	
	}
