package br.edu.ifba.saj.fwads.controller;

import java.util.List;

import br.edu.ifba.saj.fwads.exception.RegraDeNegocioException;
import br.edu.ifba.saj.fwads.model.Autor;
import br.edu.ifba.saj.fwads.model.Livro;
import br.edu.ifba.saj.fwads.service.AutorService;
import br.edu.ifba.saj.fwads.service.LivroService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.util.StringConverter;

public class CadLivroController {
    @FXML
    private TextField txTitulo;
    @FXML
    private TextField txSubTitulo;
    @FXML
    private TextField txISBN;
    @FXML
    private ChoiceBox<Autor> slAutor;

    private ListLivroController listLivroController;

    private LivroService serviceLivro = new LivroService();
    private MasterController masterController;

    public MasterController getMasterController() {
        return masterController;
    }

    public void setListLivroController(ListLivroController listLivroController) {
        this.listLivroController = listLivroController;
    }

    @FXML
    void salvarLivro(ActionEvent event) {
        Livro novoLivro = new Livro(txTitulo.getText(),
                txSubTitulo.getText(),
                txISBN.getText(),
                slAutor.getSelectionModel().getSelectedItem());

        try {
            serviceLivro.salvar(novoLivro, masterController.getUsuarioLogado());
            new Alert(AlertType.INFORMATION,
                    "Livro:" + novoLivro.getTitulo() + " cadastrado com sucesso!").showAndWait();
        } catch (RegraDeNegocioException e) {
            new Alert(AlertType.ERROR,
                    "Erro ao cadastrar o livro:" + novoLivro.getTitulo()).showAndWait();
        }

        limparTela();
        if (listLivroController != null) {
            listLivroController.loadLivroList();
        }

    }

    @FXML
    private void initialize() {
        slAutor.setConverter(new StringConverter<Autor>() {
            @Override
            public String toString(Autor obj) {
                if (obj != null) {
                    return obj.getNome() + ":" + obj.getEmail();
                }
                return "";
            }

            @Override
            public Autor fromString(String stringAutor) {
                return buscarAutores()
                        .stream()
                        .filter(autor -> stringAutor.equals(autor.getNome() + ":" + autor.getEmail()))
                        .findAny()
                        .orElse(null);
            }
        });

        carregarListaAutores();
    }

    @FXML
    private void limparTela() {
        txTitulo.setText("");
        txSubTitulo.setText("");
        txISBN.setText("");
        if (slAutor.getSelectionModel() != null && slAutor.getSelectionModel().getSelectedItem() != null) {
            slAutor.getSelectionModel().clearSelection();
        }
        // new Alert(AlertType.INFORMATION,
        // serviceLivro.findAll().toString()).showAndWait();
    }

    private void carregarListaAutores() {
        slAutor.setItems(FXCollections.observableArrayList(buscarAutores()));
    }

    private List<Autor> buscarAutores() {
        return new AutorService().buscarTodos();
    }

    public void setMasterController(MasterController masterController) {
        this.masterController = masterController;
    }

}
