package validate;

import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class FormValidate {
    /**
     * Fields check if them aren't empty
     *
     * @param list
     * @return
     */
    public static boolean isEmptyFields(ArrayList<TextField> list) {
        for (TextField field : list) {
            if (field.getText().isEmpty()) {
                new ContextMenu(new MenuItem("Required")).show(field, Side.RIGHT, 10, 0);
                return true;
            }
        }
        return false;
    }


    /**
     * Checking user mail for compliance with the standard
     *
     * @param email
     * @return
     */
    public static boolean isEmailValidate(String email) {
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * Checking the password length
     *
     * @param password
     * @return
     */
    public static boolean checkPasswordLength(String password) {
        return password.length() >= 6 && password.length() < 32;
    }
}
