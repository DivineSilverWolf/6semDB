package nsu.fit.ru.database_sports_architecture.controllers.special_assignments;

import javafx.scene.control.TextField;

public class GetterTextOrNullTextField {
    public static String getText(TextField textField){
        return textField.getText().equals("") || textField.getText().equals("N/A") ? null : textField.getText();
    }
}
