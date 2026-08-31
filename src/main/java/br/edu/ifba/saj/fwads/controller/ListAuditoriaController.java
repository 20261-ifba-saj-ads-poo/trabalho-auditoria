package br.edu.ifba.saj.fwads.controller;

import br.edu.ifba.saj.fwads.App;
import br.edu.ifba.saj.fwads.model.AbstractModel;
import br.edu.ifba.saj.fwads.model.Autor;
import br.edu.ifba.saj.fwads.model.Livro;
import br.edu.ifba.saj.fwads.service.GenericServiceImpl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
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

public class ListAuditoriaController {

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    @FXML
    private TableColumn<AbstractModel, String> clnEntidade;

    @FXML
    private TableColumn<AbstractModel, String> clnCadastro;
    @FXML
    private TableColumn<AbstractModel, String> clnCadastroLocalDate;

     @FXML
    private TableColumn<AbstractModel, String> clnAtualizo;
    @FXML
    private TableColumn<AbstractModel, String> clnAtualizoLocalDate;

    

    @FXML
    private TableView<AbstractModel> tblAuditoria;

    @FXML
    public void initialize() {
        clnEntidade.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getClass().getSimpleName()));
        clnCadastro.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getCreatedBy().getLogin()));
        clnAtualizo.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getUpdatedBy() != null ? cellData.getValue().getUpdatedBy().getLogin():""));
        clnCadastroLocalDate.setCellValueFactory(
                cellData -> new SimpleStringProperty(formatter.format(cellData.getValue().getCreatedAt())));
        clnAtualizoLocalDate.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getUpdatedAt() != null ? formatter.format(cellData.getValue().getUpdatedAt()):""));
        loadLivroList();
    }

    public void loadLivroList() {
        List<AbstractModel> list = new ArrayList<>();
        list.addAll(new GenericServiceImpl<Livro, UUID>(Livro.class, UUID.class).buscarTodos());
        list.addAll(new GenericServiceImpl<Autor, UUID>(Autor.class, UUID.class).buscarTodos());
        tblAuditoria.setItems(FXCollections
                .observableList(list));
    }

}
