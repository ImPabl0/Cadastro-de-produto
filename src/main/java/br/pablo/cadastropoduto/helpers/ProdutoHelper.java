package br.pablo.cadastropoduto.helpers;

import br.pablo.cadastropoduto.models.Produto;

import java.util.Locale;

public class ProdutoHelper {
    public static String createTable(){
        return """
                CREATE TABLE IF NOT EXISTS produtos (
                	id integer PRIMARY KEY,
                	nome text NOT NULL,
                	quantidade real
                );""";
    }

    public static String insertProduto(Produto produto){
        return String.format(Locale.ENGLISH,"INSERT INTO produtos (id, nome, quantidade) VALUES (%d, '%s', %.2f)", produto.getId(), produto.getNome(), produto.getQuantidade());
    }

    public static String updateProduto(Produto produto){
        return String.format(Locale.ENGLISH,"UPDATE produtos SET nome = '%s', quantidade = %.2f WHERE id = %d", produto.getNome(), produto.getQuantidade(), produto.getId());
    }
}
