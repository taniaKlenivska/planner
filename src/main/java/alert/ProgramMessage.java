package alert;

import javafx.scene.control.Alert;

public class ProgramMessage {

    private static ProgramMessage instance = new ProgramMessage();

    public static ProgramMessage getInstance() {
        return instance;
    }

    private ProgramMessage() {}

    private Alert.AlertType alertType = Alert.AlertType.WARNING;
    public Alert.AlertType getAlertType() {
        return alertType;
    }

    public ProgramMessage setAlertType(Alert.AlertType alertType) {
        this.alertType = alertType;
        return instance;
    }

    public void simpleAlert(String title, String header, String context) {
        Alert alert = new Alert(getAlertType());
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(context);
        alert.show();
    }
}
