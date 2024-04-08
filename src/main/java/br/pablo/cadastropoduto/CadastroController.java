package br.pablo.cadastropoduto;

import br.pablo.cadastropoduto.models.Produto;
import br.pablo.cadastropoduto.repositories.ProdutoRepository;
import br.pablo.cadastropoduto.repositories.ProdutoRepositorySQLite;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.net.URL;
import java.util.ResourceBundle;

public class CadastroController implements Initializable {
    @FXML
    public Button btnSalvar = new Button();

    @FXML
    public Text lblNome = new Text("Cadastro");

    Alert alert = new Alert(Alert.AlertType.ERROR);

    @FXML
    public TextField txtNome = new TextField();
    @FXML
    public TextField txtQuantidade = new TextField();

    public static Produto produto = new Produto();


    ProdutoRepository produtoRepository = new ProdutoRepositorySQLite();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtQuantidade.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (!t1.matches("\\d*")) {
                    txtQuantidade.setText(t1.replaceAll("[^0-9,.]", ""));
                }
            }
        });
       fillFields();
       setDispose();
    }

    private void setDispose(){
        Platform.runLater(()->{
            Stage currStage = (Stage) btnSalvar.getScene().getWindow();
            currStage.setOnCloseRequest(e -> {
               produto = new Produto();
            });
        });
    }

    private void fillFields(){
        if(produto.getId()==null){
            lblNome.setText("Cadastro de produto");
            return;
        }else{
            lblNome.setText("Alteração de produto");
        }
        txtNome.setText(produto.getNome());
        txtQuantidade.setText(String.valueOf(produto.getQuantidade()));
    }

    public void salvarProduto(){
    if(txtNome.getText().isEmpty() || txtQuantidade.getText().isEmpty()){
        alert.setTitle("Erro");
        alert.setHeaderText("Erro ao salvar");
        alert.setContentText("Preencha todos os campos");
        alert.show();
        return;
    }
    try{
        produto.setNome(txtNome.getText());
        produto.setQuantidade(Double.parseDouble(txtQuantidade.getText()));
        produtoRepository.save(produto);
        alert.setAlertType(Alert.AlertType.INFORMATION);
        alert.setTitle("Sucesso");
        alert.setHeaderText("Produto salvo com sucesso");
        alert.setContentText("Produto salvo com sucesso");
        alert.showAndWait();
        ProdutoController.refreshTable();
        Stage stage = (Stage) btnSalvar.getScene().getWindow();
        stage.close();
    }catch(Exception e){
        alert.setTitle("Erro");
        alert.setHeaderText("Erro ao salvar");
        alert.setContentText("Erro ao salvar o produto");
        alert.show();
        e.printStackTrace();
    }
    }
}
