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

    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();

    public void cadastrarProduto(ProdutosDTO produto) {

        String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";

        conn = new conectaDAO().connectDB();

        try {

            prep = conn.prepareStatement(sql);

            prep.setString(1, produto.getNome());
            prep.setInt(2, produto.getValor());
            prep.setString(3, produto.getStatus());

            prep.execute();
            prep.close();

            JOptionPane.showMessageDialog(
                    null,
                    "Produto cadastrado com sucesso!"
            );

        } catch (SQLException erro) {

            JOptionPane.showMessageDialog(
                    null,
                    "Erro ao cadastrar produto: " + erro.getMessage()
            );
        }
    }

   public ArrayList<ProdutosDTO> listarProdutos() {

    String sql = "SELECT * FROM produtos";

    conn = new conectaDAO().connectDB();

    ArrayList<ProdutosDTO> lista = new ArrayList<>();

    try {

        prep = conn.prepareStatement(sql);
        resultset = prep.executeQuery();

        while (resultset.next()) {

            ProdutosDTO produto = new ProdutosDTO();

            produto.setId(resultset.getInt("id"));
            produto.setNome(resultset.getString("nome"));
            produto.setValor(resultset.getInt("valor"));
            produto.setStatus(resultset.getString("status"));

            lista.add(produto);
        }

        resultset.close();
        prep.close();

    } catch (SQLException erro) {

        JOptionPane.showMessageDialog(
                null,
                "Erro ao listar produtos: " + erro.getMessage()
        );
    }

    return lista;
}
   public void venderProduto(int id) {

    String sql = "UPDATE produtos SET status = 'Vendido' WHERE id = ?";

    try {
        conn = new conectaDAO().connectDB();

        prep = conn.prepareStatement(sql);
        prep.setInt(1, id);

        prep.executeUpdate();

        prep.close();
        conn.close();

        JOptionPane.showMessageDialog(
            null,
            "Produto vendido com sucesso!"
        );

    } catch (SQLException erro) {

        JOptionPane.showMessageDialog(
            null,
            "Erro ao vender produto: " + erro.getMessage()
        );
    }
}
}