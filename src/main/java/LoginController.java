import email.LibraryAssistantUtil;
import hibernate.HibernateSessionConfig;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.hibernate.Session;
import validate.FormValidate;
import validate.User;
import org.hibernate.query.Query;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private AnchorPane content;
    @FXML
    private TextField fieldLogin;
    @FXML
    private PasswordField fieldPassword;
    @FXML
    private Label lblStatus, lblSignUp, lblForgotPassword;

    //Hibernate session
    private Session session;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (HibernateSessionConfig.getSessionFactory() != null) {
            session = HibernateSessionConfig.getSessionFactory().openSession();
            lblStatus.setVisible(false);
        } else {
            System.out.println("No connection to database");
            lblStatus.setVisible(true);
        }
        lblSignUp.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    AnchorPane registration = FXMLLoader.load(getClass().getClassLoader().getResource("ui/registration.fxml"));
                    content.getChildren().clear();
                    content.getChildren().add(registration);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        lblForgotPassword.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    AnchorPane registration = FXMLLoader.load(getClass().getClassLoader().getResource("ui/reset_password.fxml"));
                    content.getChildren().clear();
                    content.getChildren().add(registration);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @FXML
    public void btnSingIn(MouseEvent mouseEvent) {
        ArrayList<TextField> aListFields = new ArrayList<TextField>() {{
            add(fieldLogin);
            add(fieldPassword);
        }};

        //Field data
        String login = fieldLogin.getText().trim();
        String password = fieldPassword.getText().trim();

        //Query for get user
        Query query;
        String stringQuery;
        if (!FormValidate.isEmptyFields(aListFields)) {
            try {
            stringQuery = "from User where email = :login and password = :pass";

            query = session.createQuery(stringQuery);

            query.setParameter("login", login);
            query.setParameter("pass", password);
            List<User> list = query.list();


            System.out.println("Size: " + list.size());


            if (!list.isEmpty()) {
                System.out.println("validate.User was found");
                System.out.println(list.remove(0).getEmail());
                openMainMenu(login);
            }else{
                lblStatus.setVisible(true);
                lblStatus.setText("validate.User not found");
            }
            } catch (Exception e) {
                lblStatus.setVisible(true);
                lblStatus.setText("Server do not respond");
                e.printStackTrace();
            }

        }
    }

    private void openMainMenu(String nickname) {
        lblStatus.setVisible(false);

        LibraryAssistantUtil window = new LibraryAssistantUtil.Builder()
                .location(getClass().getClassLoader().getResource("ui/main_menu.fxml"))
                .title("Menu")
                .parentStage(null)
                .build();

        CurrentUser.getInstance().setCurrentUser(nickname);

        window.loadWindow();

        Stage current = (Stage) content.getScene().getWindow();
        current.close();
    }

}
