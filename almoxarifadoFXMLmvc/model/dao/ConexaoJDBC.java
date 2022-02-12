package almoxarifadoFXMLmvc.model.dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoJDBC {
	
	public static Connection getConection() {
		try {
			
		final String url = "jdbc:mysql://localhost/almoxarifado?verifyServerCertificate=false&useSSL=true";
		final String user = "root";
		final String senha = "Aeroporto.2905";
		return DriverManager.getConnection(url, user, senha);

		}
		catch (SQLException e) {
			throw new  RuntimeException(e);
}
}
}