/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adm
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class ProdutosDAO {

    // Método para cadastrar um novo produto no banco de dados
    public boolean cadastrarProduto(ProdutosDTO produto) {
        Connection conn = Conexao.conectar(); 
        if (conn == null) {
            JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco de dados!");
            return false;
        }

        String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";
        PreparedStatement prep = null;

        try {
            prep = conn.prepareStatement(sql);
            prep.setString(1, produto.getNome());
            prep.setInt(2, produto.getValor());
            prep.setString(3, produto.getStatus());

            int linhasAfetadas = prep.executeUpdate();
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "❌ Erro ao cadastrar produto: " + e.getMessage());
            return false;
        } finally {
            try {
                if (prep != null) prep.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "⚠️ Erro ao fechar a conexão: " + e.getMessage());
            }
        }
    }

    // Método para listar todos os produtos cadastrados
    public ArrayList<ProdutosDTO> listarProdutos() {
        Connection conn = Conexao.conectar();
        if (conn == null) {
            JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco de dados!");
            return new ArrayList<>();
        }

        String sql = "SELECT * FROM produtos";
        PreparedStatement prep = null;
        ResultSet resultSet = null;
        ArrayList<ProdutosDTO> listagem = new ArrayList<>();

        try {
            prep = conn.prepareStatement(sql);
            resultSet = prep.executeQuery();

            while (resultSet.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(resultSet.getInt("id"));
                produto.setNome(resultSet.getString("nome"));
                produto.setValor(resultSet.getInt("valor"));
                produto.setStatus(resultSet.getString("status"));
                listagem.add(produto);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "❌ Erro ao listar produtos: " + e.getMessage());
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (prep != null) prep.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "⚠️ Erro ao fechar a conexão: " + e.getMessage());
            }
        }
        return listagem;
    }

    // Método para vender um produto (atualizar status para "Vendido")
    public boolean venderProduto(int idProduto) {
        Connection conn = Conexao.conectar();
        if (conn == null) {
            JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco de dados!");
            return false;
        }

        String sql = "UPDATE produtos SET status = 'Vendido' WHERE id = ?";
        PreparedStatement prep = null;

        try {
            prep = conn.prepareStatement(sql);
            prep.setInt(1, idProduto);

            int linhasAtualizadas = prep.executeUpdate();
            return linhasAtualizadas > 0; // Retorna true se a atualização foi bem-sucedida
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "❌ Erro ao vender produto: " + e.getMessage());
            return false;
        } finally {
            try {
                if (prep != null) prep.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "⚠️ Erro ao fechar a conexão: " + e.getMessage());
            }
        }
    }
}
