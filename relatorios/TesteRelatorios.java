package relatorios;

import java.sql.Connection; 
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import almoxarifadoFXMLmvc.model.dao.ConexaoJDBC;
import almoxarifadoFXMLmvc.model.entidades.Produto;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class TesteRelatorios {
	
	public static void main(String[] args) throws SQLException, JRException  {
		List<Produto> listaParaImprimir = new ArrayList<>();
		Produto prod1 = new Produto("aa", "bb", 1, "cc");
		Produto prod2 = new Produto("aab", "bbc", 3, "cce");
		Produto prod3 = new Produto("aac", "bbd", 4, "ccf");
		
		listaParaImprimir.add(prod1);
		listaParaImprimir.add(prod2);
		listaParaImprimir.add(prod3);
	//Connection conexao = ConexaoJDBC.getConection();
	JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile("C:\\icones\\relatorioAlmoxarifado.jasper");
	JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null,new JRBeanCollectionDataSource(listaParaImprimir));

	JasperViewer jasperView = new JasperViewer(jasperPrint,false);
	jasperView.setVisible(true);

}
}
