import email.LibraryAssistantUtil;
import hibernate.HibernateSessionConfig;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXML;
import org.hibernate.Session;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {
    @FXML
    private AnchorPane rootPane;

    private String userNickname;
    private Session session;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (HibernateSessionConfig.getSessionFactory() != null) {
            session = HibernateSessionConfig.getSessionFactory().openSession();
            Platform.runLater(() -> {
                userNickname = CurrentUser.getInstance().getCurrentUser();

            });
        } else {
            System.out.println("No connection to database");
        }
    }

    @FXML
    private void menuSettings(MouseEvent event) {
        try {
            Stage current = (Stage) rootPane.getScene().getWindow();

            LibraryAssistantUtil window = new LibraryAssistantUtil.Builder()
                    .location(getClass().getClassLoader().getResource("ui/profile.fxml"))
                    .title("Profile")
                    .parentStage(current)
                    .build();

            window.loadWindow();
            window.getStage().setResizable(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void menuExit(MouseEvent event) {
        try {
            LibraryAssistantUtil window = new LibraryAssistantUtil.Builder()
                    .location(getClass().getClassLoader().getResource("ui/login.fxml"))
                    .title("Login")
                    .parentStage(null)
                    .build();

            window.loadWindow();
            window.getStage().setResizable(false);

            Stage current = (Stage)((Node) event.getSource()).getScene().getWindow();
            current.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
