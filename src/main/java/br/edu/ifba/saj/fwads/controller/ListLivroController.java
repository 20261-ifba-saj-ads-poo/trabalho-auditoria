package br.edu.ifba.saj.fwads.controller;

import br.edu.ifba.saj.fwads.App;
import br.edu.ifba.saj.fwads.model.Livro;
import br.edu.ifba.saj.fwads.service.GenericServiceImpl;
import java.util.UUID;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ListLivroController {

    @FXML
    private TableColumn<Livro, String> clnAutor;

    @FXML
    private TableColumn<Livro, String> clnISBN;

    @FXML
    private TableColumn<Livro, String> clnTitulo;

    @FXML
    private TableView<Livro> tblAutor;

    @FXML
    public void initialize() {
        clnTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        clnISBN.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
        clnAutor.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAutor().getNome()));
        loadLivroList();
    }

    public void loadLivroList() {
        tblAutor.setItems(FXCollections.observableList(new GenericServiceImpl<Livro,UUID>(Livro.class, UUID.class).buscarTodos()));
    }

    @FXML
    void showNovoLivro(ActionEvent event) {

        Stage stage = new Stage();
        Scene scene = new Scene(App.loadFXML("controller/CadLivro.fxml"), 800, 600);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        CadLivroController controller = (CadLivroController) App.getController();
        controller.setListLivroController(this);

        stage.showAndWait();

    }

}
