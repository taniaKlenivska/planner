import alert.ProgramMessage;
import hibernate.HibernateService;
import hibernate.HibernateSessionConfig;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.hibernate.Session;
import validate.FormValidate;
import validate.User;
import validate.UserDataValidate;
import validate.UserInformation;

import java.util.Arrays;


public class ProfileController implements Initializable {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private CheckBox boxChangePassword;
    @FXML
    private TextField fieldName;
    @FXML
    private TextField fieldNickname;
    @FXML
    private TextField fieldLogin;
    @FXML
    private PasswordField fieldCurrentPassword;
    @FXML
    private PasswordField fieldNewPassword;
    @FXML
    private Button btnSave;

    private String currentUser;
    private Session session;
    private User userModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (HibernateSessionConfig.getSessionFactory() != null) {
            session = HibernateSessionConfig.getSessionFactory().openSession();
        } else {
            System.out.println("No connection to database");
        }

        btnSave.setDisable(true);
        Platform.runLater(() -> {
            currentUser = CurrentUser.getInstance().getCurrentUser();
            userModel = UserInformation.getInstance().getInformationByNickname(session, currentUser);
            loadUserData(userModel);
            btnSave.setDisable(false);
            System.out.println("Current user: " + CurrentUser.getInstance().getCurrentUser());
        });

        boxChangePassword.setSelected(false);
        fieldCurrentPassword.setDisable(true);
        fieldNewPassword.setDisable(true);

        boxChangePassword.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                fieldCurrentPassword.setDisable(!boxChangePassword.isSelected());
                fieldNewPassword.setDisable(!boxChangePassword.isSelected());
            }
        });
    }

    private void loadUserData(User userModel) {
        fieldName.setText(userModel.getName());
        fieldNickname.setText(userModel.getNickname());
        fieldLogin.setText(userModel.getEmail());
    }

    @FXML
    private void btnSave(MouseEvent event) {
        if (boxChangePassword.isSelected()) {
            boolean isEmptyField = FormValidate.isEmptyFields(new ArrayList<TextField>() {{
                addAll(Arrays.asList(fieldLogin, fieldName, fieldNickname, fieldCurrentPassword, fieldNewPassword));
            }});

            if (!isEmptyField) {
                System.out.println("All fields are fine");
                System.out.println(boxChangePassword.isSelected());

                String currentPassword = fieldCurrentPassword.getText().trim();
                if (currentPassword.equals(userModel.getPassword())) {
                    prepareData(true);
                } else new ContextMenu(new MenuItem("Doesn't match")).show(fieldCurrentPassword, Side.RIGHT, 10, 0);
            }
        } else {
            boolean isEmptyField = FormValidate.isEmptyFields(new ArrayList<TextField>() {{
                addAll(Arrays.asList(fieldLogin, fieldName, fieldNickname));
            }});
            if (!isEmptyField) {
                prepareData(false);
            }
        }
    }

    private void prepareData(boolean savePassword) {
        String name = fieldName.getText().trim();
        String email = fieldLogin.getText().trim();
        String nickname = fieldNickname.getText().trim();
        String password = fieldNewPassword.getText().trim();

        if (isDataFine(email, nickname, password, savePassword)) {

            if (!nickname.equals(userModel.getNickname())){
                System.out.println("Nickname was changed");
                CurrentUser.getInstance().setCurrentUser(nickname);
            }

            userModel.setNickname(nickname);
            userModel.setName(name);
            userModel.setEmail(email);

            if (savePassword){
                userModel.setPassword(password);
            }

            saveData(userModel);
        }
    }

    private boolean isDataFine(String email, String nickname, String password, boolean isPassword) {
        if (!nickname.equals(userModel.getNickname()) && UserDataValidate.isNicknameExists(session, nickname)) {
            new ContextMenu(new MenuItem("Nickname already in use")).show(fieldNickname, Side.RIGHT, 10, 0);
            return false;
        }

        if (!email.equals(userModel.getEmail()) && UserDataValidate.isEmailExists(session, email)) {
            new ContextMenu(new MenuItem("Email already in use")).show(fieldLogin, Side.RIGHT, 10, 0);
            return false;
        }

        if (!FormValidate.isEmailValidate(email)) {
            new ContextMenu(new MenuItem("Please, enter valid email")).show(fieldLogin, Side.RIGHT, 10, 0);
            return false;
        }

        if (isPassword) {
            if (!FormValidate.checkPasswordLength(password)) {
                new ContextMenu(new MenuItem("Minimum length for password is 6")).show(fieldNewPassword, Side.RIGHT, 10, 0);
                return false;
            }
        }

        return true;
    }

    private void saveData(User model) {
        String result = HibernateService
                .getInstance()
                .update(session, model) ? "Successful" : "Failed to change data";
        ProgramMessage.getInstance().setAlertType(Alert.AlertType.INFORMATION)
                .simpleAlert("Data change", null, result);

        ((Stage) rootPane.getScene().getWindow()).close();
    }



}
