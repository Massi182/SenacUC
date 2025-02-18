import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private static final String URL = "jdbc:mysql://localhost:3306/uc11"; // Nome do banco de dados
    private static final String USUARIO = "root"; // Usuário do MySQL
    private static final String SENHA = ""; // Se houver senha, insira aqui

    public static Connection conectar() {
        try {
            return DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (SQLException e) {
            throw new RuntimeException("❌ Erro ao conectar ao banco de dados: " + e.getMessage());
        }
    }
}