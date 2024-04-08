module br.pablo.cadastropoduto {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires static lombok;
    requires atlantafx.base;
    requires org.xerial.sqlitejdbc;

    opens br.pablo.cadastropoduto.models;

    opens br.pablo.cadastropoduto to javafx.fxml;

    exports br.pablo.cadastropoduto;
}