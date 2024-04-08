package br.pablo.cadastropoduto;

import atlantafx.base.theme.PrimerDark;
import br.pablo.cadastropoduto.models.Produto;
import br.pablo.cadastropoduto.repositories.ProdutoRepository;
import br.pablo.cadastropoduto.repositories.ProdutoRepositorySQLite;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

public class ProdutoController {
    @FXML
    private TableView<Produto> produtosPane=new TableView<>();

    @FXML
    private Button cadastrarButton=new Button();

    @FXML
    private Button alterarButton=new Button();

    @FXML
    private Button deletarButton=new Button();

    private Alert alert = new Alert(Alert.AlertType.ERROR);

    private static TableView<Produto> tableViewInstance = new TableView<>();

    private static ProdutoRepository produtoRepository = new ProdutoRepositorySQLite();

    @FXML
    public void initialize(){
        setupTable();
        refreshTable();
        Application.setUserAgentStylesheet(new PrimerDark().getUserAgentStylesheet());
    }

    public void cadastrarProduto() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(ProdutoApplication.class.getResource("cadastro-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        Stage stage = new Stage();
        stage.setTitle("Cadastro");
        stage.setScene(scene);
        stage.show();
    }

    public void deletarProduto(){
        Produto produto = produtosPane.getSelectionModel().getSelectedItem();
        if(produto == null){
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao deletar");
            alert.setContentText("Selecione um produto");
            alert.show();
            return;
        }
        produtoRepository.delete(produto);
        produtosPane.getItems().remove(produto);
    }

    public void alterarProduto() throws IOException {
        Produto produto = produtosPane.getSelectionModel().getSelectedItem();
        if(produto == null){
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao alterar");
            alert.setContentText("Selecione um produto");
            alert.show();
            return;
        }
        CadastroController.produto = produto;
        FXMLLoader fxmlLoader = new FXMLLoader(ProdutoApplication.class.getResource("cadastro-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        Stage stage = new Stage();
        stage.setTitle("Edição de Produto");
        stage.setScene(scene);
        stage.show();
    }

    public static void refreshTable(){
        tableViewInstance.getItems().clear();
        tableViewInstance.getItems().addAll(produtoRepository.getAll());
    }

    private void setupTable(){
        TableColumn<Produto, Integer> idColumn = new TableColumn<>("ID");
        TableColumn<Produto, String> nomeColumn = new TableColumn<>("NOME");
        TableColumn<Produto, Double> quantidadeColumn = new TableColumn<>("QUANTIDADE");
        produtosPane.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
        quantidadeColumn.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        produtosPane.getColumns().addAll(idColumn,nomeColumn,quantidadeColumn);
        tableViewInstance = produtosPane;
    }
}