import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

//import almoxarifadoFXMLmvc.controler.ControlerConsultas;
import almoxarifadoFXMLmvc.model.entidades.Produto;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

public class Testes {
	public static void main(String[] args) {
		Testes teste = new Testes();
		teste.imprimiRelatorio();

	//Dao<Produto> dao = new Dao<>(Produto.class);
	//Produto prod1 = new Produto("Borracha", "Caixa", 10, "Prateleira");
	//dao.abrirT();
	//dao.incluir(prod1);
	//dao.fecharT();
	//dao.fechar();
		//Dao<Local> daoespecie = new Dao<>(Local.class);
		//Local esp = new Local("Prateleira 03");
		//daoespecie.abrirT();
		//daoespecie.incluir(esp);
		//daoespecie.fecharT();
		//daoespecie.fechar();
		
	}
	public void imprimiRelatorio() {
		try {
		List<Produto> listaParaImprimir = new ArrayList<>();
		Produto prod1 = new Produto("aa", "bb", 1, "cc");
		Produto prod2 = new Produto("aab", "bbc", 3, "cce");
		Produto prod3 = new Produto("aac", "bbd", 4, "ccf");
		
		listaParaImprimir.add(prod1);
		listaParaImprimir.add(prod2);
		listaParaImprimir.add(prod3);
		
		InputStream fonteRelatorio = Produto.class.getResourceAsStream("C:\\icones\\teste.jrxml");
		JasperReport jasperReport = (JasperReport) JasperCompileManager.compileReport(fonteRelatorio);
		JasperPrint print = JasperFillManager.fillReport(jasperReport, null, new JRBeanCollectionDataSource(listaParaImprimir));
		JasperViewer.viewReport(print, false);
		} catch (JRException e) {
			
			e.printStackTrace();
		}
}
}