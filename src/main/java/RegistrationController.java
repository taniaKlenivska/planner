import alert.ProgramMessage;
import hibernate.HibernateService;
import hibernate.HibernateSessionConfig;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import validate.FormValidate;
import validate.User;
import validate.UserDataValidate;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class RegistrationController implements Initializable {

    @FXML
    private AnchorPane content;
    @FXML
    private TextField fieldNickname;
    @FXML
    private TextField fieldLogin;
    @FXML
    private PasswordField fieldPassword;
    @FXML
    private TextField fieldName;
    @FXML
    private Label lblLogin;

    //Hibernate session
    private Session session;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (HibernateSessionConfig.getSessionFactory() != null) {
            session = HibernateSessionConfig.getSessionFactory().openSession();
        } else {
            System.out.println("No connection to database");

        }
        lblLogin.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    AnchorPane registration = FXMLLoader.load(getClass().getClassLoader().getResource("ui/login.fxml"));
                    content.getChildren().clear();
                    content.getChildren().add(registration);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private User createUserModel() {
        return new User(0, fieldName.getText().trim(), fieldNickname.getText().trim(), fieldLogin.getText().trim(), fieldPassword.getText().trim());
    }

    @FXML
    private void btnSingUp(MouseEvent event) {
        try {
            HibernateService service = HibernateService.getInstance();

            ArrayList<TextField> aListFields = new ArrayList<TextField>() {{
                add(fieldNickname);
                add(fieldLogin);
                add(fieldPassword);
            }};

            String nickname = fieldNickname.getText().trim();
            String email = fieldLogin.getText().trim();
            String password = fieldPassword.getText().trim();

            if (!FormValidate.isEmptyFields(aListFields)) {
                if (FormValidate.isEmailValidate(email)) {
                    if (!UserDataValidate.isNicknameExists(session, nickname)) {
                        if (!UserDataValidate.isEmailExists(session, email)) {
                                if (FormValidate.checkPasswordLength(password)) {
                                    if (!UserDataValidate.isPasswordExists(session, password)) {
                                        User userModel = createUserModel();
                                        String result = service.save(session, userModel) ? "validate.User was created"
                                                : "Something went wrong. Please try again later .. ";
                                        ProgramMessage.getInstance()
                                                .setAlertType(Alert.AlertType.INFORMATION)
                                                .simpleAlert("validate.User registration", null, result);
                                    } else new ContextMenu(new MenuItem("Password already exists")).show(fieldPassword, Side.RIGHT, 10, 0);
                                } else new ContextMenu(new MenuItem("At least 6 characters")).show(fieldPassword, Side.RIGHT, 10, 0);
                            } else new ContextMenu(new MenuItem("Email already exists")).show(fieldLogin, Side.RIGHT, 10, 0);
                    } else new ContextMenu(new MenuItem("Nickname already exists")).show(fieldNickname, Side.RIGHT, 10, 0);
                } else new ContextMenu(new MenuItem("Invalid email")).show(fieldLogin, Side.RIGHT, 10, 0);
            }

        } catch (HibernateException e) {
            e.printStackTrace();
            System.err.println("Something was wrong");
        }
    }



}
