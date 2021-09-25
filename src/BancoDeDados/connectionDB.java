package BancoDeDados;
import java.sql.*;

import javax.swing.JOptionPane;
public class connectionDB {
	
	// Configurando a nossa conexão com um banco de dados//
	
	public Connection c = null;
	private final String driver = "com.mysql.jdbc.Driver"; //Drive do banco de dados --> mysql
	private final String dbname = "onepixel";
	private final String url = "jdbc:mysql://localhost:3306/"+dbname;
	private final String login = "root";
	private final String senha = "";
	
	public boolean connection() {
		try {
			Class.forName(driver);// Carregando o JDBC Driver 
			c = DriverManager.getConnection(url, login, senha);
			DriverManager.println(driver);
			return true;
		} catch (ClassNotFoundException erro) {
			JOptionPane.showMessageDialog(null,"F... Driver não encontrado\n ",erro.toString(), JOptionPane.ERROR_MESSAGE);
			return false;
		} catch (SQLException erro) {
			JOptionPane.showMessageDialog(null,"F... ERRO \n",erro.toString(),JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
	}
	
	
	public void terminarSessao() {
		try {
			c.close();
			System.out.println("Terminou a Sessão");
		} catch (SQLException erro) {
			erro.printStackTrace();
		}
			
	}
  
	

}
