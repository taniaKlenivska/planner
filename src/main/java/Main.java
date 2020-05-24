import hibernate.HibernateSessionConfig;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {


    public static void main(String[] args){ launch(args); }

    @Override
    public void start(Stage stage) throws Exception {
        HibernateSessionConfig.buildSessionFactory();
      //  Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("ui/login.fxml"));
       Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("ui/main_menu.fxml"));
        Scene scene = new Scene(root);
        stage.getIcons().add(new Image("img/icon.png"));
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.initStyle(StageStyle.UNIFIED);
        stage.setResizable(false);
        stage.show();

    }
}
