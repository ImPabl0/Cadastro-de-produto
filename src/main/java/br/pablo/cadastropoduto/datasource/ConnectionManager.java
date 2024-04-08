package br.pablo.cadastropoduto.datasource;

import br.pablo.cadastropoduto.helpers.ProdutoHelper;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionManager {

    private static final String DATABASE_PATH = "C:\\Users\\Pablo\\Documents\\produtos.db";

    private void createIfNotExists(){
        File database = new File(DATABASE_PATH);
        if(!database.exists()){
            try {
                if(database.createNewFile()) {
                    createTables();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void createTables(){
        try(Connection connection = getConnection()){
            connection.createStatement().executeUpdate(ProdutoHelper.createTable());
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection(){
        try {
        Class.forName("org.sqlite.JDBC");
        createIfNotExists();
          return  DriverManager.getConnection(String.format("jdbc:sqlite:%s", DATABASE_PATH));
        }catch (Exception e){
        e.printStackTrace();
        throw new RuntimeException("Erro ao conectar ao banco de dados");
        }

    }
}
