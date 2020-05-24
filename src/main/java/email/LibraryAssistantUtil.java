package email;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;

public class LibraryAssistantUtil {

    private Stage stage;
    private Object controller;
    private URL location;
    private String title;
    private Stage parentStage;
    private Parent parent;

    private LibraryAssistantUtil(Builder builder) {
        this.location = builder.location;
        this.title = builder.title;
        this.parentStage = builder.parentStage;
    }

    public void init(){
        try {
            FXMLLoader loader = new FXMLLoader(location);
            parent = loader.load();
            controller = loader.getController();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void loadWindow() {
        if (parentStage != null) {
            stage = new Stage();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(parentStage);
        } else {
            stage = new Stage(StageStyle.UNIFIED);
        }
        stage.setTitle(title);
        stage.setResizable(false);
        stage.setScene(new Scene(parent));
        stage.show();
    }

    public Stage getStage() {
        return stage;
    }

    public Object getController() {
        return controller;
    }

    public Parent getParent() {
        return parent;
    }

    public static class Builder {
        private URL location;
        private String title;
        private Stage parentStage;

        public Builder location(URL location) {
            this.location = location;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder parentStage(Stage parentStage) {
            this.parentStage = parentStage;
            return this;
        }

        public LibraryAssistantUtil build(){
            LibraryAssistantUtil util = new LibraryAssistantUtil(this);
            util.init();
            return util;
        }
    }
}
