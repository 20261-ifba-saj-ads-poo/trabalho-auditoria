package br.edu.ifba.saj.fwads.controller;

import br.edu.ifba.saj.fwads.model.AbstractModel;
import br.edu.ifba.saj.fwads.model.Autor;
import br.edu.ifba.saj.fwads.model.Livro;
import br.edu.ifba.saj.fwads.service.AutorService;
import br.edu.ifba.saj.fwads.service.LivroService;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.beans.property.SimpleStringProperty;

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
        loadAuditoria();
    }

    public void loadAuditoria() {
        List<AbstractModel> list = new ArrayList<>();

        List<Livro> livros  = new LivroService().buscarTodos();
        List<Autor> autores  = new AutorService().buscarTodos();
        
        list.addAll(livros);
        list.addAll(autores);
        
        tblAuditoria.setItems(FXCollections
                .observableList(list));
    }

}
