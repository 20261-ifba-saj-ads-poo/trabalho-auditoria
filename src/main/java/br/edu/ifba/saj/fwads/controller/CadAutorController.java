package br.edu.ifba.saj.fwads.controller;

import br.edu.ifba.saj.fwads.exception.RegraDeNegocioException;
import br.edu.ifba.saj.fwads.model.Autor;
import br.edu.ifba.saj.fwads.service.AutorService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class CadAutorController {
    @FXML
    private TextField txNome;
    @FXML
    private TextField txEmail;
    @FXML
    private TextField txCPF;
    private Autor autorSelecionado;

    private MasterController masterController;
    private ListAutorController listAutorController;

    private AutorService serviceAutor = new AutorService();

    public void setMasterController(MasterController masterController) {
        this.masterController = masterController;
    }

    public void setListAutorController(ListAutorController listAutorController) {
        this.listAutorController = listAutorController;
    }

    @FXML
    private void salvarAutor() {

        if (autorSelecionado != null) {

            autorSelecionado.setNome(txNome.getText());
            autorSelecionado.setCPF(txCPF.getText());
            autorSelecionado.setEmail(txEmail.getText());
            try {
                serviceAutor.atualizar(autorSelecionado, masterController.getUsuarioLogado());
                new Alert(AlertType.INFORMATION, "Autor:" + autorSelecionado.getNome() + " atualizado com sucesso")
                        .showAndWait();
            } catch (RegraDeNegocioException e) {
                new Alert(AlertType.ERROR, "Erro ao atualizar autor").showAndWait();
            }
        } else {
            try {
                Autor novoAutor = new Autor(txNome.getText(),
                        txEmail.getText(),
                        txCPF.getText());
                serviceAutor.salvar(novoAutor, masterController.getUsuarioLogado());
                new Alert(AlertType.INFORMATION,
                        "Autor:" + novoAutor.getNome() + " cadastrado com sucesso").showAndWait();
            } catch (RegraDeNegocioException e) {
                new Alert(AlertType.ERROR, "Erro ao cadastrar autor").showAndWait();
            }
        }
        limparTela();
        if (listAutorController != null) {
            listAutorController.loadAutorList();
        }

    }

    @FXML
    private void limparTela() {
        txNome.setText("");
        txEmail.setText("");
        txCPF.setText("");
    }

    public Autor getAutorSelecionado() {
        return autorSelecionado;
    }

    public void setAutorSelecionado(Autor autorSelecionado) {
        this.autorSelecionado = autorSelecionado;
        txNome.setText(autorSelecionado.getNome());
        txCPF.setText(autorSelecionado.getCPF());
        txEmail.setText(autorSelecionado.getEmail());
    }

    public MasterController getMasterController() {
        return masterController;
    }

    public ListAutorController getListAutorController() {
        return listAutorController;
    }

}
