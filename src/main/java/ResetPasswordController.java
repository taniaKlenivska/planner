import email.Config;
import hibernate.HibernateService;
import hibernate.HibernateSessionConfig;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.hibernate.query.Query;
import validate.User;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ResetPasswordController implements Initializable {

    @FXML
    private TextField fieldPass1;
    @FXML
    private TextField fieldPass2;
    @FXML
    private Button btnReset;

    private org.hibernate.Session session;

    private String email;

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnReset.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                session = HibernateSessionConfig.getSessionFactory().openSession();

                String pass1 = fieldPass1.getText().trim();
                String pass2 = fieldPass2.getText().trim();
                if (pass1.equals(pass2)) {

                    //Query for get user
                    Query query;
                    String stringQuery;

                    stringQuery = "from User where email = :login ";

                    query = session.createQuery(stringQuery);

                    System.out.println(Config.email);

                    query.setParameter("login", email);
                    List<User> list = query.list();

                    System.out.println(list.size());

                    if (!list.isEmpty()) {
                        User u = list.remove(0);

                        System.out.println("Id: " + u.getId());

                        System.out.println(pass2);
                        u.setPassword(pass2);


                        if (fieldPass1.getText().length() > 6) {
                            HibernateService.getInstance().update(session, u);
                        }
                    }
                }
            }
        });
    }

}
