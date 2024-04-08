package br.pablo.cadastropoduto.repositories;

import br.pablo.cadastropoduto.datasource.ConnectionManager;
import br.pablo.cadastropoduto.helpers.ProdutoHelper;
import br.pablo.cadastropoduto.models.Produto;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProdutoRepositorySQLite implements ProdutoRepository{

    public ProdutoRepositorySQLite(){
    }

    private final ConnectionManager connectionManager = new ConnectionManager();


    @Override
    public List<Produto> getAll() {
        List<Produto> produtos = new ArrayList<>();
        try(Connection connection = connectionManager.getConnection()){
          ResultSet rs= connection.createStatement().executeQuery("SELECT * FROM produtos");
            while(rs.next()){
                produtos.add(new Produto(rs.getInt("id"), rs.getString("nome"), rs.getDouble("quantidade")));
            }
            return produtos;
        }catch (Exception e){
            e.printStackTrace();
            return produtos;
        }
    }

    @Override
    public void save(Produto produto) {
        if(produto.getId()!=null) {
            update(produto);
            return;
        }
        try(Connection connection = connectionManager.getConnection()){
            connection.createStatement().executeUpdate(ProdutoHelper.insertProduto(produto));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void update(Produto produto) {
        try(Connection connection = connectionManager.getConnection()){
            connection.createStatement().executeUpdate(ProdutoHelper.updateProduto(produto));
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Produto produto) {
        try(Connection connection = connectionManager.getConnection()){
            connection.createStatement().executeUpdate(String.format("DELETE FROM produtos WHERE id = %d", produto.getId()));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
