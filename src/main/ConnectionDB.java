package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class ConnectionDB {

	public static Connection getConexao(String host, String usuario, String senha, String porta, String banco, String sgbd) {
		final String url = "jdbc:" + sgbd + "://" + host + ":" + porta + "/" + banco;
		try {
			return DriverManager.getConnection(url,usuario,senha);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	};
}
