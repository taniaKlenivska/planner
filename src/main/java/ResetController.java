import email.GenerateCode;
import email.LibraryAssistantUtil;
import email.SendEmail;
import hibernate.HibernateSessionConfig;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import validate.FormValidate;

import javax.swing.text.html.ImageView;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ResetController implements Initializable {

    @FXML
    private AnchorPane content;
    @FXML
    private TextField fieldLogin;
    @FXML
    private Button btnReset, btnBack;
    @FXML
    private TextField fieldCode;
    @FXML
    private Label lblCode;



    private org.hibernate.Session session;

    String primaryCode;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        session = HibernateSessionConfig.getSessionFactory().openSession();
        primaryCode = GenerateCode.generate(5);
        btnReset.setOnMouseClicked(s1);

        btnBack.setOnMouseClicked((event -> signInUser()));
    }
    
    

    private EventHandler<MouseEvent> s1 = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            ArrayList<TextField> aListFields = new ArrayList<TextField>() {{
                add(fieldLogin);
            }};

            String email = fieldLogin.getText().trim();

            if(!FormValidate.isEmptyFields(aListFields)){
                if (FormValidate.isEmailValidate(email)) {
                    String text = "<h1>Code for resetting password: " + primaryCode + "</h1>";
                    boolean isSend = SendEmail.getInstance().send(email, "Welcome from", text);
                    System.out.println("Sending was " + (isSend ? "successfully" : "failed"));
                    lblCode.setVisible(true);
                    fieldCode.setVisible(true);
                    btnReset.setOnMouseClicked(s2);
                }else new ContextMenu(new MenuItem("Invalid email")).show(fieldLogin, Side.RIGHT, 10, 0);
            }
        }
    };

    private EventHandler<MouseEvent> s2 = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            String code = fieldCode.getText().trim();
            String email = fieldLogin.getText().trim();

            if (code.equals(primaryCode)) {

                LibraryAssistantUtil window = new LibraryAssistantUtil.Builder()
                        .location(getClass().getClassLoader().getResource("ui/reset_password_complete.fxml"))
                        .title("")
                        .parentStage(null)
                        .build();

                ResetPasswordController controller = (ResetPasswordController) window.getController();
                System.out.println(email);
                controller.setEmail(email);

                window.loadWindow();

            } else {
                System.out.println("Wrong code");
            }
        }
    };



    private void signInUser() {
        try {
            AnchorPane login = FXMLLoader.load(getClass().getClassLoader().getResource("ui/login.fxml"));
            content.getChildren().clear();
            content.getChildren().addAll(login);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
