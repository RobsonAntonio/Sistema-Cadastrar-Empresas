package Conexao;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexao {
	public static void main(String[] args) {
	
	Conexao conexao = new Conexao();
	conexao.conectar();

}
public static Connection conectar() {
	
	Connection conexao = null;
	
try {
	
	String url = "jdbc:mysql://localhost:3306/sistema";
	String usuario = "root";
	String senha = "198013";
	
	conexao = DriverManager.getConnection(url, usuario, senha);
	System.out.println("Conectado com Sucesso!");
	
} catch (Exception e) {
	
	System.out.println("Falha ao Conectar com o banco!" +e.getMessage());
		
}
	
	return conexao;
}
}
