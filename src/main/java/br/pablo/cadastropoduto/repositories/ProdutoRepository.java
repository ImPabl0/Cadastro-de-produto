package br.pablo.cadastropoduto.repositories;

import br.pablo.cadastropoduto.models.Produto;

import java.util.List;

public interface ProdutoRepository {
    List<Produto> getAll();
    void save(Produto produto);
    void update(Produto produto);
    void delete(Produto produto);
}
