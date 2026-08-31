package br.edu.ifba.saj.fwads;

import java.io.IOException;

import br.edu.ifba.saj.fwads.controller.MasterController;
import br.edu.ifba.saj.fwads.model.Usuario;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static FXMLLoader loader;

    @Override
    public void start(Stage stage) throws IOException {
        loader = new FXMLLoader(App.class.getResource("controller/Login.fxml"));
        scene = new Scene(loader.load(), 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    public static Object getController() {
        return loader.getController();
    }

    public static void setRoot(String fxml) {
        scene.setRoot(loadFXML(fxml));
    }

    public static void setMasterRoot(Usuario usuarioLogado) {

        try {
            loader = new FXMLLoader(App.class.getResource("controller/Master.fxml"));
            Parent parent = loader.load();
            MasterController controller = loader.getController();
            if (controller != null) {
                controller.setUsuarioLogado(usuarioLogado);
                parent.getProperties().put("controller", loader.getController());
            }
            scene.setRoot(parent);
        } catch (Exception e) {
            new Alert(AlertType.ERROR, "Erro ao carregar o arquivo controller/Master.fxml").show();
            e.printStackTrace();
        }
    }

    public static Parent loadFXML(String fxml) {
        try {
            loader = new FXMLLoader(App.class.getResource(fxml));
            Parent parent = loader.load();
            Object controller = loader.getController();
            if (controller != null) {
                parent.getProperties().put("controller", loader.getController());
            }
            return parent;
        } catch (Exception e) {
            new Alert(AlertType.ERROR, "Erro ao carregar o arquivo " + fxml).show();
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        //UsuarioService validarUsuario = new UsuarioService();
        //if (validarUsuario.count() == 0) {
        //    validarUsuario.salvar(new Usuario("admin", "admin", "seila@algumacoisa.com"));
        //}
        launch();
    }

}